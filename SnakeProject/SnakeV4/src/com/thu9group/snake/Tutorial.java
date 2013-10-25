package com.thu9group.snake;



import com.thu9group.snake.R;

import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Tutorial extends Activity  implements View.OnClickListener {

	private Button mainMenu;
	private int curPos;
	private TextView pages;

	private final int NO_OF_IMAGES = 8;
	private Integer[] mImageIds = {
			R.drawable.snakeone,
			R.drawable.snaketwo,
			R.drawable.snakethree,
			R.drawable.snakefour,
			R.drawable.snakefive,
			R.drawable.snakesix,
			R.drawable.snakeseven,
			R.drawable.snakeeight
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tutorial);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		mainMenu = (Button)findViewById(R.id.menuTut);
		mainMenu.setOnClickListener(this);
		Typeface typeButton = Typeface.createFromAsset(getAssets(),"fonts/woodbadge.ttf"); 
		Typeface typeText = Typeface.createFromAsset(getAssets(),"fonts/acmesab.ttf"); 
		
		mainMenu.setTypeface(typeButton);
		curPos = 0;
		pages = (TextView)findViewById(R.id.pageIdentification);
		pages.setTypeface(typeText);
		pages.setText((curPos+1)+"/" + NO_OF_IMAGES);
		final ImageView iv=(ImageView) findViewById(R.id.imageShower);

		iv.setVisibility(View.VISIBLE);
		Drawable d=getResources().getDrawable(mImageIds[0]);
		iv.setImageDrawable(d); 


		ImageView leftArrowImageView = (ImageView) findViewById(R.id.left_arrow_imageview);
		ImageView rightArrowImageView = (ImageView) findViewById(R.id.right_arrow_imageview);

		leftArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (curPos > 0) {
					--curPos;

				}


				iv.setVisibility(View.VISIBLE);
				Drawable d=getResources().getDrawable(mImageIds[curPos]);
				iv.setImageDrawable(d);
				pages.setText((curPos+1)+"/"+NO_OF_IMAGES);
				
			}
		});

		rightArrowImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (curPos < NO_OF_IMAGES - 1) {
					++curPos;

				}

				iv.setVisibility(View.VISIBLE);
				Drawable d=getResources().getDrawable(mImageIds[curPos]);
				iv.setImageDrawable(d);
				pages.setText((curPos+1)+"/"+NO_OF_IMAGES);
				
			}
		});

	}




	@Override
	public void onClick(View v) {
		switch (v.getId()){

		case R.id.menuTut:
			Intent newIntent = new Intent(this, MainActivity.class);
			newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(newIntent);
			break;

		}

	}


}
