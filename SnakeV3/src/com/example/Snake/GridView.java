package com.example.Snake;


import java.util.ArrayList;
import java.util.Random;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.View;

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
    
    private static final Random RNG = new Random();
   
    
    private RectF r;
    private Paint paint;
    private int direction = UP;
    
    public int[][] grid;
    
    private ArrayList<Coordinate> snakeList = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> foodList = new ArrayList<Coordinate>();
	
    
    /*
     * Constructor method
     */
	public GridView(Context context) {
		super(context);
		cellSize = 50;
		r = new RectF();
		paint = new Paint();
		setBackgroundColor(Color.LTGRAY);
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
        delay = 300;

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
			if(!(direction == UP && d == DOWN))
				direction = d;
			else if(!(direction == DOWN && d == UP))
				direction = d;
			else if(!(direction == LEFT && d == RIGHT))
				direction = d;
			else if(!(direction == RIGHT && d == LEFT))
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
					paint.setColor(Color.CYAN);
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
	
	public void updateSnake() {
		Coordinate oldHead = snakeList.get(0);
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
		
		snakeList.add(0, newHead);
		
		boolean grow = true;
		// Check for apple collision
		for(int i = 0 ; i < foodList.size() ; i++) {
			Coordinate c = foodList.get(i);
			if(c.equals(newHead)) {
				grow = false;
				foodList.remove(i);
				addFood();
				if(delay > 100) {
					delay = delay - 10;
				}
				break;
			}
		}
		if(grow)
			snakeList.remove(snakeList.size() -1);
	}
	
	public void snakeToGrid() {
		int size = snakeList.size();
		for(int i = 0; i < size ; i++) {
			Coordinate c = snakeList.get(i);
			grid[c.x][c.y] = SNAKE;
		}
	}
	
	public void foodToGrid() {
		int size = foodList.size();
		for(int i = 0; i < size ; i++) {
			Coordinate c = foodList.get(i);
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
