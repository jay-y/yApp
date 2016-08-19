package org.yapp.ex;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.yapp.ExceptionManager;
import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.y;

import java.io.IOException;

/**
 * Description: 异常处理实现. <br>
 * Date: 2016/1/18 18:07 <br>
 * <p/>
 * Author: user
 */
public class ExceptionManagerImpl implements ExceptionManager {
	private static final String EX_KEY = "y_ex_hander";
	private static final String EX_STATUS_KEY = "y_ex_status";
	private static ExceptionManagerImpl instance; // 单例模式
	private Context context; // 程序Context对象
	private Class<Activity> activity; // 程序指定启动Activity
	private SharedPreferences sharedPreferences;
	private int status;
	private Callback.HandlerCallback<Throwable> handler;

	private Thread.UncaughtExceptionHandler defalutHandler; // 系统默认的UncaughtException处理类

	private ExceptionManagerImpl() {
	}

	public static void registerInstance() {
		if (instance == null) {
			synchronized (ExceptionManagerImpl.class) {
				if (instance == null) {
					instance = new ExceptionManagerImpl();
				}
			}
		}
		y.Core.setExceptionManager(instance);
	}

	/**
	 * 异常处理初始化
	 * @param context
	 */
	@Override
	public void init(Context context) {
		this.context = context;
		this.sharedPreferences = context.getSharedPreferences(EX_KEY,
				Context.MODE_PRIVATE);
		// 获取系统默认的UncaughtException处理器
		defalutHandler = Thread.getDefaultUncaughtExceptionHandler();
		// 设置该CrashHandler为程序的默认处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/**
	 * 异常处理初始化
	 * @param context
	 * @param activity
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init(Context context, Class<?> activity) {
		init(context);
		this.activity = (Class<Activity>) activity;
		this.handler = null;
	}

	/**
	 * 异常处理初始化
	 * @param context
	 * @param handler
	 */
	@Override
	public void init(Context context,Callback.HandlerCallback<Throwable> handler) {
		init(context);
		this.activity = null;
		this.handler = handler;
	}

	/**
	 * 缓存库异常状态值读取 (0:正常 非0:异常)
	 * 注:使用后将重置缓存库状态
	 * @return
	 */
	@Override
	public int status() {
		this.status = sharedPreferences.getInt(EX_STATUS_KEY, 0);
		if (this.status != 0) {
			saveExStatus(0);
		}
		return status;
	}

	/**
	 * 获取当前状态值
	 * @return
	 */
	@Override
	public int getStatus() {
		return status;
	}

	/**
	 * 设置当前状态值
	 * @param status
	 */
	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 当UncaughtException发生时会转入该函数来处理
	 */
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 自定义错误处理
		boolean res = handleException(ex);
		if (!res && defalutHandler != null) {
			// 如果用户没有处理则让系统默认的异常处理器来处理
			defalutHandler.uncaughtException(thread, ex);
		} else {
			saveExStatus(ex.hashCode());
			// 处理异常回调
			if (handler != null) {
				handler.onHandle(ex);
			}else if (activity != null
					&& status == 0) {
				Intent intent = new Intent(context, activity);
				PendingIntent restartIntent = PendingIntent.getActivity(
						context, 0, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
				AlarmManager mgr = (AlarmManager) context
						.getSystemService(Context.ALARM_SERVICE);
				mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
						restartIntent); // 1秒钟后重启应用
			}
			// 杀死线程
			android.os.Process.killProcess(android.os.Process.myPid());
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return false;
		}
		Log.e("###### Exception Start #####");
		Log.e(ex.getMessage(), ex);
		Log.e("###### Exception End #####");
		return true;
	}

	/**
	 * 非法参数异常
	 * @param msg
	 * @param params
	 */
	public void illegalArgument(String msg, Object... params)
	{
		throw new IllegalArgumentException(String.format(msg, params));
	}

	/**
	 * 运行异常
	 * @param msg
	 * @param params
	 */
	public void runtime(String msg, Object... params)
	{
		throw new RuntimeException(String.format(msg, params));
	}

	/**
	 * IO异常
	 * @param msg
	 * @param params
	 */
	public void io(String msg, Object... params)
	{
		try {
			throw new IOException(String.format(msg, params));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存异常状态
	 * @param code
	 */
	private void saveExStatus(int code) {
		SharedPreferences.Editor edit = sharedPreferences.edit();
		edit.putInt(EX_STATUS_KEY, code);
		edit.commit();
	}
}
