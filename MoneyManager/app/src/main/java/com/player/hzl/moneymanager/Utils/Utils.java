package com.player.hzl.moneymanager.Utils;

import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by acer on 2017/1/31.
 */
public class Utils {

    /** 在屏幕中间显示Toast */
    public static void showToast(Context context, String text){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0 , 0 );
        toast.show();
    }

    /** 获取屏幕宽度 */
    public static int getScreenWidth(Context context) {
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = windowmanager.getDefaultDisplay().getWidth();
        return screenWidth;
    }
}
