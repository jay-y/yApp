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
    private static final Object lock = new Object();
    private static GankV4Fragment instance;

    /**
     * getInstance:(获取实例). <br>
     *
     * @return Fragment
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:48:12 <br>
     */
    public static GankV4Fragment getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new GankV4Fragment();
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
    public static GankV4Fragment newInstance() {
        GankV4Fragment fragment = new GankV4Fragment();
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
