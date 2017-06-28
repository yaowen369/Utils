package com.yaoxiaowen.android.sample.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initData();
        initView();
        initListener();
    }


    protected abstract int getLayoutId();
    protected abstract void initData();
    protected abstract void initView();
    protected abstract void initListener();

    @Override
    public void onClick(View v) {

    }
}
