package com.yaoxiaowen.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 检测网速的工具类 <br/>
 *
 * @author <a href="http://www.yaoxiaowen.com/">www.yaoxiaowen.com</a>
 * @version 0.0.1
 * <p>
 *     使用单例模式来实例化,<br/>
 *     使用hadler的message#arg1 来得到网速值.(网速kb)<br/>
 *
 *
 *     <p>
 *         具体使用方式. 得到单例对象之后, 通过 startCalculateNetSpeed() 开始计算，
 *         在handler的回调方法中得到具体网速值之后，调用stopCalculateNetSpeed()来停止计算
 *     </p>
 *
 *
 * </p>
 */
public class Utils_NetSpeed {
    private static Utils_NetSpeed ourInstance = new Utils_NetSpeed();

//    public static Utils_NetSpeed getInstance() {
//        return ourInstance;
//    }

    private Utils_NetSpeed() {
    }


    private long preRxBytes = 0;
    private Timer mTimer = null;
    private Context mContext;
    private static Utils_NetSpeed mNetSpeed;
    private Handler mHandler;

    private Utils_NetSpeed(Context mContext, Handler mHandler) {
        this.mContext = mContext;
        this.mHandler = mHandler;
    }

    public static Utils_NetSpeed getInstant(Context mContext, Handler mHandler) {
        if (mNetSpeed == null) {
            mNetSpeed = new Utils_NetSpeed(mContext, mHandler);
        }
        return mNetSpeed;
    }

    private long getNetworkRxBytes() {
        int currentUid = getUid();
        if (currentUid < 0) {
            return 0;
        }
        long rxBytes = TrafficStats.getUidRxBytes(currentUid);
        if (rxBytes == TrafficStats.UNSUPPORTED) {
            rxBytes = TrafficStats.getTotalRxBytes();
        }
        return rxBytes;
    }

    /**
     * 得到网速 (单位:kb)
     * @return int类型。
     */
    private int getNetSpeed() {

        long curRxBytes = getNetworkRxBytes();
        long bytes = curRxBytes - preRxBytes;
        preRxBytes = curRxBytes;
        int kb = (int) Math.floor(bytes / 1024 + 0.5);
        return kb;
    }

    /**
     * 调用此方法，才能正式开始计算网速.
     */
    public void startCalculateNetSpeed() {
        preRxBytes = getNetworkRxBytes();
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    msg.arg1 = getNetSpeed();
                    mHandler.sendMessage(msg);
                }
            }, 1000, 1000);
        }
    }

    /**
     * 得到网速值之后,记得进行关闭该操作.
     */
    public void stopCalculateNetSpeed() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

    private int getUid() {
        try {
            PackageManager pm = mContext.getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo(
                    mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            return ai.uid;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
