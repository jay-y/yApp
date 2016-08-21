package com.dream.example.presenter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.data.support.DataSupports;
import com.dream.example.data.support.HttpFactory;
import com.dream.example.presenter.base.AppSwipeRefreshV4FragmentPresenter;
import com.dream.example.ui.adapter.NewsAdapter;
import com.dream.example.utils.ResponseFilter;
import com.dream.example.view.INewsView;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Log;
import org.yapp.y;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: NewsV4FPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class NewsV4FPresenter extends
        AppSwipeRefreshV4FragmentPresenter implements INewsView, NewsAdapter.IClickItem {
    /**
     * the count of the size of one request
     */
    private static final int PAGE_SIZE = 10;

    @ViewInject(R.id.news_list)
    private RecyclerView mItemContent;
    private NewsAdapter mAdapter;

    public int mCurrentPage = 0;
    public boolean mHasMoreData = true;
    public List<Map<String, Object>> mDataList = null;

    public String mId = "T1348654060988";

    public NewsV4FPresenter() {
    }

    public DataSupports getDataSupports() {
        return HttpFactory.getDataSupports(AppConsts.ServerConfig.NEWS_HOST, DataSupports.class);
    }

    @Override
    public void onInit() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContent());
        mItemContent.setLayoutManager(layoutManager);
        mAdapter = new NewsAdapter(getContent());
        mAdapter.setIClickItem(this);
        mItemContent.setAdapter(mAdapter);
        mItemContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 2;
                Log.d("!isLoading:"+(!isLoading()));
                Log.d("isBottom:"+(isBottom));
                Log.d("mHasMoreData:"+(mHasMoreData));
                if (!isLoading()
                        && isBottom
                        && mHasMoreData) {
                    showLoading();
                    loadMoreData();
                }
            }
        });
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                showLoading();
            }
        }, 568);
        loadData();
    }

    @Override
    public void onClear() {
        mItemContent = null;
        mAdapter = null;
        mDataList = null;
    }

    @Override
    public void onClickItem(Map<String, Object> entity, View view) {

    }

    @Override
    public void showEmpty() {
        mAdapter.clear();
        getContent().getPresenter().showMsg(getContent().getString(R.string.data_null));
    }

    @Override
    public boolean prepareRefresh() {
        if (canRefresh()) {
            reset();
            if (!isRefreshing()) {
                showLoading();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRefreshStarted() {
        loadData();
    }

    @Override
    public void loadData() {
//        if (App.getClient().getStatus() == 0) return;
        if (null != mDataList && !mDataList.isEmpty() || false == mHasMoreData) {
            if (null != mDataList)
                mAdapter.update(mDataList);
            loadDataFinish();
            return;
        }
        getDataSupports().getNews(mId, mCurrentPage, PAGE_SIZE)
                .map(new Func1<Map<String, Object>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(Map<String, Object> result) {
                        return (List<Map<String, Object>>) result.get(mId);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {
                        loadDataFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(ResponseFilter.onError(e));
                        loadDataFinish();
                    }

                    @Override
                    public void onNext(List<Map<String, Object>> datas) {
                        if (mDataList != null) {
                            mDataList.clear();
                        }
                        mDataList = datas;
                        if (null == datas || datas.isEmpty()) {
                            showEmpty();
                            mDataList = new ArrayList();
                            mHasMoreData = false;
                        } else if (datas.size() < PAGE_SIZE) {
                            mAdapter.update(mDataList);
                            mHasMoreData = false;
                            hasNoMoreData();
                        } else if (datas.size() == PAGE_SIZE) {
                            mAdapter.update(mDataList);
                            mCurrentPage++;
                        }
                    }
                });

    }

    @Override
    public void loadMoreData() {
        getDataSupports().getNews(mId, mCurrentPage, PAGE_SIZE)
                .map(new Func1<Map<String, Object>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(Map<String, Object> result) {
                        return (List<Map<String, Object>>) result.get(mId);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Map<String, Object>>>() {
                    @Override
                    public void onCompleted() {
                        loadDataFinish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError(ResponseFilter.onError(e));
                        loadDataFinish();
                    }

                    @Override
                    public void onNext(List<Map<String, Object>> datas) {
                        mDataList.addAll(datas);
                        if (null == datas || datas.isEmpty()) {
                            hasNoMoreData();
                            mHasMoreData = false;
                        } else if (datas.size() < PAGE_SIZE) {
                            mAdapter.update(mDataList);
                            mHasMoreData = false;
                            hasNoMoreData();
                        } else if (datas.size() == PAGE_SIZE) {
                            mAdapter.update(mDataList);
                            mCurrentPage++;
                        }
                    }
                });
    }

    @Override
    public void hasNoMoreData() {
        Snackbar.make(mItemContent, R.string.data_no_more, Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_to_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (mItemContent.getLayoutManager()).smoothScrollToPosition(mItemContent, null, 0);
                    }
                })
                .show();
    }

    private void reset() {
        mCurrentPage = 0;
        mHasMoreData = true;
        if (null != mDataList) {
            mDataList.clear();
            mDataList = null;
        }
    }

    /**
     * @return
     */
    private boolean canRefresh() {
        return true;
    }
}
