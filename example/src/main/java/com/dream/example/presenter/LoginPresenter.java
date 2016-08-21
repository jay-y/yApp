package com.dream.example.presenter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dream.example.App;
import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.utils.SPUtil;
import com.dream.example.view.ILoginView;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Toast;

/**
 * Description: LoginPresenter. <br>
 * Date: 2016/08/17 16:59 <br>
 * Author: ysj
 */
public class LoginPresenter extends
        AppBaseActivityPresenter<AppBaseAppCompatActivity, App> implements ILoginView, View.OnClickListener {

    @ViewInject(R.id.login_uname)
    private AutoCompleteTextView mUnameView;

    @ViewInject(R.id.login_pwd)
    private EditText mPasswordView;

    @ViewInject(R.id.login_progress)
    private View mProgressView;

    @ViewInject(R.id.login_form)
    private View mLoginFormView;

    @ViewInject(R.id.login_btn_ok)
    private Button mSignInButton;

    @ViewInject(R.id.login_register)
    private TextView mRegisterView;

    @ViewInject(R.id.login_forgot)
    private TextView mForgotView;

    private String clientPlatform;
    private String clientInfo;
    private String iosServiceToken;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_ok:
                attemptLogin();
                break;
            case R.id.login_register:
//                go(RegisterAActivity.class);
                break;
            case R.id.login_forgot:
                // TODO
                break;
        }
    }

    @Override
    public void onInit() {
        setTitle(R.string.title_activity_login);
        String uname = (String) SPUtil.get(getContent(), AppConsts._UNAME, "");
        String passwd = (String) SPUtil.get(getContent(), AppConsts._PASSWD, "");
        if (!TextUtils.isEmpty(uname) && !TextUtils.isEmpty(passwd)) {
            mUnameView.setText(uname);
            mPasswordView.setText(passwd);
        }
        Snackbar.make(mLoginFormView, "请先登录", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        mSignInButton.setOnClickListener(this);
        mRegisterView.setOnClickListener(this);
        mForgotView.setOnClickListener(this);
    }

    @Override
    public void onClear() {
        mUnameView = null;
        mPasswordView = null;
        mProgressView = null;
        mLoginFormView = null;
        mSignInButton = null;
        mRegisterView = null;
        mForgotView = null;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    @Override
    public void attemptLogin() {
        // Reset errors.
        mUnameView.setError(null);
        mPasswordView.setError(null);
        // Store values at the time of the login attempt.
        String uname = mUnameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;
        if (TextUtils.isEmpty(uname) && TextUtils.isEmpty(password)) {
//            go(RegisterAActivity.class);
            return;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(uname)) {
            mUnameView.setError(getContent().getString(R.string.error_field_required));
            focusView = mUnameView;
            cancel = true;
        }
//        else if (!isUnameValid(uname)) {
//            mUnameView.setError(getString(R.string.error_invalid_email));
//            focusView = mUnameView;
//            cancel = true;
//        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getContent().getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getContent().getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
//            login(uname, password, app.getDeviceId(), app.getVersionName());
        }
    }

    @Override
    public boolean isEmailValid(String email) {
        //TODO: Replace getContent() with your own logic
        return (email.contains("@"));
    }

    @Override
    public boolean isPasswordValid(String password) {
        //TODO: Replace getContent() with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getContent().getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void showError(Throwable ex) {
        showDialog(ex.getMessage());
    }

    @Override
    public void loginSuccess() {
        SPUtil.put(getContent(), AppConsts._UNAME, mUnameView.getText().toString());
        SPUtil.put(getContent(), AppConsts._PASSWD, mPasswordView.getText().toString());
        Toast.showMessageForButtomShort("登陆成功");
        getContent().finish();
    }
}
