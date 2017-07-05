package com.yaoxiaowen.android.sample.test;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.yaoxiaowen.android.sample.R;
import com.yaoxiaowen.android.sample.base.BaseActivity;
import com.yaoxiaowen.android.sample.log.LogUtils;
import com.yaoxiaowen.utils.Utils_Location;

public class LocationTestActivity extends TestMainActivity {

    private static final int jumpGPSRequestCode = 42;
    private int operationCount = 0;

    @Override
    protected void onStart() {
        super.onStart();
        btn1.setText("检查gps是否打开");
        btn2.setText("跳转到GPS设置页面");
        btn3.setText("强制打开GPS");
    }


    @Override
    public void onClick(View view) {
        operationCount++;
        switch (view.getId()){
            case R.id.testaMainBtn1:
                String staus = Utils_Location.isGpsOpen(getBaseContext()) ? "打开" : "关闭";
                tv1.setText("GPS开关状态 : " + staus + "\t" + operationCount);
                break;
            case R.id.testaMainBtn2:
                Utils_Location.jumpGpsSetting(this, jumpGPSRequestCode);
                break;
            case R.id.testaMainBtn3:
                Utils_Location.openGps(getBaseContext());
                break;
            case R.id.testaMainBtn4:
                break;
            case R.id.testaMainBtn5:
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
