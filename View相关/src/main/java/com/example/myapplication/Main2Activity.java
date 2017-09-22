package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {
    TextView button;
    TextView textViewX;
    TextView textViewY;
    TextView status;
    TextView textViewGetX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textViewX = (TextView) findViewById(R.id.x);
        textViewY = (TextView) findViewById(R.id.y);
        status = (TextView) findViewById(R.id.statusbar_tv);
        button = (TextView) findViewById(R.id.button);
        textViewGetX = (TextView) findViewById(R.id.getx);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textViewX.setText("rawx==" + motionEvent.getRawX());
                textViewY.setText("rawy==" + motionEvent.getRawY());
                return true;
            }
        });
        textViewGetX.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textViewX.setText("rawx==" + event.getX());
                textViewY.setText("rawy==" + event.getY());
                return false;
            }
        });

    }

    public void getStatusH(View view) {
        TextView textView = (TextView) view;
        textView.setText("状态栏高度===" + getStatusBarHeight(this) + "");
    }

    private int getStatusBarHeight(Context context) {

        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }

    public void test1(View view) {
        textViewX.setText("getx==" + textViewGetX.getX());
        textViewY.setText("gety==" + textViewGetX.getY());
    }


    /**
     * Describe:Sets the horizontal location of this view relative to its {@link #getLeft() left} position.
     * This effectively positions the object post-layout, in addition to wherever the object's
     * layout placed it.
     *
     * @param translationX The horizontal position of this view relative to its left position,
     * in pixels.
     * <p>
     * Author:
     * <p>
     * Time:2017/9/22 17:51
     */
    int i = 0;

    public void test2(View view) {

        i += 20;
        textViewGetX.setTranslationX(i);
    }

    int a = 0;

    public void test3(View view) {

        a += 20;
        textViewGetX.scrollBy(10, 10);
    }
}
