package com.dream.example.view;

/**
 * Description: IWebView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface IWebView extends IAppBaseView{

    void onPause();

    void loadUrl(String url);
}
