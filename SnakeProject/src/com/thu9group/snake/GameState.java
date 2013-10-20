package com.thu9group.snake;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;


public class GameState {
	//constants
	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int SNAKE = 2;
	public static final int FOOD = 3;
	public static final int OBSTACLE = 4;
	public static final int SIZE_INCREASE = 5;
	public static final int SIZE_DECREASE = 6;
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	
	//configuration options
	public static final int X_COUNT = 14;
	public static final int Y_COUNT = 22;
	
	//the percentage chance of spawning an obstacle every cycle
	public static final int OBSTACLE_SPAWN_CHANCE_EASY = 0;
	public static final int OBSTACLE_SPAWN_CHANCE_MEDIUM = 5; 
	public static final int OBSTACLE_SPAWN_CHANCE_HARD = 12;
	
	public static final int SIZE_INCREASE_SPAWN_CHANCE = 5;
	public static final int SIZE_DECREASE_SPAWN_CHANCE = 5;	
	
	public static final int FOOD_LIFETIME = 100;
	public static final int OBSTACLE_LIFETIME = 50;
	
	//other variables
    public long delay;
    public static int score;
    private static final Random RNG = new Random();
    private int direction = UP;
    private int difficulty = 2;
	private boolean wallsEnabled = true;
    private int[][] grid;
    public Grid activity;
    
    private ArrayList<Coordinate> snakeList = new ArrayList<Coordinate>();
    private ArrayList<Feature> featureList = new ArrayList<Feature>();
	private boolean gameOver = false;
	private int turn = 0;
    
