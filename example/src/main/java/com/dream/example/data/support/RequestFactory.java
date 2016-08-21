package com.dream.example.data.support;

/**
 * Description: 请求URL工厂. <br>
 * Date: 2016/4/5 12:56 <br>
 * Author: ysj
 */
public class RequestFactory {
    // 签到
    private static final String REQUEST_A_SIGNIN = "a/signIn";
    // 积分规则
    public static final String REQUEST_A_SCORERULE = "a/scoreRule";
    // 当前活动
    private static final String REQUEST_A_ACT_DETAIL = "a/activity/detail?activityId=";

    private static final String REQUEST_ADIMGDOWNLOAD = "adImgDownload?adPosId="; // 下载广告活动图片
    private static final String REQUEST_GETIMG = "getImage?imgUrl="; //获取图片
    // 二维码扫描
    private static final String REQUEST_S0 = "s0?";
    private static final String REQUEST_S1 = "s1?";
    // NFC扫描
    private static final String REQUEST_R1 = "r1?u=";
    // 获取验证码
    private static final String REQUEST_VALIDATECODE = "validateCode";
    // 查看
    private static final String REQUEST_VIEW = "view?";
    // 更新页面
    private static final String REQUEST_NEW_VERSION_INFO = "newVersionInfo?clientPlatform=2&versionNo=";

    public static String signIn(){
        return host()+REQUEST_A_SIGNIN;
    }

    public static String scoreRule(){
        return host()+REQUEST_A_SCORERULE;
    }

    public static String queryActivity(String u){
        return host() + REQUEST_A_ACT_DETAIL + u;
    }

    public static String queryImage(String uri){
        return host() + REQUEST_GETIMG + uri;
    }

    public static String queryAd(String adPosId){
        return host() + REQUEST_ADIMGDOWNLOAD + adPosId;
    }

    public static String queryS0(String u){
        return host() + REQUEST_S0 + u;
    }

    public static String queryS1(String u){
        return host() + REQUEST_S1 + u;
    }

    public static String queryR1(String u){
        return host() + REQUEST_R1 + u;
    }

    public static String newVersionInfo(String u){
        return host() + REQUEST_NEW_VERSION_INFO + u;
    }

    public static String view(String u){
        return host() + REQUEST_VIEW + u +"&requestType=0";
    }

    public static String validateCode(){
        return host()+REQUEST_VALIDATECODE;
    }

    private static String host(){
        return AppConsts.ServerConfig.MAIN_HOST;
    }
}
