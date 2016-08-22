package com.dream.example.presenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.dream.example.App;
import com.dream.example.R;
import com.dream.example.data.support.AppConsts;
import com.dream.example.data.support.HttpFactory;
import com.dream.example.presenter.base.AppBaseActivityPresenter;
import com.dream.example.ui.activity.GuideActivity;
import com.dream.example.ui.activity.MainActivity;
import com.dream.example.ui.activity.base.AppBaseAppCompatActivity;
import com.dream.example.utils.SPUtil;
import com.dream.example.view.ISplashView;

import org.yapp.core.ui.inject.annotation.ViewInject;
import org.yapp.utils.SignalUtil;

/**
 * Description: SplashPresenter. <br>
 * Date: 2016/4/8 17:41 <br>
 * Author: ysj
 */
public class SplashPresenter extends AppBaseActivityPresenter implements ISplashView {
    @ViewInject(R.id.splash_img_bg)
    private ImageView mImgBg;

    @Override
    public void onInit() {
        String serverAddress = (String) SPUtil.get(getContent(), AppConsts._SERVER_ADDRESS, "");
        mImgBg.setImageResource(R.drawable.splash);
        SPUtil.put(getContent(), AppConsts._ERROR_CODE, 1);
        if (!TextUtils.isEmpty(serverAddress)) {
            AppConsts.ServerConfig.MAIN_HOST = serverAddress;
            HttpFactory.resetMainDataSupports();
        }
        if (initGuide())
            return;
        if (SignalUtil.isNetworkConnected()) {
            attemptLogin();
        } else {
            loadSuccess(null);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(null);
            return true;
        }
        return false;
    }

    @Override
    public void onClear() {
        releaseImageView(mImgBg);
    }

    private void loadSuccess(Bundle data) {
        jump(MainActivity.class, data, 3);
    }

    private void jump(Class<?> cls, Bundle data, int delayed) {
        goThenKill(cls, data, delayed);
    }

    private void attemptLogin() {
        loadSuccess(null);
//        versionCheck(app.getVersionName());
    }

    /**
     * 第一次使用APP引导
     *
     * @return
     */
    private boolean initGuide() {
        String hasApp = (String) SPUtil.get(getContent(), AppConsts.AppConfig.PARAM_HASAPP, AppConsts._YES);// hasApp变量用来判断是否第一次使用App，包括上面的YES和NO
        if (!hasApp.equals(AppConsts._NO)) {
            App.setStatus(0);
            jump(GuideActivity.class, null, 3);
            return true;
        } else {
            return false;
        }
    }


    private void versionCheck(final String versionNo) {
//        getDataSupports().versionCheck(versionNo, "2")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseData>() {
//                    @Override
//                    public void onCompleted() {
//                        autoLogin();
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        showError(ResponseFilter.onError(e));
//                        autoLogin();
//                    }
//
//                    @Override
//                    public void onNext(BaseData result) {
//                        result = ResponseFilter.onSuccess(result);
//                        Map<String, Object> data = (Map<String, Object>) result.data;
//                        int status = Integer.valueOf(result.status);
//                        App.setStatus(status);
//                        if (null != data
//                                && data.containsKey(AppConsts._VERSION_NO)
//                                && null != data.get(AppConsts._VERSION_NO)) {
//                            App.getClient().setVersionNo(
//                                    (String) data.get(AppConsts._VERSION_NO));
//                        } else {
//                            App.getClient().setVersionNo(versionNo);
//                        }
//                        if (2 == status) {
//                            showMsg("有新版本啦,可以进入个人中心更新哦~!");
//                        } else if (4 == status) {
//                            showMsg(result.errorMsg);
//                        }
//                    }
//                });
    }

    private void autoLogin(final String deviceId, final String versionNo) {
//        loginId = (String) SPUtil.get(mContext, AppConsts._UNAME, "");
//        password = (String) SPUtil.get(mContext, AppConsts._PASSWD, "");
//        if (TextUtils.isEmpty(loginId)
//                && TextUtils.isEmpty(password)) {
//            loadSuccess(bundle);
//            return;
//        }
//        clientPlatform = "2";
//        clientInfo = android.os.Build.MODEL;
//        iosServiceToken = "";
//        getDataSupports().getLoginRandom()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<BaseData>() {
//                    @Override
//                    public void onCompleted() {
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        showError(ResponseFilter.onError(e));
//                        loadSuccess(bundle);
//                    }
//
//                    @Override
//                    public void onNext(BaseData result) {
//                        result = ResponseFilter.onSuccess(result);
//                        String randomCode = (String) ((Map<String, Object>) result.data).get("randomCode");
//                        String content = randomCode + password;
//                        byte[] enc = AESUtil.encrypt(content.getBytes(),
//                                AppConsts.ServerConfig.KEY_BYTES);
//                        getDataSupports().login(loginId, Base64.encodeToString(enc,
//                                Base64.DEFAULT), versionNo, deviceId, clientInfo, clientPlatform, iosServiceToken)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(new Subscriber<BaseData>() {
//                                    @Override
//                                    public void onCompleted() {
//                                    }
//
//                                    @Override
//                                    public void onError(Throwable e) {
//                                        showError(ResponseFilter.onError(e));
//                                        loadSuccess(bundle);
//                                    }
//
//                                    @Override
//                                    public void onNext(BaseData result) {
//                                        result = ResponseFilter.onSuccess(result);
//                                        Map<String, Object> data = (Map<String, Object>) result.data;
//                                        App.getClient().setUsername(loginId);
//                                        App.getClient().setPassword(password);
//                                        App.getClient().setSessionId((String) data.get(AppConsts._SESSIONID));
//                                        App.getClient().setName((String) data.get("nickname"));
//                                        getDataSupports().getPersonal()
//                                                .subscribeOn(Schedulers.io())
//                                                .observeOn(AndroidSchedulers.mainThread())
//                                                .subscribe(new Subscriber<BaseData>() {
//                                                    @Override
//                                                    public void onCompleted() {
//                                                        loadSuccess(bundle);
//                                                    }
//
//                                                    @Override
//                                                    public void onError(Throwable e) {
//                                                        showError(ResponseFilter.onError(e));
//                                                        loadSuccess(bundle);
//                                                    }
//
//                                                    @Override
//                                                    public void onNext(BaseData result) {
//                                                        result = ResponseFilter.onSuccess(result);
//                                                        App.getClient().setStatus(AppConsts.AppConfig.STATUS_LOGIN_TRUE);
//                                                        String userInfo = null != result.data ? JsonUtil.toJson(result.data) : "";
//                                                        App.getClient()
//                                                                .setUserInfo(userInfo);
//                                                    }
//                                                });
//                                    }
//                                });
//                    }
//                });
    }
}
