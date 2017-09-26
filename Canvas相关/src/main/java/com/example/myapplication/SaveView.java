package com.example.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liuzhouliang on 2017/9/26.
 */

public class SaveView extends View {
    public SaveView(Context context) {
        super(context);
    }

    public SaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();

        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();

        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);
    }
}
