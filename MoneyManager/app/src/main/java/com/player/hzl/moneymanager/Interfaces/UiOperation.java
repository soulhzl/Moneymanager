package com.player.hzl.moneymanager.Interfaces;

/**
 * Created by acer on 2017/1/27.
 */
public interface UiOperation {
    /** ����һ��������ʾ����Ĳ���id */
    int getLayoutResID();

    /** ��ʼ��View��findViewById�Ĵ���д������������� */
    void initView();

    /** ��ʼ�������� */
    void initListener();

    /** ��ʼ�����ݣ�����ʾ�������� */
    void initData();

    /**
     * �����¼�����������д���
     * @param v �����Ŀؼ�
     * @param id �����ؼ���id
     */
}
