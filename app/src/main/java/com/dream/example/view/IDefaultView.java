package com.dream.example.view;

/**
 * Description: ITemplateView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface IDefaultView {
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
