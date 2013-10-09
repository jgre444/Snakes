package com.example.grid;


import android.os.Bundle;
import android.app.Activity;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class Grid extends Activity {

	
	
	private View gridView;
	private GestureDetectorCompat mDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grid);
		gridView = new GridView(this);
		setContentView(gridView);
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		
//		gridView.setOnTouchListener(new OnTouchListener() {
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//            	((GridView) gridView).activateCell(event.getX(), event.getY());
//				return false;
//            
//            }
//        });
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.grid, menu);
		return true;
	}
	
	@Override 
    public boolean onTouchEvent(MotionEvent event){ 
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

	class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
		private static final String DEBUG_TAG = "Gestures";
		
		
		@Override
        public boolean onDown(MotionEvent event) { 
            
			Log.d(DEBUG_TAG,"onDown: " + event.toString()); 
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, 
                float velocityX, float velocityY) {
        	String direction;
        	int d;
        	
        	float deltax = event1.getX() - event2.getX();
        	float deltay = event1.getY() - event2.getY();
        	if(Math.abs(deltax) > Math.abs(deltay)) {
        		if(deltax > 0) {
        			direction = "LEFT";
        			d = GridView.LEFT;
        		} else {
        			direction = "RIGHT";
        			d = GridView.RIGHT;
        		}
        	} else {
        		if(deltay > 0) {
        			direction = "UP";
        			d = GridView.UP;
        		} else {
        			direction = "DOWN";
        			d = GridView.DOWN;
        		}
        	}
        	
        	((GridView) gridView).updateDirection(d);
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString() + "\n" + direction);
            return true;
        }
	}
}
