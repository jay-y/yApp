package com.dream.example.presenter.base;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.v4.widget.SwipeRefreshLayout;

import com.dream.example.R;
import com.dream.example.ui.fragment.base.AppBaseFragment;
import com.dream.example.view.ISwipeRefreshView;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Log;
import org.yapp.y;

import rx.Subscriber;
import rx.Subscription;

/**
 * Description: AppSwipeRefreshFragmentPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public abstract class AppSwipeRefreshFragmentPresenter extends AppBaseFragmentPresenter implements ISwipeRefreshView {
    @ViewInject(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    // control request
    protected Subscription mRequest;

    /**
     * 构建
     *
     * @param context activity context
     */
    @Override
    public void onBuild(Context context, AppBaseFragment fragment) {
        super.onBuild(context,fragment);
        initSwipeLayout();
    }

    @Override
    public void onDestroy() {
        mSwipeRefreshLayout = null;
        super.onDestroy();
    }

    @Override
    public void request(Subscriber subscriber) {
        try {
            mRequest = getObservable().subscribe(subscriber);
        }catch (NullPointerException e){
            Log.w("Observable not initialized.");
        }
    }

    @Override
    public void cancelRequest(){
        if(null != mRequest && !mRequest.isUnsubscribed()){
            mRequest.unsubscribe();
        }
    }

    /**
     * check data status
     *
     * @return return true indicate it should load data really else indicate don't refresh
     */
    public boolean prepareRefresh() {
        return true;
    }

    @Override
    public void hideRefresh() {
        // 防止刷新消失太快，让子弹飞一会儿.
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                closeLoading();
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            }
        }, 1000);
    }

    @Override
    public void showRefresh() {
        if (null == mSwipeRefreshLayout) return;
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void loadDataFinish() {
        hideRefresh();
    }

    /**
     * check refresh layout is refreshing
     *
     * @return if the refresh layout is refreshing return true else return false
     */
    @CheckResult
    public boolean isRefreshing() {
        if (null == mSwipeRefreshLayout) return false;
        return mSwipeRefreshLayout.isRefreshing() || isLoading();
    }

    private void initSwipeLayout() {
        if (null == mSwipeRefreshLayout)
            mSwipeRefreshLayout = (SwipeRefreshLayout) getContentView().findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.green, R.color.blue, R.color.red);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (prepareRefresh()) {
                    onRefreshStarted();
                } else {
                    //产生一个加载数据的假象
                    hideRefresh();
                }
            }
        });
    }
}
