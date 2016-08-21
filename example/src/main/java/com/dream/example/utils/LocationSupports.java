package com.dream.example.utils;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;

import com.dream.example.App;
import com.dream.example.data.support.AppConsts;
import com.dream.example.data.support.GeoGoderSupports;
import com.dream.example.data.support.HttpFactory;

import org.yapp.utils.Callback;
import org.yapp.utils.Log;
import org.yapp.utils.SignalUtil;
import org.yapp.y;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Description: 定位支持工具类(仅限该工程使用). <br>
 * Date: 2016/4/7 14:25 <br>
 * Author: ysj
 */
public class LocationSupports {
    private static LocationSupports instance; // 单例模式
    /**
     * 位置提供相关(位置、管理、监听)
     */
    private LocationManager locMgr;
    private LocationListener locationListener;
    private String provider;
    /**
     * 位置提供相关(条件指定)
     */
    private static Criteria criteria;

    /**
     * 位置信息
     */
    private Location location = null;

    public int getLocationType() {
        return locationType;
    }

    private int locationType = 1;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private LocationSupports() {
    }

    public static LocationSupports getInstance() {
        if (instance == null) {
            synchronized (LocationSupports.class) {
                if (instance == null) {
                    instance = new LocationSupports();
                }
            }
        }
        return instance;
    }

    /**
     * releaseInstance:(释放实例). <br>
     *
     * @author ysj
     * @since JDK 1.7 date: 2015-6-24 下午6:04:21 <br>
     */
    public static void releaseInstance() {
        instance = null;
    }

    public void refreshLaction(){
        this.refreshLaction(null);
    }

