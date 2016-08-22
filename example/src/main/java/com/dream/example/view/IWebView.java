package com.dream.example.view;

import android.view.KeyEvent;

/**
 * Description: IWebView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface IWebView extends ISwipeRefreshView {
    boolean onKeyDown(int keyCode, KeyEvent event);

    void onPause();

    void loadUrl(String url);
}
