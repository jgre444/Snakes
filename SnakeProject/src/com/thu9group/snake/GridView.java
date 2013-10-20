package com.thu9group.snake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;


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




import android.R;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GridView extends View {

    private RectF r;
    private Paint paint;
    public GameState state;
    

    /*
     * Constructor method
     */
	public GridView(Context context) {
		super(context);
		r = new RectF();
		paint = new Paint();
		setBackgroundColor(Color.WHITE);
	}

    

	
	/*
	 * Draw Canvas
	 * (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int xGridCount = GameState.X_COUNT;
		int yGridCount = GameState.Y_COUNT;
        int xCellSize = getWidth() / xGridCount;
        int yCellSize = getHeight() / yGridCount;
        int xOffset = ((getWidth() - xGridCount*xCellSize) / 2);
        int yOffset = ((getHeight() - yGridCount*yCellSize) / 2);		
        
        
        
		paint.setColor(Color.BLACK);
		for(int i = 0 ; i < xGridCount ; i++) {
			for(int j = 0 ; j < yGridCount ; j++) {
				if(state.getCell(i, j) == GameState.EMPTY) {

				} else {
					if(state.getCell(i, j) == GameState.WALL)
						paint.setColor(Color.BLACK);
					else if(state.getCell(i, j) == GameState.SNAKE)
						paint.setColor(Color.GRAY);
					else if(state.getCell(i, j) == GameState.FOOD)
						paint.setColor(Color.GREEN);
					else if (state.getCell(i, j) == GameState.OBSTACLE)
						paint.setColor(Color.RED);
					else if (state.getCell(i, j) == GameState.SIZE_INCREASE)
						paint.setColor(Color.YELLOW);
					else if (state.getCell(i, j) == GameState.SIZE_DECREASE)
						paint.setColor(Color.CYAN);
					else
						paint.setColor(Color.RED);
				r.set(i*xCellSize + xOffset, j*yCellSize + yOffset, (i+1)*xCellSize + xOffset, (j+1)*yCellSize + yOffset);
				canvas.drawRect(r, paint);
				}
			}
			
		}	
		
	}
	

	


	
	
	/*
	 * Called on creation to get size of grid
	 * (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		/*
		cellSize = (int) Math.floor(h / yGridCount);
		xGridCount = (int) Math.floor(w / cellSize);
        
        xOffset = ((w - xGridCount*cellSize) / 2);
        yOffset = ((h - yGridCount*cellSize) / 2);
        */

    }
	
	

}
