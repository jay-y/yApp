package com.dream.example.data.support;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Description: 地理请求相关API. <br>
 * Date: 2016/4/7 11:09 <br>
 * Author: ysj
 */
public interface GeoGoderSupports {

    @GET(AppConsts.GeoConfig.API_V1)
    Observable<Map<String,Object>> getLocation(@Query("coords") String coords);

    @GET(AppConsts.GeoConfig.API_V2)
    Observable<Map<String,Object>> getLocationDetails(@Query("location") String location);

    @GET(AppConsts.GeoConfig.API_IP)
    Observable<Map<String,Object>> getLocationByIp(@Query("ip") String ip);
}
