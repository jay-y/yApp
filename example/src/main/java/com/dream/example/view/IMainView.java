package com.dream.example.view;

import android.view.KeyEvent;
import android.view.View;

import org.yapp.core.view.IBaseView;

/**
 * Description: IMainView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface IMainView extends IAppBaseView {

    void addTabView(View view);

    void initPagerView();

    void setTabSelection(int index);

    void clearSelection();

    boolean onKeyDown(int keyCode, KeyEvent event);

    boolean onBackPressed();
}
