package com.player.hzl.moneymanager.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Utils.AboutDialog;

/**
 * Created by zf2016 on 2017/2/21.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener, View.OnTouchListener{

    private JiZhangDB jzda;
    private TextView set, del, about, web;
    private RelativeLayout setL, delL, aboutL, webL;
    @Override
    public int getLayoutResID() {
        return R.layout.activity_setting;
    }

    @Override
    public void initView() {
        set = findView(R.id.set_pass);
        setL = findView(R.id.set);
        del = findView(R.id.money_del);
        delL = findView(R.id.del);
        about = findView(R.id.about_sw);
        aboutL = findView(R.id.about);
        web = findView(R.id.visit_web);
        webL = findView(R.id.web);
    }

    @Override
    public void initListener() {
        set.setOnClickListener(this);
        set.setOnTouchListener(this);
        del.setOnClickListener(this);
        del.setOnTouchListener(this);
        about.setOnClickListener(this);
        about.setOnTouchListener(this);
        web.setOnClickListener(this);
        web.setOnTouchListener(this);
    }

    @Override
    public void initData() {
        jzda = new JiZhangDB(this);
    }

    /*
     * 退出弹出框
     * */
    public void showDialog(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否确认清除所有数据？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jzda.delAll();
                showMsg("数据清除完成");
            }
        });
        builder.setNeutralButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        super.onResume();
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.set_pass:
                // 设置密码
                Intent intent = new Intent(SettingActivity.this,
                        SetPasswordActivity.class);
                startActivity(intent);
                SettingActivity.this.finish();
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                break;
            case R.id.money_del:
                showDialog();
                break;
            case R.id.about_sw:
                String text = "该理财软件功能较简单，使用方便，" +
                        "\r\n若大家对此软件有自己宝贵的想法，" +
                        "\r\n可以给我提出建议~~~" ;
                new AboutDialog(this,text);
                break;
            case R.id.visit_web:
                Uri uri = Uri.parse("http://tieba.baidu.com/f?kw=%C0%ED%B2%C6&fr=ala0&tpl=5");
                Intent toweb = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(toweb);
                break;
        }
    }

    protected void enterHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SettingActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SettingActivity.this).setTitle("提示")
                .setMessage("你确定要返回到主界面吗？").setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                enterHome();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        }).show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.set:
                switch(motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        setL.setBackgroundResource(R.drawable.press_up_concor);
                        break;
                    case MotionEvent.ACTION_UP:
                        setL.setBackgroundResource(R.drawable.nor_up_concor);
                        break;
                }
                break;
            case R.id.del:
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setL.setBackgroundResource(R.drawable.press_up_concor);
                        break;
                    case MotionEvent.ACTION_UP:
                        setL.setBackgroundResource(R.drawable.nor_up_concor);
                        break;
                }
                break;
            case R.id.about:
                switch(motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setL.setBackgroundResource(R.drawable.press_up_concor);
                        break;
                    case MotionEvent.ACTION_UP:
                        setL.setBackgroundResource(R.drawable.nor_up_concor);
                        break;
                }
                break;
        }
        return false;
    }
}
