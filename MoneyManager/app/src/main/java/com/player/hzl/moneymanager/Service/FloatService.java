package com.player.hzl.moneymanager.Service;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.player.hzl.moneymanager.Application.MyApplication;
import com.player.hzl.moneymanager.Application.memInfo;
import com.player.hzl.moneymanager.R;

/**
 * Created by acer on 2017/5/3.
 */
public class FloatService extends Service {

    WindowManager wm = null;
    WindowManager.LayoutParams wmParams = null;
    View view;
    private float mTouchStartX;
    private float mTouchStartY;
    private float x;
    private float y;
    int state;
    TextView tx1;
    TextView tx;
    ImageView iv;
    private float StartX;
    private float StartY;
    int delaytime=1000;
    @Override
    public void onCreate() {
        super.onCreate();
        view = LayoutInflater.from(this).inflate(R.layout.floating, null);
        tx = (TextView) view.findViewById(R.id.memunused);
        tx1 = (TextView) view.findViewById(R.id.memtotal);
        tx.setText("" + memInfo.getBenYueZC(this));
        tx1.setText("" + memInfo.getYuSuanYE(this));
        iv = (ImageView) view.findViewById(R.id.img2);
        iv.setVisibility(View.GONE);
        createView();
        handler.postDelayed(task, delaytime);


    }

    private void createView() {

        SharedPreferences shared = getSharedPreferences("float_flag",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.putInt("float", 1);
        editor.commit();
        // 获取WindowManager
        wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        // 设置LayoutParams(全局变量）相关参数
        wmParams = ((MyApplication) getApplication()).getMywmParams();
        wmParams.type = 2002;//置于所有应用程序之上，状态栏之下
        wmParams.flags |= 8;//不获取焦点
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // 调整悬浮窗口至左上角
        // 以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        // 设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;//位图格式


        wm.addView(view, wmParams);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // 获取相对屏幕的坐标，即以屏幕左上角为原点
                x = event.getRawX();
                y = event.getRawY() - 25; // 25是系统状态栏的高度
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        state = MotionEvent.ACTION_DOWN;
                        StartX = x;
                        StartY = y;
                        // 获取相对View的坐标，即以此View左上角为原点
                        mTouchStartX = event.getX();
                        mTouchStartY = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        state = MotionEvent.ACTION_MOVE;
                        updateViewPosition();
                        break;

                    case MotionEvent.ACTION_UP:
                        state = MotionEvent.ACTION_UP;
                        updateViewPosition();
                        showImg();
                        mTouchStartX = mTouchStartY = 0;
                        break;
                }
                return true;
            }
        });

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serviceStop = new Intent();
                serviceStop.setClass(FloatService.this, FloatService.class);
                stopService(serviceStop);
            }
        });

    }

    //显示右上角关闭IMG
    public void showImg() {
        if (Math.abs(x - StartX) < 1.5 && Math.abs(y - StartY) < 1.5
                && !iv.isShown()) {
            iv.setVisibility(View.VISIBLE);
        } else if (iv.isShown()) {
            iv.setVisibility(View.GONE);
        }
    }

    private Handler handler = new Handler();
    private Runnable task = new Runnable() {
        public void run() {
            dataRefresh();
            handler.postDelayed(this, delaytime);
            wm.updateViewLayout(view, wmParams);
        }
    };

    //设置Text信息
    public void dataRefresh() {
        tx.setText("" + memInfo.getBenYueZC(this));
        tx1.setText("" + memInfo.getYuSuanYE(this));
    }

    private void updateViewPosition() {
        // 更新浮动窗口位置参数
        wmParams.x = (int) (x - mTouchStartX);
        wmParams.y = (int) (y - mTouchStartY);
        wm.updateViewLayout(view, wmParams);
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(task);
        wm.removeView(view);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}

