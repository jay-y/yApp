package com.dream.example.presenter;

import android.support.annotation.CheckResult;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dream.example.R;
import com.dream.example.data.GankData;
import com.dream.example.data.entity.Gank;
import com.dream.example.data.support.AppConsts;
import com.dream.example.data.support.DataSupports;
import com.dream.example.data.support.HttpFactory;
import com.dream.example.presenter.base.AppSwipeRefreshV4FragmentPresenter;
import com.dream.example.ui.adapter.GankV4FAdapter;
import com.dream.example.ui.adapter.GirlV4FAdapter;
import com.dream.example.utils.IntentUtil;
import com.dream.example.view.IDefaultView;

import org.yapp.core.ui.adapter.BaseRecyclerViewAdapter;
import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Log;
import org.yapp.y;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Description: GankPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class GankPresenter extends AppSwipeRefreshV4FragmentPresenter implements IDefaultView {

    @ViewInject(R.id.gank_list)
    private RecyclerView mItemContent;
    private BaseRecyclerViewAdapter mAdapter;

    /**
     * the count of the size of one request
     */
    private static final int PAGE_SIZE = 10;
    public int mCurrentPage = 1;
    public boolean mHasMoreData = true;
    public List<Gank> mDataList = null;

    private int resId = -1;
    private String type = AppConsts.ServerConfig.PARAM_TYPE_GIRL;

    public DataSupports getDataSupports() {
        return HttpFactory.getDataSupports(AppConsts.ServerConfig.GANK_HOST, DataSupports.class);
    }

    @Override
    public void onInit() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContent());
        mItemContent.setLayoutManager(layoutManager);
        resId = getFragment().getArguments().getInt(AppConsts._ID);
        mAdapter = new GankV4FAdapter(getContent());
        switch (resId) {
            case R.string.fragment_app:
                type = AppConsts.ServerConfig.PARAM_TYPE_APP;
                break;
            case R.string.fragment_and:
                type = AppConsts.ServerConfig.PARAM_TYPE_AND;
                break;
            case R.string.fragment_ios:
                type = AppConsts.ServerConfig.PARAM_TYPE_IOS;
                break;
            case R.string.fragment_boon:
                mAdapter = new GirlV4FAdapter(getContent());
                type = AppConsts.ServerConfig.PARAM_TYPE_GIRL;
                break;
        }
        if (mAdapter instanceof GankV4FAdapter) {
            ((GankV4FAdapter) mAdapter).setIClickItem(new GankV4FAdapter.IClickItem() {
                @Override
                public void onClickItem(Gank entity, View view) {
                    IntentUtil.gotoWebActivity(getContent(), entity.getUrl(), entity.getDesc());
                }
            });
        } else if (mAdapter instanceof GirlV4FAdapter) {
            ((GirlV4FAdapter) mAdapter).setIClickItem(new GirlV4FAdapter.IClickItem() {
                @Override
                public void onClickItem(Gank entity, View view) {
                    IntentUtil.gotoImageDetailActivity(getContent(),entity.getUrl());
                }
            });
        }
        mItemContent.setAdapter(mAdapter);
//        mItemContent.addItemDecoration(new DividerItemDecoration(getContent(), DividerItemDecoration.VERTICAL_LIST));
        mItemContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                boolean isBottom =
                        layoutManager.findLastCompletelyVisibleItemPosition() >= mAdapter.getItemCount() - 4;
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
    public void showEmpty() {
        Snackbar.make(mItemContent, R.string.data_null, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(throwable.getMessage(), throwable);
        Snackbar.make(mItemContent, R.string.data_error, Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public Observable<List<Gank>> getObservable() {
        if (resId == -1) return null;

        return getDataSupports().getGankData(type, mCurrentPage, PAGE_SIZE)
                .map(new Func1<GankData, List<Gank>>() {
                    @Override
                    public List<Gank> call(GankData gankData) {
                        return gankData.getResults();
                    }
                })
                .flatMap(new Func1<List<Gank>, Observable<Gank>>() {
                    @Override
                    public Observable<Gank> call(List<Gank> ganks) {
                        return Observable.from(ganks);
                    }
                })
                .toSortedList(new Func2<Gank, Gank, Integer>() {
                    @Override
                    public Integer call(Gank gank, Gank gank2) {
                        return gank2.getPublishedAt().compareTo(gank.getPublishedAt());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void loadData() {
        if (null != mDataList && !mDataList.isEmpty() || false == mHasMoreData) {
            if (null != mDataList)
                mAdapter.update(mDataList);
            loadDataFinish();
            return;
        }
        request(new Subscriber<List<Gank>>() {
            @Override
            public void onCompleted() {
                loadDataFinish();
            }

            @Override
            public void onError(Throwable e) {
                showError(e);
                loadDataFinish();
            }

            @Override
            public void onNext(List<Gank> datas) {
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
        request(new Subscriber<List<Gank>>() {
            @Override
            public void onCompleted() {
                loadDataFinish();
            }

            @Override
            public void onError(Throwable e) {
                showError(e);
                loadDataFinish();
            }

            @Override
            public void onNext(List<Gank> datas) {
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
        mHasMoreData = false;
        Snackbar.make(mItemContent, R.string.data_no_more, Snackbar.LENGTH_SHORT)
                .setAction(R.string.action_to_top, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        (mItemContent.getLayoutManager()).smoothScrollToPosition(mItemContent, null, 0);
                    }
                })
                .show();
    }

    @Override
    @CheckResult
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

    private void reset() {
        mCurrentPage = 1;
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