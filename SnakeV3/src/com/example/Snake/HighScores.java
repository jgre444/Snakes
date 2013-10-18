package com.example.Snake;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.example.sample.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HighScores  extends Activity{
	private TextView textview;
	private ArrayList<String> topScores;
	private StringBuffer scoresAsString;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores);
		scoresAsString = new StringBuffer();
		textview=(TextView)findViewById(R.id.endScore);
		//topScores = readScores();
		tempDisplay();
		//getHighScores();
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


}
