package com.dream.example.utils;

import android.app.Application;

/**
 * ClassName: PermissionUtil <br> 
 * Description: 权限工具类. <br> 
 * Date: 2016-3-25 下午4:17:24 <br> 
 * 
 * @author ysj
 * @version  
 * @since JDK 1.7
 */
public class PermissionUtil {
	// 定位需要权限
	private static final String[] PERMISSIONS_LOACATION = {
			"android.permission.ACCESS_COARSE_LOCATION",
			"android.permission.ACCESS_FINE_LOCATION" };
	
	// 相机需要权限
	private static final String[] PERMISSIONS_CAMERA = {
		"android.permission.CAMERA",
		"android.hardware.camera",
		"android.hardware.camera.autofocus"};
	
	// 录音需要权限
	private static final String[] PERMISSIONS_RECORD_AUDIO = {
		"android.permission.WRITE_EXTERNAL_STORAGE",
		"android.permission.RECORD_AUDIO"};
	
	/**
	 * checkRecoreAudio:(检查录音权限). <br> 
	 * 
	 * @author ysj 
	 * @param application
	 * @return 
	 * @since JDK 1.7
	 * date: 2016-3-25 下午4:29:32 <br>
	 */
	public static boolean checkRecoreAudio(Application application){
		return check(application,PERMISSIONS_RECORD_AUDIO);
	}
	
	/**
	 * checkCamera:(检查相机权限). <br> 
	 * 
	 * @author ysj 
	 * @param application
	 * @return 
	 * @since JDK 1.7
	 * date: 2016-3-25 下午4:22:26 <br>
	 */
	public static boolean checkCamera(Application application){
		return check(application,PERMISSIONS_CAMERA);
	}
	
	/**
	 * checkLocation:(检查定位服务权限). <br> 
	 * 
	 * @author ysj 
	 * @param application
	 * @return 
	 * @since JDK 1.7
	 * date: 2016-3-25 下午4:22:26 <br>
	 */
	public static boolean checkLocation(Application application){
		return check(application,PERMISSIONS_LOACATION);
	}
	
	/**
	 * check:(检查权限). <br> 
	 * 
	 * @author ysj  
	 * @since JDK 1.7
	 * date: 2016-3-25 下午4:18:20 <br>
	 */
	private static boolean check(Application application,String[] premissions){
		for (int i = 0; i < premissions.length; i++) {
			int check = application.checkPermission(premissions[i],
					android.os.Process.myPid(), android.os.Process.myUid());
			if (check == -1) {
				return false;
			}
		}
		return true;
	}
		
}
