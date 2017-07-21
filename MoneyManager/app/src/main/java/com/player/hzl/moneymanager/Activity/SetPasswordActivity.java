package com.player.hzl.moneymanager.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.player.hzl.moneymanager.R;

/**
 * Created by acer on 2017/1/28.
 */
public class SetPasswordActivity extends Activity {
    private boolean isSet = false;
    private TextView textpass = null;
    private TextView noPass;
    private EditText setPass=null;
    private EditText setZh=null;
    private EditText confirmPass=null;
    private SharedPreferences preferences;
    private SharedPreferences sharedPreferences = null;
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setpassword);

        init();
    }

    private void init() {
        sharedPreferences = getSharedPreferences("image", MODE_PRIVATE);
        textpass=(TextView)findViewById(R.id.digitalpass);
        textpass.setOnClickListener(new DigitalPassListener());
        noPass=(TextView)findViewById(R.id.nopass);
        noPass.setOnClickListener(new NoPassListener());

    }

    //字符密码设置监听器
    class DigitalPassListener implements View.OnClickListener {
        public void onClick(View v) {
            // TODO Auto-generated method stub
            LayoutInflater factory=LayoutInflater.from(SetPasswordActivity.this);
            final View textEntry=factory.inflate(R.layout.digital_pass, null);
            AlertDialog.Builder builder=new AlertDialog.Builder(SetPasswordActivity.this)
                    .setTitle(getString(R.string.set_ussafe))
                    .setView(textEntry)
                    .setPositiveButton(getString(R.string.set), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            setZh=(EditText)textEntry.findViewById(R.id.set_zh);
                            setPass=(EditText)textEntry.findViewById(R.id.set_pass);
                            confirmPass=(EditText)textEntry.findViewById(R.id.confirm_pass);
                            if (!setZh.getText().toString().trim().equals("")&&
                                    !confirmPass.getText().toString().trim().equals("")&&
                                    confirmPass.getText().toString().trim().equals(setPass.getText().toString().trim()
                                    )) {
                                preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=preferences.edit();
                                editor.putBoolean("isSet", !isSet);
                                editor.putString("zh", setZh.getText().toString().trim());
                                editor.putString("password", setPass.getText().toString().trim());
                                editor.commit();
                                dialog.dismiss();
                                Toast.makeText(SetPasswordActivity.this, R.string.pass_set_success, Toast.LENGTH_LONG).show();
                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        enterHome();
                                    }
                                }, 0);
                            }
                            else {
                                Toast.makeText(SetPasswordActivity.this, R.string.dif_pass, Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .setNegativeButton(getString(R.string.cancal), new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();

        }
    }


    //无密码设置监听器
    class NoPassListener implements View.OnClickListener {

        public void onClick(View arg0) {
            preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferences.edit();
            editor.putBoolean("isSet", false);
            editor.commit();
            Toast.makeText(SetPasswordActivity.this, R.string.pass_cancal, Toast.LENGTH_LONG).show();
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    enterHome();
                }
            }, 0);
        }
    }

    protected void enterHome(){
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
        SetPasswordActivity.this.finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(SetPasswordActivity.this).setTitle("提示")
                .setMessage("你确定要返回到设置界面吗？").setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                enterHome();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
            }
        }).show();
    }

}
