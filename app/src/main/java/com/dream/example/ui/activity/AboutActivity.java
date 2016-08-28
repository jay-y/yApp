package com.dream.example.ui.activity;

import com.dream.example.R;
import com.dream.example.presenter.AboutPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: 关于应用. <br>
 * Date: 2015/8/21 10:46 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_about
        , presenter = AboutPresenter.class)
public class AboutActivity extends AppBaseAppCompatActivity<AboutPresenter> {
}
