package com.dream.example.data.support;

import org.yapp.DbManager;
import org.yapp.ex.DbException;
import org.yapp.utils.Log;

/**
 * Description: 应用常量集. <br>
 * Date: 2016/3/17 17:52 <br>
 * Author: ysj
 */
public class AppConsts {
    public static final String _ANDROID = "Android";
    public static final String _NULL = "";
    public static final String _YES = "yes";
    public static final String _NO = "no";
    public static final String _ID = "id";
    public static final String _DATA = "data";
    public static final String _LOG = "log";
    public static final String _KEY = "key";
    public static final String _TYPE = "type";
    public static final String _VALUE = "value";
    public static final String _SERVER_ADDRESS = "serverAddress";
    public static final String _SESSIONID = "sessionId";
    public static final String _STATUS = "status";
    public static final String _RESULT = "data";
    public static final String _MSG = "msg";
    public static final String _INFO = "info";
    public static final String _SUCCESS = "success";
    public static final String _ERROR_CODE = "errorCode";
    public static final String _ERROR_MSG = "errorMsg";
    public static final String _UNAME = "uname";
    public static final String _PASSWD = "passwd";
    public static final String _VERSION_NO = "versionNo";

    /**
     * ClassName: AppConfig <br>
     * Description: 应用配置常量. <br>
     * Date: 2016-6-21 上午10:50:29 <br>
     * Author： ysj
     */
    public static class AppConfig{
        public static final int STATUS_LOGIN_FALSE = 0; // 未登录
        public static final int STATUS_LOGIN_TRUE = 1; // 已登录
        public static final int STATUS_SIGN_FALSE = 2; // 未签到
        public static final int STATUS_SIGN_TRUE = 3; // 已签到

        public static final String PATH_FILE="file://";
        public static final String PATH_HTTP="http://";
        public static final String PATH_HTTPS="https://";

        // 是否第一次使用App
        public static final String PARAM_HASAPP = "hasApp";
        public static final String PARAM_USER = "userInfo";

        public static final String CACHE_IMG = "img";
    }

    /**
     * ClassName: ServerConfig <br>
     * Description: 服务器配置常量. <br>
     * Date: 2015-6-16 上午10:50:29 <br>
     * Author： ysj
     */
    public static class ServerConfig{
        // Main地址
        public static String MAIN_HOST = "http://mobile.safehy.com/";
        public static String MAIN_HOST_PRIMARY = "http://mobile.safehy.com";
        // Gank地址
        public static String GANK_HOST = "https://gank.io/api/";
        // 163地址
        public static String NEWS_HOST = "http://c.m.163.com/";

        // 安全中心
        public static final String WEBSITES_SECURITYCENTER = MAIN_HOST+"securityCenter";
        // 帮助答疑
        public static final String WEBSITES_ANSWERQUESTIONS = MAIN_HOST+"answerQuestions";
        // 服务条款
        public static final String WEBSITES_TERMSOFSERVICE = MAIN_HOST+"termsOfService";

        // 根据属性获取指定数量数据(GANK)
        public static final String API_GANK_DATA_V0="data/{type}/{pagesize}/{page}";

        // http://c.m.163.com/nc/article/list/T1348654060988/0-10.html
        public static final String API_NEWS_LIST = "nc/article/list/{id}/{pageNo}-{pageSize}.html";

        // 登录
        public static final String API_A_LOGIN = "a/login";
        // 登出
        public static final String API_A_LOGOUT = "a/logout";

        // 密码加密的秘钥
        public static final byte[] KEY_BYTES = { 0x73 ,0X6D ,0X6B ,0X6C ,0X64 ,0X6F ,0X73 ,0X70 ,0X64 ,0X6F ,0X73 ,0X6C ,0X64 ,0X61 ,0X61 ,0X61 };
    }

    /**
     * ClassName: DbConfig <br>
     * Description: 定位请求配置常量. <br>
     * Date: 2015-6-16 上午10:50:29 <br>
     * Author： ysj
     */
    public static class GeoConfig{
        public static final String BD_GEOCONV_HOST = "http://api.map.baidu.com/geoconv/";
        public static final String BD_GEOCODER_HOST = "http://api.map.baidu.com/geocoder/";
        public static final String BD_LOCATION_HOST = "http://api.map.baidu.com/location/";

        private static final String PARAM_COORD_TYPE = "coord_type";
        private static final String PARAM_FROM = "from";
        private static final String PARAM_OUTPUT = "output";
        private static final String OUTPUT_VAL = "json";
        private static final String PARAM_AK = "ak";
        private static final String AK_VAL = "NwODwM7eiGOTfneECG1Z7BPp";
        // 定位
        public static final String API_V1="v1/?"+PARAM_FROM+"=1&"+PARAM_AK+"="+AK_VAL;
        // 精确已定位信息
        public static final String API_V2 = "v2/?"+PARAM_AK+"="+AK_VAL+"&"+PARAM_OUTPUT+"="+OUTPUT_VAL;
        // 精确已定位信息
        public static final String API_DEFAULT = "?"+PARAM_COORD_TYPE+"=bd09&"+PARAM_FROM+"="+"1&"+PARAM_AK+"="+AK_VAL;
        // IP定位
        public static final String API_IP = "ip?"+PARAM_AK+"="+AK_VAL;

        /* Set Listener Parameter */
        public static final int LOCATION_LISTEN_MIN_TIME = 5000; // 毫秒
        public static final int LOCATION_LISTEN_MIN_DISTANCE = 1000; // 米
    }


    /**
     * ClassName: DbConfig <br>
     * Description: DB配置常量. <br>
     * Date: 2015-6-16 上午10:50:29 <br>
     * Author： ysj
     */
    public static class DbConfig {
        public static final org.yapp.db.Config ClientDb = new org.yapp.db.Config()
                .setDbName("client_cache.db").setDbVersion(1)
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion,
                                          int newVersion) {
                        try {
                            db.dropDb(); // 默认删除所有表
                        } catch (DbException ex) {
                            Log.e(ex.getMessage(), ex);
                        }
                    }
                });
    }
}
