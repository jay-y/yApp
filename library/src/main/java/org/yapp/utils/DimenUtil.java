package org.yapp.utils;

import android.content.Context;

/**
 * Description: DimenUtil. <br>
 * Date: 2016/8/28 23:55 <br>
 * Author: ysj
 */
public class DimenUtil {
    public DimenUtil() {
    }

    public static int dp2px(Context context, float dpValue) {
        return (int) (dpValue * context.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2dp(Context context, float pxValue) {
        return (int) (pxValue / context.getResources().getDisplayMetrics().density + 0.5F);
    }
}
