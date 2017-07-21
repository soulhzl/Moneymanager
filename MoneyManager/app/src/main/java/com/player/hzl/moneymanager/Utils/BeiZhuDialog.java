package com.player.hzl.moneymanager.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Message;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.player.hzl.moneymanager.Activity.JZAddActivity;
import com.player.hzl.moneymanager.R;

/**
 * Created by zf2016 on 2017/2/14.
 */

/*
 * 备注dialog,主要为一个带有编辑框的弹出框，用来编辑备注
 * */
public class BeiZhuDialog extends Dialog implements View.OnClickListener {
    private Button queding, quxiao;
    private EditText et;
    private Context context;
    private View v;

    public BeiZhuDialog(Context context, String beizhuString) {
        super(context, R.style.maindialog);
        this.context = context;
        View diaView = View.inflate(context, R.layout.dialog_beizhu, null);
        this.setContentView(diaView);
        this.v = diaView;
        //添加备注编辑框
        et = (EditText) diaView.findViewById(R.id.jz_add_beizhu_et);
        //确定按钮
        queding = (Button) diaView.findViewById(R.id.jz_add_beizhu_queding);
        //取消按钮
        quxiao = (Button) diaView.findViewById(R.id.jz_add_beizhu_quxiao);
        et.clearFocus();
        queding.setOnClickListener(this);
        quxiao.setOnClickListener(this);
        this.show();
        diaView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
        if("".equals(beizhuString)){
            et.setHint("请输入备注内容...");
        }else{
            et.setText(beizhuString);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jz_add_beizhu_queding:
                String content = et.getText().toString().trim();
                if (content.length() > 0) {
                    hidden();
                    Message msg  =Message.obtain();
                    msg.what=JZAddActivity.beizhu_msg;
                    msg.obj = content;
                    JZAddActivity.mh.sendMessage(msg);
                } else {
                    Toast.makeText(context, "输入不能为空", 1000).show();
                }
                break;
            case R.id.jz_add_beizhu_quxiao:
                et.setText("");
                // 取消后的动画效果，需要启动一个线程延迟播放结束动画
                hidden();
                break;
        }
    }

    /*
     * 隐藏dialog界面
     * */
    public void hidden(){
        this.v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(250);//为了显示退出动画而延迟
                    BeiZhuDialog.this.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
