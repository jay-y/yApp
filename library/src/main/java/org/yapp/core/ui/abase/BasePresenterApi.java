package org.yapp.core.ui.abase;

import android.content.Context;

/**
 * Description: 主持层基础接口. <br>
 * Date: 2016/3/31 11:34 <br>
 * Author: ysj
 */
public interface BasePresenterApi<T> {
    void onBuild(Context context);

    void onDestroy();

    T getContent();
}
