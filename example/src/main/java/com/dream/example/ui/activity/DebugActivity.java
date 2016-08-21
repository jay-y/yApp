package com.dream.example.ui.activity;

import com.dream.example.R;
import com.dream.example.presenter.DebugPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * ClassName: DebugActivity <br>
 * Description: DEBUG模式使用. <br>
 * Date: 2015-8-21 上午10:25:10 <br>
 *
 * @author ysj
 * @version 1.0
 * @since JDK 1.7
 */
@ContentInject(value = R.layout.activity_debug
        , presenter = DebugPresenter.class)
public class DebugActivity extends AppBaseAppCompatActivity<DebugPresenter> {
}