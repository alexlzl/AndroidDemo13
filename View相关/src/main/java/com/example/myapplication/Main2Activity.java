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
        textViewX= (TextView) findViewById(R.id.x);
        textViewY= (TextView) findViewById(R.id.y);
        status= (TextView) findViewById(R.id.statusbar_tv);
        button= (TextView) findViewById(R.id.button);
        textViewGetX= (TextView) findViewById(R.id.getx);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                textViewX.setText("rawx=="+motionEvent.getRawX());
                textViewY.setText("rawy=="+motionEvent.getRawY());
                return true;
            }
        });
        textViewGetX.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                textViewX.setText("rawx=="+event.getX());
                textViewY.setText("rawy=="+event.getY());
                return false;
            }
        });
        textViewX.getX();
        textViewX.getScrollX();
    }

    public void getStatusH(View view){
        TextView textView= (TextView) view;
        textView.setText("状态栏高度==="+getStatusBarHeight(this)+"");
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
}
