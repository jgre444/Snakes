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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class OptionMenu extends Activity implements View.OnClickListener {
	
	public RadioGroup radioGroup;
	public RadioButton radioButton;
	private RadioButton easy;
	private RadioButton medium;
	private RadioButton hard;
	private TextView text;
	private TextView title; 
	public int radioId;
	private Button get;
	private Button home;
	private String level;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionmenu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		get=(Button)findViewById(R.id.setLevel);
		home=(Button)findViewById(R.id.home2);
	
	
		Typeface typeButton = Typeface.createFromAsset(getAssets(),"fonts/woodbadge.ttf"); 
		home.setTypeface(typeButton);
		get.setTypeface(typeButton);
		
		
		text=(TextView)findViewById(R.id.textView11);
		Typeface typeText = Typeface.createFromAsset(getAssets(),"fonts/acmesa.ttf"); 
		Typeface typeHead = Typeface.createFromAsset(getAssets(),"fonts/acmesab.ttf"); 
		
		text.setTypeface(typeText);
		
		easy = (RadioButton)findViewById(R.id.radioButton1);
		medium = (RadioButton)findViewById(R.id.radioButton2);
		hard = (RadioButton)findViewById(R.id.radioButton3);
		title = (TextView)findViewById(R.id.TextView01);
		title.setTypeface(typeHead);
		easy.setTypeface(typeText);
		medium.setTypeface(typeText);
		hard.setTypeface(typeText);
		
		
		home.setOnClickListener(this);
		get.setOnClickListener(this);
		setRadioLevel();
		
		
	}
	
	public void updateLevel() {
		try{
		radioGroup=(RadioGroup)findViewById(R.id.radioSelection);
		radioId=radioGroup.getCheckedRadioButtonId();
		radioButton=(RadioButton)findViewById(radioId);
		System.err.println("RadioButton: "+ radioButton.getText());
		level = "";
		if (radioButton.getText().toString().contains("Easy")){
			level="1";
			
		}else if (radioButton.getText().toString().contains("Medium")){
			level = "2";
			
		}else if (radioButton.getText().toString().contains("Hard")){
			level ="3";
			
		}
		OutputStreamWriter out = new OutputStreamWriter(openFileOutput("level.txt",0));
		out.write(level.toString());
		System.err.println("Level: " +level.toString());
		out.close();
		
		}catch (IOException e){
			e.printStackTrace();
		}

		
	}
	
	public void setRadioLevel(){
		String output=" ";
		int level = 1;
		try{
			File file = getBaseContext().getFileStreamPath("level.txt");
			if (file.exists()){
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String aLine = null;
				output = " ";
				while ((aLine=br.readLine())!=null){
					output+=aLine;
					
				}
				System.err.println("output"+output);
				br.close();
			}
		}  catch (IOException e){
				e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		if (output.contains("3")){
			radioButton=(RadioButton)findViewById(R.id.radioButton3);
			radioButton.setChecked(true);
		}else if (output.contains("1")){
			radioButton=(RadioButton)findViewById(R.id.radioButton1);
			radioButton.setChecked(true);
		}else if (output.contains("2"))	{
			radioButton=(RadioButton)findViewById(R.id.radioButton2);
			radioButton.setChecked(true);
			
		}
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		// this will exit the program
	  
	
	   case R.id.setLevel:
		   
			   updateLevel();
			   text.setText("Difficulty  Updated");
			   
		   
		   break;
	   case R.id.home2:
		   Intent i3 = new Intent(this, MainActivity.class);
			i3.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    	startActivity(i3);
	    	break;
		}
		
	}
	
	
	
}


