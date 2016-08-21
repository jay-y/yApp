package com.dream.example.view;

import java.util.List;

/**
 * Description: ISwipeRefreshView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface ISwipeRefreshView<T> extends IAppBaseView {

    void loadDataFinish();

    void showEmpty();

    void showRefresh();

    void hideRefresh();

    /**
     * load data successfully
     * @param data
     */
    void fillData(List<T> data);

    /**
     * append data to history list(load more)
     * @param data
     */
    void appendData(List<T> data);

    /**
     * no more data for show and this condition is hard to appear,it need you scroll main view long time
     * I think it has no body do it like this ,even though，I deal this condition also, In case someone does it.
     */
    void hasNoMoreData();
}
