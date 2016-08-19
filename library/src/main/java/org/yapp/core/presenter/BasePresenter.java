package org.yapp.core.presenter;

import android.content.Context;

import org.yapp.core.view.IBaseView;

/**
 * Description: 表示层基类. <br>
 * Date: 2016/3/14 16:03 <br>
 * Author: ysj
 */
public class BasePresenter<V extends IBaseView> {
    protected V mView;
    protected Context mContext;

    /**
     * @param context activity context
     * @param view
     */
    public BasePresenter(Context context, V view) {
        mContext = context;
        mView = view;
    }
}