package org.yapp.core.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.yapp.core.Application;
import org.yapp.core.ui.abase.BaseActivityPresenterApi;
import org.yapp.core.ui.activity.BaseAppCompatActivity;
import org.yapp.core.ui.inject.ViewInjector;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.utils.Toast;
import org.yapp.y;

/**
 * Description: Activity主持层抽象基类. <br>
 * Date: 2016/3/14 16:03 <br>
 * Author: ysj
 */
public abstract class BaseActivityPresenter<T extends BaseAppCompatActivity, A extends Application>
        extends BasePresenter<T, A> implements BaseActivityPresenterApi {

    /**
     * 构建
     *
     * @param context activity context
     */
    @Override
    public void onBuild(Context context) {
        super.onBuild(context);
        ViewInjector.inject(getContext(), this);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void go(Class<?> clazz) {
        Intent intent = new Intent(getContext(), clazz);
        getContext().startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void go(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getContext().startActivity(intent);
    }

    /**
     * startActivity with bundle and delayed
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void go(Class<?> clazz, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(intent);
            }
        }, delayed * 1000);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void goThenKill(Class<?> clazz) {
        Intent intent = new Intent(getContext(), clazz);
        getContext().startActivity(intent);
        getContext().finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public void goThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getContext().startActivity(intent);
        getContext().finish();
    }

    /**
     * startActivity with bundle and delayed then finish
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void goThenKill(Class<?> clazz, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContext().startActivity(intent);
                getContext().finish();
            }
        }, delayed * 1000);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    public void goForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(getContext(), clazz);
        getContext().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void goForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContext(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getContext().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle and delayed
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void goForResult(Class<?> clazz, final int requestCode, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContext(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContext().startActivityForResult(intent, requestCode);
            }
        }, delayed * 1000);
    }

    @Override
    public void showMsg(String msg) {
        Toast.showMessageForButtomShort(msg);
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e(throwable.getMessage(), throwable);
        Toast.showMessageForButtomShort(throwable.getMessage());
    }

    /**
     * 弹出Dialog
     *
     * @param msg
     * @param title
     * @param callback
     */
    @Override
    public void showDialog(String msg, String title, final Callback.DialogCallback callback) {
        Toast.showMessageForCenterLong(msg);
    }

    public void showDialog(String msg, String title) {
        showDialog(msg, title, null);
    }

    public void showDialog(String msg) {
        showDialog(msg, null);
    }

    @Override
    public void closeDialog() {
        // do nothing
    }

    @Override
    public void showLoading() {
        Toast.showMessageForCenterLong("正在加载...");
    }

    @Override
    public void closeLoading() {
        // do nothing
    }

    @Override
    public boolean isLoading() {
        return false;
    }
}