package org.yapp.utils;

import android.text.TextUtils;

import org.yapp.y;

/**
 * ClassName: Log <br>
 * Description: tag自动产生,格式:customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber). <br>
 * <p/>
 * Date: 2015-12-2 下午2:17:16 <br>
 * Author: ysj
 */
public class Log {
    private static final String EX_NULL = "Error message is empty, please check the program output before this";
    public static String customTagPrefix = "y_log";

    private Log() {
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        tag = TextUtils.isEmpty(customTagPrefix) ? tag : customTagPrefix + ":" + tag;
        return tag;
    }

    public static void d(String content) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.d(tag, check(content));
    }

    public static void d(String content, Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.d(tag, check(content), tr);
    }

    public static void e(String content) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.e(tag, check(content));
    }

    public static void e(String content, Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.e(tag, check(content), tr);
    }

    public static void i(String content) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.i(tag, check(content));
    }

    public static void i(String content, Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.i(tag, check(content), tr);
    }

    public static void v(String content) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.v(tag, check(content));
    }

    public static void v(String content, Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.v(tag, check(content), tr);
    }

    public static void w(String content) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.w(tag, check(content));
    }

    public static void w(String content, Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.w(tag, check(content), tr);
    }

    public static void w(Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.w(tag, tr);
    }

    public static void wtf(String content) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.wtf(tag, check(content));
    }

    public static void wtf(String content, Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.wtf(tag, check(content), tr);
    }

    public static void wtf(Throwable tr) {
        if (!y.isDebug()) return;
        String tag = generateTag();
        android.util.Log.wtf(tag, tr);
    }

    private static String check(String msg) {
        return msg != null ? msg : EX_NULL;
    }
}
