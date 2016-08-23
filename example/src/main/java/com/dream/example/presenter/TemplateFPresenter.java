package com.dream.example.presenter;

import com.dream.example.presenter.base.AppSwipeRefreshFragmentPresenter;
import com.dream.example.view.IDefaultView;

import rx.Observable;

/**
 * Description: TemplateV4FPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class TemplateFPresenter extends AppSwipeRefreshFragmentPresenter implements IDefaultView {

    @Override
    public void onInit() {
        // TODO UI, monitor, data, etc for initialization.
    }

    @Override
    public void onClear() {
        // TODO clear.
    }

    @Override
    public void showEmpty() {
        // TODO show
    }

    @Override
    public Observable getObservable() {
        // TODO observable for initialization.
        return null;
    }

    @Override
    public void loadData() {
        // TODO load data.
    }

    @Override
    public void loadMoreData() {
        // TODO load more data.
    }

    @Override
    public void hasNoMoreData() {
        // TODO has more data?
    }

    @Override
    public void onRefreshStarted() {

    }
}
