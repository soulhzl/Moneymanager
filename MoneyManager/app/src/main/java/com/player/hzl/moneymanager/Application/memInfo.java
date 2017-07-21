package com.player.hzl.moneymanager.Application;

import android.app.ActivityManager;
import android.content.Context;

import com.player.hzl.moneymanager.Adapter.JZMingXiAdapter;
import com.player.hzl.moneymanager.DataBase.JZSqliteHelper;
import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.Service.FloatService;
import com.player.hzl.moneymanager.Utils.GetTime;
import com.player.hzl.moneymanager.bean.JZZhiChu;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by acer on 2017/5/3.
 */
public class memInfo {
    public static final int zhichu_flag=4020;

    //获取本月支出
    public static double getBenYueZC(Context mContext) {
        int year = GetTime.getYear();
        int month = GetTime.getMonth();
        int flag = zhichu_flag;
        JZMingXiAdapter adapter = new JZMingXiAdapter(mContext);
        double benyuezhichu[] = adapter.getList(year, month, 0, flag);
        return benyuezhichu[0];
    }

    //获取预算余额
    public static float getYuSuanYE(Context mContext) {
        JiZhangDB dataHelper = new JiZhangDB(mContext);
        float count_zc_yue = 0;
        final float yusuan_yue = JZSqliteHelper.readPreferenceFile(mContext, JZSqliteHelper.YUSUAN_MONTH, JZSqliteHelper.YUSUAN_MONTH);

        String selectionMonth = JZZhiChu.ZC_YEAR + "=" + GetTime.getYear() + " and " + JZZhiChu.ZC_MONTH + "=" + GetTime.getMonth();
        List<JZZhiChu> zhichuMonthList = dataHelper.GetZhiChuList(selectionMonth);
        if (zhichuMonthList != null) {
            for (JZZhiChu zhichu : zhichuMonthList) {
                count_zc_yue += zhichu.getZc_Count();
            }
        }
        return yusuan_yue - count_zc_yue;
    }
}
