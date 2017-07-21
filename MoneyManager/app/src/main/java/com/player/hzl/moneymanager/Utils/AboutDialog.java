package com.player.hzl.moneymanager.Utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.player.hzl.moneymanager.R;

/**
 * Created by zf2016 on 2017/2/27.
 */

public class AboutDialog extends Dialog {
    private View diaView ;
    private Context context;
    private Handler handler ;
    public AboutDialog(Context context, String text) {
        super(context, R.style.maindialog);
        this.context = context;
        handler = new Handler();
        diaView = View.inflate(context, R.layout.dialog_about, null);
        this.setContentView(diaView);
        TextView textView= (TextView)diaView.findViewById(R.id.dialog_about_text);
        textView.setText(text);
        //Ìí¼Ó±¸×¢±à¼­¿ò
        this.show();
        diaView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.push_up_in));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                DelayAnimation.animationDialogEnd(this, diaView, context, handler, R.anim.push_up_out,300);
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
