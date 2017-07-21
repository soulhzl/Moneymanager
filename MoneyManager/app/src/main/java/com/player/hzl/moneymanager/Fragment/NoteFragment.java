package com.player.hzl.moneymanager.Fragment;

import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.player.hzl.moneymanager.Activity.JSNewActivity;
import com.player.hzl.moneymanager.Adapter.JSAdapter;
import com.player.hzl.moneymanager.DataBase.JiShiDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Utils.RunAnimation;
import com.player.hzl.moneymanager.Utils.DelayAnimation;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by acer on 2017/1/31.
 */
public class NoteFragment extends BaseFragmet implements View.OnClickListener,AdapterView.OnItemClickListener{
    // ������list
    private ListView listView;
    // ����±�����ť
    private ImageButton ib_new, ib_dele;
    // ������list������
    private JSAdapter adapter;
    // ������ײ���ѡɾ������
    private LinearLayout del_ll;
    // ��ѡɾ��������ť
    private Button selectAll, delSelect, cancelSelect, exitSelect;
    //
    private Handler handler;
    // �������ݿ����
    JiShiDB dataHelper;
    @Override
    public int getLayoutResID() {
        return R.layout.fragment_note;
    }

    @Override
    public void initView() {
        del_ll = findView(R.id.js_main_select_ll);
        listView = findView(R.id.js_list);
        ib_new = findView(R.id.js_main_addbt);
        ib_dele = findView(R.id.js_main_del);

        selectAll = findView(R.id.js_main_select_quanxuan);
        delSelect = findView(R.id.js_main_select_del);
        cancelSelect = findView(R.id.js_main_select_quxiao);
        exitSelect = findView(R.id.js_main_select_exit);
    }

    @Override
    public void initListener() {
        ib_new.setOnClickListener(this);
        selectAll.setOnClickListener(this);
        delSelect.setOnClickListener(this);
        cancelSelect.setOnClickListener(this);
        exitSelect.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        ib_dele.setOnClickListener(this);
    }

    @Override
    public void initData() {
        del_ll.setVisibility(View.GONE);
        adapter = new JSAdapter(getContext());
        listView.setAdapter(adapter);
        handler = new Handler();
        dataHelper = new JiShiDB(getContext());
    }

    @Override
    public void onResume() {
        adapter.getList();
        adapter.notifyDataSetChanged();
        listView.setLayoutAnimation(RunAnimation.listAnimation());
        super.onResume();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // ��ȡ�����Item��Tag
        int content_id = (Integer) view.getTag();
        Intent intent = new Intent(getContext(), JSNewActivity.class);
        intent.putExtra("id", content_id);// ��id���͵��½���activity
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.js_main_addbt:
                Intent intent = new Intent(getContext(), JSNewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//������Intent��Activity���ڷ�destroy״̬����ô����Intent��Activity�ͻᱻ����ջ��
                NoteFragment.this.startActivity(intent);
                break;
            case R.id.js_main_del:
                if (!del_ll.isShown()) {
                    if (JSAdapter.jSlist != null && JSAdapter.jSlist.size() > 0) {
                        del_ll.setVisibility(View.VISIBLE);
                        del_ll.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.jz_menu_up));
                        adapter.isShowCheck = true;
                        adapter.notifyDataSetChanged();
                    } else {
                        showMsg("û�п�ѡ��Ŀ");
                    }
                } else {
                    DelayAnimation.animationEnd(del_ll, getContext(), handler, R.anim.jz_menu_down, 300);
                    adapter.isShowCheck = false;
                    adapter.notifyDataSetChanged();
                }
                break;
            case R.id.js_main_select_quanxuan:
                adapter.isSelectAll = true;
                adapter.notifyDataSetChanged();
                break;
            case R.id.js_main_select_del:
                delSelect();
                break;
            case R.id.js_main_select_quxiao:
                adapter.isSelectAll = false;
                adapter.notifyDataSetChanged();
                break;
            case R.id.js_main_select_exit:
                if (del_ll.isShown()) {
                    del_ll.setVisibility(View.GONE);
                    DelayAnimation.animationEnd(del_ll, getContext(), handler, R.anim.jz_menu_down, 300);
                    adapter.isShowCheck = false;
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    //ɾ��ѡ�е���Ŀ
    public void delSelect() {
        if (adapter.idList != null && adapter.idList.size() > 0) {
            for (int id : adapter.idList) {
                dataHelper.DelJSInfo(id);
            }
            adapter.getList();
            adapter.isShowCheck = false;
            adapter.isSelectAll = false;
            adapter.notifyDataSetChanged();
            DelayAnimation.animationEnd(del_ll, getContext(), handler, R.anim.jz_menu_down, 300);
        } else {
            showMsg("�㻹û��ѡ��Ҫɾ����");
        }
    }

    public void changeActivity(final Class<?> c) {
        new Thread() {
            public void run() {
                try {
                    sleep(300);
                    Intent intent = new Intent(getContext(), c);
                    intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    getContext().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /*
	 * �˳�������
	 */
    String fileName;// �Ե�ǰʱ���������ļ���



    /*
	 * ��ȡ�ļ���
	 */
    public String getFileName(String houzhui) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date) + houzhui;
    }


    /*
     * ���ͼƬ�Ժ�Ķ���Ч��
     */
    public void shakeAnimation(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.js_shake));
    }

    public void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
