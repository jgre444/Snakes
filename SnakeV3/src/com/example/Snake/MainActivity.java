package com.example.Snake;

import com.example.sample.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {
	//this is button constants
	
	private Button button1;
	private Button button2;
	private Button button3;
	private Button button4;
	private Button button5;
	private Button button6;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			button1 = (Button) findViewById(R.id.button1);
	        button2 = (Button) findViewById(R.id.button2);
	        button3 = (Button) findViewById(R.id.button3);
	    	button4 = (Button) findViewById(R.id.button4);
	        button5 = (Button) findViewById(R.id.button5);
	        button6 = (Button) findViewById(R.id.button6);
	        button1.setOnClickListener(this);
	        button2.setOnClickListener(this);
	        button3.setOnClickListener(this);
	        button4.setOnClickListener(this);
	        button5.setOnClickListener(this);
	        button6.setOnClickListener(this);
	        
	        

	       
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

		
	public void onClick(View v) {
		switch(v.getId()) {
			// this will exit the program
		  
		
		   case R.id.button1:
		    	Intent i3 = new Intent(this, Grid.class);
		    	startActivity(i3);
		    	break;
		    case R.id.button2:
		    	Intent i2 = new Intent(this,HighScores.class);
		    	startActivity(i2);
		    	break;
		    case R.id.button3:
		    	Intent i4 = new Intent(this,Tutorial.class);
		    	startActivity(i4);
		    	break;
		    case R.id.button5:
		    	Intent i5 = new Intent(this,Credit.class);
		    	startActivity(i5);
		    	break;
		    	
		    case R.id.button4:
		    	// this will take us to the option page
		    	Intent i = new Intent(this,OptionMenu.class);
		    	startActivity(i);
		    	break;
		
		    case R.id.button6:
			    System.exit(0);
			    finish();
		   
		}
		
	}
}
