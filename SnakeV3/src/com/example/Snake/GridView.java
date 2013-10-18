package com.example.Snake;

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

	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int SNAKE = 2;
	public static final int FOOD = 3;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	
	protected static int cellSize;
	protected static int xGridCount;
    protected static int yGridCount;
    
    private long lastUpdate;
    private long delay;
    private int score;
    private static final Random RNG = new Random();
   
    
    private RectF r;
    private Paint paint;
    private int direction = UP;
    
    public int[][] grid;
    public Grid activity;
    
    private ArrayList<Coordinate> snakeList = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> foodList = new ArrayList<Coordinate>();
	
    private String otput;
    private int level;
    /*
     * Constructor method
     */
	public GridView(Context context) {
		super(context);
		activity = (Grid)context;
		cellSize = 30;
		r = new RectF();
		paint = new Paint();
	
		
		setBackgroundColor(Color.WHITE);
		initNewGame();
		
		
	}
	
	/*
	 * Initialize new game
	 */
    private void initNewGame() {
        snakeList.clear();
        foodList.clear();

        // For now we're just going to load up a short default eastbound snake
        // that's just turned north
        snakeList.add(new Coordinate(7, 7));
        snakeList.add(new Coordinate(6, 7));
        snakeList.add(new Coordinate(5, 7));
        snakeList.add(new Coordinate(4, 7));
        snakeList.add(new Coordinate(3, 7));
        snakeList.add(new Coordinate(2, 7));
        direction = UP;
      
        delay= getLevel();
        score = 0;

    }
    
    public int getLevel(){
    	String level = changeLevel();
		int myDelay=300;
		if (level.toString().equals("1")){
			myDelay=300;
		}if (level.toString().equals("2")){
			myDelay=290;
		}if (level.toString().equals("3")){
			myDelay=280;
		}if (level.toString().equals("4")){
			myDelay=270;
		}if (level.toString().equals("5")){
			myDelay=260;
		}if (level.toString().equals("6")){
			myDelay=250;
		}if (level.toString().equals("7")){
			myDelay=240;
		}if (level.toString().equals("8")){
			myDelay=230;
		}if (level.toString().equals("9")){
			myDelay=220;
		}
		return myDelay;
	}
	public String changeLevel(){
		
			try{
			File file = getContext().getFileStreamPath("level.txt");
			if (file.exists()){
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String aLine = null;
			otput = " ";
			while ((aLine=br.readLine())!=null){
				otput+=aLine;
				
			}
			System.err.println("output"+otput);
			}
			}catch (IOException e){
				e.printStackTrace();
			}
			return otput;
	}
	
    
    public void addFood() {
    	Log.d("RNG PROBLEM:", "xGridCount = " + xGridCount + "yGridCount =" + yGridCount);
    	int x = RNG.nextInt(xGridCount);
    	int y = RNG.nextInt(yGridCount);
    	Coordinate c = new Coordinate(x, y);
    	foodList.add(c);
    }

	
	/*
	 * Clear grid at start
	 */
	public void clearGrid() {
		for(int i = 0 ; i < xGridCount ; i++) {
			for(int j = 0 ; j < yGridCount ; j++) {
				grid[i][j] = EMPTY;
			}
		}
	}
	
	/*
	 * Update direction of snake but do not allow snake to turn back on itself
	 * Potential issue for snake to turn back on itself by doing quick 360
	 */
	public void updateDirection(int d) {
		if(d == direction) {
			; // do nothing
		} else {
			if((direction != UP) && (d == DOWN))
				direction = d;
			else if((direction != DOWN) && (d == UP))
				direction = d;
			else if((direction != LEFT) && (d == RIGHT))
				direction = d;
			else if((direction != RIGHT) && (d == LEFT))
				direction = d;	
		}
	}
	
	/*
	 * Draw Canvas
	 * (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		paint.setColor(Color.BLACK);

		for(int i = 0 ; i < xGridCount ; i++) {
			for(int j = 0 ; j < yGridCount ; j++) {
				if(grid[i][j] == EMPTY) {
					;
				} else {
				if(grid[i][j] == WALL)
					paint.setColor(Color.BLACK);
				else if(grid[i][j] == SNAKE)
					paint.setColor(Color.DKGRAY);
				else if(grid[i][j] == FOOD)
					paint.setColor(Color.GREEN);
				else
					paint.setColor(Color.RED);
				r.set(i*cellSize, j*cellSize, (i+1)*cellSize, (j+1)*cellSize);
				canvas.drawRect(r, paint);
				}
			}
		}		
		update();

	}
	
	/*
	 * Update by forcing a redraw
	 */
	private void update() {
		long now = System.currentTimeMillis();
		if(now - lastUpdate > delay) {
			lastUpdate = now;
			checkGameOver();
			clearGrid();
			updateSnake();
			updateFood();
			snakeToGrid();
			foodToGrid();
		}
	    invalidate();  // Force a re-draw


	}
	
	public void updateFood() {
		int size = foodList.size();
		while(size < 2) {
			addFood();
			size++;
		}
	}
	
	public void checkGameOver() {
		Coordinate oldHead = snakeList.get(0);
		Coordinate newHead = calcNewHead(oldHead);
		//check for wall or self collision
		if (grid[newHead.x][newHead.y] == SNAKE) {
	    	Log.d("Snake hit itself" ,"Snake hit itself");
			Intent intent = new Intent(activity, GameOver.class);
			intent.putExtra(Grid.HIGH_SCORE, score);
			activity.startActivity(intent);
		}
	}
	
	public Coordinate calcNewHead(Coordinate oldHead) {
		Coordinate newHead = new Coordinate(0,0);
		
		if(direction == UP) {
			newHead.x = oldHead.x;
			newHead.y = oldHead.y - 1;
		} else if (direction == DOWN) {
			newHead.x = oldHead.x;
			newHead.y = oldHead.y + 1;
		} else if (direction == LEFT) {
			newHead.x = oldHead.x - 1;
			newHead.y = oldHead.y;
		} else {
			newHead.x = oldHead.x + 1;
			newHead.y = oldHead.y;
		}
		
		if(newHead.x < 0)
			newHead.x = xGridCount - 1;
		else if (newHead.x >= xGridCount)
			newHead.x = 0;
		if(newHead.y < 0)
			newHead.y = yGridCount - 1;
		else if (newHead.y >= yGridCount)
			newHead.y = 0;
		
		return newHead;
	}
	
	public void updateSnake() {
		Coordinate oldHead = snakeList.get(0);
		Coordinate newHead = calcNewHead(oldHead);
		
		snakeList.add(0, newHead);
		boolean grow = true;
		// Check for apple collision
		for(int i = 0 ; i < foodList.size() ; i++) {
			Coordinate c = foodList.get(i);
			if(c.equals(newHead)) {
				grow = false;
				foodList.remove(i);
				addFood();
		    	score++;				
				if(delay > 100) {
					delay = delay - 10;
				}
				break;
			}
		}
		
		
		if(grow) {
			snakeList.remove(snakeList.size() -1);
		}
		
	}
	
	public void snakeToGrid() {
		for (Coordinate c : snakeList) {
			grid[c.x][c.y] = SNAKE;		
		}
	}
	
	public void foodToGrid() {
		for (Coordinate c : foodList) {
			grid[c.x][c.y] = FOOD;		
		}
	}
	
	
	/*
	 * Called on creation to get size of grid
	 * (non-Javadoc)
	 * @see android.view.View#onSizeChanged(int, int, int, int)
	 */
	@Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		xGridCount = (int) Math.floor(w / cellSize);
        yGridCount = (int) Math.floor(h / cellSize);


        grid = new int[xGridCount][yGridCount];
        clearGrid();
    }
	
	
	/*
	 * Used for snake co ords
	 */
    private class Coordinate {
        public int x;
        public int y;

        public Coordinate(int newX, int newY) {
            x = newX;
            y = newY;
        }

        public boolean equals(Coordinate other) {
            if (x == other.x && y == other.y) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Coordinate: [" + x + "," + y + "]";
        }
    }
}
