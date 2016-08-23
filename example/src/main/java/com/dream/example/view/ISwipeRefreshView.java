package com.dream.example.view;

import android.support.annotation.CheckResult;

import rx.Observable;
import rx.Subscriber;

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
     * request for initialization
     *
     * @return
     */
    Observable getObservable();

    void request(Subscriber subscriber);

    void cancelRequest();
}
