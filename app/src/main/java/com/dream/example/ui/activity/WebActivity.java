package com.dream.example.ui.activity;

import com.dream.example.R;
import com.dream.example.presenter.WebPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: WebActivity. <br>
 * Date: 2015/8/21 10:46 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_web
        , presenter = WebPresenter.class)
public class WebActivity extends AppBaseAppCompatActivity<WebPresenter> {

    @Override
    public void onPause() {
        mPresenter.onPause();
        super.onPause();
    }
}
