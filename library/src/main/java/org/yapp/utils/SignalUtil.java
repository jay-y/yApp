package org.yapp.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import org.yapp.y;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * ClassName: SignalUtil <br>
 * Description: 网络讯息等工具类. <br>
 * Date: 2015-12-3 下午3:22:03 <br>
 * 
 * @author ysj
 * @version
 * @since JDK 1.7
 */
public class SignalUtil {
	/** 网络不可用 */
	public static final int NONETWORK = 0;
	/** 是wifi连接 */
	public static final int WIFI = 1;
	/** 不是wifi连接 */
	public static final int NOWIFI = 2;

	/**
	 * getLocalIpAddress:(wifi网络IP). <br>
	 * 
	 * @author ysj
	 * @return
	 * @since JDK 1.7 date: 2015-10-17 下午2:50:42 <br>
	 */
	public static String getLocalIpAddress() {
		WifiManager wifiManager = (WifiManager) y.app().getSystemService(
				Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		// 获取32位整型IP地址
		int ipAddress = wifiInfo.getIpAddress();

		// 返回整型地址转换成“*.*.*.*”地址
		return String.format("%d.%d.%d.%d", (ipAddress & 0xff),
				(ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff),
				(ipAddress >> 24 & 0xff));
	}

	/**
	 * getIpAddress:(3G网络IP). <br>
	 * 
	 * @author ysj
	 * @return
	 * @since JDK 1.7 date: 2015-10-17 下午2:50:08 <br>
	 */
	public static String getIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface
					.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf
						.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()
							&& inetAddress instanceof Inet4Address) {
						// if (!inetAddress.isLoopbackAddress() && inetAddress
						// instanceof Inet6Address) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 检查是否有网络
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected() {
		ConnectivityManager mConnectivityManager = (ConnectivityManager) y
				.app().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
		if (mNetworkInfo != null) {
			return mNetworkInfo.isAvailable();
		}
		return false;
	}

	/**
	 * 检验网络连接 并判断是否是wifi连接
	 * 
	 * @param context
	 * @return <li>没有网络：SignalUtil.NONETWORK;</li> <li>wifi连接：SignalUtil.WIFI;</li>
	 *         <li>没有wifi：SignalUtil.NOWIFI</li>
	 */
	public static int checkNetWorkType(Context context) {
		if (!isNetworkConnected()) {
			return SignalUtil.NONETWORK;
		}
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.isConnectedOrConnecting())
			return SignalUtil.WIFI;
		else
			return SignalUtil.NOWIFI;
	}
	
	/**
	 * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
	 * 
	 * @return true 表示开启
	 */
	public static boolean checkGpsOrAGps(){
		LocationManager locationManager = (LocationManager) y.app().getSystemService(Context.LOCATION_SERVICE);
		// 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
		boolean gps = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		// 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
		boolean network = locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		if (gps || network) {
			return true;
		}
		return false;
	}

	private SignalUtil() {
	}
}
