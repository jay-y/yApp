package org.yapp.core.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import org.yapp.core.presenter.BaseActivityPresenter;
import org.yapp.core.ui.abase.BasePartsApi;
import org.yapp.core.ui.inject.annotation.ContentInject;
import org.yapp.ex.ApplicationException;
import org.yapp.utils.Log;

/**
 * Description: Activity基类 通一编码规范(V7). <br>
 * Date: 2015-6-15 上午10:37:00 <br>
 * Author: ysj
 */
public abstract class BaseAppCompatActivity<P extends BaseActivityPresenter> extends AppCompatActivity implements BasePartsApi {
    private ContentInject mInject;

    /**
     * the presenter of this Activity
     */
    protected P mPresenter;

    public P getPresenter() {
        return mPresenter;
    }

    /**
     * init presenter
     */
    @Override
    public void initPresenter(){
        try {
            Class<?> cls = Class.forName(mInject.presenter().getName());
            mPresenter = (P)cls.newInstance();
            if (null == mPresenter) {
                throw new ApplicationException("Do you need to init mPresenter in " + this.getClass().getSimpleName() + " initPresenter() method.");
            }
        } catch (Exception e) {
            Log.e(e.getMessage(),e);
            throw new ApplicationException("Presenter mapping failure.");
        }
    }

    @Override
    public int getLayoutId() {
        try {
            return mInject.value();
        }catch (Exception e){
            Log.e(e.getMessage(),e);
            throw new ApplicationException("Please setting id of "+this.getClass().getSimpleName()+" by @ViewInject.");
        }
    }

    /**
     * 监听onKeyDown事件
     *
     * @see android.app.Activity#onKeyUp(int, KeyEvent)
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mInject = this.getClass().getAnnotation(ContentInject.class);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initPresenter();
        mPresenter.onBuild(this);
        mPresenter.onInit();
        mInject = null;
    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        mPresenter = null;
        super.onDestroy();
        System.gc();
    }
}
