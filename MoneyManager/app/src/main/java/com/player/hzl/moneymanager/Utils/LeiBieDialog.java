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
 * ���ѡ��dialog
 * */
public class LeiBieDialog extends Dialog implements View.OnClickListener {
    //����б����������б�
    private ListView lbList, lbsubList;
    //��������
    private RelativeLayout rLayout;
    private Context context;
    private View lbView;
    //����б�������
    private JZLeibieAdapter adapter;
    //������������ʶ
    public static final int flagsubleibie = 3020;
    //��ǰѡ���ѡ����룬֧�������
    private int now_flag = 0;
    public LeiBieDialog(Context context, int now_flag) {
        super(context, R.style.leibiedialog);
        this.context = context;
        this.now_flag = now_flag;
        lbView = View.inflate(context, R.layout.dialog_leibie, null);
        this.setContentView(lbView);
        //���������
        adapter = new JZLeibieAdapter(context, now_flag, null);
        //����б�
        lbList = (ListView) lbView.findViewById(R.id.leibie_dialog_list);
        lbList.setAdapter(adapter);
        lbList.setOnItemClickListener(new clickItem());
        //��������б�
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

    String flagShow;//�жϵ�ǰ���б��Ƿ�����ʾ
    Handler handler = new Handler();

    private class clickItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (now_flag == JZAddActivity.zhichu_flag) {
                String leibieString = (String) view.getTag();
                lbsubList.setAdapter(new JZLeibieAdapter(context, flagsubleibie, leibieString));
                if (lbsubList.isShown() && flagShow.equals(leibieString)) {
                    //��list�ָ�����
                    lbList.setAnimation(AnimationUtils.loadAnimation(context, R.anim.picpush_right_in));
                    //��list����
                    lbsubList.setVisibility(View.GONE);
                } else {
                    //��list��������
                    lbList.setAnimation(AnimationUtils.loadAnimation(context, R.anim.picpush_left_out));
                    //��list���붯��
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
     * ����Ŀ����¼�
     * */
    private class clickSubItem implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            textGetandSend(view);
        }
    }

    public void textGetandSend(View view) {
        String string = (String) view.getTag();
        //dialog�˳�����
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
