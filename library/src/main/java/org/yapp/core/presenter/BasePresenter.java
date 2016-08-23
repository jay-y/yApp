package org.yapp.core.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.yapp.core.Application;
import org.yapp.core.ui.abase.BasePresenterApi;
import org.yapp.core.view.IBaseView;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.utils.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description: 主持层抽象基类. <br>
 * Date: 2016/3/14 16:03 <br>
 * Author: ysj
 */
public abstract class BasePresenter<T,A extends Application> implements IBaseView,BasePresenterApi<T> {
    public A app;

    protected Context mContext;

    /**
     * 双击退出函数
     */
    private static boolean isExitAPP = false;

    public BasePresenter() {
    }

    @Override
    public T getContent() {
        return (T) mContext;
    }

    /**
     * 构建
     *
     * @param context activity context
     */
    @Override
    public void onBuild(Context context){
        if (app == null) app = (A)((AppCompatActivity)context).getApplication();
        app.addActivity((AppCompatActivity) context);
        mContext = context;
    }

    @Override
    public void onDestroy() {
        mContext = null;
        app = null;
    }
    /**
     * exitBy2Click:(双击退出). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午6:02:57 <br>
     */
    public void exitBy2Click(Callback.ExecCallback callback) {
        Timer tExit = null;
        if (isExitAPP == false) {
            isExitAPP = true; // 准备退出
            Toast.showMessageForButtomShort("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExitAPP = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            exit(callback);
        }
    }

    /**
     * 退出应用
     * @param callback
     */
    public void exit(Callback.ExecCallback callback) {
        Log.d("Base销毁");
        if (callback != null) {
            callback.run();
        }
        app.clear();
        // kill程序进程
        android.os.Process.killProcess(android.os.Process.myPid());
        // 程序退出
        System.exit(0);
    }
}