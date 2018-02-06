package com.yaoxiaowen.utils;

import android.text.TextUtils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils_Verification {


    /**
     * 校验是否是正确的手机号
     *
     * @param phoneNo
     * @return
     */
    public static boolean isPhoneNo(String phoneNo) {
        if (TextUtils.isEmpty(phoneNo)) {
            return false;
        }
        /**
         * YaoWen(43194) modify  at 2017/12/25 9:59
         * 现在已经拥有 199， 166号段了
         */
        String regex = "^1[3,4,5,6,7,8,9]\\d{9}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phoneNo);

        return m.matches();
    }

    /**
     * 校验是否是正确的护照号
     *
     * @param passport
     * @return
     */
    public static boolean isPassportNo(String passport) {
        if (TextUtils.isEmpty(passport)) {
            return false;
        }
        String regex = "^[a-zA-Z0-9]{5,17}$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(passport);

        return m.matches();
    }

    /**
     * 校验 是否是 正确的身份证号
     *
     * @param idCard
     * @return
     */
    public static boolean isIDCardNo(String idCard) {

        String regex = "^\\d{17}[0-9xX]$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(idCard);
        if (!m.matches()) {
            //不符合数字字母规则
            return false;
        }

        Map<String, String> cityMap = getIdCityMap();
        if (!cityMap.containsKey(idCard.substring(0, 2))) {
            //不包含这个省份
            return false;
        }

        String birthday = idCard.substring(6, 10) + "-"
                + idCard.substring(10, 12) + "-" + idCard.substring(12, 14);
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setLenient(false);
        try {
            Date date = formatter.parse(birthday);
        } catch (Exception e) {
            //日期不对
            return false;
        }
        idCard = idCard.replace("x", "a");
        idCard = idCard.replace("X", "a");
        int sum = 0;
        try {
            for (int i = 17; i >= 0; i--) {
                sum += (Math.pow(2, i) % 11) * Integer.parseInt(String.valueOf(idCard.charAt(17 - i)), 11);
            }

        } catch (Exception e) {
            e.printStackTrace();
            //求和计算的时候，有异常
            return false;
        }

        if (sum % 11 != 1) {
            //校验的规则不对
            return false;
        }
        return true;
    }


    private static Map<String, String> getIdCityMap() {
        Map<String, String> cityMap = new HashMap<String, String>();
        cityMap.put("11", "北京");
        cityMap.put("12", "天津");
        cityMap.put("13", "河北");
        cityMap.put("14", "山西");
        cityMap.put("15", "内蒙古");
        cityMap.put("21", "辽宁");
        cityMap.put("22", "吉林");
        cityMap.put("23", "黑龙江");
        cityMap.put("31", "上海");
        cityMap.put("32", "江苏");
        cityMap.put("33", "浙江");
        cityMap.put("34", "安徽");
        cityMap.put("35", "福建");
        cityMap.put("36", "江西");
        cityMap.put("37", "山东");
        cityMap.put("41", "河南");
        cityMap.put("42", "湖北");
        cityMap.put("43", "湖南");
        cityMap.put("44", "广东");
        cityMap.put("45", "广西");
        cityMap.put("46", "海南");
        cityMap.put("50", "重庆");
        cityMap.put("51", "四川");
        cityMap.put("52", "贵州");
        cityMap.put("53", "云南");
        cityMap.put("54", "西藏");
        cityMap.put("61", "陕西");
        cityMap.put("62", "甘肃");
        cityMap.put("63", "青海");
        cityMap.put("64", "宁夏");
        cityMap.put("65", "新疆");
        cityMap.put("71", "台湾");
        cityMap.put("81", "香港");
        cityMap.put("82", "澳门");
        cityMap.put("91", "国外");
        return cityMap;
    }
    

}
