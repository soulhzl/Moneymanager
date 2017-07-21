package com.player.hzl.moneymanager.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.player.hzl.moneymanager.bean.JZShouRu;
import com.player.hzl.moneymanager.bean.JZZhiChu;

import java.util.ArrayList;

/**
 * Created by acer on 2017/2/12.
 */
public class JiZhangDB {
    //数据库名称
    private String DB_NAME="JiZhang.db";
    //数据库版本
    private static int DB_VERSION=1;
    private SQLiteDatabase db;
    private JZSqliteHelper dbHelper;
    private Context context;

    public JiZhangDB(Context context){
        this.context = context;
        dbHelper=new JZSqliteHelper(context,DB_NAME, null, DB_VERSION);
        db= dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
        dbHelper.close();
    }

    /*
     * 获取支出表中的所有数据
     * */
    public ArrayList<JZZhiChu> GetZhiChuList(String selection){
        ArrayList<JZZhiChu> zhichulist=new ArrayList<JZZhiChu>();
        Cursor cursor=db.query(JZSqliteHelper.ZHICHU, null, selection, null, null, null, "ID DESC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&&(cursor.getString(1)!=null)){
            JZZhiChu zhichu=new JZZhiChu();
            zhichu.setZc_Id(cursor.getInt(0));
            zhichu.setZc_Item(cursor.getString(1));
            zhichu.setZc_SubItem(cursor.getString(2));
            zhichu.setZc_Year(cursor.getInt(3));
            zhichu.setZc_Month(cursor.getInt(4));
            zhichu.setZc_Week(cursor.getInt(5));
            zhichu.setZc_Day(cursor.getInt(6));
            zhichu.setZc_Time(cursor.getString(7));
            zhichu.setZc_Count(cursor.getDouble(8));
            zhichu.setZc_Beizhu(cursor.getString(9));
            zhichulist.add(zhichu);
            cursor.moveToNext();
        }
        cursor.close();
        return zhichulist;
    }

    /*
     * 获取收入表中的所有数据
     * */
    public ArrayList<JZShouRu> GetShouRuList(String selection){
        ArrayList<JZShouRu> shourulist=new ArrayList<JZShouRu>();
        Cursor cursor=db.query(JZSqliteHelper.SHOURU, null, selection, null, null, null, "ID DESC");
        cursor.moveToFirst();
        while(!cursor.isAfterLast()&&(cursor.getString(1)!=null)){
            JZShouRu shouru=new JZShouRu();
            shouru.setSr_Id(cursor.getInt(0));
            shouru.setSr_Item(cursor.getString(1));
            shouru.setSr_Year(cursor.getInt(2));
            shouru.setSr_Month(cursor.getInt(3));
            shouru.setSr_Week(cursor.getInt(4));
            shouru.setSr_Day(cursor.getInt(5));
            shouru.setSr_Time(cursor.getString(6));
            shouru.setSr_Count(cursor.getDouble(7));
            shouru.setSr_Beizhu(cursor.getString(8));
            shourulist.add(shouru);
            cursor.moveToNext();
        }
        cursor.close();
        return shourulist;
    }

