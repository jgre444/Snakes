package com.thu9group.snake;

import com.thu9group.snake.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Credit extends Activity  implements View.OnClickListener{
	
	private Button mainMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credit);
		mainMenu=(Button)findViewById(R.id.menu3);
		mainMenu.setOnClickListener(this);
		
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.menu3:
				Intent newIntent = new Intent(this, MainActivity.class);
		    	newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);	  
				startActivity(newIntent);
		    	break;
		
		}
		
	}

}
