package com.player.hzl.moneymanager.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.player.hzl.moneymanager.bean.JSContent;

import java.util.ArrayList;

/**
 * Created by zf2016 on 2017/2/17.
 */

public class JiShiDB {
    //数据库名称
    private String DB_NAME="JiShi.db";
    //数据库版本
    private static int DB_VERSION=1;
    private SQLiteDatabase db;
    private JSSqliteHelper dbHelper;

    public JiShiDB(Context context){
        dbHelper=new JSSqliteHelper(context,DB_NAME, null, DB_VERSION);
        db= dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    /*
     * 获取备忘表中的所有数据
     * */
    public ArrayList<JSContent> GetJSList(String selection){
        ArrayList<JSContent> jishilist=new ArrayList<JSContent>();
        Cursor cursor=db.query(JSSqliteHelper.JISHI, null, selection, null, null, null, "ID DESC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&&(cursor.getString(1)!=null)){
            JSContent jishi=new JSContent();
            jishi.setId(cursor.getInt(0));
            jishi.setYear(cursor.getInt(1));
            jishi.setMonth(cursor.getInt(2));
            jishi.setWeek(cursor.getInt(3));
            jishi.setDay(cursor.getInt(4));
            jishi.setTime(cursor.getString(5));
            jishi.setContent(cursor.getString(6));
            jishi.setColor(cursor.getInt(7));
            jishi.setSize(cursor.getFloat(8));
            jishilist.add(jishi);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return jishilist;
    }

    /*
     * 更新备忘表的记录
     * */
    public int UpdateJSInfo(JSContent jishi, int id){
        ContentValues values = new ContentValues();
        values.put(JSContent.YEAR, jishi.getYear());
        values.put(JSContent.MONTH, jishi.getMonth());
        values.put(JSContent.WEEK, jishi.getWeek());
        values.put(JSContent.DAY, jishi.getDay());
        values.put(JSContent.TIME, jishi.getTime());
        values.put(JSContent.CONTENT, jishi.getContent());
        values.put(JSContent.COLOR, jishi.getColor());
        values.put(JSContent.SIZE, jishi.getSize());
        int idupdate= db.update(JSSqliteHelper.JISHI, values, "ID ='"+id+"'", null);
        this.close();
        return idupdate;
    }
    /*
     * 添加备忘记录
     * */
    public Long SaveJSInfo(JSContent jishi){
        ContentValues values = new ContentValues();
        values.put(JSContent.YEAR, jishi.getYear());
        values.put(JSContent.MONTH, jishi.getMonth());
        values.put(JSContent.WEEK, jishi.getWeek());
        values.put(JSContent.DAY, jishi.getDay());
        values.put(JSContent.TIME, jishi.getTime());
        values.put(JSContent.CONTENT, jishi.getContent());
        values.put(JSContent.COLOR, jishi.getColor());
        values.put(JSContent.SIZE, jishi.getSize());
        Long updateid = db.insert(JSSqliteHelper.JISHI, JSContent.YEAR, values);
        return updateid;
    }

    /*
     * 删除备忘表的记录
     * */
    public int DelJSInfo(int id){
        int delid=  db.delete(JSSqliteHelper.JISHI, "ID ="+id, null);
        return delid;
    }
}
