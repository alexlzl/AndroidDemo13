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
		public boolean onDown(MotionEvent e) {
			Log.i("MyGesture", "onDown");
			Toast.makeText(MainActivity.this, "onDown", Toast.LENGTH_SHORT)
					.show();
			return false;
		}

		public void onShowPress(MotionEvent e) {
			Log.i("MyGesture", "onShowPress");
			Toast.makeText(MainActivity.this, "onShowPress", Toast.LENGTH_SHORT)
					.show();
		}

		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapUp");
			Toast.makeText(MainActivity.this, "onSingleTapUp",
					Toast.LENGTH_SHORT).show();
			return true;
		}

		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.i("MyGesture", "onScroll:" + (e2.getX() - e1.getX()) + "   "
					+ distanceX);
			Toast.makeText(MainActivity.this, "onScroll", Toast.LENGTH_LONG)
					.show();

			return true;
		}

		public void onLongPress(MotionEvent e) {
			Log.i("MyGesture", "onLongPress");
			Toast.makeText(MainActivity.this, "onLongPress", Toast.LENGTH_LONG)
					.show();
		}

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.i("MyGesture", "onFling");
			Toast.makeText(MainActivity.this, "onFling", Toast.LENGTH_LONG)
					.show();
			return true;
		}
		
		/*****OnDoubleTapListener的函数*****/
		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapConfirmed");
			Toast.makeText(MainActivity.this, "onSingleTapConfirmed",
					Toast.LENGTH_LONG).show();
			return true;
		}

		public boolean onDoubleTap(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTap");
			Toast.makeText(MainActivity.this, "onDoubleTap", Toast.LENGTH_LONG)
					.show();
			return true;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTapEvent");
			Toast.makeText(MainActivity.this, "onDoubleTapEvent",
					Toast.LENGTH_LONG).show();
			return true;
		}

	}
}
