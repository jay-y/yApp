package com.dream.example.view;

import android.view.KeyEvent;

import org.yapp.core.view.IBaseView;

/**
 * Description: ISplashView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface ISplashView extends IAppBaseView {

    boolean onKeyDown(int keyCode, KeyEvent event);
}
