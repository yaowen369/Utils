package com.yaoxiaowen.android.sample.test;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yaoxiaowen.android.sample.R;
import com.yaoxiaowen.android.sample.base.BaseActivity;
import com.yaoxiaowen.android.sample.log.LogUtils;
import com.yaoxiaowen.utils.Utils_Screen;

public class TestBaseActivity extends BaseActivity {

    protected Button btn1;
    protected Button btn2;
    protected Button btn3;
    protected Button btn4;
    protected Button btn5;

    protected TextView tv1;
    protected TextView tv2;
    protected TextView tv3;
    protected TextView tv4;
    protected TextView tv5;

    private static final String TAG  = "TestBaseActivity";

    @Override
    protected void onStart() {
        super.onStart();

        testGetScreenWidthHeight();

    }


    @Override
    protected void onResume() {
        super.onResume();
        goneIfEmptyBtn();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_test_main;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btn1 = (Button) findViewById(R.id.testaMainBtn1);
        btn2 = (Button) findViewById(R.id.testaMainBtn2);
        btn3 = (Button) findViewById(R.id.testaMainBtn3);
        btn4 = (Button) findViewById(R.id.testaMainBtn4);
        btn5 = (Button) findViewById(R.id.testaMainBtn5);

        tv1 = (TextView) findViewById(R.id.testaMainTv1);
        tv2 = (TextView) findViewById(R.id.testaMainTv2);
        tv3 = (TextView) findViewById(R.id.testaMainTv3);
        tv4 = (TextView) findViewById(R.id.testaMainTv4);
        tv5 = (TextView) findViewById(R.id.testaMainTv5);
    }

    @Override
    protected void initListener() {
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.testaMainBtn1:
                break;
            case R.id.testaMainBtn2:
                break;
            case R.id.testaMainBtn3:
                break;
            case R.id.testaMainBtn4:
                break;
            case R.id.testaMainBtn5:
                break;
        }
    }

    /**
     * 刷新几个btn的你内容，如果为空 则隐藏
     */
    private void goneIfEmptyBtn(){
        if (TextUtils.isEmpty(btn1.getText().toString())){
            btn1.setVisibility(View.GONE);
        }else {
            btn1.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(btn2.getText().toString())){
            btn2.setVisibility(View.GONE);
        }else {
            btn2.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(btn3.getText().toString())){
            btn3.setVisibility(View.GONE);
        }else {
            btn3.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(btn4.getText().toString())){
            btn4.setVisibility(View.GONE);
        }else {
            btn4.setVisibility(View.VISIBLE);
        }

        if (TextUtils.isEmpty(btn5.getText().toString())){
            btn5.setVisibility(View.GONE);
        }else {
            btn5.setVisibility(View.VISIBLE);
        }
    }//end of ""

    /**
     * 测试代码，得到屏幕宽高,未来需要删除
     */
    private void testGetScreenWidthHeight(){

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("手机型号 : model=" + Build.MODEL);
            sb.append("\t手机厂商: " + Build.MANUFACTURER);
            sb.append("\t屏幕宽度 " + Utils_Screen.getScreenWidth(this));
            sb.append("\t屏幕高度" + Utils_Screen.getScreenHeight(this));
//            mLog.e(TAG, sb.toString());

//            tv1.setText(sb.toString());

            LogUtils.i(TAG, "" + sb.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
