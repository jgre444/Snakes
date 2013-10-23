package com.thu9group.snake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.graphics.Canvas;
import android.widget.TextView;

public class DifficultyView extends TextView {

	private String difficulty;

	public DifficultyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void setDifficultyViewInfo(FileInputStream fis) {
		try{

			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String aLine = null;

			while ((aLine=br.readLine())!=null){
				if(aLine.equals("1")){
					difficulty = "easy";		
				}else if(aLine.equals("2")){
					difficulty = "medium";
				}
				else if(aLine.equals("3")){
					difficulty = "hard";
				}

			}


		}catch (IOException e){
			e.printStackTrace();
		}
	}

	protected void onDraw(Canvas canvas) {
		this.setText("   LEVEL: " + difficulty);
		super.onDraw(canvas);
	}

}
