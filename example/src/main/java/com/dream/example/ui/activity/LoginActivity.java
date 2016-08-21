package com.dream.example.ui.activity;

import com.dream.example.R;
import com.dream.example.presenter.LoginPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: 登录. <br>
 * Date: 2016/4/8 14:42 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_login
        , presenter = LoginPresenter.class)
public class LoginActivity extends AppBaseAppCompatActivity<LoginPresenter> {

}