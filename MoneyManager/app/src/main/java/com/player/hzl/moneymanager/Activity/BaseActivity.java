package com.player.hzl.moneymanager.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;


import com.player.hzl.moneymanager.Interfaces.UiOperation;


/**
 * Created by acer on 2017/1/27.
 */
public abstract class BaseActivity extends FragmentActivity implements UiOperation {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResID());
        View rootView = findViewById(android.R.id.content);	// android.R.id.content 这个id可以获取到Activity的根View
        initView();
        initListener();
        initData();
    }

    /**
     * 查找View，可以省去强转操作
     * @param id
     * @return
     */
    public <T> T findView(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) findViewById(id);
        return view;
    }


}
