package com.dream.example.ui.fragment;

import com.dream.example.R;
import com.dream.example.presenter.NewsV4FPresenter;
import com.dream.example.ui.fragment.base.AppBaseV4Fragment;

import org.yapp.core.ui.inject.annotation.ContentInject;

/**
 * Description: NewsV4Fragment. <br>
 * Date: 2016/3/15 13:43 <br>
 * Author: ysj
 */
@ContentInject(value = R.layout.fragment_news
        , presenter = NewsV4FPresenter.class)
public class NewsV4Fragment extends AppBaseV4Fragment<NewsV4FPresenter> {
}
