package com.dream.example.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import java.lang.reflect.Field;

/**
 * Description: 应用启动图标未读消息数显示 工具类(效果如：QQ、微信、未读短信 等应用图标)<br>
 * Date: 2016/4/14 9:43 <br>
 * Author: ysj
 */
public class BadgeUtil {
    private static final int INFO_RES_ID = android.R.drawable.ic_dialog_info;

    private static final String INFO_TITLE= "您有%d条未读消息";

    private static final String INFO_CONTENT = "您有%d条未读消息";

    /**
     * Set badge count
     *
     * @param context The context of the application package.
     * @param count   Badge count to be set
     */
    public static void setBadgeCount(Context context, int count) {
        if (count <= 0) {
            count = 0;
        } else {
            count = Math.max(0, Math.min(count, 99));
        }
        if (Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
            sendToXiaoMi(context, count);
        } else if (Build.MANUFACTURER.equalsIgnoreCase("sony")) {
            sendToSony(context, count);
        } else if (Build.MANUFACTURER.toLowerCase().contains("samsung")) {
            sendToSamsumg(context, count);
        } else {
            sendNotification(context,count);
        }
    }

    /**
     * Reset badge count. The badge count is set to "0"
     * @param context
     */
    public static void resetBadgeCount(Context context) {
        setBadgeCount(context, 0);
    }

    /**
     * Retrieve launcher activity name of the application from the context
     *
     * @param context The context of the application package.
     * @return launcher activity name of this application. From the
     * "android:name" attribute.
     */
    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();

        Intent intent = new Intent(Intent.ACTION_MAIN);
        // To limit the components this Intent will resolve to, by setting an
        // explicit package name.
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        // All Application must have 1 Activity at least.
        // Launcher activity must be found!
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);

        // get a ResolveInfo containing ACTION_MAIN, CATEGORY_LAUNCHER
        // if there is no Activity which has filtered by CATEGORY_DEFAULT
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }

        return info.activityInfo.name;
    }

    /**
     * 发送通知(其他手机使用通知形式)
     * @param context
     * @param number
     */
    private static void sendNotification(Context context, int number){
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(String.format(INFO_TITLE,number));
        builder.setTicker(String.format(INFO_CONTENT,number));
        builder.setAutoCancel(true);
        builder.setSmallIcon(INFO_RES_ID);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);
        nm.notify(101010, builder.build());
    }

    /**
     * 主要适用于小米
     * @param context
     * @param number
     */
    private static void sendToXiaoMi(Context context, int number) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = null;
        boolean isMiUIV6 = true;
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            builder.setContentTitle(String.format(INFO_TITLE,number));
            builder.setTicker(String.format(INFO_CONTENT,number));
            builder.setAutoCancel(true);
            builder.setSmallIcon(INFO_RES_ID);
            builder.setDefaults(Notification.DEFAULT_LIGHTS);
            notification = builder.build();

            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);

            field.set(miuiNotification, number);// 设置信息数
            field = notification.getClass().getField("extraNotification");
            field.setAccessible(true);

            field.set(notification, miuiNotification);
        } catch (Exception e) {
            e.printStackTrace();
            //miui 6之前的版本
            isMiUIV6 = false;
            Intent localIntent = new Intent("android.intent.action.APPLICATION_MESSAGE_UPDATE");
            localIntent.putExtra("android.intent.extra.update_application_component_name", context.getPackageName() + "/" + getLauncherClassName(context));
            localIntent.putExtra("android.intent.extra.update_application_message_text", number);
            context.sendBroadcast(localIntent);
        } finally {
            if (notification != null && isMiUIV6) {
                //miui6以上版本需要使用通知发送
                nm.notify(101010, notification);
            }
        }

    }

    /**
     * 主要适用于索尼
     * @param context
     * @param number
     */
    private static void sendToSony(Context context, int number) {
        boolean isShow = true;
        if (0 == number) {
            isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE", isShow);//是否显示
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME", getLauncherClassName(context));//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", number);//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());//包名
        context.sendBroadcast(localIntent);
    }

    /**
     * 主要适用于三星
     * @param context
     * @param number
     */
    private static void sendToSamsumg(Context context, int number) {
        Intent localIntent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
        localIntent.putExtra("badge_count", number);//数字
        localIntent.putExtra("badge_count_package_name", context.getPackageName());//包名
        localIntent.putExtra("badge_count_class_name", getLauncherClassName(context)); //启动页
        context.sendBroadcast(localIntent);
    }
}
