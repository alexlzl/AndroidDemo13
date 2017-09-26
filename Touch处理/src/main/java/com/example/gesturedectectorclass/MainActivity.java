package com.example.gesturedectectorclass;
/**
 * author:write by harvic
 * date:2014-9-26
 * address:blog.csdn.net/harvic880925
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {

	private GestureDetector mGestureDetector;   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mGestureDetector = new GestureDetector(new simpleGestureListener());
		
		TextView tv = (TextView)findViewById(R.id.tv);
	    tv.setOnTouchListener(this);
	    tv.setFocusable(true);   
	    tv.setClickable(true);   
	    tv.setLongClickable(true); 
	}
	
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return mGestureDetector.onTouchEvent(event);   
	}

	private class simpleGestureListener extends
			GestureDetector.SimpleOnGestureListener {
		
		/*****OnGestureListener的函数*****/

		final int FLING_MIN_DISTANCE = 100, FLING_MIN_VELOCITY = 200;  
		
		// 触发条件 ：   
        // X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒   
       
		// 参数解释：   
        // e1：第1个ACTION_DOWN MotionEvent   
        // e2：最后一个ACTION_MOVE MotionEvent   
        // velocityX：X轴上的移动速度，像素/秒   
        // velocityY：Y轴上的移动速度，像素/秒   
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			
	        
	        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE  
	                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
	            // Fling left   
	            Log.i("MyGesture", "Fling left");  
	            Toast.makeText(MainActivity.this, "Fling Left", Toast.LENGTH_SHORT).show();  
	        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE  
	                && Math.abs(velocityX) > FLING_MIN_VELOCITY) {  
	            // Fling right   
	            Log.i("MyGesture", "Fling right");  
	            Toast.makeText(MainActivity.this, "Fling Right", Toast.LENGTH_SHORT).show();  
	        }  
			return true;
		}

	}
}
