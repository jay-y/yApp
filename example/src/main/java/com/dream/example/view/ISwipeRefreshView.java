package com.dream.example.view;

import android.support.annotation.CheckResult;

/**
 * Description: ISwipeRefreshView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface ISwipeRefreshView extends IAppBaseView {

    void loadDataFinish();

    void showEmpty();

    /**
     * the method of get data
     */
    void onRefreshStarted();

    /**
     * check data status
     *
     * @return return true indicate it should load data really else indicate don't refresh
     */
    boolean prepareRefresh();

    void showRefresh();

    void hideRefresh();

    /**
     * check refresh layout is refreshing
     *
     * @return if the refresh layout is refreshing return true else return false
     */
    @CheckResult
    boolean isRefreshing();

    /**
     * load data
     */
    void loadData();

    /**
     * load more data to history list(load more)
     */
    void loadMoreData();

    /**
     * no more data for show and this condition is hard to appear,it need you scroll main view long time
     * I think it has no body do it like this ,even though，I deal this condition also, In case someone does it.
     */
    void hasNoMoreData();
}
