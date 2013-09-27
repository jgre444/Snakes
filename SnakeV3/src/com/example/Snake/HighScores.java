package com.example.Snake;

import com.example.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScores  extends Activity{
	private TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores);
		textview=(TextView)findViewById(R.id.textView1);
		getHighScores();
	}
	
	public void getHighScores(){
		textview.setText("Hello");
		
	}
}
