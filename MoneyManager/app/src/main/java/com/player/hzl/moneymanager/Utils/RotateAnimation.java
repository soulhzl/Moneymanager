package com.player.hzl.moneymanager.Utils;

import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by zf2016 on 2017/2/14.
 */

public class RotateAnimation extends Animation {
    private final float rFromDegrees;
    private final float rToDegrees;
    private final float rCenterX;
    private final float rCenterY;
    private final float rDepthZ;
    private final boolean rReverse;

    public RotateAnimation(float fromDegrees, float toDegrees,
                           float centerX, float centerY, float depthZ, boolean reverse) {
        rFromDegrees = fromDegrees;
        rToDegrees = toDegrees;
        rCenterX = centerX;
        rCenterY = centerY;
        rDepthZ = depthZ;
        rReverse = reverse;
    }

    //初始化参数
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

    }

    //实现效果
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final float fromDegrees = rFromDegrees;
        float degrees = fromDegrees + ((rToDegrees - fromDegrees) * interpolatedTime);

        final float centerX = rCenterX;
        final float centerY = rCenterY;


        final Matrix matrix = t.getMatrix();

        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
