//package com.dream.example.ui.activity;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.support.design.widget.Snackbar;
//import android.text.TextUtils;
//import android.view.KeyEvent;
//import android.view.MenuItem;
//import android.view.WindowManager;
//import android.webkit.WebView;
//
//import com.dream.example.R;
//
//import org.yapp.core.ui.inject.annotation.ViewInject;
//import org.yapp.utils.Log;
//import org.yapp.utils.Toast;
//
//public class WebActivity extends AppBaseSwipeRefreshActivity<WebPresenter> implements IWebView {
//    private static final String EXTRA_URL = "URL";
//    private static final String EXTRA_TITLE = "TITLE";
//
//    @ViewInject(R.id.wb_content)
//    private WebView mWebContent;
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK)
//                && mWebContent.canGoBack()) {
//            mWebContent.goBack();
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_web;
//    }
//
//    @Override
//    public void onInitChild() {
//        String url = getIntent().getStringExtra(EXTRA_URL);
//        String title = getIntent().getStringExtra(EXTRA_TITLE);
//
//        if (!TextUtils.isEmpty(title)) {
//            setTitle(title, true);
//        } else {
//            setTitle(R.string.app_name, true);
//        }
//        mPresenter.init(mWebContent);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//        mPresenter.loadUrl(mWebContent, url);
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter = new WebPresenter(this, this);
//    }
//
//    @Override
//    public void onClear() {
//        mWebContent = null;
//    }
//
//    @Override
//    public int getMenuRes() {
//        return R.menu.menu_web;
//    }
//
//    @Override
//    public void onRefreshStarted() {
//        refresh();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case android.R.id.home:
//                if (mWebContent.canGoBack()) {
//                    mWebContent.goBack();
//                    return true;
//                }
//                break;
//            case R.id.action_copy_url:
//                String copyDone = getString(R.string.toast_copy_done);
//                SynUtils.copyToClipBoard(this, mWebContent.getUrl(), copyDone);
//                return true;
//            case R.id.action_open_url:
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);
//                Uri uri = Uri.parse(mWebContent.getUrl());
//                intent.setData(uri);
//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(intent);
//                } else {
//                    Toast.showMessageForButtomShort(getString(R.string.toast_open_fail));
//                }
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mWebContent != null) mWebContent.destroy();
//    }
//
//    @Override
//    public void onPause() {
//        if (mWebContent != null) mWebContent.onPause();
//        super.onPause();
//    }
//
//    @Override
//    public void showMsg(String msg) {
//        Snackbar.make(mWebContent, msg, Snackbar.LENGTH_SHORT).show();
//    }
//
//    private void refresh() {
//        mWebContent.reload();
//    }
//}
