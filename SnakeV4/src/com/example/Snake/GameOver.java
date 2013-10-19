package com.example.Snake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

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
	private final int TOP_SCORES = 5;
	private ArrayList<String> scores;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_over);
		Intent intent = getIntent();
		scores = new ArrayList<String>();
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
		loadScoresFile();
		boolean success = addScore();

		if(success){
			saveTopScore();
		}

	}



	private void saveTopScore() {
		try{

			//I want to re-write highscores.txt from beginning but don't know how, so I delete the file first.
			
			
			OutputStreamWriter out = new OutputStreamWriter(openFileOutput("highscores.txt",0));
			StringBuffer scrBuff = new StringBuffer();

			for(int ix = 0; ix < scores.size(); ix++){
				scrBuff.append(scores.get(ix)+"\n");
			}
			out.write(scrBuff.toString());
			System.err.println("successfully written tp highscores.txt: " + scrBuff.toString());
			out.close();
			
		}
		catch (IOException e){
			e.printStackTrace();
		}

	}

	private void loadScoresFile() {

		scores = new ArrayList<String>();

		try{

			File file = getBaseContext().getFileStreamPath("highscores.txt");
			if(file.exists()){
				FileInputStream fis = openFileInput("highscores.txt");
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String aLine = null;

				while ((aLine=br.readLine())!=null){
					System.err.println("added to arraylist: " + aLine);
					scores.add(aLine);

				}
			}
			else{
				System.err.println("FILE DOES NOT EXIST");
			}
			

		}catch (IOException e){
			e.printStackTrace();
		}



	}
	//Check eligibility of the mostRecentScore(score from the most recently played game)
	//return indexLocation for new Entry, -1 for inelgibility
	private int checkEligbility(){// if the type is int already is great, but String  is okay also, easy to manipulate

		int indexToPush = -1;
		//newStrs is an arrayList of scores, if 0 that means no scores has been recorded
		if(scores.size() == 0){
			indexToPush  = 0;
		}
		else{
			for(int i = 0; i < scores.size(); i++){
				int currentScore = Integer.parseInt(scores.get(i).split("\t")[1]);
				
				if(this.Score > currentScore){
					indexToPush = i;
					//System.out.println("ELIGIBLE for TOP 5 SCORES!!:  " + newStrs.get(i) + " is pushed down!");
					break;
				}
			}
			
			if(indexToPush == -1 && scores.size() < TOP_SCORES){
				indexToPush = scores.size();
			}
			
		}
		return indexToPush;
	}




	//parent method of checkEligibility
	//return true if successfully add
	private boolean  addScore(){
		int indexToBeInserted = checkEligbility();
		boolean success = false;

		//StringBuffer playerScore = new StringBuffer();
		//playerScore.append(getPlayersName() + “\tmostRecentScore”); //getPlayersName() prompts the user to enter his/hername
		if(indexToBeInserted != -1){
			if(scores.size() == 0){
				scores.add(name.getText() +"\t"+ this.Score);
				System.err.println("successfully added score of: " + name.getText());
			}
			else{ 
				System.err.println("successfully added score to INDEX: " + indexToBeInserted);
				scores.add(indexToBeInserted, name.getText() +"\t"+ this.Score);
				if(scores.size()> TOP_SCORES){
					scores.remove(TOP_SCORES);
				}
			}
			success = true;
		}

		return success;
	}

	public void submit(View view) {
		Intent i = new Intent(this,MainActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}



}
