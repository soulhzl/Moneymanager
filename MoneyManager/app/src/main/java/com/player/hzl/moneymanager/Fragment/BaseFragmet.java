package com.player.hzl.moneymanager.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.player.hzl.moneymanager.Interfaces.UiOperation;

/**
 * Created by acer on 2017/1/31.
 */
public abstract class BaseFragmet extends Fragment implements UiOperation {
    protected View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResID(), null);
        initView();
        initListener();
        initData();
        return rootView;

    }

    public <T> T findView(int id) {
        T view = (T) rootView.findViewById(id);
        return view;
    }

}
