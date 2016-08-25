package com.dream.example.data.support;

import com.dream.example.data.BaseData;
import com.dream.example.data.GankData;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Description: 数据支持. <br>
 * Date: 2016/3/14 16:57 <br>
 * Author: ysj
 */
public interface DataSupports {

    /**
     * get request
     *
     * @return
     */
    @GET
    Observable<BaseData> get(@Url String url);

    /**
     * @param id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET(AppConsts.ServerConfig.API_NEWS_LIST)
    Observable<Map<String,Object>> getNews(@Path("id") String id,@Path("pageNo") int pageNo,@Path("pageSize") int pageSize);

    /**
     * @param type
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET(AppConsts.ServerConfig.API_GANK_DATA)
    Observable<GankData> getGankData(@Path("type") String type,@Path("pageNo") int pageNo,@Path("pageSize") int pageSize);

//    /**
//     * 登录
//     *
//     * @param loginId
//     * @param password
//     * @param versionNo
//     * @param deviceToken
//     * @param clientInfo
//     * @param clientPlatform
//     * @param iosServiceToken
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(AppConsts.ServerConfig.API_A_LOGIN)
//    Observable<BaseData> login(@Field("loginId") String loginId, @Field("password") String password,
//                               @Field("versionNo") String versionNo, @Field("deviceToken") String deviceToken,
//                               @Field("clientInfo") String clientInfo, @Field("clientPlatform") String clientPlatform,
//                               @Field("iosServiceToken") String iosServiceToken);
//
//    /**
//     * 登出
//     *
//     * @param sessionId
//     * @return
//     */
//    @FormUrlEncoded
//    @POST(AppConsts.ServerConfig.API_A_LOGOUT)
//    Observable<BaseData> logout(@Field("sid") String sessionId);

}