package com.dream.example.utils;

import android.text.TextUtils;

import com.dream.example.App;
import com.dream.example.data.BaseData;

import org.yapp.ex.RequestException;
import org.yapp.utils.Log;

import java.net.SocketException;

import retrofit2.adapter.rxjava.HttpException;

/**
 * HttpFilter(响应拦截加工)
 *
 * @author ysj
 * @Date: 2015-1-30 下午2:07:55
 */
public class ResponseFilter {
    public static final int RESPONSE_STATUS_SUCCESS = 0; // 成功
    public static final int RESPONSE_STATUS_ERROR = 1; // 失败
    public static final int RESPONSE_STATUS_EXCEPTION = 2; // 处理异常
    public static final int RESPONSE_STATUS_RANDOM_TIMEOUT = 8; // 获取动态验证码超时
    public static final int RESPONSE_STATUS_TIMEOUT = 9; // 会话超时
    public static final String MSG_ERROR_REQUEST = "请求失败";
    public static final String MSG_ERROR_RESPONSE_3XX = "请求重定向";
    public static final String MSG_ERROR_RESPONSE_4XX = "请求错误";
    public static final String MSG_ERROR_RESPONSE_5XX = "服务器错误";
    //public static final String MSG_ERROR_TIMEOUT = "请求过于频繁,请稍后";
    public static final String MSG_ERROR_SOCKETTIMEOUT = "当前网络状态不佳,请重试";
    public static final String MSG_ERROR_NETWORK = "当前网络状态不佳,请检查网络";

    /**
     * onError:(拦截处理错误信息). <br>
     *
     * @param ex 返回数据
     * @author ysj
     * @since JDK 1.7
     * date: 2015-12-9 下午5:20:36 <br>
     */
    public static Throwable onError(Throwable ex) {
        Log.e(ex.getMessage(), ex);
        if (ex instanceof HttpException) {
            int code = ((HttpException) ex).code();
            if (code >= 300 && code < 400) {
                return new RequestException(code, MSG_ERROR_RESPONSE_3XX);
            } else if (code >= 400 && code < 500) {
                return new RequestException(code, MSG_ERROR_RESPONSE_4XX);
            } else if (code >= 500 && code < 600) {
                return new RequestException(code, MSG_ERROR_RESPONSE_5XX);
            }
        } else if (ex instanceof SocketException) {
            return new RequestException(RESPONSE_STATUS_TIMEOUT, MSG_ERROR_SOCKETTIMEOUT);
        } else if (ex instanceof RequestException) {
            return ex;
        } else if (TextUtils.isEmpty(ex.getMessage())) {
            return new RequestException(2, MSG_ERROR_REQUEST);
        }
        return new RequestException(1, MSG_ERROR_REQUEST);
    }

    /**
     * onNext:(拦截加工服务器返回信息). <br>
     *
     * @param result 返回数据
     * @author ysj
     * @since JDK 1.7
     * date: 2015-12-9 下午5:20:36 <br>
     */
    public static <T extends BaseData> T onSuccess(T result) {
        int status = RESPONSE_STATUS_SUCCESS;
        String code = "";
        if (null != result.errorCode
                && !TextUtils.isEmpty(result.errorCode)) {
            code += result.errorCode;
        }
        if (null != result.status
                && !TextUtils.isEmpty(result.status)) {
            status = Integer.valueOf(result.status);
        }
        switch (status) {
            case RESPONSE_STATUS_SUCCESS:
                return result;
            case RESPONSE_STATUS_ERROR:
                throw new RequestException(code, result.errorMsg);
            case RESPONSE_STATUS_EXCEPTION:
                throw new RequestException(code, result.errorMsg);
            case RESPONSE_STATUS_RANDOM_TIMEOUT:
                throw new RequestException(code, result.errorMsg);
            case RESPONSE_STATUS_TIMEOUT:
                App.getClient().setStatus(1);
                throw new RequestException(code, result.errorMsg);
            default:
                if (!TextUtils.isEmpty(result.errorMsg)) {
                    throw new RequestException(code, result.errorMsg);
                }
                return result;
        }
    }
}