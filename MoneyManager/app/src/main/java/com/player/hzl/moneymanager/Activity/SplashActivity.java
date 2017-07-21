package com.player.hzl.moneymanager.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.player.hzl.moneymanager.R;

/**
 * Created by acer on 2017/1/27.
 */
public class SplashActivity extends BaseActivity {

    private ImageView iv_anim1;
    private ImageView iv_anim2;
    private ImageView iv_anim3;
    private ImageView iv_anim4;
    private ImageView iv_anim5;
    private ImageView iv_anim6;
    private Handler handler;
    private SharedPreferences sp=null;

    @Override
    public int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        iv_anim1 = findView(R.id.anim1);
        iv_anim2 = findView(R.id.anim2);
        iv_anim3 = findView(R.id.anim3);
        iv_anim4 = findView(R.id.anim4);
        iv_anim5 = findView(R.id.anim5);
        iv_anim6 = findView(R.id.anim6);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        delayEnterHome();
    }

    private void delayEnterHome(){
        SharedPreferences.Editor editor = getSharedPreferences("first_pref",
                MODE_PRIVATE).edit();
        editor.remove("isCheck");
        editor.commit();
        handler = new Handler();
        animForApp();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHome();
            }
        }, 6000);
    }

    private void animForApp(){
        Animation a1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_down1);
        Animation a2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_down2);
        iv_anim1.startAnimation(a1);
        iv_anim2.startAnimation(a2);
        iv_anim3.startAnimation(a1);
        iv_anim4.startAnimation(a2);
        iv_anim5.startAnimation(a1);
        iv_anim6.startAnimation(a2);

    }

    protected void enterHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        SplashActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handler.removeCallbacksAndMessages(null);
                enterHome();
                break;

            default:
                break;
        }
        return super.onTouchEvent(event);
    }
}