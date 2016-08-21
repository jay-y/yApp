package com.dream.example.presenter.base;

import android.content.Context;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.dream.example.R;
import com.dream.example.data.support.DataSupports;
import com.dream.example.data.support.HttpFactory;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.ui.widget.LoadingDialog;
import com.dream.example.view.IAppBaseView;

import org.yapp.core.Application;
import org.yapp.core.presenter.BaseActivityPresenter;
import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.utils.Toast;

/**
 * Description: App Activity主持层抽象基类. <br>
 * Date: 2016/3/17 10:32 <br>
 * Author: ysj
 */
public abstract class AppBaseActivityPresenter<T extends AppBaseAppCompatActivity, A extends Application>
        extends BaseActivityPresenter<T, A> implements IAppBaseView {
    @ViewInject(R.id.toolbar)
    protected Toolbar mToolbar;
    @ViewInject(R.id.toolbar_title)
    protected TextView mTitle;

    protected InputMethodManager mImm;
    protected LoadingDialog mLoadingDialog;

    public Menu mMenu;
    public ActionBarDrawerToggle mToggle;

    public DataSupports getDataSupports() {
        return HttpFactory.getMainDataSupports();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        int menuId = getMenuRes();
        if (menuId < 0) return true;
        getContent().getMenuInflater().inflate(menuId, menu);
        mMenu = menu;
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (null != this.mToggle && this.mToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            if (android.R.id.home == item.getItemId()) {
                getContent().onBackPressed();
            }
            return false;
        }
    }

    /**
     * set the id of menu
     *
     * @return if values is less then zero ,and the activity will not show menu
     */
    public int getMenuRes() {
        return -1;
    }

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
    public void onBuild(Context context) {
        super.onBuild(context);
        initToolBar();
        mImm = (InputMethodManager) getContent().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onClear() {
        Log.w("Do you need to release memory , Please override the onClear() method in " + this.getClass().getSimpleName() + ".");
    }

    @Override
    public void onDestroy() {
        onClear();
        mToolbar = null;
        mTitle = null;
        mImm = null;
        mLoadingDialog = null;
        mMenu = null;
        mToggle = null;
        super.onDestroy();
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
    public void showDialog(String msg, String title, Callback.DialogCallback callback) {
    }

    public void showDialog(String msg, String title) {
        showDialog(msg, title, null);
    }

    public void showDialog(String msg) {
        showDialog(msg, null);
    }

    @Override
    public void closeDialog() {
    }

//    /**
//     * 弹出Dialog
//     *
//     * @param title
//     * @param msg
//     * @param callback
//     */
//    public void showDialog(String title, String msg, final Callback.DialogCallback callback) {
//        if (null == mMaterialDialog) {
//            mMaterialDialog = new MaterialDialog.Builder(this)
//                    .cancelable(true)
//                    .title(TextUtils.isEmpty(title) ? getString(R.string.app_name) : title)
//                    .content(TextUtils.isEmpty(msg) ? "" : msg)
//                    .positiveText(R.string.action_ok)
//                    .negativeText(R.string.action_cancle)
//                    .onPositive(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(MaterialDialog dialog, DialogAction which) {
//                            if (null != callback) callback.onPositive();
//                        }
//                    })
//                    .onNegative(new MaterialDialog.SingleButtonCallback() {
//                        @Override
//                        public void onClick(MaterialDialog dialog, DialogAction which) {
//                            if (null != callback) callback.onNegative();
//                        }
//                    })
//                    .build();
//        } else {
//            mMaterialDialog.setTitle(TextUtils.isEmpty(title) ? getString(R.string.app_name) : title);
//            mMaterialDialog.setContent(TextUtils.isEmpty(msg) ? "" : msg);
//            mMaterialDialog.getBuilder().onPositive(new MaterialDialog.SingleButtonCallback() {
//                @Override
//                public void onClick(MaterialDialog dialog, DialogAction which) {
//                    if (null != callback) callback.onPositive();
//                }
//            });
//            mMaterialDialog.getBuilder().onNegative(new MaterialDialog.SingleButtonCallback() {
//                @Override
//                public void onClick(MaterialDialog dialog, DialogAction which) {
//                    if (null != callback) callback.onNegative();
//                }
//            });
//        }
//        mMaterialDialog.show();
//    }
//
//    public void closeDialog() {
//        if (null != mMaterialDialog) {
//            mMaterialDialog.dismiss();
//        }
//    }

    @Override
    public void showLoading() {
        if (null == mLoadingDialog) {
            mLoadingDialog = new LoadingDialog(getContent());
        }
        mLoadingDialog.show();
    }

    @Override
    public void closeLoading() {
        if (null != mLoadingDialog && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    @Override
    public boolean isLoading() {
        if(null != mLoadingDialog){
            return mLoadingDialog.isShowing();
        }else{
            return false;
        }
    }

    private void initToolBar() {
        if (null == mToolbar) mToolbar = (Toolbar) getContent().findViewById(R.id.toolbar);
        if (null == mTitle) mTitle = (TextView) getContent().findViewById(R.id.toolbar_title);
        if (null != mToolbar) getContent().setSupportActionBar(mToolbar);
    }
}
