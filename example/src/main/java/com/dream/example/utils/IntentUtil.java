package com.dream.example.utils;

/**
 * Description: Intent跳转辅助类.(仅适用于该工程) <br>
 * Date: 2016/3/30 17:21 <br>
 * Author: ysj
 */
public class IntentUtil {
    public static final String EXTRA_INDEX = "INDEX";
    public static final String EXTRA_URL = "URL";
    public static final String EXTRA_URLS = "URLS";
    public static final String EXTRA_TITLE = "TITLE";
    public static final String EXTRA_DATA = "DATA";

//    /**
//     * 单图展示
//     *
//     * @param context
//     * @param url
//     */
//    public static void gotoImageDetailActivity(Context context, String url) {
//        Intent intent = new Intent(context, ImageDetailActivity.class);
//        intent.putExtra(EXTRA_URL, url);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 多图展示
//     *
//     * @param context
//     * @param urlList
//     * @param index
//     */
//    public static void gotoImageDetailActivity(Context context, ArrayList<String> urlList, int index) {
//        Intent intent = new Intent(context, ImageDetailActivity.class);
//        intent.putStringArrayListExtra(EXTRA_URLS, urlList);
//        intent.putExtra(EXTRA_INDEX, index);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 网页展示
//     *
//     * @param context
//     * @param url
//     * @param title
//     */
//    public static void gotoWebActivity(Context context, String url, String title) {
//        Intent intent = new Intent(context, WebActivity.class);
//        intent.putExtra(EXTRA_URL, url);
//        intent.putExtra(EXTRA_TITLE, title);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 二维码名片展示
//     *
//     * @param context
//     * @param name
//     * @param vcf
//     */
//    public static void gotoCardActivity(Context context, String name,String vcf,String url) {
//        Intent intent = new Intent(context, CardActivity.class);
//        intent.putExtra(EXTRA_TITLE, name);
//        intent.putExtra(EXTRA_DATA, vcf);
//        intent.putExtra(EXTRA_URL, url);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 信息展示
//     *
//     * @param context
//     * @param data
//     */
//    public static void gotoInformationActivity(Context context, String data) {
//        Intent intent = new Intent(context, InformationActivity.class);
//        intent.putExtra(EXTRA_DATA, data);
//        context.startActivity(intent);
//    }
}
