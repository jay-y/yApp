package com.dream.example.ui.activity.base;

import com.dream.example.presenter.base.AppBaseActivityPresenter;

/**
 * Description: 应用Activity基类. <br>
 * Date: 2016/3/16 14:51 <br>
 * Author: ysj
 */
public abstract class AppBaseSwipeRefreshActivity<P extends AppBaseActivityPresenter> extends AppBaseAppCompatActivity<P> {

//    @ViewInject(R.id.swipe_refresh_layout)
//    protected SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * the method of get data
     */
    public abstract void onRefreshStarted();

    /**
     * check data status
     *
     * @return return true indicate it should load data really else indicate don't refresh
     */
    public boolean prepareRefresh() {
        return true;
    }

//    @Override
//    public void hideRefresh() {
//        if (null == mSwipeRefreshLayout) return;
//        // 防止刷新消失太快，让子弹飞一会儿. do not use lambda!!
//        mSwipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                if (mSwipeRefreshLayout != null) {
//                    mSwipeRefreshLayout.setRefreshing(false);
//                }
//            }
//        }, 1000);
//    }
//
//    @Override
//    public void showRefresh() {
//        if (null == mSwipeRefreshLayout) return;
//        mSwipeRefreshLayout.setRefreshing(true);
//    }
//
//    @Override
//    public void loadDataFinish() {
//        hideRefresh();
//    }
//
//    @Override
//    public void onDestroy() {
//        mSwipeRefreshLayout = null;
//        super.onDestroy();
//    }

//    @Override
//    public void showEmpty() {
//        if (null == mSwipeRefreshLayout) return;
//        Snackbar.make(mSwipeRefreshLayout, R.string.data_null, Snackbar.LENGTH_SHORT)
//                .show();
//    }
//
//    @Override
//    public void showMsg(String msg) {
//        if (null == mSwipeRefreshLayout) return;
//        Snackbar.make(mSwipeRefreshLayout, msg, Snackbar.LENGTH_SHORT)
//                .show();
//    }
//
//    @Override
//    public void showError(Throwable throwable) {
//        Log.e(throwable.getMessage(), throwable);
//        if (null == mSwipeRefreshLayout) return;
//        Snackbar.make(mSwipeRefreshLayout, throwable.getMessage(), Snackbar.LENGTH_SHORT)
//                .show();
//    }

//    /**
//     * check refresh layout is refreshing
//     * @return if the refresh layout is refreshing return true else return false
//     */
//    @CheckResult
//    public boolean isRefreshing(){
//        if (null == mSwipeRefreshLayout) return false;
//        return mSwipeRefreshLayout.isRefreshing();
//    }

//    private void initSwipeLayout(){
//        if (null == mSwipeRefreshLayout) mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
//        mSwipeRefreshLayout.setColorSchemeResources(R.color.green,R.color.blue,R.color.red);
//        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                if (prepareRefresh()) {
//                    onRefreshStarted();
//                } else {
//                    //产生一个加载数据的假象
//                    hideRefresh();
//                }
//            }
//        });
//    }
}
