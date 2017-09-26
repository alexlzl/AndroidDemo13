package com.example.gesturedetectorinterface;

/**
 * write by harvic
 * 2014-9-25
 * blog.csdn.net/harvic880925
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener{

	private GestureDetector mGestureDetector;   
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		

      mGestureDetector = new GestureDetector(new gestureListener()); //ʹ��������OnGestureListener
      mGestureDetector.setOnDoubleTapListener(new doubleTapListener());
        
      TextView tv = (TextView)findViewById(R.id.tv);
      tv.setOnTouchListener(this);
      tv.setFocusable(true);   
      tv.setClickable(true);   
      tv.setLongClickable(true); 
	}
	
	
	/* 
     * ��onTouch()�����У����ǵ���GestureDetector��onTouchEvent()����������׽����MotionEvent����GestureDetector 
     * �������Ƿ��к��ʵ�callback�����������û������� 
     */  
	public boolean onTouch(View v, MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);   
	}
	
	//OnGestureListener����
	private class gestureListener implements GestureDetector.OnGestureListener{

		
		// �û��ᴥ����������1��MotionEvent ACTION_DOWN����   
		public boolean onDown(MotionEvent e) {
			Log.i("MyGesture", "onDown");   
	        Toast.makeText(MainActivity.this, "onDown", Toast.LENGTH_SHORT).show();   
			return false;
		}

		/*  
	     * �û��ᴥ����������δ�ɿ����϶�����һ��1��MotionEvent ACTION_DOWN����  
	     * ע���onDown()������ǿ������û���ɿ������϶���״̬  
	     * 
	     * ��onDownҲ����һ��MotionEventACTION_DOWN�����ģ�������û���κ����ƣ�
	     * Ҳ����˵���û������ʱ������MotionEventACTION_DOWN��onDown�ͻ�ִ�У�
	     * ����ڰ��µ�˲��û���ɿ��������϶���ʱ��onShowPress�ͻ�ִ�У�����ǰ��µ�ʱ�䳬��˲��
	     * �������Ҳ��̫���˲���ʱ����Ƕ��٣�һ������¶���ִ��onShowPress�����϶��ˣ��Ͳ�ִ��onShowPress��
	     */
		public void onShowPress(MotionEvent e) {
			Log.i("MyGesture", "onShowPress");   
	        Toast.makeText(MainActivity.this, "onShowPress", Toast.LENGTH_SHORT).show();   
		}

		// �û����ᴥ���������ɿ�����һ��1��MotionEvent ACTION_UP����   
		///���һ����Ļ������̧�������Ż����������
		//������Ҳ���Կ���,һ�ε��������̧�����,��Ȼ,�������Down���⻹����������,�ǾͲ�������Single������,��������¼� �Ͳ�����Ӧ
		public boolean onSingleTapUp(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapUp");   
	        Toast.makeText(MainActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();   
	        return true;   
		}

		// �û����´����������϶�����1��MotionEvent ACTION_DOWN, ���ACTION_MOVE����   
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.i("MyGesture", "onScroll:"+(e2.getX()-e1.getX()) +"   "+distanceX);   
	        Toast.makeText(MainActivity.this, "onScroll", Toast.LENGTH_LONG).show();   
	        
	        return true;   
		}

		// �û��������������ɶ��MotionEvent ACTION_DOWN����   
		public void onLongPress(MotionEvent e) {
			 Log.i("MyGesture", "onLongPress");   
		     Toast.makeText(MainActivity.this, "onLongPress", Toast.LENGTH_LONG).show();   
		}

		// �û����´������������ƶ����ɿ�����1��MotionEvent ACTION_DOWN, ���ACTION_MOVE, 1��ACTION_UP����   
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			Log.i("MyGesture", "onFling");   
	        Toast.makeText(MainActivity.this, "onFling", Toast.LENGTH_LONG).show();   
			return true;
		}
	};
	
	//OnDoubleTapListener����
	private class doubleTapListener implements GestureDetector.OnDoubleTapListener{

		public boolean onSingleTapConfirmed(MotionEvent e) {
			Log.i("MyGesture", "onSingleTapConfirmed");   
	        Toast.makeText(MainActivity.this, "onSingleTapConfirmed", Toast.LENGTH_LONG).show();  
			return true;
		}

		public boolean onDoubleTap(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTap");   
	        Toast.makeText(MainActivity.this, "onDoubleTap", Toast.LENGTH_LONG).show();  
			return true;
		}

		public boolean onDoubleTapEvent(MotionEvent e) {
			Log.i("MyGesture", "onDoubleTapEvent");   
	        Toast.makeText(MainActivity.this, "onDoubleTapEvent", Toast.LENGTH_LONG).show();  
			return true;
		}
	};
}
	


