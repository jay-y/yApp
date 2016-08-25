package com.dream.example.ui.fragment;

import com.dream.example.R;
import com.dream.example.presenter.TemplateFPresenter;
import com.dream.example.ui.fragment.base.AppBaseFragment;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: TemplateFragment模板. <br>
 * Date: 2016/3/15 13:43 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.fragment_template
        , presenter = TemplateFPresenter.class)
public class TemplateFragment extends AppBaseFragment<TemplateFPresenter> {
}
