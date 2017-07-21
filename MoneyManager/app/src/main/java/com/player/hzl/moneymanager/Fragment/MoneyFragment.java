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

    // 支出界面的TextView
    TextView zhichu_week, zhichu_month, zhichu_shouru_month, yusuan_month, yusuanyue_month;
    // 收入界面的TextView
    TextView shouru_year, shouru_month, shouru_day;
    // 顶部framelayout支出和收入
    private FrameLayout zhihcu_fl, shouru_fl;
    // 绘图区域
    private RelativeLayout zhichu_shang_rl, zhichu_xia_rl, shouru_shang_rl;
    // 底部环形按钮 添加， 明细，报表，预算，设置。
    private Button tianjia, mingxi, baobiao, yusuan;
    // tab1顶部支出和收入按钮选中后背景图像
    private ImageView ivZhichu, ivShuru, menu;
    // tab1顶部支出和收入按钮选中后下面显示的内容
    private LinearLayout zhichull, shourull;
    // 底部环形按钮是否显示
    private boolean isShown = false;
    // button集合 方便管理按钮显示隐藏
    private Button bts[] = null;
    // 数据库操作
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
     * 底部环形按钮显示动画
     */
    public void btAnimation() {
        isShown = true;
        for (Button bt : bts) {
            btUpAnimation(bt);
        }
    }

    //显示底部环形按钮
    public void btUpAnimation(Button b) {
        b.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_menu_up));
        b.setVisibility(View.VISIBLE);
    }

    /*
     * 隐藏底部环形按钮动画
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
     * 隐藏底部环形按钮
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
            case R.id.jz_main_zhichu_fl:// 顶部支出按钮
                ivZhichu.setImageResource(R.mipmap.jz_tab1_bt_bgs);
                ivShuru.setImageDrawable(null);
                ivZhichu.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_top_right2left));
                RunAnimation.delayShow(zhichull, shourull);
                break;
            case R.id.jz_main_shouru_fl:// 顶部收入按钮
                ivShuru.setImageResource(R.mipmap.jz_tab1_bt_bgs);
                ivZhichu.setImageDrawable(null);
                ivShuru.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_top_left2right));
                RunAnimation.delayShow(shourull, zhichull);
                break;
            case R.id.jz_mian_menuiv:// 底部环形按钮
                if (!isShown) {
                    btAnimation();
                    menu.setImageResource(R.mipmap.jz_main_more_s);
                } else {
                    btHiddenAnimation();
                    menu.setImageResource(R.mipmap.jz_main_more);
                }
                break;
            case R.id.jz_main_bt_add:// 记账按钮
                changeActivity(JZAddActivity.class);
                break;
            case R.id.jz_main_bt_mingxi:// 明细
                changeActivity(JZMingXiActivity.class);
                break;
            case R.id.jz_main_bt_yusuan:// 预算
                changeActivity(JZYuSuanActivity.class);
                break;
            case R.id.jz_main_bt_baobiao://报表
                changeActivity(JZBaoBiaoActivity.class);
                break;
        }
    }

    /*
     * 切换界面
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
     * 获取支出 显示在页面上
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
        //判断当前状态确定是否绘图
        if(count_zc_week>0||count_zc_yue>0||count_sr_yue>0){
            // 创建绘图区域
            zhichu_shang_rl.setBackgroundDrawable(null);
            zhichu_shang_rl.addView(new JZMainPaintView(getContext(), Color.BLUE,30,count_zc_week/20,"本周支出"));
            zhichu_shang_rl.addView(new JZMainPaintView(getContext(),Color.BLACK,100,count_zc_yue/20,"本月支出"));
            zhichu_shang_rl.addView(new JZMainPaintView(getContext(),Color.CYAN,170,count_sr_yue/20,"本月收入"));
        }

        final float yusuan_yue = JZSqliteHelper.readPreferenceFile(getContext(), JZSqliteHelper.YUSUAN_MONTH, JZSqliteHelper.YUSUAN_MONTH);
        // 月预算
        yusuan_month.setText(yusuan_yue + "");
        // 月预算余额
        final float zhichu_yue = Float.parseFloat(zhichu_month.getText().toString().trim());
        yusuanyue_month.setText((yusuan_yue - zhichu_yue) + "");

        int bujin=0;//根据不同的剩余调整步进速度
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
         * 获取收入页面各个金额总数
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
        //判断当前状态确定是否绘图
        if(count_sr_year>0||count_sr_yue>0||count_sr_day>0){
            // 创建绘图区域
            shouru_shang_rl.setBackgroundDrawable(null);
            shouru_shang_rl.addView(new JZMainPaintView(getContext(),Color.BLUE,30,count_sr_year/40,"本年收入"));
            shouru_shang_rl.addView(new JZMainPaintView(getContext(),Color.BLACK,100,count_sr_yue/40,"本月收入"));
            shouru_shang_rl.addView(new JZMainPaintView(getContext(),Color.CYAN,170,count_sr_day/40,"今天收入"));
        }
    }


}
