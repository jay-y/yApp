package org.yapp;

import android.content.Context;

import org.yapp.utils.Callback;

/**
 * ClassName: ExceptionManager <br>
 * Description: 异常处理接口. <br>
 * Date: 2015-12-3 上午10:29:12 <br>
 * Author: ysj
 */
public interface ExceptionManager extends Thread.UncaughtExceptionHandler {
    void init(Context context);

    void init(Context context, Class<?> activity);

    void init(Context context, Callback.HandlerCallback<Throwable> handler);

    /**
     * 缓存库异常状态值读取 (0:正常 非0:异常)
     * 注:使用后将重置缓存库状态
     *
     * @return
     */
    int status();

    /**
     * 获取当前状态值
     *
     * @return
     */
    int getStatus();

    /**
     * 设置当前状态值
     *
     * @param code
     */
    void setStatus(int code);

    /**
     * 非法参数异常
     * @param msg
     * @param params
     */
    public void illegalArgument(String msg, Object... params);

    /**
     * 运行异常
     * @param msg
     * @param params
     */
    public void runtime(String msg, Object... params);

    /**
     * IO异常
     * @param msg
     * @param params
     */
    public void io(String msg, Object... params);
}
