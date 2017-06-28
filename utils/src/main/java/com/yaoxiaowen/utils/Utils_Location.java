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
 * YaoWen(43194) create at tongcheng work pc,
 * time:  2017/6/28 19:38  qq:2669932513
 *
 * 关于定位的相关工具类
 */

public class Utils_Location {

    public static final String TAG = "Utils_Location";

    /**
     * Gps 是否打开
     * @param context
     * @return true:打开状态      false:关闭状态
     */
    public static boolean isGpsOpen(Context context){
        LocationManager locationManager = (LocationManager)context
                .getSystemService(Context.LOCATION_SERVICE);
        List<String> accessibleProviders = locationManager.getProviders(true);

        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        boolean isEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);


        Log.i(TAG, "accessibleProviders=" + accessibleProviders + "\t gps=" + gps +
                "\t network=" + network + "\t isEnable=" + isEnable);

        return accessibleProviders!=null && accessibleProviders.size()>0;
    }

    /**
     * 跳转到Gps设置页面
     * @param activity
     * @param requsetCode
     */
    public static void jumpGpsSetting(Activity activity, int requsetCode){
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        activity.startActivityForResult(intent, requsetCode);
    }


    /**
     * 强制打开GPS  (不一定能正常使用)
     * @param context
     */
    public static void openGps(Context context){
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
