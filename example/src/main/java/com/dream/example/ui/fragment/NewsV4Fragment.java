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
    private static final Object lock = new Object();
    private static NewsV4Fragment instance;

    /**
     * getInstance:(获取实例). <br>
     *
     * @return Fragment
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:48:12 <br>
     */
    public static NewsV4Fragment getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new NewsV4Fragment();
                }
            }
        }
        return instance;
    }

    /**
     * newInstance:(创建新实例). <br>
     *
     * @return this
     */
    public static NewsV4Fragment newInstance() {
        NewsV4Fragment fragment = new NewsV4Fragment();
        return fragment;
    }

    /**
     * releaseInstance:(释放实例). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:50:21 <br>
     */
    public static void releaseInstance() {
        instance = null;
    }
}
