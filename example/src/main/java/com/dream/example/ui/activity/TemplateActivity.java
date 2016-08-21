package com.dream.example.ui.activity;

import com.dream.example.R;
import com.dream.example.presenter.TemplatePresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: Activity模板. <br>
 * Date: 2015/8/21 10:46 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.activity_template
        , presenter = TemplatePresenter.class)
public class TemplateActivity extends AppBaseAppCompatActivity<TemplatePresenter> {
}
