package com.thu9group.snake;


import com.thu9group.snake.R;


import android.net.NetworkInfo.State;
import android.os.*;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Grid extends Activity implements Runnable {

	
	public final static String HIGH_SCORE = "com.thu9group.snake.SCORE";	
	private View gridView;
	private GameState gameState;
	private GestureDetectorCompat mDetector;
	public TextView score;
	private Thread gameThread;
    private long lastUpdate;
    private GameOverHandler mHandler;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.snakes);
		score = (TextView)findViewById(R.id.scoreDisplay);
		gameState = new GameState(this);	
		gridView = new GridView(this);
		mHandler = new GameOverHandler(Looper.getMainLooper(), this);

		((GridView)gridView).state = gameState;
		setContentView(gridView);
		mDetector = new GestureDetectorCompat(this, new MyGestureListener());
		gameThread = new Thread(this);
		gameThread.start();
		

		
	}
	
	//this is the main game loop
	public void run() {
		while(gameState.isGameOver() == false) {
			long now = System.currentTimeMillis();
			if(now - lastUpdate > gameState.delay) {
				lastUpdate = now;
				gameState.cycle();
			    gridView.postInvalidate();  // Force a re-draw
			}
		}
		
		//game over
		mHandler.sendMessage(mHandler.obtainMessage());
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

	private class GameOverHandler extends Handler {
		private Activity activity;
		
		public GameOverHandler(Looper looper, Activity activity) {
			super(looper);
			this.activity = activity;
		}

        @Override
        public void handleMessage(Message inputMessage) {
            // Gets the message from the Game Thread
        	// In this case, the message will always be a game over message.
        	// That means we must close the activity and start the GameOver activity
	    	Log.d("Snake hit itself" ,"Snake hit itself");
			Intent intent = new Intent(activity, GameOver.class);
			//intent.putExtra(Grid.HIGH_SCORE, (Integer)inputMessage);
			activity.startActivity(intent);
			
        }		
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
        			d = GameState.LEFT;
        		} else {
        			direction = "RIGHT";
        			d = GameState.RIGHT;
        		}
        	} else {
        		if(deltay > 0) {
        			direction = "UP";
        			d = GameState.UP;
        		} else {
        			direction = "DOWN";
        			d = GameState.DOWN;
        		}
        	}
        	
        	gameState.updateDirection(d);
            Log.d(DEBUG_TAG, "onFling: " + event1.toString()+event2.toString() + "\n" + direction);
            return true;
        }
	}
}
