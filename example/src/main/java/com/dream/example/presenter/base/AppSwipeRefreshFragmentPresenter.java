package com.dream.example.presenter.base;

import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.v4.widget.SwipeRefreshLayout;

import com.dream.example.App;
import com.dream.example.R;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.ui.fragment.base.AppBaseFragment;
import com.dream.example.view.ISwipeRefreshView;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.y;

/**
 * Description: AppSwipeRefreshV4FragmentPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public abstract class AppSwipeRefreshFragmentPresenter extends
        AppBaseFragmentPresenter<AppBaseAppCompatActivity, AppBaseFragment, App> implements ISwipeRefreshView {
    @ViewInject(R.id.swipe_refresh_layout)
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * 构建
     *
     * @param context activity context
     */
    @Override
    public void onBuild(Context context) {
        super.onBuild(context);
        initSwipeLayout();
    }

    @Override
    public void onDestroy() {
        mSwipeRefreshLayout = null;
        super.onDestroy();
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
        if (null == mSwipeRefreshLayout) return;
        // 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mSwipeRefreshLayout != null) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
                closeLoading();
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
            mSwipeRefreshLayout = (SwipeRefreshLayout) getContent().findViewById(R.id.swipe_refresh_layout);
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
