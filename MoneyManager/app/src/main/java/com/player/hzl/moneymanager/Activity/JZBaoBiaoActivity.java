package com.player.hzl.moneymanager.Activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.player.hzl.moneymanager.DataBase.JiZhangDB;
import com.player.hzl.moneymanager.R;
import com.player.hzl.moneymanager.Utils.GetTime;
import com.player.hzl.moneymanager.bean.JZItem;
import com.player.hzl.moneymanager.bean.JZShouRu;
import com.player.hzl.moneymanager.bean.JZZhiChu;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zf2016 on 2017/2/14.
 */

public class JZBaoBiaoActivity extends Activity {
    private JiZhangDB dataHelper;
    public static final int ZCSR=1111;
    public static final int zcMingXi=2222;
    public static final int srMingXi=3333;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataHelper = new JiZhangDB(this);
        setContentView(setView(ZCSR));
    }

    /*
     * 设置走线图
     * */
    public View setView(int flag){
        String[] titles=null;
        int[] colors =null;
        PointStyle[] styles= null;
        List<double[]> values = null;
        String title = "";
        switch (flag) {
            case ZCSR:
                titles = new String[] { "月收入", "月支出"};
                colors = new int[] { Color.WHITE, Color.GREEN};
                styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.DIAMOND};
                values = new ArrayList<double[]>();
                values.add(getShouRuYear(""));
                values.add(getZhiChuYear(""));
                title = "月收入和支出走势图";
                break;
            case zcMingXi:
                titles = new String[] {JZItem.canyin, JZItem.jiaotong,JZItem.gouwu,JZItem.yule,JZItem.yijiao,JZItem.jujia,JZItem.touzi,JZItem.renqing,JZItem.jiechu,JZItem.huankuan};
                colors = new int[] { Color.WHITE, Color.GREEN, Color.CYAN, Color.YELLOW , Color.DKGRAY , Color.MAGENTA, Color.RED , Color.BLUE, Color.LTGRAY, Color.GRAY };
                styles = new PointStyle[] { PointStyle.DIAMOND, PointStyle.DIAMOND, PointStyle.DIAMOND, PointStyle.DIAMOND , PointStyle.DIAMOND, PointStyle.DIAMOND, PointStyle.DIAMOND, PointStyle.DIAMOND, PointStyle.DIAMOND, PointStyle.DIAMOND};
                values = new ArrayList<double[]>();
                String selectCanYin = " and "+ JZZhiChu.ZC_ITEM+" = '"+JZItem.canyin+"'";
                values.add(getZhiChuYear(selectCanYin));
                String selectJiaoTong = " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.jiaotong+"'";
                values.add(getZhiChuYear(selectJiaoTong));
                String selectGouWu = " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.gouwu+"'";
                values.add(getZhiChuYear(selectGouWu));
                String selectYuLe = " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.yule+"'";
                values.add(getZhiChuYear(selectYuLe));
                String selectYiJiao = " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.yijiao+"'";
                values.add(getZhiChuYear(selectYiJiao));
                String selectJuJia = " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.jujia+"'";
                values.add(getZhiChuYear(selectJuJia));
                String selectTouZi = " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.touzi+"'";
                values.add(getZhiChuYear(selectTouZi));
                String selectRenQing= " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.renqing+"'";
                values.add(getZhiChuYear(selectRenQing));
                String selectJieChu= " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.jiechu+"'";
                values.add(getZhiChuYear(selectJieChu));
                String selectHuanKuan= " and "+ JZZhiChu.ZC_ITEM+" = '"+ JZItem.huankuan+"'";
                values.add(getZhiChuYear(selectHuanKuan));
                title = "支出走势图";
                break;
            case srMingXi:
                titles = new String[] {JZItem.gongzi,JZItem.gupiao,JZItem.jiangjin,JZItem.lixi,JZItem.fenhong,JZItem.butie,JZItem.baoxiao,JZItem.qita,JZItem.jieru,JZItem.shoukuan};
                colors = new int[] { Color.WHITE, Color.GREEN, Color.CYAN, Color.YELLOW , Color.DKGRAY , Color.MAGENTA, Color.RED , Color.BLUE, Color.LTGRAY, Color.GRAY };
                styles = new PointStyle[] { PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE , PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE, PointStyle.CIRCLE};
                values = new ArrayList<double[]>();
                String selectGongZi = " and "+ JZShouRu.SR_ITEM+" = '"+JZItem.gongzi+"'";
                values.add(getShouRuYear(selectGongZi));
                String selectGuPiao = " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.gupiao+"'";
                values.add(getShouRuYear(selectGuPiao));
                String selectJiangJin = " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.jiangjin+"'";
                values.add(getShouRuYear(selectJiangJin));
                String selectLiXi = " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.lixi+"'";
                values.add(getShouRuYear(selectLiXi));
                String selectFenHong = " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.fenhong+"'";
                values.add(getShouRuYear(selectFenHong));
                String selectBuTie = " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.butie+"'";
                values.add(getShouRuYear(selectBuTie));
                String selectBaoBiao = " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.baoxiao+"'";
                values.add(getShouRuYear(selectBaoBiao));
                String selectQiTa= " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.qita+"'";
                values.add(getShouRuYear(selectQiTa));
                String selectJieRu= " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.jieru+"'";
                values.add(getShouRuYear(selectJieRu));
                String selectShouKuan= " and "+ JZShouRu.SR_ITEM+" = '"+ JZItem.shoukuan+"'";
                values.add(getShouRuYear(selectShouKuan));
                title = "收入走势图";
                break;
        }
        List<double[]> x = new ArrayList<double[]>();
        for (int i = 0; i < titles.length; i++) {
            x.add(new double[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 });
        }

        XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) renderer.getSeriesRendererAt(i)).setFillPoints(true);
        }
        setChartSettings(renderer, title, "月份", "金额", 1, 12, 0, 10000, Color.LTGRAY, Color.LTGRAY);
        renderer.setXLabels(12);
        renderer.setYLabels(10);
        renderer.setShowGrid(true);
        renderer.setXLabelsAlign(Paint.Align.RIGHT);
        renderer.setYLabelsAlign(Paint.Align.RIGHT);
        renderer.setZoomButtonsVisible(true);
        renderer.setPanLimits(new double[] {0, 12, 0, 10000 });
        renderer.setZoomLimits(new double[] {0, 12, 0, 10000});
        View view = ChartFactory.getLineChartView(this, buildDataset(titles, x, values), renderer);
        view.setBackgroundColor(Color.BLACK);
        return view;
    }

    /*
     * 从数据库中获得当年每月的支出信息
     * */
    public double [] getZhiChuYear(String select){
        double d[]=new double[12];
        ArrayList<JZZhiChu> zhiChuList = new ArrayList<JZZhiChu>();
        for(int i=1;i<=12;i++){
            double countZhiChu=0;
            String selectionzhichu = JZZhiChu.ZC_YEAR+"="+GetTime.getYear()+" and "+ JZZhiChu.ZC_MONTH+"="+i+select;
            zhiChuList =  dataHelper.GetZhiChuList(selectionzhichu);
            for(JZZhiChu zhichu:zhiChuList){
                countZhiChu += zhichu.getZc_Count();
            }
            d[i-1]=countZhiChu;
        }
        return d;
    }
    /*
     * 从数据库中获得当年每月的收入信息
     * */
    public double [] getShouRuYear(String select){
        double d[]=new double[12];
        ArrayList<JZShouRu> shouRuList = new ArrayList<JZShouRu>();
        for(int i=1;i<=12;i++){
            double countShouRu=0;
            String selectionshouru = JZShouRu.SR_YEAR+"="+ GetTime.getYear()+" and "+ JZShouRu.SR_MONTH+"="+i+select;
            shouRuList =  dataHelper.GetShouRuList(selectionshouru);
            for(JZShouRu shouru:shouRuList){
                countShouRu += shouru.getSr_Count();
            }
            d[i-1]=countShouRu;
        }
        return d;
    }

    @Override
    protected void onResume() {
        overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
        super.onResume();
    }


    private XYMultipleSeriesRenderer buildRenderer(int[] colors, PointStyle[] styles) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();//构造显示用渲染图
        setRenderer(renderer, colors, styles);
        return renderer;
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors, PointStyle[] styles) {
        renderer.setAxisTitleTextSize(16);//设置坐标轴标题文本大小
        renderer.setChartTitleTextSize(20);//图表标题字体大小
        renderer.setLabelsTextSize(15);//坐标标签文字大小
        renderer.setLegendTextSize(15);//设置图例字号
        renderer.setPointSize(5f);///设置坐标点大小
        renderer.setMargins(new int[] { 20, 30, 15, 20 });
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();//对一条系列点的绘制进行设置
            r.setColor(colors[i]);//设置颜色
            r.setPointStyle(styles[i]);//设置点样式
            renderer.addSeriesRenderer(r);//将要绘制的点添加到坐标绘制中
        }
    }

    private void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle, String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor, int labelsColor) {
        renderer.setChartTitle(title);//图表标题
        renderer.setXTitle(xTitle); //X轴标题
        renderer.setYTitle(yTitle); //Y轴标题
        renderer.setXAxisMin(xMin);//设置X轴起点
        renderer.setXAxisMax(xMax);//设置X轴终点
        renderer.setYAxisMin(yMin);//设置Y轴起点
        renderer.setYAxisMax(yMax);//设置Y轴终点
        renderer.setAxesColor(axesColor);//设置X轴的颜色.
        renderer.setLabelsColor(labelsColor);//设置坐标颜色
    }

    private XYMultipleSeriesDataset buildDataset(String[] titles, List<double[]> xValues, List<double[]> yValues) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();//构造存放显示用的数据集
        addXYSeries(dataset, titles, xValues, yValues, 0);
        return dataset;
    }

    private void addXYSeries(XYMultipleSeriesDataset dataset, String[] titles, List<double[]> xValues, List<double[]> yValues, int scale) {
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            XYSeries series = new XYSeries(titles[i], scale);
            double[] xV = xValues.get(i);
            double[] yV = yValues.get(i);
            int seriesLength = xV.length;
            for (int k = 0; k < seriesLength; k++) {
                series.add(xV[k], yV[k]);
            }
            dataset.addSeries(series);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 100, 0, "月收入和支出");
        menu.add(0, 200, 0, "详细月收入");
        menu.add(0, 300, 0, "详细月支出");
        return true;
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case 100:
                setContentView(setView(ZCSR));
                break;
            case 200:
                setContentView(setView(srMingXi));
                break;
            case 300:
                setContentView(setView(zcMingXi));
                break;
        }
        return true;
    }

}
