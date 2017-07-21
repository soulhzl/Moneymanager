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
        View rootView = findViewById(android.R.id.content);	// android.R.id.content ���id���Ի�ȡ��Activity�ĸ�View
        initView();
        initListener();
        initData();
    }

    /**
     * ����View������ʡȥǿת����
     * @param id
     * @return
     */
    public <T> T findView(int id) {
        @SuppressWarnings("unchecked")
        T view = (T) findViewById(id);
        return view;
    }


}
