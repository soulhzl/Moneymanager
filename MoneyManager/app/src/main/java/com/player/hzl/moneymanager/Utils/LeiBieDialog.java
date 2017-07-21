package com.player.hzl.moneymanager.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.player.hzl.moneymanager.Activity.JZAddActivity;
import com.player.hzl.moneymanager.Adapter.JZLeibieAdapter;
import com.player.hzl.moneymanager.R;

/**
 * Created by zf2016 on 2017/2/14.
 */

/*
 * 类别选择dialog
 * */
public class LeiBieDialog extends Dialog implements View.OnClickListener {
    //类别列表和类别子类列表
    private ListView lbList, lbsubList;
    //顶部背景
    private RelativeLayout rLayout;
    private Context context;
    private View lbView;
    //类别列表适配器
    private JZLeibieAdapter adapter;
    //类别和类别子类标识
    public static final int flagsubleibie = 3020;
    //当前选择的选项，收入，支出，借贷
    private int now_flag = 0;
    public LeiBieDialog(Context context, int now_flag) {
        super(context, R.style.leibiedialog);
        this.context = context;
        this.now_flag = now_flag;
        lbView = View.inflate(context, R.layout.dialog_leibie, null);
        this.setContentView(lbView);
        //类别适配器
        adapter = new JZLeibieAdapter(context, now_flag, null);
        //类别列表
        lbList = (ListView) lbView.findViewById(R.id.leibie_dialog_list);
        lbList.setAdapter(adapter);
        lbList.setOnItemClickListener(new clickItem());
        //类别子类列表
        lbsubList = (ListView) lbView.findViewById(R.id.leibie_dialog_sub_list);
        lbsubList.setVisibility(View.GONE);
        rLayout = (RelativeLayout) lbView.findViewById(R.id.leibie_dialog_rl);
        rLayout.setOnClickListener(this);
        this.show();
        lbView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_in));
    }

    @Override
    public void onClick(View v) {
    }

    String flagShow;//判断当前该列表是否已显示
    Handler handler = new Handler();

    private class clickItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (now_flag == JZAddActivity.zhichu_flag) {
                String leibieString = (String) view.getTag();
                lbsubList.setAdapter(new JZLeibieAdapter(context, flagsubleibie, leibieString));
                if (lbsubList.isShown() && flagShow.equals(leibieString)) {
                    //主list恢复动画
                    lbList.setAnimation(AnimationUtils.loadAnimation(context, R.anim.picpush_right_in));
                    //子list隐藏
                    lbsubList.setVisibility(View.GONE);
                } else {
                    //主list收缩动画
                    lbList.setAnimation(AnimationUtils.loadAnimation(context, R.anim.picpush_left_out));
                    //子list进入动画
                    lbsubList.setAnimation(AnimationUtils.loadAnimation(context, R.anim.push_left_in));
                    lbsubList.setVisibility(View.VISIBLE);
                    lbsubList.setLayoutAnimation(RunAnimation.listAnimation());
                }
                flagShow = leibieString;
                lbsubList.setOnItemClickListener(new clickSubItem());
            } else if (now_flag == JZAddActivity.shouru_flag) {
                textGetandSend(view);
            } else if (now_flag == JZAddActivity.jiedai_flag) {
                textGetandSend(view);
            }
        }
    }

    /*
     * 子条目点击事件
     * */
    private class clickSubItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            textGetandSend(view);
        }
    }

    public void textGetandSend(View view) {
        String string = (String) view.getTag();
        //dialog退出动画
        DelayAnimation.animationDialogEnd(this, lbView, context, handler, R.anim.push_up_out, 300);
        Message msg = Message.obtain();
        msg.what = JZAddActivity.leibie_msg;
        msg.obj = string;
        JZAddActivity.mh.sendMessage(msg);
    }

    public boolean onKeyDown(int kCode, KeyEvent kEvent) {
        switch (kCode) {
            case KeyEvent.KEYCODE_BACK: {
                if (lbView.isShown()) {
                    DelayAnimation.animationDialogEnd(this, lbView, context, handler, R.anim.push_up_out, 300);
                    return false;
                } else {
                    this.cancel();
                }
            }
        }
        return super.onKeyDown(kCode, kEvent);
    }
}
