package com.player.hzl.moneymanager.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.player.hzl.moneymanager.Adapter.JSAdapter;
import com.player.hzl.moneymanager.DataBase.JiShiDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Utils.DelayAnimation;
import com.player.hzl.moneymanager.Utils.GetTime;
import com.player.hzl.moneymanager.bean.JSContent;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zf2016 on 2017/2/17.
 */

public class JSNewActivity extends Activity implements View.OnClickListener {
    //�ı��༭��
    private EditText et;
    //������ɫ������LinerLayout
    LinearLayout color_ll, size_ll;
    //��������
    private TextView time;
    private ImageButton colorbt, sizebt, delbt, exitbt;
    private JiShiDB dataHelper;
    //�Ƿ�Ϊ����
    private Boolean isUpdate;
    private JSContent jishi;
    private String updateString;
    //�����С
    private float textSize = 24;
    private float updateTextSize = 0;
    private float sizeFloats[] = new float[]{20, 24, 29, 34};
    //������ɫ
    private int bgColorId = R.mipmap.js_new_et_bg_1;
    private int updateBgColorId = R.mipmap.js_new_et_bg_1;
    private int ids[] = new int[]{R.mipmap.js_new_et_bg_1, R.mipmap.js_new_et_bg_2, R.mipmap.js_new_et_bg_3, R.mipmap.js_new_et_bg_4, R.mipmap.js_new_et_bg_5};
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_new);
        time = (TextView) this.findViewById(R.id.js_new_time_text);
        et = (EditText) this.findViewById(R.id.js_et);
        et.setOnClickListener(this);
        et.setTextSize(sizeFloats[1]);
        colorbt = (ImageButton) this.findViewById(R.id.js_new_color_ib);
        colorbt.setOnClickListener(this);
        sizebt = (ImageButton) this.findViewById(R.id.js_new_size_ib);
        sizebt.setOnClickListener(this);
        delbt = (ImageButton) this.findViewById(R.id.js_new_del_ib);
        delbt.setOnClickListener(this);
        exitbt = (ImageButton) this.findViewById(R.id.js_new_exit_ib);
        exitbt.setOnClickListener(this);
        isUpdate = false;
        jishi = new JSContent();
        handler = new Handler();
        dataHelper = new JiShiDB(this);
        initUpdate();
        initBGColor();
        initTextSize();
    }

    /*
     * ��ʼ��������ɫ
     * */
    public void initBGColor() {
        color_ll = (LinearLayout) this.findViewById(R.id.js_new_bgcolor_ll);
        color_ll.setVisibility(View.GONE);
        RelativeLayout color1 = (RelativeLayout) this.findViewById(R.id.js_new_bgcolor_1_rl);
        RelativeLayout color2 = (RelativeLayout) this.findViewById(R.id.js_new_bgcolor_2_rl);
        RelativeLayout color3 = (RelativeLayout) this.findViewById(R.id.js_new_bgcolor_3_rl);
        RelativeLayout color4 = (RelativeLayout) this.findViewById(R.id.js_new_bgcolor_4_rl);
        RelativeLayout color5 = (RelativeLayout) this.findViewById(R.id.js_new_bgcolor_5_rl);
        RelativeLayout colors[] = new RelativeLayout[]{color1, color2, color3, color4, color5};
        for (RelativeLayout color : colors) {
            color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.js_new_bgcolor_1_rl:
                            bgColorId = ids[0];
                            break;
                        case R.id.js_new_bgcolor_2_rl:
                            bgColorId = ids[1];
                            break;
                        case R.id.js_new_bgcolor_3_rl:
                            bgColorId = ids[2];
                            break;
                        case R.id.js_new_bgcolor_4_rl:
                            bgColorId = ids[3];
                            break;
                        case R.id.js_new_bgcolor_5_rl:
                            bgColorId = ids[4];
                            break;
                    }
                    et.setBackgroundResource(bgColorId);
                    DelayAnimation.animationEnd(color_ll, JSNewActivity.this, handler, R.anim.picpush_right_out, 300);
                }
            });
        }
    }

    /*
     * ��ʼ���ı���С
     * */
    public void initTextSize() {
        size_ll = (LinearLayout) this.findViewById(R.id.js_new_textsize_ll);
        size_ll.setVisibility(View.GONE);
        RelativeLayout size1 = (RelativeLayout) this.findViewById(R.id.js_new_textsize_1_rl);
        RelativeLayout size2 = (RelativeLayout) this.findViewById(R.id.js_new_textsize_2_rl);
        RelativeLayout size3 = (RelativeLayout) this.findViewById(R.id.js_new_textsize_3_rl);
        RelativeLayout size4 = (RelativeLayout) this.findViewById(R.id.js_new_textsize_4_rl);
        RelativeLayout sizes[] = new RelativeLayout[]{size1, size2, size3, size4};
        for (RelativeLayout size : sizes) {
            size.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()) {
                        case R.id.js_new_textsize_1_rl:
                            textSize = sizeFloats[0];
                            break;
                        case R.id.js_new_textsize_2_rl:
                            textSize = sizeFloats[1];
                            break;
                        case R.id.js_new_textsize_3_rl:
                            textSize = sizeFloats[2];
                            break;
                        case R.id.js_new_textsize_4_rl:
                            textSize = sizeFloats[3];
                            break;
                    }
                    et.setTextSize(textSize);
                    DelayAnimation.animationEnd(size_ll, JSNewActivity.this, handler, R.anim.jz_menu_down, 300);
                }
            });
        }
    }

    /*
        * ��ʼ��������Ϣ
        * */
    public void initUpdate() {
        Intent intent = this.getIntent();
        if (intent.hasExtra("id")) {
            int id = intent.getIntExtra("id", 0);
            for (JSContent jishi : JSAdapter.jSlist) {
                if (jishi.getId() == id) {
                    time.setText(jishi.getYear() + "��" + jishi.getMonth() + "��" + jishi.getDay() + "��" + " " + jishi.getTime());
                    isUpdate = true;
                    updateString = jishi.getContent();
                    et.setText(updateString);
                    Editable ea = et.getText();//���ù��������ĩβ
                    Selection.setSelection((Spannable) ea, ea.length());
                    //��ʼ�����ֵĴ�С
                    textSize = jishi.getSize();
                    updateTextSize = textSize;
                    if (textSize == 0) {
                        textSize = 24;
                        updateTextSize = 24;
                    }
                    et.setTextSize(textSize);

                    //��ʼ��������ɫ
                    bgColorId = jishi.getColor();
                    updateBgColorId = bgColorId;
                    et.setBackgroundResource(jishi.getColor());
                    this.jishi = jishi;
                    return;
                }
            }
        } else {
            time.setText(GetTime.getYear() + "��" + GetTime.getMonth() + "��" + GetTime.getDay() + "��" + " " + GetTime.getTime());
        }
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.js_new_color_ib:
                if (color_ll.isShown()) {
                    DelayAnimation.animationEnd(color_ll, JSNewActivity.this, handler, R.anim.picpush_right_out, 300);
                } else {
                    color_ll.setVisibility(View.VISIBLE);
                    size_ll.setVisibility(View.INVISIBLE);
                    color_ll.setAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
                }
                break;
            case R.id.js_et:
                if (color_ll.isShown()) {
                    DelayAnimation.animationEnd(color_ll, JSNewActivity.this, handler, R.anim.picpush_right_out, 300);
                }
                if (size_ll.isShown()) {
                    DelayAnimation.animationEnd(size_ll, JSNewActivity.this, handler, R.anim.jz_menu_down, 300);
                }
                break;
            case R.id.js_new_size_ib:
                if (size_ll.isShown()) {
                    DelayAnimation.animationEnd(size_ll, JSNewActivity.this, handler, R.anim.jz_menu_down, 300);
                } else {
                    size_ll.setVisibility(View.VISIBLE);
                    color_ll.setVisibility(View.INVISIBLE);
                    size_ll.setAnimation(AnimationUtils.loadAnimation(this, R.anim.jz_menu_up));
                }
                break;
            case R.id.js_new_del_ib:
                showDialog("del");
                break;
            case R.id.js_new_exit_ib:
                this.finish();
                break;
        }
    }

    /*
 * �洢����ռ����ݵ����ݿ�
 * */
    public void saveToDB() {
        JSContent jishi = new JSContent();
        int year = GetTime.getYear();
        int month = GetTime.getMonth();
        int week = GetTime.getWeek();
        int day = GetTime.getDay();
        String time = GetTime.getTime();
        //����
        String content = et.getText().toString().trim();
        int color = bgColorId;
        if (content.equals(null) || content.equals("")) {
            showMsg("���벻��Ϊ��");
            return;
        }
        jishi.setYear(year);
        jishi.setMonth(month);
        jishi.setWeek(week);
        jishi.setDay(day);
        jishi.setTime(time);
        jishi.setContent(content);
        jishi.setColor(color);
        jishi.setSize(textSize);
        if (!isUpdate) {//�жϵ�ǰ���½������޸�
            dataHelper.SaveJSInfo(jishi);
            showMsg("�洢�ɹ�");
        } else {
            if (!updateString.equals(content) ||
                    updateBgColorId != bgColorId ||
                    updateTextSize != textSize) {
            }
            dataHelper.UpdateJSInfo(jishi, this.jishi.getId());
            showMsg("�޸ĳɹ�");
        }
        this.finish();
    }

    /*
     * ɾ��������
     * */

    public void showDialog(final String flag) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (flag.equals("del")) {//�����ʶΪɾ��
            builder.setTitle("�Ƿ�ȷ��ɾ��������¼��");
        }
        builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (flag.equals("del")) {
                    if (isUpdate) {
                        dataHelper.DelJSInfo(jishi.getId());
                    } else {
                        et.setText("");
                    }
                    JSNewActivity.this.finish();
                }
            }
        });
        builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
        switch (kCode) {
            case KeyEvent.KEYCODE_BACK: {
                if (color_ll.isShown() || size_ll.isShown()) {
                    //��ѡ����ɫ��ѡ�������С������ʾʱ���·��ؼ��ȹر���ʾ�Ľ���
                    if (color_ll.isShown()) {
                        DelayAnimation.animationEnd(color_ll, JSNewActivity.this, handler, R.anim.picpush_right_out, 300);
                    }
                    if (size_ll.isShown()) {
                        DelayAnimation.animationEnd(size_ll, JSNewActivity.this, handler, R.anim.jz_menu_down, 300);
                    }
                    return false;
                }
                //����ı������ݳ��ȴ���һ��洢�����ݿ���
                if (!"".equals(et.getText().toString().trim())) {
                    saveToDB();
                    this.finish();
                    return false;
                } else {
                    if (isUpdate) {//�����ǰ״̬Ϊ������Ϊ����ɾ������
                        dataHelper.DelJSInfo(jishi.getId());
                    }
                    this.finish();
                }
            }
        }
        return super.onKeyDown(kCode, kEvent);
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}

