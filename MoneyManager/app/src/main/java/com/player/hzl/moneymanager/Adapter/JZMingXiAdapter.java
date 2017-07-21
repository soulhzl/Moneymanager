package com.player.hzl.moneymanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.player.hzl.moneymanager.Activity.JZMingXiActivity;
import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.bean.JZShouRu;
import com.player.hzl.moneymanager.bean.JZZhiChu;

import java.util.ArrayList;

/**
 * Created by zf2016 on 2017/2/14.
 */

public class JZMingXiAdapter extends BaseAdapter {
    //类别列表或类别子类列表的集合
    public static ArrayList<?> mingXiList;
    private Context context;
    private int flag = 0;
    //数据库操作
    JiZhangDB dataHelper ;
    public JZMingXiAdapter(Context context) {
        this.context = context;
        dataHelper= new JiZhangDB(context);
    }

    //根据传入的参数获取相应的集合
    public double[] getList(int year,int month,int day,int flag){
        this.flag = flag;
        double countZhiChu = 0,countShouRu = 0;
        ArrayList<JZZhiChu> zhiChuList = new ArrayList<JZZhiChu>();
        String selectionzhichu = JZZhiChu.ZC_YEAR+"="+year+" and "+ JZZhiChu.ZC_MONTH+"="+month;
        zhiChuList =  dataHelper.GetZhiChuList(selectionzhichu);
        for(JZZhiChu zhichu:zhiChuList){
            countZhiChu += zhichu.getZc_Count();
        }
        ArrayList<JZShouRu> shouRuList = new ArrayList<JZShouRu>();
        String selectionshouru = JZShouRu.SR_YEAR+"="+year+" and "+ JZShouRu.SR_MONTH+"="+month;
        shouRuList =  dataHelper.GetShouRuList(selectionshouru);
        for(JZShouRu shouru:shouRuList){
            countShouRu += shouru.getSr_Count();
        }
        if(flag== JZMingXiActivity.zhichu_flag){
            mingXiList = zhiChuList;
        }else if(flag==JZMingXiActivity.shouru_flag){
            mingXiList = shouRuList;
        }
        return new double[]{countZhiChu,countShouRu};
    }
    @Override
    public int getCount() {
        return mingXiList.size();
    }

    @Override
    public Object getItem(int position) {
        return mingXiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.jz_mingxi_item, null);
        JZZhiChu zhichu = new JZZhiChu();
        JZShouRu shouru = new JZShouRu();
        //类别
        TextView lb_text = (TextView) convertView.findViewById(R.id.mingxi_leibie_item_text);
        TextView jine_text = (TextView) convertView.findViewById(R.id.mingxi_jine_item_text);
        TextView beizhu_text = (TextView) convertView.findViewById(R.id.mingxi_beizhu_item_text);
        TextView time_text = (TextView) convertView.findViewById(R.id.mingxi_time_item_text);
        if(flag==JZMingXiActivity.zhichu_flag){
            zhichu = (JZZhiChu)mingXiList.get(position);
            convertView.setTag(zhichu);
            if(zhichu.getZc_SubItem().length()>0){
                lb_text.setText(zhichu.getZc_Item()+">"+zhichu.getZc_SubItem());
            }else{
                lb_text.setText(zhichu.getZc_Item());
            }
            jine_text.setText(zhichu.getZc_Count()+"");
            beizhu_text.setText(zhichu.getZc_Beizhu());
            time_text.setText(zhichu.getZc_Day()+"日 "+zhichu.getZc_Time());
        }else if(flag==JZMingXiActivity.shouru_flag){
            shouru = (JZShouRu)mingXiList.get(position);
            convertView.setTag(shouru);
            lb_text.setText(shouru.getSr_Item());
            jine_text.setText(shouru.getSr_Count()+"");
            beizhu_text.setText(shouru.getSr_Beizhu());
            time_text.setText(shouru.getSr_Day()+"日 "+shouru.getSr_Time());
        }
        return convertView;
    }
}
