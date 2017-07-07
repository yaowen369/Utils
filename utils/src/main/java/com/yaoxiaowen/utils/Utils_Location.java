package com.yaoxiaowen.utils;


import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.util.Log;

import java.util.List;

/**
 * 关于定位功能的相关工具类<br/>
 *
 * @author <a href="http://www.yaoxiaowen.com/">www.yaoxiaowen.com</a>
 * @version 0.0.1
 * <p>
 *
 * 实现定位的方式有很多,比如卫星定位,（常见的是美国GPS,也有中国北斗,俄罗斯GLONASS,欧洲Galile),
 * 网络（基站）定位,蓝牙,wifi,ip等都可作为实现手段,具体到手机上来讲,常用的是 GPS定位和网络定位
 * 所谓AGPS就是通过网络把GPS卫星的星历数据发送到手机上,因为手机接收GPS卫星完整星历较耗时,所以是GPS
 * 定位的一种辅助手段。
 *
 * <p>
 * 而为了实现更好的定位较好,自然是GPS+网络结合起来。
 *
 * <p>
 *
 * 为了实现更好的定位,需要以下权限
 * <br/>
 * <block>
 *  &#60;uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /&#62;<br/>
 *  &#60;uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" /&#62;<br/>
 *  &#60;uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /&#62;<br/>
 *  &#60;uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /&#62;<br/>
 *  &#60;uses-permission android:name="android.permission.CHANGE_WIFI_STATE" /&#62;<br/>
 *  &#60;uses-permission android:name="android.permission.INTERNET" /&#62;<br/>
 * </block>
 *
 *
 */
public class Utils_Location {

    private static final String TAG = "Utils_Location";

    /**
     * Gps定位服务 是否使能
     *
     *
     * @param context Context
     * @return true:打开状态      false:关闭状态
     */
    public static boolean isGpsEnable(Context context){
        LocationManager locationManager = (LocationManager)context
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     *  网络(基站)定位功能 是否使能
     * @param context  Context
     * @return  true: 打开状态   false:关闭状态
     */
    public static boolean isNetworkEnable(Context context){
        LocationManager locationManager = (LocationManager)context
                .getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    /**
     *  是否存在一种可用的定位手段, 或者说, 定位功能是否可用。
     * @param context Context
     * @return true:定位功能可用   false: 定位功能不可用
     */
    public static boolean isLocationEnable(Context context){
        LocationManager locationManager = (LocationManager)context
                .getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = locationManager.getProviders(true);
        Log.i(TAG, "accessibleProviders=" + accessibleProviders);
        return accessibleProviders!=null && accessibleProviders.size()>0;
    }

    /**
     * 跳转到Gps设置页面,让用户自己打开
     * <p>
     * <b><em>并不是100%有可供匹配的Activity</em></b>
     * @param activity
     * @param requsetCode  请求码,在设置页面结束之后,activity可用<code>onActivityResult</>接受回调
     */
    public static void jumpGpsSetting(Activity activity, int requsetCode){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, requsetCode);
    }


    /**
     * 强制打开GPS  (不一定能正常使用)
     *
     * @param context
     */
    private static void openGps(Context context){
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
        gpsIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, gpsIntent, 0).send();
        }catch (PendingIntent.CanceledException e){
            e.printStackTrace();
        }
    }

}
