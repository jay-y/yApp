package com.dream.example;import android.app.AlarmManager;import android.app.PendingIntent;import android.content.Context;import android.content.Intent;import android.content.pm.PackageInfo;import android.content.pm.PackageManager;import android.provider.Settings;import android.telephony.TelephonyManager;import android.text.TextUtils;import android.webkit.CookieManager;import android.webkit.CookieSyncManager;import com.dream.example.data.entity.Client;import com.dream.example.data.support.AppConsts;import com.dream.example.ui.activity.MainActivity;import org.yapp.core.Application;import org.yapp.utils.Callback;import org.yapp.utils.Log;import org.yapp.utils.Toast;import org.yapp.y;import java.io.File;/** * ClassName: App <br>  * Description: 应用核心. <br>  * Date: 2015-12-3 下午9:36:50 <br>  * Author: ysj */public class App extends Application {    private static final Object lock = new Object();    // 用户信息    private static Client client = null;    // 1-不更新 2-非强制更新 3-强制更新 4-其他(多为错误信息)    private static int status = 1;    public static int getStatus() {        return status;    }    public static void setStatus(int status) {        App.status = status;    }    public static void setClient(Client c) {        synchronized (lock) {            if (c == null) {                client = new Client("0");            } else {                client = c;            }        }    }    public static Client getClient() {        if (client == null) {            synchronized (lock) {                client = new Client("0");            }        }        return client;    }    /**     * 获取DeviceId     *     * @return String     */    public String getDeviceId() {        TelephonyManager tm = (TelephonyManager) this                .getSystemService(Context.TELEPHONY_SERVICE);        String str = "";        str = tm.getDeviceId();        if (TextUtils.isEmpty(str))            str = Settings.Secure                    .getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);        return str;    }    /**     * getVersionName:(获取当前程序的版本). <br>     *     * @author ysj     * @return Integer     * @since JDK 1.7 date: 2015-6-24 下午6:02:30 <br>     */    public String getVersionName() {        // 获取packagemanager的实例        PackageManager packageManager = getPackageManager();        // getPackageName()是你当前类的包名，0代表是获取版本信息        try {            PackageInfo packInfo = packageManager.getPackageInfo(                    getPackageName(), 0);            return packInfo.versionName;        } catch (PackageManager.NameNotFoundException e) {            e.printStackTrace();        }        return "0";    }    /**     * synCookies:(同步Cookies,暂时仅存储sessionId). <br>     *     * @author ysj     * @param url     * @since JDK 1.7 date: 2015-9-28 下午2:12:25 <br>     */    public void synCookies(String url) {        clearCookies();        if (getClient() != null                && !TextUtils.isEmpty(getClient().getSessionId())) {            String cookieString = AppConsts._SESSIONID + "="                    + getClient().getSessionId();            CookieManager.getInstance().setCookie(                    AppConsts.ServerConfig.MAIN_HOST, cookieString);            CookieManager.getInstance().setCookie(url, cookieString);            CookieSyncManager.getInstance().sync();        }    }    /**     * clearCookies:(清理Cookies). <br>     *     * @author user     * @since JDK 1.7 date: 2015-9-18 下午2:46:35 <br>     */    public void clearCookies() {        CookieSyncManager.createInstance(this);        CookieManager.getInstance().removeSessionCookie();        CookieManager.getInstance().removeAllCookie();        CookieSyncManager.getInstance().startSync();    }    /**     * clear:(清理). <br>     *     * @author ysj     * @since JDK 1.7     * date: 2015-6-24 下午5:59:02 <br>     */    public void clear() {        clearCookies();        super.clear();    }    @Override    protected void init() {        y.setDebug(true);        Log.d("App启动");        // 异常处理初始化        y.ex().init(this, new Callback.HandlerCallback<Throwable>() {            @Override            public void onHandle(Throwable ex) {                File log = null;                try {//                    int stackTraceSize = ex.getStackTrace().length;//                    String errorMsg = ex.getLocalizedMessage() + "-&&-";//                    for (int i = 0; i < stackTraceSize; i++) {//                        StackTraceElement stack = ex.getStackTrace()[i];//                        errorMsg += stack.toString() + "\n";//                        if (errorMsg.length() >= 254) {//                            errorMsg += (stackTraceSize - i)//                                    + " more...\n";//                            break;//                        }//                    }//                    log = new File(FileUtil.getCacheDir(AppConsts._LOG),//                            "error.log");//                    IOUtil.writeStr(new FileOutputStream(log), errorMsg);                } catch (Exception e) {                    Log.e(e.getMessage(), e);                } finally {//                    log = null;                    if (y.ex().getStatus() == 0) {                        Intent intent = new Intent(App.this, MainActivity.class);                        PendingIntent restartIntent = PendingIntent                                .getActivity(App.this, 0, intent,                                        Intent.FLAG_ACTIVITY_NEW_TASK);                        AlarmManager mgr = (AlarmManager) App.this                                .getSystemService(Context.ALARM_SERVICE);                        mgr.set(AlarmManager.RTC,                                System.currentTimeMillis() + 3000,                                restartIntent); // 3秒钟后重启应用                    }                    // 杀死线程                    android.os.Process.killProcess(android.os.Process                            .myPid());                }            }        });        if(y.ex().status() != 0){            Toast.showMessageForCenterShort("有异常退出");        }//        LocationSupports.getInstance().refreshLaction();//        ImageLoader.build(this, HttpFactory.getDefaultDownLoader());//        // Fresco库初始化加载//        Fresco.initialize(this);    }}