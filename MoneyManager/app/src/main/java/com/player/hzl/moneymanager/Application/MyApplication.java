package com.player.hzl.moneymanager.Application;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by acer on 2017/5/3.
 */
public class MyApplication extends Application {

    //ʵ��Ӧ�ó��򼶵�ȫ�ֱ���,���ַ�����Ծ�̬����б���
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }
}
