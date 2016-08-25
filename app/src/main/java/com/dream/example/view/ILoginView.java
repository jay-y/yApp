package com.dream.example.view;

/**
 * Description: ILoginView. <br>
 * Date: 2016/3/14 18:36 <br>
 * Author: ysj
 */
public interface ILoginView extends IAppBaseView {
    boolean isEmailValid(String email);

    boolean isPasswordValid(String password);

    void attemptLogin();

    void showProgress(boolean show);

    void showError(Throwable ex);

    void loginSuccess();
}
