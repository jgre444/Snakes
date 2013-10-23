package com.thu9group.snake;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.thu9group.snake.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScores  extends Activity  implements View.OnClickListener{
	private TextView textview;
	private TextView scoreHeading;
	private Button delScores;
	private Button mainMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		textview=(TextView)findViewById(R.id.endScore);
		scoreHeading=(TextView)findViewById(R.id.scoreHeading);
		mainMenu=(Button)findViewById(R.id.returnHome);
		mainMenu.setOnClickListener(this);
		tempDisplay();
		
	}
	//changed this 
	public void tempDisplay(){
		try{
		
		FileInputStream fis = openFileInput("highscores.txt");
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String aLine = null;
		String otput = "\n-----------------";
		int counter = 1;
		while ((aLine=br.readLine())!=null){
			String name = aLine.split("\t")[0];
			
			int nameSize = name.length();
			String addedString = "  ";
			for(int ix = nameSize -1; ix < 6; ix++){
				addedString += " ";
			}
			name += addedString;
			
			String score = aLine.split("\t")[1];
			otput+="\n" + counter+ ". "+ name+"\t" + score;
			counter+=1;
			
		}
		
		Typeface typeHead = Typeface.createFromAsset(getAssets(),"fonts/acmesab.ttf"); 
		scoreHeading.setTypeface(typeHead);
		scoreHeading.setText("Name\t\t\tScore");
		
		Typeface typeScore = Typeface.createFromAsset(getAssets(),"fonts/acmesa.ttf"); 
		textview.setTypeface(typeScore);
		textview.setText(otput.toString());
		
		
		}catch (IOException e){
			e.printStackTrace();
		}

		
	}
	public void deleteScores(){
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		// this will exit the program
	  
	
	   case R.id.returnHome:
	    	Intent newIntent= new Intent(this, MainActivity.class);
	    	newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	    	
	    	startActivity(newIntent);
	    	break;
	  
		}
		
	}


}
