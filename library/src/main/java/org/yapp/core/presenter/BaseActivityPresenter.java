package org.yapp.core.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.yapp.core.Application;
import org.yapp.core.ui.abase.BaseActivityPresenterApi;
import org.yapp.core.ui.activity.BaseAppCompatActivity;
import org.yapp.core.ui.inject.ViewInjector;
import org.yapp.y;

/**
 * Description: Activity主持层抽象基类. <br>
 * Date: 2016/3/14 16:03 <br>
 * Author: ysj
 */
public abstract class BaseActivityPresenter<T extends BaseAppCompatActivity,A extends Application>
        extends BasePresenter<T,A> implements BaseActivityPresenterApi {

    /**
     * 构建
     *
     * @param context activity context
     */
    @Override
    public void onBuild(Context context){
        super.onBuild(context);
        ViewInjector.inject(getContent(), this);
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void go(Class<?> clazz) {
        Intent intent = new Intent(getContent(), clazz);
        getContent().startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void go(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContent(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getContent().startActivity(intent);
    }

    /**
     * startActivity with bundle and delayed
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void go(Class<?> clazz, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContent(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContent().startActivity(intent);
            }
        }, delayed * 1000);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void goThenKill(Class<?> clazz) {
        Intent intent = new Intent(getContent(), clazz);
        getContent().startActivity(intent);
        getContent().finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public void goThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(getContent(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getContent().startActivity(intent);
        getContent().finish();
    }

    /**
     * startActivity with bundle and delayed then finish
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void goThenKill(Class<?> clazz, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContent(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContent().startActivity(intent);
                getContent().finish();
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
        Intent intent = new Intent(getContent(), clazz);
        getContent().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void goForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(getContent(), clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        getContent().startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle and delayed
     *
     * @param clazz   需要跳转的Activity
     * @param bundle  携带数据
     * @param delayed 延迟加载时间
     */
    public void goForResult(Class<?> clazz, final int requestCode, Bundle bundle, int delayed) {
        final Intent intent = new Intent(getContent(), clazz);
        if (bundle != null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                getContent().startActivityForResult(intent, requestCode);
            }
        }, delayed * 1000);
    }
}