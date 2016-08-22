package com.dream.example.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;

import com.dream.example.R;
import com.dream.example.presenter.TemplatePresenter;
import com.dream.example.presenter.WebPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;
import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Log;
import org.yapp.utils.Toast;

/**
 * Description: WebActivity. <br>
 * Date: 2015/8/21 10:46 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_template
        , presenter = TemplatePresenter.class)
public class WebActivity extends AppBaseAppCompatActivity<WebPresenter> {

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
    public void onPause() {
        mPresenter.onPause();
    }
}
