package com.player.hzl.moneymanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.player.hzl.moneymanager.DataBase.JiShiDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.bean.JSContent;

import java.util.ArrayList;

/**
 * Created by zf2016 on 2017/2/17.
 */

public class JSAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {
    //����б����������б�ļ���
    public static ArrayList<JSContent> jSlist;
    //�洢��ѡitem��id
    public ArrayList<Integer> idList;
    //�Ƿ���ʾ��ѡ��
    public boolean isShowCheck;
    //�Ƿ�ȫѡ
    public boolean isSelectAll;
    private Context context;

    public JSAdapter(Context context) {
        this.context = context;
        jSlist = new ArrayList<JSContent>();
        idList=new ArrayList<Integer>();
        getList();
        isShowCheck=false;
        isSelectAll=false;
    }

    public void getList(){
        JiShiDB dataHelper = new JiShiDB(context);
        jSlist = dataHelper.GetJSList("");
    }

    @Override
    public int getCount() {
        return jSlist.size();
    }

    @Override
    public Object getItem(int position) {
        return jSlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.js_list_item, null);
        JSContent jishi = new JSContent();
        jishi = jSlist.get(position);
        convertView.setTag(jishi.getId());
        TextView content_text = (TextView) convertView.findViewById(R.id.js_list_item_content_text);
        TextView date_text = (TextView) convertView.findViewById(R.id.js_list_item_date_text);
        TextView week_text = (TextView) convertView.findViewById(R.id.js_list_item_week_text);
        TextView time_text = (TextView) convertView.findViewById(R.id.js_list_item_time_text);
        content_text.setText(jishi.getContent());
        content_text.setSingleLine(true);
        date_text.setText(jishi.getMonth()+"��"+jishi.getDay()+"��");
        time_text.setText(jishi.getTime());
        String weekString ="����";
        if(jishi.getWeek()==1){
            weekString = weekString+"һ";
        }else if(jishi.getWeek()==2){
            weekString = weekString+"��";
        }else if(jishi.getWeek()==3){
            weekString = weekString+"��";
        }else if(jishi.getWeek()==4){
            weekString = weekString+"��";
        }else if(jishi.getWeek()==5){
            weekString = weekString+"��";
        }else if(jishi.getWeek()==6){
            weekString = weekString+"��";
        }else if(jishi.getWeek()==7){
            weekString = weekString+"��";
        }
        week_text.setText(weekString);
        CheckBox cb =(CheckBox)convertView.findViewById(R.id.js_list_item_check);
        cb.setTag(jishi.getId());
        cb.setOnCheckedChangeListener(this);
        if(!isShowCheck){
            cb.setVisibility(View.GONE);
        }else{
            cb.setVisibility(View.VISIBLE);
            if(isSelectAll){
                cb.setChecked(true);
            }else{
                idList.clear();
                cb.setChecked(false);
            }
        }
        return convertView;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            idList.add((Integer)buttonView.getTag());
        }else{
            idList.remove((Integer)buttonView.getTag());
        }
    }
}
