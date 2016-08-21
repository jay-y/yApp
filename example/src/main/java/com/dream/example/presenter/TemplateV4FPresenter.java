package com.dream.example.presenter;

import com.dream.example.App;
import com.dream.example.presenter.base.AppBaseV4FragmentPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.ui.fragment.base.AppBaseV4Fragment;
import com.dream.example.view.ITemplateView;

/**
 * Description: TemplateV4FPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class TemplateV4FPresenter extends
        AppBaseV4FragmentPresenter<AppBaseAppCompatActivity,AppBaseV4Fragment,App> implements ITemplateView {

    @Override
    public void onInit() {
        // TODO Initialization UI, monitor, data, etc.
    }

    @Override
    public void onClear(){
        // TODO Clear
    }
}
