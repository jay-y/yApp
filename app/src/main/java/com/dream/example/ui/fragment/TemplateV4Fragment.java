package com.dream.example.ui.fragment;

import com.dream.example.R;
import com.dream.example.presenter.TemplateV4FPresenter;
import com.dream.example.ui.fragment.base.AppBaseV4Fragment;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: TemplateV4Fragment模板. <br>
 * Date: 2016/3/15 13:43 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.fragment_template
        , presenter = TemplateV4FPresenter.class)
public class TemplateV4Fragment extends AppBaseV4Fragment<TemplateV4FPresenter> {
}
