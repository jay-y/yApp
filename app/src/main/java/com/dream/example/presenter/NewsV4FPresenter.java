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
import com.dream.example.ui.adapter.NewsV4FAdapter;
import com.dream.example.utils.IntentUtil;
import com.dream.example.utils.ResponseFilter;
import com.dream.example.view.IDefaultView;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.y;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: NewsV4FPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class NewsV4FPresenter extends AppSwipeRefreshV4FragmentPresenter implements IDefaultView, NewsV4FAdapter.IClickItem {
    @ViewInject(R.id.news_list)
    private RecyclerView mItemContent;
    private NewsV4FAdapter mAdapter;

    /**
     * the count of the size of one request
     */
    // the count of the size of one request
    private static final int PAGE_SIZE = 10;
    public int mCurrentPage = 0;
    public boolean mHasMoreData = true;
    public List<Map<String, Object>> mDataList = null;

    public String mId = "T1348654060988";

    public DataSupports getDataSupports() {
        return HttpFactory.getDataSupports(AppConsts.ServerConfig.NEWS_HOST, DataSupports.class);
    }

    @Override
    public void onInit() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mItemContent.setLayoutManager(layoutManager);
        mAdapter = new NewsV4FAdapter(getContext());
        mAdapter.setIClickItem(this);
        mItemContent.setAdapter(mAdapter);
        mItemContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 2;
                if (!isRefreshing()
                        && isBottom
                        && mHasMoreData) {
                    showRefresh();
                    loadMoreData();
                }
            }
        });
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                showRefresh();
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
        IntentUtil.gotoImageDetailActivity(getContext(), (String) entity.get("imgsrc"));
    }

    @Override
    public void showEmpty() {
        mAdapter.clear();
        showMsg(getContext().getString(R.string.data_null));
    }

    @Override
    public boolean prepareRefresh() {
        if (canRefresh()) {
            reset();
            if (!isRefreshing()) {
                showRefresh();
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
    public Observable<List<Map<String, Object>>> getObservable() {
        return getDataSupports().getNews(mId, mCurrentPage, PAGE_SIZE)
                .map(new Func1<Map<String, Object>, List<Map<String, Object>>>() {
                    @Override
                    public List<Map<String, Object>> call(Map<String, Object> result) {
                        return (List<Map<String, Object>>) result.get(mId);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
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
        request(new Subscriber<List<Map<String, Object>>>() {
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
                    mHasMoreData = false;
                    hasNoMoreData();
                } else if (datas.size() == PAGE_SIZE) {
                    mCurrentPage++;
                }
                mAdapter.update(mDataList);
            }
        });
    }

    @Override
    public void loadMoreData() {
        request(new Subscriber<List<Map<String, Object>>>() {
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
                    mHasMoreData = false;
                    hasNoMoreData();
                } else if (datas.size() == PAGE_SIZE) {
                    mCurrentPage++;
                }
                mAdapter.update(mDataList);
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
