package com.thu9group.snake;

import com.thu9group.snake.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Tutorial extends Activity  implements View.OnClickListener {
	
	private Button mainMenu;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial);
		mainMenu = (Button)findViewById(R.id.menu4);
		mainMenu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
		
		case R.id.menu4:
				Intent newIntent = new Intent(this, MainActivity.class);
				startActivity(newIntent);
		    	break;
		
		}
		
	}

}
