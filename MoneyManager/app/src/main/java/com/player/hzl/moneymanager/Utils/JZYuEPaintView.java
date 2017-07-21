package com.player.hzl.moneymanager.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by zf2016 on 2017/2/14.
 */

//画图界面
public  class JZYuEPaintView extends View {
    private Paint mPaints;
    private Paint mFramePaint;
    private boolean mUseCenters;
    private RectF mBigOval;
    private float mStart;
    private float mSweep;
    private int bujin;
    //预算  余额   比率
    private float bi;

    public JZYuEPaintView(Context context, float yusuan, float yue, int color, int bujin) {
        super(context);
        this.bi = yue/(yusuan/360);	//控制绘图区域比率
        this.bujin = bujin;
        mPaints = new Paint();
        mPaints = new Paint(mPaints);
        mPaints.setColor(color);
        mUseCenters = true;
        mBigOval = new RectF(15, 0, 175, 160);//绘图区域 距左， 距上 ，左起点至右距离 ，上起点距下距离
        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);//抗锯齿
        mFramePaint.setStyle(Paint.Style.STROKE);//空心矩形
        mFramePaint.setStrokeWidth(0);//设置画出的线的 粗细程度
    }

    private void drawArcs(Canvas canvas, RectF oval, boolean useCenter, Paint paint) {
        canvas.drawRect(oval, mFramePaint);//画一个正方形
        canvas.drawArc(oval, mStart, mSweep, useCenter, paint);//画弧，起始角度，扫过的角度，画弧时包括圆心，画笔
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArcs(canvas, mBigOval, mUseCenters, mPaints);
        mStart = 0;
        mSweep +=bujin ;
        //如果扫过的角度大于设置的比例，将比例赋值给扫过的弧度
        if (mSweep > bi) {
            mSweep = bi;
        }
        invalidate();
    }
}
