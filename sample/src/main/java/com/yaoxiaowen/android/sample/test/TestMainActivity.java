package com.yaoxiaowen.android.sample.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yaoxiaowen.android.sample.R;
import com.yaoxiaowen.android.sample.base.BaseActivity;

public class TestMainActivity extends BaseActivity {

    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;


    @Override
    protected void onStart() {
        super.onStart();
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
}
