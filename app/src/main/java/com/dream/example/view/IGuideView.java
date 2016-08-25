package com.dream.example.view;

import android.view.KeyEvent;

/**
 * Description: IGuideView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface IGuideView extends IAppBaseView {

    boolean onKeyDown(int keyCode, KeyEvent event);
}
