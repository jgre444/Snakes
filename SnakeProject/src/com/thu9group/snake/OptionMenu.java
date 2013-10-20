package com.thu9group.snake;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.thu9group.snake.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


public class OptionMenu extends Activity implements View.OnClickListener {
	
	private RadioGroup radioGroup;
	private RadioButton radioButton;
	private TextView text;
	private int radioId;
	private Button get;
	private Button home;
	private int snakeLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionmenu);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		get=(Button)findViewById(R.id.setLevel);
		home=(Button)findViewById(R.id.home2);
	
		text=(TextView)findViewById(R.id.textView11);
		home.setOnClickListener(this);
		get.setOnClickListener(this);
		
		
	}

	public void updateLevel() {
		try{
		radioGroup=(RadioGroup)findViewById(R.id.radioSelection);
		radioId=radioGroup.getCheckedRadioButtonId();
		radioButton=(RadioButton)findViewById(radioId);
		System.err.println("RadioButton: "+ radioButton.getText());
		String level = "";
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