    /*
     * 更新支出表的记录
     * */
    public int UpdateZhiChuInfo(JZZhiChu zhichu, int id){
        ContentValues values = new ContentValues();
        values.put(JZZhiChu.ZC_ITEM, zhichu.getZc_Item());
        values.put(JZZhiChu.ZC_SUBITEM, zhichu.getZc_SubItem());
        values.put(JZZhiChu.ZC_YEAR, zhichu.getZc_Year());
        values.put(JZZhiChu.ZC_MONTH, zhichu.getZc_Month());
        values.put(JZZhiChu.ZC_WEEK, zhichu.getZc_Week());
        values.put(JZZhiChu.ZC_DAY, zhichu.getZc_Day());
        values.put(JZZhiChu.ZC_TIME, zhichu.getZc_Time());
        values.put(JZZhiChu.ZC_COUNT, zhichu.getZc_Count());
        values.put(JZZhiChu.ZC_BEIZHU, zhichu.getZc_Beizhu());
        int idupdate= db.update(JZSqliteHelper.ZHICHU, values, "ID ='"+id+"'", null);
        this.close();
        return idupdate;
    }
    /*
     * 更新收入表的记录
     * */
    public int UpdateShouRuInfo(JZShouRu shouru, int id){
        ContentValues values = new ContentValues();
        values.put(JZShouRu.SR_ITEM, shouru.getSr_Item());
        values.put(JZShouRu.SR_YEAR, shouru.getSr_Year());
        values.put(JZShouRu.SR_MONTH, shouru.getSr_Month());
        values.put(JZShouRu.SR_WEEK, shouru.getSr_Week());
        values.put(JZShouRu.SR_DAY, shouru.getSr_Day());
        values.put(JZShouRu.SR_TIME, shouru.getSr_Time());
        values.put(JZShouRu.SR_COUNT, shouru.getSr_Count());
        values.put(JZShouRu.SR_BEIZHU, shouru.getSr_Beizhu());
        int idupdate= db.update(JZSqliteHelper.SHOURU, values, "ID ='"+id+"'", null);
        this.close();
        return idupdate;
    }
    /*
     * 添加支出记录
     * */
    public Long SaveZhiChuInfo(JZZhiChu zhichu){
        ContentValues values = new ContentValues();
        values.put(JZZhiChu.ZC_ITEM, zhichu.getZc_Item());
        values.put(JZZhiChu.ZC_SUBITEM, zhichu.getZc_SubItem());
        values.put(JZZhiChu.ZC_YEAR, zhichu.getZc_Year());
        values.put(JZZhiChu.ZC_MONTH, zhichu.getZc_Month());
        values.put(JZZhiChu.ZC_WEEK, zhichu.getZc_Week());
        values.put(JZZhiChu.ZC_DAY, zhichu.getZc_Day());
        values.put(JZZhiChu.ZC_TIME, zhichu.getZc_Time());
        values.put(JZZhiChu.ZC_COUNT, zhichu.getZc_Count());
        values.put(JZZhiChu.ZC_BEIZHU, zhichu.getZc_Beizhu());
        Long updateid = db.insert(JZSqliteHelper.ZHICHU, JZZhiChu.ZC_YEAR, values);
        this.close();
        return updateid;
    }

    /*
     * 添加收入记录
     * */
    public Long SaveShouRuInfo(JZShouRu shouru){
        ContentValues values = new ContentValues();
        values.put(JZShouRu.SR_ITEM, shouru.getSr_Item());
        values.put(JZShouRu.SR_YEAR, shouru.getSr_Year());
        values.put(JZShouRu.SR_MONTH, shouru.getSr_Month());
        values.put(JZShouRu.SR_WEEK, shouru.getSr_Week());
        values.put(JZShouRu.SR_DAY, shouru.getSr_Day());
        values.put(JZShouRu.SR_TIME, shouru.getSr_Time());
        values.put(JZShouRu.SR_COUNT, shouru.getSr_Count());
        values.put(JZShouRu.SR_BEIZHU, shouru.getSr_Beizhu());
        Long updateid = db.insert(JZSqliteHelper.SHOURU, JZShouRu.SR_YEAR, values);
        this.close();
        return updateid;
    }

    /*
     * 删除支出表的记录
     * */
    public int DelZhiChuInfo(int id){
        int delid=  db.delete(JZSqliteHelper.ZHICHU, "ID ="+id, null);
        this.close();
        return delid;
    }

    /*
     * 删除所有记录
     * */
    public void delAll(){
        JZSqliteHelper.saveYuSuan(context,JZSqliteHelper.YUSUAN_MONTH,JZSqliteHelper.YUSUAN_MONTH, 0);
        db.delete(JZSqliteHelper.ZHICHU, null, null);
        db.delete(JZSqliteHelper.SHOURU, null, null);
    }

    /*
     * 删除收入表的记录
     * */
    public int DelShouRuInfo(int id){
        int delid=  db.delete(JZSqliteHelper.SHOURU, "ID ="+id, null);
        this.close();
        return delid;
    }

}
