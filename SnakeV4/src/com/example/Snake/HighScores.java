package com.example.Snake;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScores  extends Activity  implements View.OnClickListener{
	private TextView textview;
	private ArrayList<String> topScores;
	private StringBuffer scoresAsString;
	private Button delScores;
	private Button mainMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores);
		scoresAsString = new StringBuffer();
		textview=(TextView)findViewById(R.id.endScore);
		mainMenu=(Button)findViewById(R.id.returnHome);
		delScores=(Button)findViewById(R.id.deleteScores);
		mainMenu.setOnClickListener(this);
		delScores.setOnClickListener(this);
		tempDisplay();
		
	}
	//changed this 
	public void tempDisplay(){
		try{
		
		FileInputStream fis = openFileInput("highscores.txt");
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String aLine = null;
		String otput = " ";
		while ((aLine=br.readLine())!=null){
			otput+="\n" + aLine;
			
		}
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
	    	startActivity(newIntent);
	    	break;
	   case R.id.deleteScores:
		   deleteScores();
		}
		
	}


}
