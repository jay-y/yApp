package com.dream.example.data.support;

import com.dream.example.data.BaseData;

import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
     * 获取登录随机码
     *
     * @return
     */
    @GET(AppConsts.ServerConfig.API_LOGINRANDOM)
    Observable<BaseData> getLoginRandom();

    /**
     * 获取个人信息
     *
     * @return
     */
    @GET(AppConsts.ServerConfig.API_A_PERSONAL)
    Observable<BaseData> getPersonal();

    /**
     * 修改头像
     *
     * @param photo
     * @return
     */
    @Multipart
    @POST(AppConsts.ServerConfig.API_A_CHANGE_PHOTO)
    Observable<BaseData> changePhoto(@Part("file") RequestBody photo);

    /**
     * 修改密码
     *
     * @param oldPasswd
     * @param newPasswd
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_A_CHANGE_PWD)
    Observable<BaseData> changePwd(@Field("oldPasswd") String oldPasswd, @Field("newPasswd") String newPasswd);

    /**
     * 获取广告位信息
     *
     * @param adPosType
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_QUERY_ADVERTISEMENT)
    Observable<BaseData> getAds(@Field("adPosType") String adPosType);

    /**
     * 版本检查
     *
     * @param versionNo
     * @param clientPlatform
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_VERSIONCHECK)
    Observable<BaseData> versionCheck(@Field("versionNo") String versionNo, @Field("clientPlatform") String clientPlatform);

    /**
     * 登录
     *
     * @param loginId
     * @param password
     * @param versionNo
     * @param deviceToken
     * @param clientInfo
     * @param clientPlatform
     * @param iosServiceToken
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_A_LOGIN)
    Observable<BaseData> login(@Field("loginId") String loginId, @Field("password") String password,
                               @Field("versionNo") String versionNo, @Field("deviceToken") String deviceToken,
                               @Field("clientInfo") String clientInfo, @Field("clientPlatform") String clientPlatform,
                               @Field("iosServiceToken") String iosServiceToken);


    /**
     * 登出
     *
     * @param sessionId
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_A_LOGOUT)
    Observable<BaseData> logout(@Field("sid") String sessionId);

    /**
     * 注册第一步
     *
     * @param mobile
     * @param imageCode
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_REGISTER_STEP1)
    Observable<BaseData> regStep1(@Field("mobile") String mobile, @Field("imageCode") String imageCode);

    /**
     * 注册第二步
     *
     * @param newPassword
     * @param mobileVerifyCode
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_REGISTER_STEP2)
    Observable<BaseData> regStep2(@Field("newPassword") String newPassword, @Field("mobileVerifyCode") String mobileVerifyCode);

    /**
     * 忘记密码修改第一步
     *
     * @param mobile
     * @param imageCode
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_FORGET_STEP1)
    Observable<BaseData> forgStep1(@Field("mobile") String mobile, @Field("imageCode") String imageCode);

    /**
     * 忘记密码修改第二步
     *
     * @param newPassword
     * @param mobileVerifyCode
     * @return
     */
    @FormUrlEncoded
    @POST(AppConsts.ServerConfig.API_FORGET_STEP2)
    Observable<BaseData> forgStep2(@Field("newPassword") String newPassword, @Field("mobileVerifyCode") String mobileVerifyCode);
}
