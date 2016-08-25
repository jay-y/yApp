package com.dream.example.ui.fragment;

import com.dream.example.R;
import com.dream.example.presenter.GankPresenter;
import com.dream.example.ui.fragment.base.AppBaseV4Fragment;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * ClassName: GankV4Fragment <br>
 * Description: 干货组件. <br>
 * Date: 2015-6-24 下午5:45:52 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.fragment_gank
        , presenter = GankPresenter.class)
public class GankV4Fragment extends AppBaseV4Fragment<GankPresenter> {
}