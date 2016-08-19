package org.yapp.core.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import org.yapp.core.Application;
import org.yapp.core.presenter.BasePresenter;
import org.yapp.core.ui.abase.BaseActivityApi;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.utils.Toast;
import org.yapp.view.ViewInjector;
import org.yapp.y;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Description: Activity基类 通一编码规范(V7). <br>
 * Date: 2015-6-15 上午10:37:00 <br>
 * Author: ysj
 */
public abstract class BaseAppCompatActivity<P extends BasePresenter> extends AppCompatActivity implements BaseActivityApi{
    public Application app;
    public Menu mMenu;
    public ActionBarDrawerToggle mToggle;

    /**
     * 双击退出函数
     */
    private static boolean isExitAPP = false;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() < 0) return true;
        getMenuInflater().inflate(getMenuRes(), menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (null != mToggle && mToggle.onOptionsItemSelected(item)) {
            return true;
        } else if (android.R.id.home == item.getItemId()) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * set the id of menu
     *
     * @return if values is less then zero ,and the activity will not show menu
     */
    public int getMenuRes() {
        return -1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
    }

    @Override
    public void onDestroy() {
        app = null;
        mMenu = null;
        mToggle = null;
        onClear();
        super.onDestroy();
        System.gc();
    }

    /**
     * startActivity
     *
     * @param clazz
     */
    public void go(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    public void go(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivity with bundle and delayed
     *
     * @param clazz 需要跳转的Activity
     * @param bundle 携带数据
     * @param delayed 延迟加载时间
     */
    public void go(Class<?> clazz, Bundle bundle,int delayed) {
        final Intent intent = new Intent(this, clazz);
        if(bundle!=null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, delayed * 1000);
    }

    /**
     * startActivity then finish
     *
     * @param clazz
     */
    public void goThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle then finish
     *
     * @param clazz
     * @param bundle
     */
    public void goThenKill(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    /**
     * startActivity with bundle and delayed then finish
     *
     * @param clazz 需要跳转的Activity
     * @param bundle 携带数据
     * @param delayed 延迟加载时间
     */
    public void goThenKill(Class<?> clazz, Bundle bundle,int delayed) {
        final Intent intent = new Intent(this, clazz);
        if(bundle!=null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
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
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    public void goForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle and delayed
     *
     * @param clazz 需要跳转的Activity
     * @param bundle 携带数据
     * @param delayed 延迟加载时间
     */
    public void goForResult(Class<?> clazz,final int requestCode,Bundle bundle,int delayed) {
        final Intent intent = new Intent(this, clazz);
        if(bundle!=null) intent.putExtras(bundle);
        y.task().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivityForResult(intent, requestCode);
            }
        }, delayed * 1000);
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

    /**
     * 初始化
     */
    private void init() {
        if (app == null) app = (Application) getApplication();
        app.addActivity(this);
        ViewInjector.inject(this);
        onBuild();
        onInit();
    }
}