    public GameState(Grid activity) {
    	this.activity = activity;
    	grid = new int[X_COUNT][Y_COUNT];

        // For now we're just going to load up a short default eastbound snake
        // that's just turned north
        snakeList.add(new Coordinate(7, 7));
        snakeList.add(new Coordinate(6, 7));
        snakeList.add(new Coordinate(5, 7));
        snakeList.add(new Coordinate(4, 7));
        snakeList.add(new Coordinate(3, 7));
        snakeList.add(new Coordinate(2, 7));
        direction = UP;
        difficulty = getLevel();
        if (difficulty == EASY) {
        	wallsEnabled = false;
        	delay = 300;
        } else if (difficulty == MEDIUM){
        	wallsEnabled = true;
        	delay = 250;
        } else {
        	wallsEnabled = true;
        	delay = 200;
        }
        //create walls
        if (wallsEnabled) {
        	for (int i =0; i<X_COUNT; i++) {
        		featureList.add(new Feature(new Coordinate(i, 0), WALL));
        		featureList.add(new Feature(new Coordinate(i, Y_COUNT - 1), WALL));   		
        	}
        	for (int i =0; i<Y_COUNT; i++) {
        		featureList.add(new Feature(new Coordinate(0, i), WALL));
        		featureList.add(new Feature(new Coordinate(X_COUNT -1, i), WALL));   	
        	} 	
        }
        
        System.err.println("Delay: "+ delay	);
        score = 0;
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
    
    
  
    private int getLevel(){
		String output=" ";
		int level = MEDIUM;
		try{
			File file = activity.getFileStreamPath("level.txt");
			if (file.exists()){
				FileInputStream fis = new FileInputStream(file);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				String aLine = null;
				output = " ";
				while ((aLine=br.readLine())!=null){
					output+=aLine;
					
				}
				System.err.println("output"+output);
				br.close();
			}
		}  catch (IOException e){
				e.printStackTrace();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		if (output.contains("3")){
			level = HARD;
		}else if (output.contains("1")){
			level = EASY;
		}
		return level;
	}
   

	
	/**
	 * this is called whenever it is time for the snake to move one position forward
	 */
	public void cycle() {
		turn++;
		Coordinate oldHead = snakeList.get(0);
		Coordinate newHead = calcNewHead(oldHead);
		//check for wall or self or obstacle collision
		if ((grid[newHead.x][newHead.y] == SNAKE) || (grid[newHead.x][newHead.y] == WALL)
				|| (grid[newHead.x][newHead.y]== OBSTACLE)) {
			gameOver = true;
			return;
		}
		updateSnake();
		updateFeatures();
		clearGrid();
		generateGrid();	
	}
	
	private void updateFeatures() {
		int size = 0;
		for (Feature f : featureList) {
			if (f.type == FOOD) size++;
		}
		while(size < 2) {
	    	Coordinate c = generateRandomCoordinate();
	    	featureList.add(new Feature(c, FOOD, FOOD_LIFETIME));
			size++;
		}
		
		//delete any features that have expired
		ArrayList<Feature> toRemove = new ArrayList<Feature>();
		for (Feature f : featureList) {
			if (f.cyclesRemaining >= 0) {
				f.cyclesRemaining--;
				if (f.cyclesRemaining == 0) {
					toRemove.add(f);
				}
			}
		}
		for (Feature f : toRemove) {
			featureList.remove(f);
		}
		
		//generate random obstacle
		int rnd = RNG.nextInt(99) + 1;
		int spawnChance = OBSTACLE_SPAWN_CHANCE_EASY;
		if (difficulty == MEDIUM) {
			spawnChance = OBSTACLE_SPAWN_CHANCE_MEDIUM;
		} else if (difficulty == HARD) {
			spawnChance = OBSTACLE_SPAWN_CHANCE_HARD;
		}
		
		if (rnd <= spawnChance) {
	    	Coordinate c = generateRandomCoordinate();
	    	featureList.add(new Feature(c, OBSTACLE, OBSTACLE_LIFETIME));
		}
		
	}
	
	/**
	 * Generates a random coordinate that isn't occupied by a snake or a feature.
	 * The random coordinate also cannot be within 5 squares or less of the snake head.
	 * @return
	 */
	private Coordinate generateRandomCoordinate() {
		boolean illegal = true;
		Coordinate c=null;
		while (illegal == true) {
			int x = RNG.nextInt(X_COUNT);
			int y = RNG.nextInt(Y_COUNT);
			c = new Coordinate(x,y);
			illegal = false;
			for (Feature f : featureList) {
				if ((f.coordinate.x == x) && (f.coordinate.y == y)) {
					illegal = true;
				}
			}
			for (Coordinate c2 : snakeList) {
				if ((c2.x == x) && (c2.y == y)) {
					illegal = true;
				}
			}
			Coordinate snakeHead = snakeList.get(0);
			for (int i = 0; i<10; i++) {
				for (int j = 0; j<10; j++) {
					int x2 = snakeHead.x + i - 5;
					int y2 = snakeHead.y + j - 5;
					if (x2 < 0) {
						x2 = X_COUNT + x2;
					}
					if (y2 < 0) {
						y2 = Y_COUNT + y2;
					}
					
					if ((x == x2) && (y == y2)) {
						illegal = true;
					}
				}
			}		
			
		}
		return c;
	}
	
	public boolean isGameOver() {
		return gameOver;
	}



	private Coordinate calcNewHead(Coordinate oldHead) {
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
			newHead.x = X_COUNT - 1;
		else if (newHead.x >= X_COUNT)
			newHead.x = 0;
		if(newHead.y < 0)
			newHead.y = Y_COUNT - 1;
		else if (newHead.y >= Y_COUNT)
			newHead.y = 0;
		
		return newHead;
	}
	
	private void updateSnake() {
		Coordinate oldHead = snakeList.get(0);
		Coordinate newHead = calcNewHead(oldHead);
		
		snakeList.add(0, newHead);
		boolean grow = true;
		// Check for apple collision
		for(int i = 0 ; i < featureList.size() ; i++) {
			Feature f = featureList.get(i);
			if ((f.coordinate.equals(newHead)) && (f.type == FOOD)) {
				grow = false;
				featureList.remove(i);
		    	score++;				
				if((delay > 150) && (difficulty != EASY)) {
					delay = delay - 10;
				}
				break;
			}
		}
		
		
		if(grow) {
			snakeList.remove(snakeList.size() -1);
		}
		
	}
	
	/**
	 * Clear grid at start
	 */
	private void clearGrid() {
		for(int i = 0 ; i < X_COUNT ; i++) {
			for(int j = 0 ; j < Y_COUNT ; j++) {
				grid[i][j] = EMPTY;
			}
		}
	}
	
	private void generateGrid() {
		for (Feature f : featureList) {
			grid[f.coordinate.x][f.coordinate.y] = f.type;	
		}
		for (Coordinate c : snakeList) {
			grid[c.x][c.y] = SNAKE;		
		}
	}
	
	public int getCell(int x, int y) {
		return grid[x][y];
	}
	
	
	
	private class Feature {
		public Coordinate coordinate;
		public int type;
		//cycles remaining is how long left the feature has until it dies
		//if it is set to -1, then it will last forever
		public int cyclesRemaining;
		public Feature (Coordinate co, int type) {
			this.coordinate = co;
			this.type = type;
			cyclesRemaining = -1;
		}
		public Feature (Coordinate co, int type, int cycles) {
			this.coordinate = co;
			this.type = type;
			cyclesRemaining = cycles;
		}	
	}
	
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