    /**
     * refreshLaction:(刷新并获取位置信息). <br>
     * 当位置信息为空时可用.<br>
     *
     * @param callback
     * @author ysj
     * @since JDK 1.7 date: 2015-6-24 下午6:01:16 <br>
     */
    public void refreshLaction(Callback.CommonCallback callback) {
        if (null == y.app())
            throw new RuntimeException("Please LocationSupports.init(app)");
        if (!PermissionUtil.checkLocation(y.app())) {
            Log.e("获取定位信息未授权");
            return;
        }
//        if (app.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && app.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            return;
//        }
        if (null != location && !TextUtils.isEmpty(provider)
                && location == locMgr.getLastKnownLocation(provider)) {
            Log.d("位置信息未更新");
            return;
        }
        locMgr = (LocationManager) y.app().getSystemService(Context.LOCATION_SERVICE);
        locationListener = DefinedLocationListener.getInstance();
        criteria = new Criteria();
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快)
        boolean isGps = locMgr.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位)
        boolean isNetWork = locMgr
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        /** 根据提供器状态确定精确度 **/
        if (isGps && isNetWork) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);// 粗略精确度
            locationType = 2;
        } else if (isGps) {
            criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精确度
            locationType = 1;
        } else if (isNetWork) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);// 粗略精确度
            locationType = 2;
        }
        Log.d("GPS:" + isGps + " NetWork:" + isNetWork);
        criteria.setAltitudeRequired(false);// 不要求海拔
        criteria.setBearingRequired(false);// 不要求方位
        criteria.setCostAllowed(true);// 允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
        // 从可用的位置提供器中，匹配以上标准的最佳位置提供器
        provider = locMgr.getBestProvider(criteria, true);
        Log.d("最佳位置提供器:" + provider);
        if (locMgr != null) {
            if (location == null && !TextUtils.isEmpty(provider)) {
                // 获得最后一次变化的位置
                location = locMgr.getLastKnownLocation(provider);
                locMgr.requestLocationUpdates(provider,
                        AppConsts.GeoConfig.LOCATION_LISTEN_MIN_TIME,
                        AppConsts.GeoConfig.LOCATION_LISTEN_MIN_DISTANCE, locationListener);
            }
            if (location == null) {
                // 获取所有可用的位置提供器
                List<String> providerList = locMgr.getAllProviders();
                for (String providerBak : providerList) {
                    provider = providerBak;
                    Log.d("位置提供器:" + provider);
                    if (location == null
                            && !TextUtils.isEmpty(provider)) {
                        // 获得最后一次变化的位置
                        location = locMgr.getLastKnownLocation(provider);
                    } else {
                        break;
                    }
                }
                providerList = null;
            }
            if (location != null
                    && TextUtils.isEmpty(App.getClient().getLocation())) {
                locationListener.onLocationChanged(location);
            }
            if (!TextUtils.isEmpty(provider)) {
                // 开启位置更新监听
                locMgr.requestLocationUpdates(provider,
                        AppConsts.GeoConfig.LOCATION_LISTEN_MIN_TIME,
                        AppConsts.GeoConfig.LOCATION_LISTEN_MIN_DISTANCE, locationListener);
                if (location == null) {
                    if (null != callback)
                        callback.onError(new RuntimeException("Location Update Failure"), true);
                }
            } else {
                locMgr = null;
                locationListener = null;
            }
        }
        /** 回收处理 **/
        criteria = null;
        provider = null;
        System.gc();
        return;
    }

    /**
     * ClassName: DefinedLocationListner <br>
     * Description: 自定义位置监听器. <br>
     * Date: 2015-6-15 上午10:37:00 <br>
     *
     * @author ysj
     * @version 1.0
     * @since JDK 1.7
     */
    public static class DefinedLocationListener implements LocationListener {
        /**
         * 标识
         */
        private static DefinedLocationListener instance;

        private DefinedLocationListener() {
        }

        public static DefinedLocationListener getInstance() {
            if (instance == null) {
                synchronized (DefinedLocationListener.class) {
                    if (instance == null) {
                        instance = new DefinedLocationListener();
                    }
                }
            }
            return instance;
        }

        /**
         * releaseInstance:(释放实例). <br>
         *
         * @author ysj
         * @since JDK 1.7 date: 2015-6-24 下午6:04:21 <br>
         */
        public static void releaseInstance() {
            instance = null;
        }

        @Override
        public void onLocationChanged(Location location) {
            // 基于新位置触发
            if (location != null) {
                if (SignalUtil.isNetworkConnected()) {
                    requestLocation();
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 提供器硬件状态改变触发
        }

        @Override
        public void onProviderEnabled(String provider) {
            // 提供器被启用触发
        }

        @Override
        public void onProviderDisabled(String provider) {
            // 提供器被禁用触发
        }

        private void requestLocation() {
            Location mLocation = LocationSupports.getInstance().getLocation();
            if (null != LocationSupports.getInstance().getLocation()) {
                GeoGoderSupports mSupports = HttpFactory.getDataSupports(AppConsts.GeoConfig.BD_GEOCONV_HOST, GeoGoderSupports.class);
                mSupports.getLocation(mLocation.getLongitude()+","+ mLocation.getLatitude())
                        .map(new Func1<Map<String, Object>, List<Map<String, Object>>>() {
                            @Override
                            public List<Map<String, Object>> call(Map<String, Object> map) {
                                return (List<Map<String, Object>>) map.get("result");
                            }
                        })
                        .map(new Func1<List<Map<String, Object>>, Map<String, Object>>() {
                            @Override
                            public Map<String, Object> call(List<Map<String, Object>> result) {
                                return result.get(0);
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Map<String, Object>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(e.getMessage(), e);
                            }

                            @Override
                            public void onNext(Map<String, Object> data) {
                                Log.d(data.toString());
                                String lgt = String.valueOf(data.get("x"));
                                String lat = String.valueOf(data.get("y"));
                                if (!TextUtils.isEmpty(lgt) && !TextUtils.isEmpty(lat)) {
                                    App.getClient().setLocLongitude(Double.valueOf(lgt));
                                    App.getClient().setLocLatitude(Double.valueOf(lat));
                                }
                                GeoGoderSupports mSupports = HttpFactory.getDataSupports(AppConsts.GeoConfig.BD_GEOCODER_HOST, GeoGoderSupports.class);
                                mSupports.getLocationDetails(Double.valueOf(lat) + "," + Double.valueOf(lgt))
                                        .map(new Func1<Map<String, Object>, Map<String, Object>>() {
                                            @Override
                                            public Map<String, Object> call(Map<String, Object> map) {
                                                return (Map<String, Object>) map.get("result");
                                            }
                                        })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(new Subscriber<Map<String, Object>>() {
                                            @Override
                                            public void onCompleted() {

                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.e(e.getMessage(), e);
                                            }

                                            @Override
                                            public void onNext(Map<String, Object> data) {
                                                Log.d(data.toString());
                                                Map<String, Object> addressComponent = (Map<String, Object>) data.get("addressComponent");
                                                App.getClient().setProvince(String.valueOf(addressComponent.get("province")));
                                                App.getClient().setCity(String.valueOf(addressComponent.get("city")));
                                                App.getClient().setDistinct(String.valueOf(addressComponent.get("district")));
                                                App.getClient().setLocation((String) data.get("formatted_address")
                                                        + (String) data.get("sematic_description"));
                                            }
                                        });
                            }
                        });
            } else {
                final GeoGoderSupports mSupports = HttpFactory.getDataSupports(AppConsts.GeoConfig.BD_LOCATION_HOST, GeoGoderSupports.class);
                String ip = SignalUtil.getLocalIpAddress() != null
                        && !"".equals(SignalUtil.getLocalIpAddress())
                        ? SignalUtil.getLocalIpAddress() : SignalUtil.getIpAddress();
                mSupports.getLocationByIp(ip)
                        .map(new Func1<Map<String, Object>, Map<String, Object>>() {
                            @Override
                            public Map<String, Object> call(Map<String, Object> map) {
                                return (Map<String, Object>) map.get("content");
                            }
                        })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Map<String, Object>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(e.getMessage(), e);
                            }

                            @Override
                            public void onNext(Map<String, Object> data) {
                                Log.d(data.toString());
                                try {
                                    App.getClient().setLocation(
                                            URLDecoder.decode(String.valueOf(data.get("address")), "unicode"));
                                } catch (UnsupportedEncodingException e) {
                                    Log.e(e.getMessage(), e);
                                }
                            }
                        });
            }
        }
    }
}
