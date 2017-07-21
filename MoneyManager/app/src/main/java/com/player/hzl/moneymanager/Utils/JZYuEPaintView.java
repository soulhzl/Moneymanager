package com.player.hzl.moneymanager.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

/**
 * Created by zf2016 on 2017/2/14.
 */

//��ͼ����
public  class JZYuEPaintView extends View {
    private Paint mPaints;
    private Paint mFramePaint;
    private boolean mUseCenters;
    private RectF mBigOval;
    private float mStart;
    private float mSweep;
    private int bujin;
    //Ԥ��  ���   ����
    private float bi;

    public JZYuEPaintView(Context context, float yusuan, float yue, int color, int bujin) {
        super(context);
        this.bi = yue/(yusuan/360);	//���ƻ�ͼ�������
        this.bujin = bujin;
        mPaints = new Paint();
        mPaints = new Paint(mPaints);
        mPaints.setColor(color);
        mUseCenters = true;
        mBigOval = new RectF(15, 0, 175, 160);//��ͼ���� ���� ���� ����������Ҿ��� ���������¾���
        mFramePaint = new Paint();
        mFramePaint.setAntiAlias(true);//�����
        mFramePaint.setStyle(Paint.Style.STROKE);//���ľ���
        mFramePaint.setStrokeWidth(0);//���û������ߵ� ��ϸ�̶�
    }

    private void drawArcs(Canvas canvas, RectF oval, boolean useCenter, Paint paint) {
        canvas.drawRect(oval, mFramePaint);//��һ��������
        canvas.drawArc(oval, mStart, mSweep, useCenter, paint);//��������ʼ�Ƕȣ�ɨ���ĽǶȣ�����ʱ����Բ�ģ�����
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawArcs(canvas, mBigOval, mUseCenters, mPaints);
        mStart = 0;
        mSweep +=bujin ;
        //���ɨ���ĽǶȴ������õı�������������ֵ��ɨ���Ļ���
        if (mSweep > bi) {
            mSweep = bi;
        }
        invalidate();
    }
}
