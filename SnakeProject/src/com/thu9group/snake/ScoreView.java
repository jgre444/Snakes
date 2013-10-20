package com.thu9group.snake;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

public class ScoreView extends TextView {

	private int score;
	
	public ScoreView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setscore(int s) {
		score = s;
	}
	
	protected void onDraw(Canvas canvas) {
		this.setText("Score: " + score);
		super.onDraw(canvas);
	}

}
