package com.dream.example.ui.fragment.base;

import com.dream.example.presenter.base.AppBaseFragmentPresenter;

import org.yapp.core.presenter.BaseFragmentPresenter;
import org.yapp.core.ui.fragment.BaseFragment;

/**
 * Description: 应用Fragment基类. <br>
 * Date: 2016/3/15 13:43 <br>
 * Author: ysj
 */
public abstract class AppBaseFragment<P extends AppBaseFragmentPresenter> extends BaseFragment<P> {
}
