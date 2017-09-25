package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhouliang on 2017/9/25.
 */

public class DrawBitmap extends View {
    Paint mPaint;
    Bitmap mBitmap;

    public DrawBitmap(Context context) {
        super(context);
        init();
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        //绘制Bitmap的一部分，并对其拉伸
//        //srcRect绘制Bitmap的哪一部分
        Rect src = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight() / 3);
        //dstRecF绘制的Bitmap拉伸到哪里
        RectF dst = new RectF(0, mBitmap.getHeight(), canvas.getWidth(), mBitmap.getHeight() + 200);
        canvas.drawBitmap(mBitmap, src, dst, mPaint);
//        Matrix matrix = new Matrix();
//        matrix.postTranslate(0, mBitmap.getHeight() + 200);
//        canvas.drawBitmap(mBitmap, matrix, mPaint);
    }

}
