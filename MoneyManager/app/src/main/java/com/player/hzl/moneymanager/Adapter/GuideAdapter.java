package com.player.hzl.moneymanager.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zf2016 on 2017/2/18.
 */

public class GuideAdapter extends PagerAdapter {

    // �����б�
    private List<View> views;
    private AppCompatActivity activity;


    public GuideAdapter(List<View> views, AppCompatActivity activity) {
        this.views = views;
        this.activity = activity;
    }
    //����viewpager��ÿ��item
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position),0);
        return views.get(position);
    }
    //ɾ��ViewPager��item
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    // ��õ�ǰ������
    @Override
    public int getCount() {
        if (views != null) {
            return views.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


}
