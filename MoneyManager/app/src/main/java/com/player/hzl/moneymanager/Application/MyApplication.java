package com.player.hzl.moneymanager.Application;

import android.app.Application;
import android.view.WindowManager;

/**
 * Created by acer on 2017/5/3.
 */
public class MyApplication extends Application {

    //实现应用程序级的全局变量,这种方法相对静态类更有保障
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();
    public WindowManager.LayoutParams getMywmParams(){
        return wmParams;
    }
}
