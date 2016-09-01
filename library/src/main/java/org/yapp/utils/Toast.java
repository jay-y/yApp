package org.yapp.utils;

import android.content.Context;
import android.view.Gravity;

import org.yapp.y;

/**
 * ClassName: Toast <br>
 * Description: Android提示框工具包. <br>
 * Date: 2015-2-6 下午12:17:40 <br>
 * Author: ysj
 */
public class Toast {
    private static final Object lock = new Object();
    private static Context ctx;

    /**
     * init:(初始化提示工具类). <br>
     *
     * @param context
     * @author ysj
     * @since JDK 1.7
     * date: 2016-1-16 下午5:42:42 <br>
     */
    public static void init(Context context) {
        ctx = context;
    }

    /**
     * 上方位置提示(Long)
     *
     * @param message
     */
    public static void showMessageForTopLong(String message) {
        execute(Gravity.TOP, message, android.widget.Toast.LENGTH_LONG);
    }

    /**
     * 中间位置提示(Long)
     *
     * @param message
     */
    public static void showMessageForCenterLong(String message) {
        execute(Gravity.CENTER, message, android.widget.Toast.LENGTH_LONG);
    }

    /**
     * 下方位置提示(Long)
     *
     * @param message
     */
    public static void showMessageForButtomLong(String message) {
        execute(Gravity.BOTTOM, message, android.widget.Toast.LENGTH_LONG);
    }

    /**
     * 上方位置提示(Short)
     *
     * @param message
     */
    public static void showMessageForTopShort(String message) {
        execute(Gravity.TOP, message, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 中间位置提示(Short)
     *
     * @param message
     */
    public static void showMessageForCenterShort(String message) {
        execute(Gravity.CENTER, message, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * 下方位置提示(Short)
     *
     * @param message
     */
    public static void showMessageForButtomShort(String message) {
        execute(Gravity.BOTTOM, message, android.widget.Toast.LENGTH_SHORT);
    }

    /**
     * execute:(执行). <br>
     *
     * @author ysj
     * @since JDK 1.7 date: 2015-12-3 上午11:41:02 <br>
     */
    private static void execute(final int gravityType, final String message, final int timeType) {
        try {
            if (ctx == null) {
                init(y.app());
            }
            y.task().run(new Runnable() {
                @Override
                public void run() {
                    y.task().post(new Runnable() {
                        @Override
                        public void run() {
                            synchronized (lock) {
                                android.widget.Toast toast = android.widget.Toast.makeText(ctx, message, timeType);
                                toast.setGravity(gravityType, 0, 0);
                                toast.show();
                                toast = null;
                            }
                        }
                    });
                }
            });
        } catch (Exception e) {
            Log.e(e.getMessage());
        }
    }

    private Toast() {
    }
}