package org.yapp.core.view;

import org.yapp.utils.Callback;

/**
 * Description: 视图控制基类. <br>
 * Date: 2016/3/14 16:07 <br>
 * Author: ysj
 */
public interface IBaseView {
    void onInit();

    void showMsg(String msg);

    void showError(Throwable throwable);

    void showDialog(String title, String msg, Callback.DialogCallback callback);

    void closeDialog();

    void showLoading();

    void closeLoading();
}
