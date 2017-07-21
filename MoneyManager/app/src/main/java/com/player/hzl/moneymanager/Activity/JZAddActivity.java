package com.player.hzl.moneymanager.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.player.hzl.moneymanager.Adapter.JZMingXiAdapter;
import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Utils.BeiZhuDialog;
import com.player.hzl.moneymanager.Utils.LeiBieDialog;
import com.player.hzl.moneymanager.Utils.DelayAnimation;
import com.player.hzl.moneymanager.Utils.GetTime;
import com.player.hzl.moneymanager.bean.JZItem;
import com.player.hzl.moneymanager.bean.JZShouRu;
import com.player.hzl.moneymanager.bean.JZZhiChu;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by zf2016 on 2017/2/14.
 */

public class JZAddActivity extends Activity implements View.OnClickListener {
    // TextView�����ʱ�䣬��ע��
    private TextView jine, leibie, date, time, beizhu;
    // FrameLayout֧�������룬��������棬ȡ����
    private FrameLayout zhichu_fl, shouru_fl, jiedai_fl, save_fl, cancel_fl, del_fl;
    // LinearLayoutͼƬ���ײ����ְ�ť
    private LinearLayout num_ll;
    // ����ѡ�б�ʶImageView֧�������룬�����ͼƬ
    private ImageView zhichu_iv, shouru_iv, jiedai_iv;
    // �ײ����ְ�ť
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bd, bdel;
    // ��ť����
    private Button bt[] = new Button[] { b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, bd, bdel };
    public static MessageHandler mh;
    // ��ǰ�����յ�����Ϣ��ʾ��msg.what��
    public static final int leibie_msg = 1010, beizhu_msg = 1020;
    // ��ǰѡ���������֧�������룬���
    public static final int zhichu_flag = 2010, shouru_flag = 2020, jiedai_flag = 2030;
    // ��ǰѡ�������
    private int now_flag = zhichu_flag;
    // ���ݿ����
    JiZhangDB dataHelper;
    // ���ĵ����ͣ�֧�� ���� �����
    private int update_type, update_id, update_flag;
    // �жϵ�ǰ�ǳ�ʼ�������Ƕ��θ���
    private boolean isUpdate = false;
    // ����֧�� ���� ��� �ı����޸�ʱ��Ҫ�Ķ��ı�����
    private TextView zhichu_text, shouru_text, jiedai_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jz_add);
        mh = new MessageHandler();
        initZhiChu();
        initBt(jine);
        initUpdate();
    }

    /*
     * ��������޸ĵķ�ʽ�򿪸ý���
     */
    JZZhiChu zc = new JZZhiChu();
    JZShouRu sr = new JZShouRu();

    private void initUpdate() {
        Intent intent = this.getIntent();
        if (intent.hasExtra("update")) {
            zhichu_text = (TextView) this.findViewById(R.id.jz_add_zhichu_text);
            shouru_text = (TextView) this.findViewById(R.id.jz_add_shouru_text);
            jiedai_text = (TextView) this.findViewById(R.id.jz_add_jiedai_text);
            del_fl.setVisibility(View.VISIBLE);
            isUpdate = true;
            update_type = intent.getIntExtra("type", 0);
            update_id = intent.getIntExtra("id", 0);
            ArrayList<?> mingXiList = JZMingXiAdapter.mingXiList;
            if (update_type == zhichu_flag) {
                for (Object zhichu : mingXiList) {
                    JZZhiChu zc = (JZZhiChu) zhichu;
                    if (update_id == zc.getZc_Id()) {
                        this.zc = zc;
                        getZhiChuType(zc);
                        return;
                    }
                }
            } else if (update_type == shouru_flag) {
                for (Object shouru : mingXiList) {
                    JZShouRu sr = (JZShouRu) shouru;
                    if (update_id == sr.getSr_Id()) {
                        this.sr = sr;
                        getShouRuType(sr);
                        return;
                    }
                }
            }
        }
    }

    /*
     * �жϸ�֧�������Ƿ�Ϊ���
     */
    public void getZhiChuType(JZZhiChu zc) {
        if (zc.getZc_Item().equals(JZItem.jiechu) || zc.getZc_Item().equals(JZItem.huankuan)) {
            now_flag = jiedai_flag;
            jiedai_text.setText("�޸Ľ��");
            leibie.setText(zc.getZc_Item());
            setTopBG(now_flag, jiedai_iv);
            zhichu_fl.setVisibility(View.INVISIBLE);
            shouru_fl.setVisibility(View.INVISIBLE);
        } else {
            now_flag = zhichu_flag;
            zhichu_text.setText("�޸�֧��");
            leibie.setText(zc.getZc_Item() + ">" + zc.getZc_SubItem());
            setTopBG(now_flag, zhichu_iv);
            jiedai_fl.setVisibility(View.INVISIBLE);
            shouru_fl.setVisibility(View.INVISIBLE);
        }
        update_flag = zhichu_flag;// ����ɾ����ǰ���ݹ���
        jine.setText(zc.getZc_Count() + "");
        date.setText(zc.getZc_Year() + "-" + zc.getZc_Month() + "-" + zc.getZc_Day());
        time.setText(zc.getZc_Time());
        beizhu.setText(zc.getZc_Beizhu());
    }

    /*
     * �жϸ����������Ƿ�Ϊ���
     */
    public void getShouRuType(JZShouRu sr) {
        if (sr.getSr_Item().equals(JZItem.jieru) || sr.getSr_Item().equals(JZItem.shoukuan)) {
            now_flag = jiedai_flag;// �����жϵ�ǰ״̬
            jiedai_text.setText("�޸Ľ��");
            setTopBG(now_flag, jiedai_iv);
            zhichu_fl.setVisibility(View.INVISIBLE);
            shouru_fl.setVisibility(View.INVISIBLE);
        } else {
            now_flag = shouru_flag;
            shouru_text.setText("�޸�����");
            setTopBG(now_flag, shouru_iv);
            jiedai_fl.setVisibility(View.INVISIBLE);
            zhichu_fl.setVisibility(View.INVISIBLE);
        }
        update_flag = shouru_flag;// ����ɾ����ǰ���ݹ���
        jine.setText(sr.getSr_Count() + "");
        leibie.setText(sr.getSr_Item());
        date.setText(sr.getSr_Year() + "-" + sr.getSr_Month() + "-" + sr.getSr_Day());
        time.setText(sr.getSr_Time());
        beizhu.setText(sr.getSr_Beizhu());
    }

    private void initZhiChu() {
        // �������
        jine = (TextView) findViewById(R.id.jz_add_jine_text);
        jine.setOnClickListener(new TextClick());
        // ���
        leibie = (TextView) findViewById(R.id.jz_add_leibie_text);
        leibie.setOnClickListener(new TextClick());
        // ����
        date = (TextView) findViewById(R.id.jz_add_date_text);
        date.setText(GetTime.getYear() + "-" + GetTime.getMonth() + "-" + GetTime.getDay());
        date.setOnClickListener(new TextClick());
        // ʱ��
        time = (TextView) findViewById(R.id.jz_add_time_text);
        time.setText(GetTime.getHour() + ":" + GetTime.getMinute());
        time.setOnClickListener(new TextClick());
        // ��ע
        beizhu = (TextView) findViewById(R.id.jz_add_beizhu_text);
        beizhu.setOnClickListener(new TextClick());
        // �ײ����ְ�ť
        num_ll = (LinearLayout) findViewById(R.id.jz_add_numbt_ll);
        num_ll.setVisibility(View.GONE);
        DelayAnimation.animationStart(num_ll, this, mh, R.anim.jz_menu_up, 400);

        zhichu_iv = (ImageView) findViewById(R.id.jz_add_zhichu_iv);
        shouru_iv = (ImageView) findViewById(R.id.jz_add_shouru_iv);
        jiedai_iv = (ImageView) findViewById(R.id.jz_add_jiedai_iv);

        zhichu_fl = (FrameLayout) findViewById(R.id.jz_add_zhichu_fl);
        zhichu_fl.setOnClickListener(this);
        shouru_fl = (FrameLayout) findViewById(R.id.jz_add_shouru_fl);
        shouru_fl.setOnClickListener(this);
        jiedai_fl = (FrameLayout) findViewById(R.id.jz_add_jiedai_fl);
        jiedai_fl.setOnClickListener(this);
        save_fl = (FrameLayout) findViewById(R.id.jz_add_save_fl);
        save_fl.setOnClickListener(this);
        cancel_fl = (FrameLayout) findViewById(R.id.jz_add_cancel_fl);
        cancel_fl.setOnClickListener(this);
        // ɾ����ǰҪ�޸ĵ����ݣ�ֻ���޸�ʱ��Ч
        del_fl = (FrameLayout) this.findViewById(R.id.jz_add_del_fl);
        del_fl.setOnClickListener(this);
        del_fl.setVisibility(View.INVISIBLE);
    }

    /*
     * ��ʼ�����ְ�ť
     */
    private void initBt(TextView tv) {
        int id[] = new int[] { R.id.jz_add_bt_0, R.id.jz_add_bt_1, R.id.jz_add_bt_2, R.id.jz_add_bt_3, R.id.jz_add_bt_4, R.id.jz_add_bt_5, R.id.jz_add_bt_6, R.id.jz_add_bt_7, R.id.jz_add_bt_8, R.id.jz_add_bt_9, R.id.jz_add_bt_d, R.id.jz_add_bt_del };
        for (int i = 0; i < bt.length; i++) {
            bt[i] = (Button) this.findViewById(id[i]);
            bt[i].setOnClickListener(new MyClick(tv));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jz_add_zhichu_fl:// ֧��tab
                setTopBG(zhichu_flag, zhichu_iv);
                leibie.setText("����>���");
                break;
            case R.id.jz_add_shouru_fl:// ����tab
                setTopBG(shouru_flag, shouru_iv);
                leibie.setText("����");
                break;
            case R.id.jz_add_jiedai_fl:// ���tab
                setTopBG(jiedai_flag, jiedai_iv);
                leibie.setText("���");
                break;
            case R.id.jz_add_save_fl:// ���水ť
                saveToDB();
                break;
            case R.id.jz_add_cancel_fl:// ȡ����ť
                this.finish();
                break;
            case R.id.jz_add_del_fl:// ɾ����ť
                dataHelper = new JiZhangDB(this);
                switch (update_flag) {
                    case zhichu_flag:
                        int i1 = dataHelper.DelZhiChuInfo(update_id);
                        if (i1 > 0) {
                            showMsg("ɾ���ɹ�");
                            this.finish();
                        } else {
                            showMsg("ɾ��ʧ��");
                        }
                        break;
                    case shouru_flag:
                        int i2 = dataHelper.DelShouRuInfo(update_id);
                        if (i2 > 0) {
                            showMsg("ɾ���ɹ�");
                            this.finish();
                        } else {
                            showMsg("ɾ��ʧ��");
                        }
                        break;
                }
                break;
        }
    }

    /*
     * �洢֧�������������ݿ�
     */
    public void saveToDB() {
        dataHelper = new JiZhangDB(this);
        JZZhiChu zhichu = new JZZhiChu();
        JZShouRu shouru = new JZShouRu();
        // ���
        String leibies = leibie.getText().toString().trim();
        String items[] = leibies.split(">");
        // ����
        String dateString = date.getText().toString().trim();
        String dates[] = dateString.split("-");
        // ʱ��
        String timeString = time.getText().toString().trim();
        // ���
        String jineString = jine.getText().toString().trim();
        // ��ע
        String beizhuString = beizhu.getText().toString().trim();
        if (jineString.equals("0.00")) {
            showMsg("����Ϊ��");
            return;
        }
        if (now_flag == zhichu_flag) {
            zhichu.setZc_Item(items[0]);
            zhichu.setZc_SubItem(items[1]);
            zhichu.setZc_Year(Integer.parseInt(dates[0]));
            zhichu.setZc_Month(Integer.parseInt(dates[1]));
            zhichu.setZc_Day(Integer.parseInt(dates[2]));
            zhichu.setZc_Time(timeString);
            zhichu.setZc_Week(GetTime.getTheWeekOfYear(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])));
            zhichu.setZc_Count(Double.parseDouble(jineString));
            zhichu.setZc_Beizhu(beizhuString);
            if (!isUpdate) {
                dataHelper.SaveZhiChuInfo(zhichu);
                showMsg("����֧���洢�ɹ�");
            } else {
                dataHelper.UpdateZhiChuInfo(zhichu, zc.getZc_Id());
                showMsg("����֧���޸ĳɹ�");
            }
        } else if (now_flag == shouru_flag) {
            shouru.setSr_Item(leibies);
            shouru.setSr_Year(Integer.parseInt(dates[0]));
            shouru.setSr_Month(Integer.parseInt(dates[1]));
            shouru.setSr_Day(Integer.parseInt(dates[2]));
            shouru.setSr_Time(timeString);
            shouru.setSr_Week(GetTime.getTheWeekOfYear(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])));
            shouru.setSr_Count(Double.parseDouble(jineString));
            shouru.setSr_Beizhu(beizhuString);
            if (!isUpdate) {
                dataHelper.SaveShouRuInfo(shouru);
                showMsg("��������洢�ɹ�");
            } else {
                dataHelper.UpdateShouRuInfo(shouru, sr.getSr_Id());
                showMsg("���������޸ĳɹ�");
            }
        } else if (now_flag == jiedai_flag) {// ����а�������ͽ�� �ֱ�洢��֧����������
            if (leibies.equals(JZItem.jiechu) || leibies.equals(JZItem.huankuan)) {
                zhichu.setZc_Item(leibies);
                zhichu.setZc_SubItem("");
                zhichu.setZc_Year(Integer.parseInt(dates[0]));
                zhichu.setZc_Month(Integer.parseInt(dates[1]));
                zhichu.setZc_Day(Integer.parseInt(dates[2]));
                zhichu.setZc_Time(timeString);
                zhichu.setZc_Week(GetTime.getTheWeekOfYear(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])));
                zhichu.setZc_Count(Double.parseDouble(jineString));
                zhichu.setZc_Beizhu(beizhuString);
                if (!isUpdate) {
                    dataHelper.SaveZhiChuInfo(zhichu);
                    showMsg("����֧���洢�ɹ�");
                } else {
                    dataHelper.UpdateZhiChuInfo(zhichu, zc.getZc_Id());
                    showMsg("����֧���޸ĳɹ�");
                }
            } else if (leibies.equals(JZItem.jieru) || leibies.equals(JZItem.shoukuan)) {
                shouru.setSr_Item(leibies);
                shouru.setSr_Year(Integer.parseInt(dates[0]));
                shouru.setSr_Month(Integer.parseInt(dates[1]));
                shouru.setSr_Day(Integer.parseInt(dates[2]));
                shouru.setSr_Time(timeString);
                shouru.setSr_Week(GetTime.getTheWeekOfYear(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])));
                shouru.setSr_Count(Double.parseDouble(jineString));
                shouru.setSr_Beizhu(beizhuString);
                if (!isUpdate) {
                    dataHelper.SaveShouRuInfo(shouru);
                    showMsg("��������洢�ɹ�");
                } else {
                    dataHelper.UpdateShouRuInfo(shouru, sr.getSr_Id());
                    showMsg("��������޸ĳɹ�");
                }
            }
        }
        this.finish();
    }

    /*
     * ���ö����л���ť�ı���������
     */
    private void setTopBG(int now_flag, ImageView iv) {
        this.now_flag = now_flag;// ��ֵ��ȫ�ֱ���
        shouru_iv.setImageDrawable(null);
        zhichu_iv.setImageDrawable(null);
        jiedai_iv.setImageDrawable(null);
        iv.setImageResource(R.mipmap.jz_tab1_bt_bgs);
        iv.setAnimation(AnimationUtils.loadAnimation(this, R.anim.jz_top_right2left));
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        super.onResume();
    }

    /*
     * ����dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == R.id.jz_add_date_text) {// �������ťΪR.id.button1��ʾ��dialog
            Calendar c = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener osl = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth + " ");
                }
            };
            new DatePickerDialog(this, 0, osl, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
        } else if (id == R.id.jz_add_time_text) {
            Calendar c = Calendar.getInstance();
            TimePickerDialog.OnTimeSetListener otl = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    time.setText(hourOfDay + ":" + minute);
                }
            };
            new TimePickerDialog(this, 0, otl, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
        }
        return null;
    }

    /*
     * �������ּ���
     */
    private class TextClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.jz_add_jine_text:// ���num��ʾ���ְ���
                    if (num_ll.isShown()) {
                        DelayAnimation.animationEnd(num_ll, JZAddActivity.this, mh, R.anim.jz_menu_down, 300);
                    } else {
                        num_ll.setAnimation(AnimationUtils.loadAnimation(JZAddActivity.this, R.anim.jz_menu_up));
                        num_ll.setVisibility(View.VISIBLE);
                    }
                    break;
                case R.id.jz_add_leibie_text:// �������
                    new LeiBieDialog(JZAddActivity.this, now_flag);
                    break;
                case R.id.jz_add_date_text:// ��������
                    onCreateDialog(R.id.jz_add_date_text);
                    break;
                case R.id.jz_add_time_text:// ����ʱ��
                    onCreateDialog(R.id.jz_add_time_text);
                    break;
                case R.id.jz_add_beizhu_text:// ��ӱ�ע
                    String beizhuString = beizhu.getText().toString();
                    if(beizhuString.equals("�ޱ�ע")){
                        new BeiZhuDialog(JZAddActivity.this,"");
                    }else{
                        new BeiZhuDialog(JZAddActivity.this,beizhuString);
                    }
                    break;
            }
        }
    }

    /*
     * �������ּ���
     */
    private class MyClick implements View.OnClickListener {
        private TextView tv;

        public MyClick(TextView tv) {
            this.tv = tv;
        }

        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            String jines = tv.getText().toString().trim();
            if (v.getId() != R.id.jz_add_bt_del && jines.length() > 9) {
                showMsg("������ô��Ǯ��");
                return;
            }
            if (v.getId() != R.id.jz_add_bt_del) {
                if (jines.equals("0.00")) {// ��һ������ʱ
                    if (!button.getText().equals(".") && !button.getText().equals("0")) {
                        tv.setText(button.getText());
                    }
                } else {
                    if (jines.contains(".")) {// ������Ѿ�����С����
                        if (button.getText().equals(".")) {// �����ΪС����
                            showMsg("���Ŵ���");
                            return;
                        }
                        // С����󳬹���λʱ
                        if ((jines.length() - jines.indexOf(".")) <= 2) {
                            tv.append(button.getText());
                        } else {
                            showMsg("������ô����Ǯ��");
                        }
                    } else {
                        tv.append(button.getText());
                    }
                }
            } else {// �����ɾ����
                if (!jines.equals("0.00")) {
                    if (jines.length() > 1) {
                        String str = jines.substring(0, jines.length() - 1);
                        tv.setText(str);
                    } else {
                        tv.setText("0.00");
                    }
                }
            }
        }
    }

    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
        switch (kCode) {
            case KeyEvent.KEYCODE_BACK: {
                if (num_ll.isShown()) {
                    DelayAnimation.animationEnd(num_ll, JZAddActivity.this, mh, R.anim.jz_menu_down, 300);
                    return false;
                } else {
                    this.finish();
                }
            }
        }
        return super.onKeyDown(kCode, kEvent);
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public class MessageHandler extends Handler {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case leibie_msg:
                    leibie.setText((String) msg.obj);
                    break;
                case beizhu_msg:
                    beizhu.setText((String) msg.obj);
                    break;
                default:
                    break;
            }
        }
    }
}
