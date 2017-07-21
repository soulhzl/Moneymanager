package com.player.hzl.moneymanager.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.player.hzl.moneymanager.Activity.JZAddActivity;
import com.player.hzl.moneymanager.Activity.JZBaoBiaoActivity;
import com.player.hzl.moneymanager.Activity.JZMingXiActivity;
import com.player.hzl.moneymanager.Activity.JZYuSuanActivity;
import com.player.hzl.moneymanager.DataBase.JZSqliteHelper;
import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Utils.RunAnimation;
import com.player.hzl.moneymanager.Utils.GetTime;
import com.player.hzl.moneymanager.Utils.JZYuEPaintView;
import com.player.hzl.moneymanager.Utils.JZMainPaintView;
import com.player.hzl.moneymanager.bean.JZShouRu;
import com.player.hzl.moneymanager.bean.JZZhiChu;

import java.util.List;

/**
 * Created by acer on 2017/1/31.
 */
public class MoneyFragment extends BaseFragmet implements View.OnClickListener{

    // ֧�������TextView
    TextView zhichu_week, zhichu_month, zhichu_shouru_month, yusuan_month, yusuanyue_month;
    // ��������TextView
    TextView shouru_year, shouru_month, shouru_day;
    // ����framelayout֧��������
    private FrameLayout zhihcu_fl, shouru_fl;
    // ��ͼ����
    private RelativeLayout zhichu_shang_rl, zhichu_xia_rl, shouru_shang_rl;
    // �ײ����ΰ�ť ��ӣ� ��ϸ������Ԥ�㣬���á�
    private Button tianjia, mingxi, baobiao, yusuan;
    // tab1����֧�������밴ťѡ�к󱳾�ͼ��
    private ImageView ivZhichu, ivShuru, menu;
    // tab1����֧�������밴ťѡ�к�������ʾ������
    private LinearLayout zhichull, shourull;
    // �ײ����ΰ�ť�Ƿ���ʾ
    private boolean isShown = false;
    // button���� �������ť��ʾ����
    private Button bts[] = null;
    // ���ݿ����
    JiZhangDB dataHelper;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_money;
    }

    @Override
    public void initView() {
        ivZhichu = findView(R.id.jz_tab1_ivzhichu);
        ivShuru = findView(R.id.jz_tab1_ivshouru);
        zhichull = findView(R.id.jz_ll_zhichu);
        shourull = findView(R.id.jz_ll_shouru);
        zhichull.setVisibility(View.VISIBLE);
        shourull.setVisibility(View.GONE);
        ivZhichu.setImageResource(R.mipmap.jz_tab1_bt_bgs);
        zhichu_shang_rl = findView(R.id.jz_main_zhichu_pic_shang_rl);
        zhichu_xia_rl = findView(R.id.jz_main_zhichu_pic_xia_rl);
        shouru_shang_rl = findView(R.id.jz_main_shouru_pic_shang_rl);
        zhichu_week = findView(R.id.jz_main_zhichu_week_text);
        zhichu_month = findView(R.id.jz_main_zhichu_month_text);
        zhichu_shouru_month = findView(R.id.jz_main_zhichu_shouru_text);
        yusuan_month = findView(R.id.jz_main_zhichu_yusuan_text);
        yusuanyue_month = findView(R.id.jz_main_zhichu_yusuanyue_text);
        shouru_year = findView(R.id.jz_main_shouru_year_text);
        shouru_month = findView(R.id.jz_main_shouru_month_text);
        shouru_day = findView(R.id.jz_main_shouru_day_text);

        zhihcu_fl = findView(R.id.jz_main_zhichu_fl);
        shouru_fl = findView(R.id.jz_main_shouru_fl);
        tianjia = findView(R.id.jz_main_bt_add);
        mingxi = findView(R.id.jz_main_bt_mingxi);
        baobiao = findView(R.id.jz_main_bt_baobiao);
        yusuan = findView(R.id.jz_main_bt_yusuan);
        menu = findView(R.id.jz_mian_menuiv);
        bts = new Button[] { tianjia, mingxi, baobiao, yusuan };
    }

    @Override
    public void initListener() {
        zhihcu_fl.setOnClickListener(this);
        shouru_fl.setOnClickListener(this);
        tianjia.setOnClickListener(this);
        mingxi.setOnClickListener(this);
        baobiao.setOnClickListener(this);
        yusuan.setOnClickListener(this);
        menu.setOnClickListener(this);
        hiddenView();
    }

    @Override
    public void initData() {
        dataHelper = new JiZhangDB(getContext());
    }

    @Override
    public void onResume() {
        zhichu_shang_rl.removeAllViews();
        zhichu_xia_rl.removeAllViews();
        shouru_shang_rl.removeAllViews();

        getZhiChu();
        getShouRu();
        super.onResume();
    }

    @Override
    public void onPause() {
        hiddenView();
        menu.setImageResource(R.mipmap.jz_main_more);
        super.onPause();
    }

    /*
     * �ײ����ΰ�ť��ʾ����
     */
    public void btAnimation() {
        isShown = true;
        for (Button bt : bts) {
            btUpAnimation(bt);
        }
    }

    //��ʾ�ײ����ΰ�ť
    public void btUpAnimation(Button b) {
        b.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_menu_up));
        b.setVisibility(View.VISIBLE);
    }

    /*
     * ���صײ����ΰ�ť����
     */
    public void btHiddenAnimation() {
        isShown = false;
        final Handler handler = new Handler();
        for (Button bt : bts) {
            bt.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_menu_down));
        }
        new Thread() {
            public void run() {
                try {
                    sleep(300);
                    handler.post(new Runnable() {
                        public void run() {
                            hiddenView();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /*
     * ���صײ����ΰ�ť
     */
    private void hiddenView() {
        isShown = false;
        for (Button bt : bts) {
            bt.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jz_main_zhichu_fl:// ����֧����ť
                ivZhichu.setImageResource(R.mipmap.jz_tab1_bt_bgs);
                ivShuru.setImageDrawable(null);
                ivZhichu.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_top_right2left));
                RunAnimation.delayShow(zhichull, shourull);
                break;
            case R.id.jz_main_shouru_fl:// �������밴ť
                ivShuru.setImageResource(R.mipmap.jz_tab1_bt_bgs);
                ivZhichu.setImageDrawable(null);
                ivShuru.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_top_left2right));
                RunAnimation.delayShow(shourull, zhichull);
                break;
            case R.id.jz_mian_menuiv:// �ײ����ΰ�ť
                if (!isShown) {
                    btAnimation();
                    menu.setImageResource(R.mipmap.jz_main_more_s);
                } else {
                    btHiddenAnimation();
                    menu.setImageResource(R.mipmap.jz_main_more);
                }
                break;
            case R.id.jz_main_bt_add:// ���˰�ť
                changeActivity(JZAddActivity.class);
                break;
            case R.id.jz_main_bt_mingxi:// ��ϸ
                changeActivity(JZMingXiActivity.class);
                break;
            case R.id.jz_main_bt_yusuan:// Ԥ��
                changeActivity(JZYuSuanActivity.class);
                break;
            case R.id.jz_main_bt_baobiao://����
                changeActivity(JZBaoBiaoActivity.class);
                break;
        }
    }

    /*
     * �л�����
     */
    public void changeActivity(Class<?> c) {
        Intent intent = new Intent(getContext(), c);
        startActivity(intent);

    }

    public void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
        switch (kCode) {
            case KeyEvent.KEYCODE_MENU:
                if (isShown) {
                    btHiddenAnimation();
                    menu.setImageResource(R.mipmap.jz_main_more);
                } else {
                    btAnimation();
                    menu.setImageResource(R.mipmap.jz_main_more_s);
                }
                break;
        }
        return onKeyDown(kCode, kEvent);
    }

    /*
     * ��ȡ֧�� ��ʾ��ҳ����
     */
    float count_sr_yue;
    public void getZhiChu() {
        count_sr_yue = 0;
        float count_zc_week = 0,count_zc_yue = 0;
        String selectionWeek = JZZhiChu.ZC_WEEK + "=" + GetTime.getWeekOfYear();
        List<JZZhiChu> zhichuWeekList = dataHelper.GetZhiChuList(selectionWeek);
        if (zhichuWeekList != null) {
            for (JZZhiChu zhichu : zhichuWeekList) {
                count_zc_week += zhichu.getZc_Count();
            }
            zhichu_week.setText(count_zc_week + "");
        } else {
            zhichu_week.setText(0 + "");
        }

        String selectionMonth = JZZhiChu.ZC_YEAR + "=" + GetTime.getYear() + " and " + JZZhiChu.ZC_MONTH + "=" + GetTime.getMonth();
        List<JZZhiChu> zhichuMonthList = dataHelper.GetZhiChuList(selectionMonth);
        if (zhichuMonthList != null) {
            for (JZZhiChu zhichu : zhichuMonthList) {
                count_zc_yue += zhichu.getZc_Count();
            }
            zhichu_month.setText(count_zc_yue + "");
        } else {
            zhichu_month.setText(0 + "");
        }

        String selectionShouRuMonth = JZShouRu.SR_YEAR + "=" + GetTime.getYear() + " and " + JZShouRu.SR_MONTH + "=" + GetTime.getMonth();
        List<JZShouRu> shouruMonthList = dataHelper.GetShouRuList(selectionShouRuMonth);
        if (shouruMonthList != null) {
            for (JZShouRu shouru : shouruMonthList) {
                count_sr_yue += shouru.getSr_Count();
            }
            zhichu_shouru_month.setText(count_sr_yue + "");
            shouru_month.setText(count_sr_yue + "");
        } else {
            zhichu_shouru_month.setText(0 + "");
            shouru_month.setText(0 + "");
        }
        //�жϵ�ǰ״̬ȷ���Ƿ��ͼ
        if(count_zc_week>0||count_zc_yue>0||count_sr_yue>0){
            // ������ͼ����
            zhichu_shang_rl.setBackgroundDrawable(null);
            zhichu_shang_rl.addView(new JZMainPaintView(getContext(), Color.BLUE,30,count_zc_week/20,"����֧��"));
            zhichu_shang_rl.addView(new JZMainPaintView(getContext(),Color.BLACK,100,count_zc_yue/20,"����֧��"));
            zhichu_shang_rl.addView(new JZMainPaintView(getContext(),Color.CYAN,170,count_sr_yue/20,"��������"));
        }

        final float yusuan_yue = JZSqliteHelper.readPreferenceFile(getContext(), JZSqliteHelper.YUSUAN_MONTH, JZSqliteHelper.YUSUAN_MONTH);
        // ��Ԥ��
        yusuan_month.setText(yusuan_yue + "");
        // ��Ԥ�����
        final float zhichu_yue = Float.parseFloat(zhichu_month.getText().toString().trim());
        yusuanyue_month.setText((yusuan_yue - zhichu_yue) + "");

        int bujin=0;//���ݲ�ͬ��ʣ����������ٶ�
        if((yusuan_yue/zhichu_yue)<0.3){
            bujin = 1;
        }else if((yusuan_yue/zhichu_yue)>=0.3&&(yusuan_yue/zhichu_yue)<=0.6){
            bujin = 3;
        }else {
            bujin = 6;
        }
        if(yusuan_yue>0||zhichu_yue>0){
            zhichu_xia_rl.setBackgroundDrawable(null);
            zhichu_xia_rl.addView(new JZYuEPaintView(getContext(),0,0,Color.BLUE,50));
            zhichu_xia_rl.addView(new JZYuEPaintView(getContext(),yusuan_yue,zhichu_yue,Color.RED,bujin));
        }
    }

    /*
         * ��ȡ����ҳ������������
         */
    public void getShouRu() {
        float count_sr_year = 0,count_sr_day = 0;
        String selectionShouRuYear = JZShouRu.SR_YEAR + "=" + GetTime.getYear();
        List<JZShouRu> shouruYearList = dataHelper.GetShouRuList(selectionShouRuYear);
        if (shouruYearList != null) {
            count_sr_year = 0;
            for (JZShouRu shouru : shouruYearList) {
                count_sr_year += shouru.getSr_Count();
            }
            shouru_year.setText(count_sr_year + "");
        } else {
            shouru_year.setText(0 + "");
        }

        String selectionShouRuDay = JZShouRu.SR_YEAR + "=" + GetTime.getYear() + " and " + JZShouRu.SR_MONTH + "=" + GetTime.getMonth()+" and "+ JZShouRu.SR_DAY + "=" + GetTime.getDay();
        List<JZShouRu> shouruDayList = dataHelper.GetShouRuList(selectionShouRuDay);
        if (shouruDayList != null) {
            count_sr_day = 0;
            for (JZShouRu shouru : shouruDayList) {
                count_sr_day += shouru.getSr_Count();
            }
            shouru_day.setText(count_sr_day + "");
        } else {
            shouru_day.setText(0 + "");
        }
        //�жϵ�ǰ״̬ȷ���Ƿ��ͼ
        if(count_sr_year>0||count_sr_yue>0||count_sr_day>0){
            // ������ͼ����
            shouru_shang_rl.setBackgroundDrawable(null);
            shouru_shang_rl.addView(new JZMainPaintView(getContext(),Color.BLUE,30,count_sr_year/40,"��������"));
            shouru_shang_rl.addView(new JZMainPaintView(getContext(),Color.BLACK,100,count_sr_yue/40,"��������"));
            shouru_shang_rl.addView(new JZMainPaintView(getContext(),Color.CYAN,170,count_sr_day/40,"��������"));
        }
    }


}
