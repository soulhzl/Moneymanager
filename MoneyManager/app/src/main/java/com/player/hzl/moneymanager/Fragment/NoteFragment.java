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
    // 主界面list
    private ListView listView;
    // 添加新备忘按钮
    private ImageButton ib_new, ib_dele;
    // 主界面list适配器
    private JSAdapter adapter;
    // 主界面底部多选删除界面
    private LinearLayout del_ll;
    // 多选删除三个按钮
    private Button selectAll, delSelect, cancelSelect, exitSelect;
    //
    private Handler handler;
    // 创建数据库对象
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
        // 获取点击的Item的Tag
        int content_id = (Integer) view.getTag();
        Intent intent = new Intent(getContext(), JSNewActivity.class);
        intent.putExtra("id", content_id);// 将id传送到新建的activity
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.js_main_addbt:
                Intent intent = new Intent(getContext(), JSNewActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);//当接收Intent的Activity处于非destroy状态，那么接收Intent的Activity就会被置于栈顶
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
                        showMsg("没有可选条目");
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

    //删除选中的条目
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
            showMsg("你还没有选择要删除项");
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
	 * 退出弹出框
	 */
    String fileName;// 以当前时间命名的文件名



    /*
	 * 获取文件名
	 */
    public String getFileName(String houzhui) {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(date) + houzhui;
    }


    /*
     * 点击图片以后的动画效果
     */
    public void shakeAnimation(View v) {
        v.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.js_shake));
    }

    public void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
