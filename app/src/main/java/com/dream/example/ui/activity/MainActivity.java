package com.dream.example.ui.activity;

import android.view.KeyEvent;

import com.dream.example.R;
import com.dream.example.presenter.MainPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: 主界面. <br>
 * Date: 2016/3/31 1:46 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_main
        , presenter = MainPresenter.class)
public class MainActivity extends AppBaseAppCompatActivity<MainPresenter> {

    /**
     * 监听onKeyDown事件
     *
     * @see android.app.Activity#onKeyUp(int, KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mPresenter.onKeyDown(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (!mPresenter.onBackPressed()) {
            super.onBackPressed();
        }
    }
}
