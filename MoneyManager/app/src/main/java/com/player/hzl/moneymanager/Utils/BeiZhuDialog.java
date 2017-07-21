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
 * ��עdialog,��ҪΪһ�����б༭��ĵ����������༭��ע
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
        //��ӱ�ע�༭��
        et = (EditText) diaView.findViewById(R.id.jz_add_beizhu_et);
        //ȷ����ť
        queding = (Button) diaView.findViewById(R.id.jz_add_beizhu_queding);
        //ȡ����ť
        quxiao = (Button) diaView.findViewById(R.id.jz_add_beizhu_quxiao);
        et.clearFocus();
        queding.setOnClickListener(this);
        quxiao.setOnClickListener(this);
        this.show();
        diaView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
        if("".equals(beizhuString)){
            et.setHint("�����뱸ע����...");
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
                    Toast.makeText(context, "���벻��Ϊ��", 1000).show();
                }
                break;
            case R.id.jz_add_beizhu_quxiao:
                et.setText("");
                // ȡ����Ķ���Ч������Ҫ����һ���߳��ӳٲ��Ž�������
                hidden();
                break;
        }
    }

    /*
     * ����dialog����
     * */
    public void hidden(){
        this.v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_out));
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(250);//Ϊ����ʾ�˳��������ӳ�
                    BeiZhuDialog.this.dismiss();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
