package com.example.Snake;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
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
		topScores = readScores();
		getHighScores();
	}

	public void getHighScores(){

		

		if(topScores.size() == 0){
			scoresAsString.append("\nreading ERROR");
		}
		else{
			scoresAsString.append("Name\tScore\n\n");
			for(int i = 0; i < topScores.size(); i++){
				scoresAsString.append(topScores.get(i) + "\n");
			}
		}
		

		textview.setText(scoresAsString.toString());

	}

	public ArrayList<String>  readScores() {
		ArrayList<String> scores = new ArrayList<String>();
		

		try{
			BufferedReader newBufferReader=new BufferedReader(new 
		            InputStreamReader(getAssets().open("Scores.txt")));
			
			String line = newBufferReader.readLine();
			
	        while (line != null) {	         
					scores.add(line);	
					line = newBufferReader.readLine();
			}
	        
			newBufferReader.close();

		} catch (Exception e) {
			scoresAsString.append("\n" + e.getMessage());
		}


		return scores;

	}
}
