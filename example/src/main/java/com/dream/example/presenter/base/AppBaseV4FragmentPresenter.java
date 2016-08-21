package com.dream.example.presenter.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.view.IAppBaseView;

import org.yapp.core.Application;
import org.yapp.core.presenter.BaseV4FragmentPresenter;
import org.yapp.core.ui.fragment.BaseV4Fragment;
import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;

/**
 * Description: App Fragment主持层抽象基类. <br>
 * Date: 2016/3/17 10:32 <br>
 * Author: ysj
 */
public abstract class AppBaseV4FragmentPresenter<T extends AppBaseAppCompatActivity, F extends BaseV4Fragment, A extends Application>
        extends BaseV4FragmentPresenter<T, F, A> implements IAppBaseView {
    @ViewInject(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewInject(R.id.toolbar_title)
    protected TextView mTitle;

    protected InputMethodManager mImm;

    //    public DataSupports getDataSupports(){
//        return HttpFactory.getMainDataSupports();
//    }

    /**
     * 设置标题
     *
     * @param resId
     */
    public void setTitle(int resId) {
        String strTitle = getContent().getString(resId);
        setTitle(strTitle, true);
    }

    /**
     * 设置标题
     *
     * @param strTitle
     */
    public void setTitle(String strTitle) {
        setTitle(strTitle, true);
    }

    /**
     * 设置标题
     *
     * @param strTitle
     * @param isShowHome
     */
    public void setTitle(String strTitle, boolean isShowHome) {
        if (strTitle.length() > 10) strTitle = strTitle.substring(0, 10) + "...";
        if (null != mTitle) {
            mTitle.setText(strTitle); //设置自定义标题文字
            getContent().getSupportActionBar().setDisplayShowTitleEnabled(false); //隐藏Toolbar标题
            getContent().getSupportActionBar().setDisplayShowHomeEnabled(isShowHome);
            getContent().getSupportActionBar().setDisplayHomeAsUpEnabled(isShowHome);
        } else if (null != mToolbar) {
            mToolbar.setTitle(strTitle);
        }
    }

    /**
     * 释放图片视图
     *
     * @param obj
     */
    public void releaseImageView(ImageView obj) {
        if (null != obj.getDrawable())
            obj.getDrawable().setCallback(null);
        obj.setImageDrawable(null);
        obj.setBackgroundDrawable(null);
        obj = null;
    }


    @Override
    public void onBuild(Context context, F fragment) {
        super.onBuild(context, fragment);
        initToolBar();
        mImm = (InputMethodManager) getContent().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClear() {
        Log.w("Do you need to release memory , Please override the onDestroy() method in " + this.getClass().getSimpleName() + ".");
    }

    @Override
    public void onDestroy() {
        onClear();
        mToolbar = null;
        mTitle = null;
        mImm = null;
        super.onDestroy();
    }

    @Override
    public void showMsg(String msg) {
        getContent().getPresenter().showMsg(msg);
    }

    @Override
    public void showError(Throwable throwable) {
        getContent().getPresenter().showError(throwable);
    }

    /**
     * 弹出Dialog
     *
     * @param msg
     * @param title
     * @param callback
     */
    @Override
    public void showDialog(String msg, String title, Callback.DialogCallback callback) {
        getContent().getPresenter().showDialog(title, msg, callback);
    }

    public void showDialog(String msg, String title) {
        showDialog(msg, title, null);
    }

    public void showDialog(String msg) {
        showDialog(msg, null);
    }

    @Override
    public void closeDialog() {
        getContent().getPresenter().closeDialog();
    }

    @Override
    public void showLoading() {
        getContent().getPresenter().showLoading();
    }

    @Override
    public void closeLoading() {
        getContent().getPresenter().closeLoading();
    }

    private void initToolBar() {
        if (null == mToolbar) mToolbar = (Toolbar) getContent().findViewById(R.id.toolbar);
        if (null == mTitle) mTitle = (TextView) getContent().findViewById(R.id.toolbar_title);
        if (null != mToolbar) getContent().setSupportActionBar(mToolbar);
    }
}
