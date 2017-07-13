package com.yaoxiaowen.android.sample.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author：yaowen on 17/7/12 23:00
 * email：yaowen369@gmail.com
 * www.yaoxiaowen.com
 */

public class TimeUtils {

    /**
     *
     * @return eg : \t操作时间:12:30:16
     */
    public static String getOperationTime(){
        return "\t操作时间:" + getTime();
    }

    /**
     *
     * @return eg: 12:30:16
     */
    public static String getTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }


}
