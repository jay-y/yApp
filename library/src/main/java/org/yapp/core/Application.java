package org.yapp.core;

import android.app.Activity;

import org.yapp.y;

import java.util.Stack;

/**
 * Description: Application. <br>
 * Date: 2016/3/14 16:35 <br>
 * Author: ysj
 */
public abstract class Application extends android.app.Application{
    /**
     * 堆栈存储activity
     * 注:建议只存储launchMode="singleInstance"的Activity
     */
    protected Stack<Activity> mActivityStack;

    @Override
    public void onCreate() {
        super.onCreate();
        build();
        init();
    }

    /**
     * addActivity:(添加Activity到堆栈). <br>
     *
     * @param activity
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:58:07 <br>
     */
    public void addActivity(Activity activity) {
        if (mActivityStack == null) {
            mActivityStack = new Stack<Activity>();
        }
        mActivityStack.add(activity);
    }

    /**
     * finishActivity:(结束指定的Activity). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:58:33 <br>
     */
    public void finishActivity(Activity activity) {
        if (null !=activity) {
            activity.finish();
            mActivityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * killAllActivity:(结束所有堆栈中的Activity). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:58:33 <br>
     */
    public void killAllActivity() {
        if (mActivityStack == null) return;
        for (int i = 0, size = mActivityStack.size(); i < size; i++) {
            if (null != mActivityStack.get(i)) {
                mActivityStack.get(i).finish();
            }
        }
        mActivityStack.clear();
        mActivityStack = null;
    }

    /**
     * clear:(清理). <br>
     *
     * @author ysj
     * @since JDK 1.7
     * date: 2015-6-24 下午5:59:02 <br>
     */
    public void clear() {
        killAllActivity();
    }

    protected abstract void init();

    private void build() {
        y.Core.init(this);
    }
}
