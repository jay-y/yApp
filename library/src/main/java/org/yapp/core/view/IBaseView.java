package org.yapp.core.view;

import org.yapp.utils.Callback;

/**
 * Description: 视图控制基类. <br>
 * Date: 2016/3/14 16:07 <br>
 * Author: ysj
 */
public interface IBaseView {
    void showDialog(String msg);

    void showDialog(String title, String msg);

    void showDialog(String title, String msg, Callback.DialogCallback callback);

    void closeDialog();

    void showLoading();

    void closeLoading();
}
