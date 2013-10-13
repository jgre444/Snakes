package com.example.Snake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.example.sample.R;
import com.example.sample.R.layout;
import com.example.sample.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class GameOver extends Activity implements View.OnClickListener {

	
	private Button submitButton;
	private TextView textDisplay;
	private EditText name;
	private int Score;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		Intent intent = getIntent();
		int score = intent.getIntExtra(Grid.HIGH_SCORE, 0);
		this.Score=score;
		TextView view = (TextView)findViewById(R.id.endScore);
		view.setText("Your score was: " + score);
		submitButton=(Button)findViewById(R.id.submitScore);
		submitButton.setOnClickListener(this);
		name = (EditText)findViewById(R.id.editText1);
		textDisplay = (TextView)findViewById(R.id.txtDisplay);
	
	}
	
	public void onClick (View v){
		switch(v.getId()) {
		// this will exit the program
	  
		//store the users score 
		case R.id.submitScore:
			if (isEmpty()==false){
				textDisplay.setText("Please enter a valid name");
			}else{
				//allow the user to store the score 
				storeScore();
				textDisplay.setText("Your score has been saved");
			}
		   	break;
		} 
	}
	public boolean isEmpty(){
		boolean isTextEmpty = false;
		if (name.length()>0){
			isTextEmpty=true;
		}
		return isTextEmpty;
	}
	public void storeScore(){
		
		try{
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput("highscores.txt",MODE_APPEND));
			String totalScore = name.getText() +"		"+ this.Score+"\n";
			
			out.write(totalScore);
			out.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}

	        
			
	}
	
	public void submit(View view) {
    	Intent i = new Intent(this,MainActivity.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(i);
	}
	
	

}
