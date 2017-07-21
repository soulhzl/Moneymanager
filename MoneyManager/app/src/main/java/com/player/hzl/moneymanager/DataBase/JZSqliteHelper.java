package com.player.hzl.moneymanager.DataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.player.hzl.moneymanager.bean.JZShouRu;
import com.player.hzl.moneymanager.bean.JZZhiChu;

/**
 * Created by acer on 2017/2/12.
 */
public class JZSqliteHelper extends SQLiteOpenHelper {

    public static final String ZHICHU = "ZHICHU";// 支出
    public static final String SHOURU = "SHOURU";// 收入
    public static final String YUSUAN_MONTH = "YUSUAN_MONTH";//月预算
    public static final String ISHIDDEN ="HIDDEN";
    public Context context;
    public JZSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        saveYuSuan(context,YUSUAN_MONTH,YUSUAN_MONTH,0);
        //这个用来存储当前是否显示提醒   显示为1，不显示为0
        saveYuSuan(context,ISHIDDEN,ISHIDDEN,1);
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                ZHICHU + "(" + "ID" + " integer primary key," +
                JZZhiChu.ZC_ITEM + " varchar," +
                JZZhiChu.ZC_SUBITEM + " varchar," +
                JZZhiChu.ZC_YEAR + " Integer," +
                JZZhiChu.ZC_MONTH + " Integer," +
                JZZhiChu.ZC_WEEK + " Integer," +
                JZZhiChu.ZC_DAY + " Integer," +
                JZZhiChu.ZC_TIME + " varchar," +
                JZZhiChu.ZC_COUNT + " REAL," +
                JZZhiChu.ZC_BEIZHU + " varchar" + ");");

        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                SHOURU + "(" + "ID" + " integer primary key," +
                JZShouRu.SR_ITEM + " varchar," +
                JZShouRu.SR_YEAR + " Integer," +
                JZShouRu.SR_MONTH + " Integer," +
                JZShouRu.SR_WEEK + " Integer," +
                JZShouRu.SR_DAY + " Integer," +
                JZShouRu.SR_TIME + " varchar," +
                JZShouRu.SR_COUNT + " REAL," +
                JZShouRu.SR_BEIZHU + " varchar" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS" + ZHICHU);
        db.execSQL("DROP TABLE IF EXISTS" + SHOURU);
        onCreate(db);
    }

    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn) {
        try {
            db.execSQL("ALTER TABLE " + ZHICHU + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
            db.execSQL("ALTER TABLE " + SHOURU + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
	 * 存储月预算或者加密密码
	 * */
    public static void saveYuSuan(Context context, String filename, String name, int num) {
        SharedPreferences preference = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        // 获取编辑器
        SharedPreferences.Editor editor = preference.edit();
        // 数据暂时存放在内存中
        editor.putInt(name, num);
        // 提交修改，将内存中的数据保存至xawx.xml文件中
        editor.commit();
    }


    /*
     * 读取Preference参数
     */
    public static int readPreferenceFile(Context context,String filename, String name) {
        SharedPreferences preference = context.getSharedPreferences(filename, Context.MODE_PRIVATE);
        int num = preference.getInt(name, 0);
        return num;
    }
}
