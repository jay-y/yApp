package org.yapp.core.ui.abase;

/**
 * Description: 基础组件接口. <br>
 * Date: 2016/3/31 11:34 <br>
 * Author: ysj
 */
public interface BasePartsApi {
    void initPresenter();

    /**
     * set layout of this activity
     *
     * @return the id of layout
     */
    int getLayoutId();
}
