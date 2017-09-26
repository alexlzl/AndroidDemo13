package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhouliang on 2017/9/26.
 */

public class RegionView extends View {
    public RegionView(Context context) {
        super(context);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Describe:矩形集枚举区域——RegionIterator类
     * 对于特定的区域，我们都可以使用多个矩形来表示其大致形状。事实上，如果矩形足够小，一定数量的矩形就能够精确表示区域的形状，也就是说，一定数量的矩形所合成的形状，也可以代表区域的形状。RegionIterator类，实现了获取组成区域的矩形集的功能，其实RegionIterator类非常简单，总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
     * RegionIterator(Region region) //根据区域构建对应的矩形集
     * boolean	next(Rect r) //获取下一个矩形，结果保存在参数Rect r 中
     * <p>
     * 由于在Canvas中没有直接绘制Region的函数，我们想要绘制一个区域，就只能通过利用RegionIterator构造矩形集来逼近的显示区域。用法如下：
     * Author:
     * <p>
     * Time:2017/9/26 15:56
     */
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //初始化Paint
        Paint paint = new Paint();
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        //构造一个椭圆路径
        Path ovalPath = new Path();
        RectF rect = new RectF(50, 50, 200, 500);
        ovalPath.addOval(rect, Path.Direction.CCW);
        //SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
        Region rgn = new Region();
        rgn.setPath(ovalPath, new Region(50, 50, 200, 200));
        //画出路径
        drawRegion(canvas, rgn, paint);


        Paint paint1=new Paint();
        canvas.scale(0.5f, 0.5f);
        canvas.save();
        canvas.clipRect(new Rect(100,100,200,200));//裁剪区域实际大小为50*50
        canvas.drawColor(Color.RED);
        canvas.restore();

        canvas.drawRect(new Rect(0,0,100,100), paint1);//矩形实际大小为50*50

        canvas.clipRegion(new Region(new Rect(300,300,400,400)));//裁剪区域实际大小为100*100
        canvas.drawColor(Color.BLACK);


    }

    //这个函数不懂没关系，下面会细讲
    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }
}
