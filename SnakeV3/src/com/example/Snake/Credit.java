package com.example.Snake;

import com.example.sample.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
				startActivity(newIntent);
		    	break;
		
		}
		
	}

}
