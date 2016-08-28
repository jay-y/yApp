package com.dream.example.presenter;

import android.support.design.widget.CollapsingToolbarLayout;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.view.ITemplateView;

import org.yapp.core.ui.inject.annotation.ViewInject;

/**
 * Description: AboutPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class AboutPresenter extends AppBaseActivityPresenter implements ITemplateView {
    @ViewInject(R.id.about_collapsing_toolbar)
    private CollapsingToolbarLayout mCollapsingToolbar;
    @ViewInject(R.id.about_tv_version)
    private TextView mTvVersion;

    @Override
    public void onInit() {
        mCollapsingToolbar.setTitle(getContext().getString(R.string.title_activity_about));
//        getContext().setSupportActionBar(mToolbar);
        getContext().setTitle("");
        getContext().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvVersion.setText("V" + app.getVersionName());
    }

    @Override
    public void onClear() {
        mCollapsingToolbar = null;
        mTvVersion = null;
    }
}
