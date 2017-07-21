package com.player.hzl.moneymanager.DataBase;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.player.hzl.moneymanager.bean.JSContent;

/**
 * Created by zf2016 on 2017/2/17.
 */

public class JSSqliteHelper extends SQLiteOpenHelper{
    public static final String JISHI = "JISHI";// 理财日记

    public JSSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " +
                JISHI + "(" + "ID" + " integer primary key," +
                JSContent.YEAR + " Integer," +
                JSContent.MONTH + " Integer," +
                JSContent.WEEK + " Integer," +
                JSContent.DAY + " Integer," +
                JSContent.TIME + " varchar," +
                JSContent.CONTENT + " varchar," +
                JSContent.COLOR + " Integer," +
                JSContent.SIZE + " REAL" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + JISHI);
        onCreate(db);
    }

    public void updateColumn(SQLiteDatabase db, String oldColumn, String newColumn, String typeColumn) {
        try {
            db.execSQL("ALTER TABLE " + JISHI + " CHANGE " + oldColumn + " " + newColumn + " " + typeColumn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
