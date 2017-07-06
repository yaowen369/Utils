package com.yaoxiaowen.android.sample.test;

import android.content.Intent;
import android.view.View;

import com.yaoxiaowen.android.sample.R;
import com.yaoxiaowen.utils.Utils_Location;

public class LocationTestActivity extends TestMainActivity {

    private static final int jumpGPSRequestCode = 42;
    private int operationCount = 0;

    @Override
    protected void onStart() {
        super.onStart();
        btn1.setText("检查gps是否打开");
        btn2.setText("网络定位功能是否打开");
        btn3.setText("定位功能是否打开");
        btn4.setText("跳转到GPS设置页面");
        btn5.setText("强制打开GPS");
    }


    @Override
    public void onClick(View view) {
        operationCount++;
        switch (view.getId()){
            case R.id.testaMainBtn1:
                String stausGps = Utils_Location.isGpsEnable(getBaseContext()) ? "打开" : "关闭";
                tv1.setText("GPS开关状态 : " + stausGps + "\t" + operationCount);
                break;
            case R.id.testaMainBtn2:
                String stausNetWork = Utils_Location.isNetWorkEnable(getBaseContext()) ? "打开" : "关闭";
                tv1.setText("网络定位状态 : " + stausNetWork + "\t" + operationCount);
                break;
            case R.id.testaMainBtn3:
                String stausLocaton = Utils_Location.isLocationEnable(getBaseContext()) ? "打开" : "关闭";
                tv1.setText("定位功能总体 : " + stausLocaton + "\t" + operationCount);
                Utils_Location.openGps(getBaseContext());
                break;
            case R.id.testaMainBtn4:
                Utils_Location.jumpGpsSetting(this, jumpGPSRequestCode);
                break;
            case R.id.testaMainBtn5:
                tv5.setText("设置了强制打开GPS  " + operationCount);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String info = "activity 回调 requestCode=" + requestCode + "\t resultCode" + resultCode;
        tv4.setText(info + "\t" + operationCount);
    }
}
