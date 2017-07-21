package com.player.hzl.moneymanager.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;

/**
 * Created by zf2016 on 2017/2/14.
 */

public class DelayAnimation {
    //��������  ��ͼ�������ģ�handler,��ʼ�������ӳ�ʱ��
    public static void animationEnd(final View v, final Context context, final Handler handler, final int eanimId, final int time) {
        new Thread() {
            public void run() {
                try {
                    handler.post(new Runnable(){
                        public void run(){
                            v.startAnimation(AnimationUtils.loadAnimation(context, eanimId));
                        }
                    });
                    sleep(time);
                    handler.post(new Runnable(){
                        public void run(){
                            v.setVisibility(View.GONE);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //��������  �Ի���, ��ͼ�������ģ�handler,��ʼ�������ӳ�ʱ��
    public static void animationDialogEnd(final Dialog d, final View v, final Context context, final Handler handler, final int eanimId, final int time) {
        new Thread() {
            public void run() {
                try {
                    handler.post(new Runnable(){
                        public void run(){
                            v.startAnimation(AnimationUtils.loadAnimation(context, eanimId));
                        }
                    });
                    sleep(time);
                    handler.post(new Runnable(){
                        public void run(){
                            d.cancel();
                            d.dismiss();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //��ʼ����  ��ͼ�������ģ�handler,��ʼ�������ӳ�ʱ��
    public static void animationStart(final View v, final Context context, final Handler handler, final int eanimId, final int time) {
        new Thread() {
            public void run() {
                try {
                    sleep(time);
                    handler.post(new Runnable(){
                        public void run(){
                            v.setVisibility(View.VISIBLE);
                            v.startAnimation(AnimationUtils.loadAnimation(context, eanimId));
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
