package com.example.Snake;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.example.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class OptionMenu extends Activity implements View.OnClickListener {
	
	private TextView displayText;
	private EditText getLevel;
	private Button get;
	private int snakeLevel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.optionmenu);
		displayText=(TextView)findViewById(R.id.displayText);
		getLevel=(EditText)findViewById(R.id.levelTxt);
		get=(Button)findViewById(R.id.setLevel);
		get.setOnClickListener(this);
		
		
	}
	public boolean isEmpty(){
		boolean isEmpty=false;
		if(getLevel.length()==0){
			isEmpty=true;
		}
		return isEmpty;
	}
	public void updateLevel() {
		try{
		String level = "";
		OutputStreamWriter out = new OutputStreamWriter(openFileOutput("level.txt",0));
		level=getLevel.getText().toString();
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
		   if(isEmpty()){
			   displayText.setText("Please enter a level between 1 ...9");
		   }else{
			   updateLevel();
			   displayText.setText("Level updated");
		   }
		   break;
		}
		
	}
	
	
	
}


