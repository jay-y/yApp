package com.dream.example.data.support;

/**
 * Description: 请求URL工厂. <br>
 * Date: 2016/4/5 12:56 <br>
 * Author: ysj
 */
public class RequestFactory {
    // 163 news car
    private static final String REQUEST_NES_CAR = "view?";

    // 查看
    private static final String REQUEST_VIEW = "view?";

    public static String view(String u){
        return host() + REQUEST_VIEW + u +"&requestType=0";
    }

    private static String host(){
        return AppConsts.ServerConfig.MAIN_HOST;
    }
}
