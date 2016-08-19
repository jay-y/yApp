package org.yapp.core.ui.abase;

/**
 * Description: 活动层基础接口. <br>
 * Date: 2016/3/31 11:34 <br>
 * Author: ysj
 */
public interface BaseActivityApi {
    /**
     * set layout of this activity
     *
     * @return the id of layout
     */
    int getLayoutId();

    /**
     * build
     */
    void onBuild();

    /**
     * init
     */
    void onInit();

    /**
     * clear
     */
    void onClear();
}
