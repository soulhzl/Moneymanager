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
        // ��ȡWindowManager
        wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
        // ����LayoutParams(ȫ�ֱ�������ز���
        wmParams = ((MyApplication) getApplication()).getMywmParams();
        wmParams.type = 2002;//��������Ӧ�ó���֮�ϣ�״̬��֮��
        wmParams.flags |= 8;//����ȡ����
        wmParams.gravity = Gravity.LEFT | Gravity.TOP; // �����������������Ͻ�
        // ����Ļ���Ͻ�Ϊԭ�㣬����x��y��ʼֵ
        wmParams.x = 0;
        wmParams.y = 0;
        // �����������ڳ�������
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.format = 1;//λͼ��ʽ


        wm.addView(view, wmParams);

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // ��ȡ�����Ļ�����꣬������Ļ���Ͻ�Ϊԭ��
                x = event.getRawX();
                y = event.getRawY() - 25; // 25��ϵͳ״̬���ĸ߶�
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        state = MotionEvent.ACTION_DOWN;
                        StartX = x;
                        StartY = y;
                        // ��ȡ���View�����꣬���Դ�View���Ͻ�Ϊԭ��
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

    //��ʾ���Ͻǹر�IMG
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

    //����Text��Ϣ
    public void dataRefresh() {
        tx.setText("" + memInfo.getBenYueZC(this));
        tx1.setText("" + memInfo.getYuSuanYE(this));
    }

    private void updateViewPosition() {
        // ���¸�������λ�ò���
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

