package com.thu9group.snake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

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
	private TextView nameText;
	private TextView scoreText;
	private TextView scoreHeading;
	private Button delScores;
	private Button mainMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.highscores);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		nameText=(TextView)findViewById(R.id.nameText);
		scoreText=(TextView)findViewById(R.id.scoreText);
		scoreHeading=(TextView)findViewById(R.id.scoreHeading);
		mainMenu=(Button)findViewById(R.id.returnHome);
		delScores = (Button)findViewById(R.id.resetScore);
		mainMenu.setOnClickListener(this);
		delScores.setOnClickListener(this);
		Typeface typeButton = Typeface.createFromAsset(getAssets(),"fonts/woodbadge.ttf"); 
		mainMenu.setTypeface(typeButton);
		delScores.setTypeface(typeButton);
		tempDisplay();

	}

	public void resetScore(){
		try{
			String emptyString = "";
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput("highscores.txt",0));
			out.write(emptyString);
			out.close();

		}catch (IOException e){
			e.printStackTrace();
		}

	}


	//changed this 
	public void tempDisplay(){
		Typeface typeHead = Typeface.createFromAsset(getAssets(),"fonts/acmesab.ttf"); 
		scoreHeading.setTypeface(typeHead);
		scoreHeading.setText("  NAME                 SCORE\n------------------------");

		try{

			FileInputStream fis = openFileInput("highscores.txt");
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String aLine = null;
			int counter = 1;
			String finalNames = "\n";
			String finalScores = "\n";
			while ((aLine=br.readLine())!=null){
				String name = aLine.split("\t")[0];	
				String score = aLine.split("\t")[1];
				finalNames+="\n" + counter+ ". "+ name;
				finalScores+= "\n    " + score;
				counter+=1;

			}

			
			Typeface typeScore = Typeface.createFromAsset(getAssets(),"fonts/acmesa.ttf"); 
			nameText.setTypeface(typeScore);
			nameText.setText(finalNames.toString());

			scoreText.setTypeface(typeScore);
			scoreText.setText(finalScores.toString());


		}catch (IOException e){
			e.printStackTrace();
		}


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
		case R.id.resetScore:
			resetScore();
			tempDisplay();
			break;

		}

	}


}
