package com.thu9group.snake;

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

import com.thu9group.snake.R;
import com.thu9group.snake.R.layout;
import com.thu9group.snake.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.v4.app.NavUtils;
import android.text.InputFilter;
import android.text.Spanned;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Build;

public class GameOver extends Activity implements View.OnClickListener {


	private Button submitButton;
	private Button gameOverButton;//<--- return to main menu button, dont know why its called gameoverbutton in xml, so i went along with it here too 
	private TextView textDisplay;
	private TextView goTitle;
	private EditText name;
	private int Score;
	public final static int TOP_SCORES = 5;
	public final static int MAX_LENGTH_NAME= 10;
	
	private ArrayList<String> scores;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


		setContentView(R.layout.activity_game_over);
		Intent intent = getIntent();
		scores = new ArrayList<String>();
		//int score = intent.getIntExtra(Grid.HIGH_SCORE, 0);
		this.Score= GameState.score;
		Typeface typeHead = Typeface.createFromAsset(getAssets(),"fonts/acmesab.ttf"); 
		Typeface typeText = Typeface.createFromAsset(getAssets(),"fonts/acmesa.ttf"); 

		TextView view = (TextView)findViewById(R.id.endScore);
		goTitle = (TextView)findViewById(R.id.goTitle);
		view.setTypeface(typeText);
		goTitle.setTypeface(typeHead);
		view.setText("Your score was: " + Score);
		submitButton=(Button)findViewById(R.id.submitScore);
		gameOverButton = (Button)findViewById(R.id.gameOverButton);
		Typeface typeButton = Typeface.createFromAsset(getAssets(),"fonts/woodbadge.ttf"); 
		gameOverButton.setTypeface(typeButton);
		submitButton.setTypeface(typeButton);
		submitButton.setEnabled(true);
		submitButton.setOnClickListener(this);
		name = (EditText)findViewById(R.id.editText1);
		textDisplay = (TextView)findViewById(R.id.txtDisplay);
		filterInput();
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
				v.setEnabled(false);
			}
			break;
		} 
	}


	private void filterInput() {
		InputFilter filter = new InputFilter() { 
			@Override
			public CharSequence filter(CharSequence source, int start, int end, 
					Spanned dest, int dstart, int dend) { 
				for (int i = start; i < end; i++) { 
					if (!Character.isLetterOrDigit(source.charAt(i))) { 
						return ""; 
					} 
				} 
				return null; 
			}

			
		}; 
		InputFilter[] filterArray = new InputFilter[2];
		filterArray[0] = new InputFilter.LengthFilter(MAX_LENGTH_NAME);
		filterArray[1] = filter;
		name.setFilters(filterArray); 
	

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
