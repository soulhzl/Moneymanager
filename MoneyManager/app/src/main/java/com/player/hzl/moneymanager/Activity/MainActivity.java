package com.player.hzl.moneymanager.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;
import com.player.hzl.moneymanager.Adapter.MainAdapter;
import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.Fragment.MoneyFragment;
import com.player.hzl.moneymanager.Fragment.NoteFragment;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Service.FloatService;
import com.player.hzl.moneymanager.Utils.Utils;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private SharedPreferences textpassword = null;
    private String password = null;
    private String zh = null;
    private boolean isSet = false;
    private EditText checkpass = null;
    private EditText checkzh = null;
    public static final int MENU_ITEM_SETTING = Menu.FIRST;
    public static final int MENU_ITEM_OPEN = Menu.FIRST + 1;
    public static final int MENU_ITEM_QUIT = Menu.FIRST + 2;
    // 检查是否第一次进入应用
    boolean isFirstIn = false;
    int isCheck;
    private TextView tv_money;
    private TextView tv_note;
    private ViewPager view_pager;
    private View view_indicator;
    private int indicatorWidth;
    //记账数据库管理
    private JiZhangDB jzdh;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        try {
            //屏蔽真机的Menu来显示右边overflow button按钮
            ViewConfiguration mconfig = ViewConfiguration.get(this);
            Field menuKeyField;
            menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(mconfig, false);
            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        tv_money = findView(R.id.tv_money);
        tv_note = findView(R.id.tv_note);
        view_indicator = findView(R.id.view_indicator);
        view_pager = findView(R.id.view_pager);
        initIndicator();
    }

    /**
     * 初始化指示器
     */
    private void initIndicator() {
        int screenWidth = Utils.getScreenWidth(this);
        indicatorWidth = screenWidth / 2;
        view_indicator.getLayoutParams().width = indicatorWidth;
        view_indicator.requestLayout();    // 通知这个View去更新它的布局参数
    }

    @Override
    public void initListener() {
        tv_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_pager.setCurrentItem(0);
            }
        });

        tv_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view_pager.setCurrentItem(1);
            }
        });

        view_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                scrollIndicator(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                changeTitleTextState(position == 0);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    protected void scrollIndicator(int position, float positionOffset) {
        float translationX = indicatorWidth * position + indicatorWidth * positionOffset;
        ViewHelper.setTranslationX(view_indicator, translationX);
    }

    @Override
    public void initData() {
        checkisFirsttime();
        changeTitleTextState(true);
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(new MoneyFragment());
        fragments.add(new NoteFragment());
        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragments);
        view_pager.setAdapter(adapter);
    }

    private void changeTitleTextState(boolean isSelectMoney) {

        tv_money.setSelected(isSelectMoney);
        tv_note.setSelected(!isSelectMoney);

        scaleTitle(isSelectMoney ? 1.2f : 1.0f, tv_money);
        scaleTitle(!isSelectMoney ? 1.2f : 1.0f, tv_note);
    }

    private void scaleTitle(float scale, TextView textView) {
        ViewPropertyAnimator.animate(textView).scaleX(scale).scaleY(scale);
    }

    private void checkisFirsttime() {
        SharedPreferences preferences = getSharedPreferences("first_pref",
                MODE_PRIVATE);
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                if (isFirstIn) {
                    // 启动半透明教程界面
                    intent = new Intent(MainActivity.this, GuideActivity.class);
                    MainActivity.this.startActivity(intent);
                } else {
                    // 这里检查密码
                    SharedPreferences preferences = getSharedPreferences("first_pref",
                            MODE_PRIVATE);
                    isCheck = preferences.getInt("isCheck", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    if (isCheck == 0) {
                        checkPass();
                        isCheck++;
                        editor.putInt("isCheck", isCheck);
                    } else {
                        isCheck++;
                        editor.putInt("isCheck", isCheck);
                    }
                    editor.commit();
                }

            }
        }, 0);
    }

    // 检查密码
    private void checkPass() {
        textpassword = getSharedPreferences("pass", Context.MODE_PRIVATE);
        zh = textpassword.getString("zh", null);
        password = textpassword.getString("password", null);
        isSet = textpassword.getBoolean("isSet", false);
        if (isSet) {
            LayoutInflater factory = LayoutInflater.from(MainActivity.this);
            final View textEntry = factory.inflate(R.layout.confirm_pass, null);
            AlertDialog.Builder bulider = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.pass_title))
                    .setIcon(
                            getResources().getDrawable(
                                    android.R.drawable.ic_lock_lock))
                    .setView(textEntry)
                    .setCancelable(false)
                    .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            System.exit(0);
                        }
                    })
                    .setPositiveButton(getString(R.string.ok),
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    checkzh = (EditText) textEntry
                                            .findViewById(R.id.check_zh);
                                    checkpass = (EditText) textEntry
                                            .findViewById(R.id.check_pass);
                                    if (checkpass.getText().toString().trim()
                                            .equals(password) && checkzh.getText().toString().trim()
                                            .equals(zh)) {
                                        // 关闭对话框
                                        try {
                                            Field field = dialog
                                                    .getClass()
                                                    .getSuperclass()
                                                    .getDeclaredField(
                                                            "mShowing");
                                            field.setAccessible(true);
                                            field.set(dialog, true);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dismiss();
                                    } else {
                                        // 对话框不消失，toast提示密码错误
                                        try {
                                            Field field = dialog
                                                    .getClass()
                                                    .getSuperclass()
                                                    .getDeclaredField(
                                                            "mShowing");
                                            field.setAccessible(true);
                                            field.set(dialog, false);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        Toast.makeText(MainActivity.this,
                                                R.string.password_wrong,
                                                Toast.LENGTH_LONG).show();
                                        // 这里不要忘记把密码重新设置为空
                                        checkzh.setText("");
                                        checkpass.setText("");
                                    }
                                }
                            });
            bulider.create().show();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                .setMessage("你确定要退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                System.exit(0);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        }).show();
    }

    // 添加选择菜单
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, MENU_ITEM_SETTING, 0, R.string.set);
        menu.add(Menu.NONE, MENU_ITEM_OPEN, 0, R.string.open);
        menu.add(Menu.NONE, MENU_ITEM_QUIT, 0, "退出");
        return true;
    }

    // 添加选择菜单的选择事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ITEM_SETTING:
                Intent intent1 = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent1);
                MainActivity.this.finish();
                break;
            case MENU_ITEM_OPEN:
                Intent service = new Intent();
                service.setClass(MainActivity.this, FloatService.class);
                startService(service);
                break;
            case MENU_ITEM_QUIT:
                new AlertDialog.Builder(MainActivity.this).setTitle("提示")
                        .setMessage("你确定要退出吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                        System.exit(0);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
                }).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
