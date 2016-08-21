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
@ContentInject(value = R.layout.activity_template
        , presenter = TemplateFPresenter.class)
public class TemplateFragment extends AppBaseFragment<TemplateFPresenter> {
    private static final Object lock = new Object();
    private static TemplateFragment instance;

    /**
     * getInstance:(获取实例). <br>
     *
     * @return Fragment
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:48:12 <br>
     */
    public static TemplateFragment getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new TemplateFragment();
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
    public static TemplateFragment newInstance() {
        TemplateFragment fragment = new TemplateFragment();
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
