package com.example.Snake;

import com.example.sample.R;
import com.example.sample.R.layout;
import com.example.sample.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class GameOver extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		Intent intent = getIntent();
		int score = intent.getIntExtra(Grid.HIGH_SCORE, 0);
		TextView view = (TextView)findViewById(R.id.endScore);
		view.setText("Your score was: " + score);
	}
	
	public void submit(View view) {
    	Intent i = new Intent(this,MainActivity.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(i);
	}

}
