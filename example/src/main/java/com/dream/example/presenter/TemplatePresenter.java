package com.dream.example.presenter;

import com.dream.example.App;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.view.ITemplateView;

/**
 * Description: TemplateActivityPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class TemplatePresenter extends
        AppBaseActivityPresenter<AppBaseAppCompatActivity,App> implements ITemplateView {

    @Override
    public void onInit() {
        // TODO Initialization UI, monitor, data, etc.
    }

    @Override
    public void onClear(){
        // TODO Clear
    }
}
