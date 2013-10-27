package com.thu9group.snake;




import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

public class GridView extends View {

    private RectF r;
    private Paint paint;
    public GameState gameState;
    
    private int xGridCount;
    private int yGridCount;
    private int xCellSize;
    private int yCellSize;
    private int xOffset;
    private int yOffset;
    
    private int xSnakeIndent;
    private int ySnakeIndent;
     
    private int w;
    private int h;
    
    private final static int GRASS = 0xAA5D8960;
    private static final int SNAKE_BODY = 0XFF255C3D;
    private static final int SNAKE_EDGE =  0xFF085027; 
    private static final int FLASH_COLOUR = Color.GRAY;
    
    private boolean flashGray = false;
    private Bitmap sizeIncrease;
    private Bitmap sizeDecrease;
    private Bitmap food;
    private Bitmap obstacle;
    private boolean bitmapsInitialized = false;

    /*
     * Constructor method
     */
	public GridView(Context context) {
		super(context);
		

		
		r = new RectF();
		paint = new Paint();
		setBackgroundColor(GRASS);
		
		
	}
    	
	private void initBitmaps() {
		Bitmap bitmap;
		
		bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.apple);
		food = Bitmap.createScaledBitmap(bitmap, xCellSize, yCellSize, true);
		
		bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.stone);
		obstacle = Bitmap.createScaledBitmap(bitmap, xCellSize, yCellSize, true);
		
		bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.powerup1);
		sizeIncrease = Bitmap.createScaledBitmap(bitmap, xCellSize, yCellSize, true);	
		
		bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.powerup2);
		sizeDecrease = Bitmap.createScaledBitmap(bitmap, xCellSize, yCellSize, true);			
	}
	/*
	 * Draw Canvas
	 * (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	
		if (bitmapsInitialized == false) {
			initBitmaps();
			bitmapsInitialized = true;
		}
		// Draw the walls
		paint.setColor(Color.BLACK);
		RectF wall = new RectF();
		
		wall.set(0, 0, w, yOffset);
		canvas.drawRect(wall, paint);
		wall.set(0, 0, xOffset, h);
		canvas.drawRect(wall, paint);
		wall.set(w-xOffset, 0, w, h);
		canvas.drawRect(wall,paint);
		wall.set(0, h-yOffset, w, h);
		canvas.drawRect(wall, paint);

		
		// Draw the snake
		ArrayList<Coordinate> snakeList = gameState.getSnakeList();
		
		drawSnakeHead(snakeList.get(0), canvas);
		for(int i = 1 ;  i < snakeList.size() - 1 ; i++) {
			drawSnakeBody(snakeList.get(i), canvas);
		}
		drawSnakeTail(snakeList.get(snakeList.size()-1), snakeList.get(snakeList.size()-2), canvas);
		
		if (flashGray == true) {
			flashGray = false;
		} else {
			if (gameState.sizeIncrease > 0) {
				flashGray = true;
			}
		}
		// Draw features
		ArrayList<Feature> featureList = gameState.getFeatureList();
		for(int i = 0 ; i < featureList.size() ; i++) {
			Feature f = featureList.get(i);
			Coordinate c = f.coordinate;
			
			if(f.type == Feature.FOOD) {
				paint.setColor(Color.GREEN);
			} else if (f.type == Feature.OBSTACLE) {
				paint.setColor(Color.RED);
			} else if (f.type == Feature.SIZE_DECREASE) {
				paint.setColor(Color.CYAN);
			} else if (f.type == Feature.SIZE_INCREASE) {
				paint.setColor(Color.YELLOW);
			} else {
				paint.setColor(Color.MAGENTA);
			}
			int left, top, right, bottom;
			left = c.x*xCellSize + xOffset;
			right = (c.x+1)*xCellSize + xOffset;
			
			top = c.y*yCellSize + yOffset;
			bottom = (c.y+1)*yCellSize + yOffset;
			
			r.set(left, top, right, bottom);

			
			if (f.type == Feature.FOOD) {
				canvas.drawBitmap(food, null, r, paint);		
			} else if (f.type == Feature.OBSTACLE) {
				canvas.drawBitmap(obstacle, null, r, paint);					
			} else if (f.type == Feature.SIZE_INCREASE) {
				canvas.drawBitmap(sizeIncrease, null, r, paint);					
			} else if (f.type == Feature.SIZE_DECREASE) {
				canvas.drawBitmap(sizeDecrease, null, r, paint);					
			}
			else {
				canvas.drawRect(r, paint);				
			}
			//BitmapFactory.decodeResource(., id)
		//	canvas.drawBitmap(bitmap, matrix, paint)

		}		
	}
	
	private void drawSnakeHead(Coordinate c, Canvas canvas) {
		int orientation = c.orientation;
		int boxleft, boxright, boxtop, boxbottom;
		
		boxleft = c.x*xCellSize + xOffset;
		boxright = (c.x+1)*xCellSize + xOffset;
		boxtop = c.y*yCellSize + yOffset;
		boxbottom = (c.y+1)*yCellSize + yOffset;
		
		r.set(boxleft + xSnakeIndent, boxtop+ySnakeIndent, boxright-xSnakeIndent, boxbottom-ySnakeIndent);
		paint.setColor(SNAKE_BODY);
		if (flashGray == true) {
			paint.setColor(FLASH_COLOUR);
		} 
		canvas.drawRect(r, paint);
		
		if(orientation == Coordinate.UP) {
			r.top = r.bottom;
			r.bottom = boxbottom;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxright-xSnakeIndent, boxtop+2*ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxleft + 2*xSnakeIndent, boxbottom);
			canvas.drawRect(r, paint);
			r.set(boxright - 2* xSnakeIndent, boxtop + ySnakeIndent, boxright - xSnakeIndent, boxbottom);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_BODY);
			
			paint.setColor(Color.WHITE);
			r.left = boxleft + 3*xSnakeIndent;
			r.right = r.left + (3*xSnakeIndent)/2;
			r.top = boxtop + 3*ySnakeIndent;
			r.bottom = r.top + 2*ySnakeIndent;
			canvas.drawRect(r, paint);
			
			r.right = boxright - 3*xSnakeIndent;
			r.left = r.right - (3*xSnakeIndent)/2;
			canvas.drawRect(r, paint);
		} else if (orientation == Coordinate.DOWN) {
			r.bottom = r.top;
			r.top = boxtop;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.set(boxleft + xSnakeIndent, boxbottom - 2*ySnakeIndent, boxright-xSnakeIndent, boxbottom - ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxleft + xSnakeIndent, boxtop, boxleft + 2*xSnakeIndent, boxbottom - ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxright - 2* xSnakeIndent, boxtop, boxright - xSnakeIndent, boxbottom - ySnakeIndent);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_BODY);
			
			paint.setColor(Color.WHITE);
			r.left = boxleft + 3*xSnakeIndent;
			r.right = r.left + (3*xSnakeIndent)/2;
			r.bottom = boxbottom - 3*ySnakeIndent;
			r.top = r.bottom - 2*ySnakeIndent;
			canvas.drawRect(r, paint);
			
			r.right = boxright - 3*xSnakeIndent;
			r.left = r.right - (3*xSnakeIndent)/2;
			canvas.drawRect(r, paint);
		} else if (orientation == Coordinate.LEFT) {
			r.left = r.right;
			r.right = boxright;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.left = boxleft+xSnakeIndent;
			r.top = boxtop + ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			r.top = boxbottom - 2*ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			r.top = boxtop + ySnakeIndent;
			r.right = r.left + xSnakeIndent;
			canvas.drawRect(r, paint);
			
			paint.setColor(Color.WHITE);
			r.top = boxtop + 3*ySnakeIndent;
			r.bottom = r.top + (3*ySnakeIndent)/2;
			r.left = boxleft + 3*xSnakeIndent;
			r.right = r.left + 2*xSnakeIndent;
			canvas.drawRect(r, paint);
			
			r.bottom = boxbottom - 3*ySnakeIndent;
			r.top = r.bottom - (3*ySnakeIndent)/2;
			canvas.drawRect(r, paint);
			
		} else {
			r.right = r.left;
			r.left = boxleft;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.left = boxright - 2*xSnakeIndent;
			r.right = r.left + xSnakeIndent;
			r.top = boxtop + ySnakeIndent;
			r.bottom = boxbottom - ySnakeIndent;
			canvas.drawRect(r, paint);
			r.left = boxleft;
			r.right = boxright - ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			r.top = boxbottom - 2*ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			
			paint.setColor(Color.WHITE);
			r.top = boxtop + 3*ySnakeIndent;
			r.bottom = r.top + (3*ySnakeIndent)/2;
			r.right = boxright - 3*xSnakeIndent;
			r.left = r.right - 2*xSnakeIndent;
			canvas.drawRect(r, paint);
			
			r.bottom = boxbottom - 3*ySnakeIndent;
			r.top = r.bottom - (3*ySnakeIndent)/2;
			canvas.drawRect(r, paint);
			
			
		}

	}
	
	private void drawSnakeTail(Coordinate tail, Coordinate oldtail, Canvas canvas) {
		int orientation = tail.orientation;
		int boxleft, boxright, boxtop, boxbottom;
		
		
		if(oldtail.x > tail.x)
			orientation = Coordinate.LEFT;
		else if (oldtail.x < tail.x)
			orientation = Coordinate.RIGHT;
		else if (oldtail.y < tail.y) 
			orientation = Coordinate.DOWN;
		else
			orientation = Coordinate.UP;
	
		
		boxleft = tail.x*xCellSize + xOffset;
		boxright = (tail.x+1)*xCellSize + xOffset;
		boxtop = tail.y*yCellSize + yOffset;
		boxbottom = (tail.y+1)*yCellSize + yOffset;
		
		r.set(boxleft + xSnakeIndent, boxtop+ySnakeIndent, boxright-xSnakeIndent, boxbottom-ySnakeIndent);
		paint.setColor(SNAKE_BODY);
		canvas.drawRect(r, paint);
		
		if(orientation == Coordinate.UP) {
			r.top = r.bottom;
			r.bottom = boxbottom;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxright-xSnakeIndent, boxtop+2*ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxleft + 2*xSnakeIndent, boxbottom);
			canvas.drawRect(r, paint);
			r.set(boxright - 2* xSnakeIndent, boxtop + ySnakeIndent, boxright - xSnakeIndent, boxbottom);
			canvas.drawRect(r, paint);

			
		} else if (orientation == Coordinate.DOWN) {
			r.bottom = r.top;
			r.top = boxtop;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.set(boxleft + xSnakeIndent, boxbottom - 2*ySnakeIndent, boxright-xSnakeIndent, boxbottom - ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxleft + xSnakeIndent, boxtop, boxleft + 2*xSnakeIndent, boxbottom - ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxright - 2* xSnakeIndent, boxtop, boxright - xSnakeIndent, boxbottom - ySnakeIndent);
			canvas.drawRect(r, paint);

		} else if (orientation == Coordinate.LEFT) {
			r.left = r.right;
			r.right = boxright;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.left = boxleft+xSnakeIndent;
			r.top = boxtop + ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			r.top = boxbottom - 2*ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			r.top = boxtop + ySnakeIndent;
			r.right = r.left + xSnakeIndent;
			canvas.drawRect(r, paint);
			
			
		} else {
			r.right = r.left;
			r.left = boxleft;
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.left = boxright - 2*xSnakeIndent;
			r.right = r.left + xSnakeIndent;
			r.top = boxtop + ySnakeIndent;
			r.bottom = boxbottom - ySnakeIndent;
			canvas.drawRect(r, paint);
			r.left = boxleft;
			r.right = boxright - ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			r.top = boxbottom - 2*ySnakeIndent;
			r.bottom = r.top + ySnakeIndent;
			canvas.drawRect(r, paint);
			
		}
		
		
	}
	
	private void drawSnakeBody(Coordinate c, Canvas canvas) {
		int orientation = c.orientation;
		paint.setColor(SNAKE_BODY);
		
		int boxleft, boxright, boxtop, boxbottom;
		
		boxleft = c.x*xCellSize+xOffset;
		boxright = (c.x+1)*xCellSize+xOffset;
		boxtop = c.y*yCellSize+yOffset;
		boxbottom = (c.y+1)*yCellSize+yOffset;
		
		if (orientation == Coordinate.LEFT || orientation == Coordinate.RIGHT) {
			r.set(boxleft, boxtop+ySnakeIndent, boxright, boxbottom-ySnakeIndent);
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.set(boxleft, boxtop+ySnakeIndent, boxright, boxtop+2*ySnakeIndent);
			canvas.drawRect(r, paint);
			r.set(boxleft, boxbottom-2*ySnakeIndent, boxright, boxbottom-ySnakeIndent);
			canvas.drawRect(r, paint);
		} else if (orientation == Coordinate.UP || orientation == Coordinate.DOWN) {
			r.set(boxleft+xSnakeIndent, boxtop, boxright-xSnakeIndent, boxbottom);
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			paint.setColor(SNAKE_EDGE);
			r.set(boxleft+xSnakeIndent, boxtop, boxleft + 2*xSnakeIndent, boxbottom);
			canvas.drawRect(r, paint);
			r.set(boxright-2*xSnakeIndent, boxtop, boxright - xSnakeIndent, boxbottom);
			canvas.drawRect(r, paint);
		} else if (orientation >= Coordinate.CORNER1 && orientation <= Coordinate.CORNER4) {
			
			r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxright - xSnakeIndent, boxbottom - ySnakeIndent);
			paint.setColor(SNAKE_BODY);
			canvas.drawRect(r, paint);
			if(orientation == Coordinate.CORNER1) {
				paint.setColor(SNAKE_EDGE);
				r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxright, boxtop + 2*ySnakeIndent);
				canvas.drawRect(r, paint);
				r.set(boxleft + xSnakeIndent, boxtop + ySnakeIndent, boxleft + 2*xSnakeIndent, boxbottom);
				canvas.drawRect(r,  paint);
				r.set(boxright-2*xSnakeIndent, boxbottom-2*ySnakeIndent, boxright, boxbottom-ySnakeIndent);
				canvas.drawRect(r, paint);
				r.set(boxright-2*xSnakeIndent, boxbottom-ySnakeIndent, boxright-xSnakeIndent, boxbottom);
				canvas.drawRect(r, paint);
			} else if (orientation == Coordinate.CORNER2) {
				paint.setColor(SNAKE_EDGE);
				r.set(boxleft, boxtop+ySnakeIndent, boxright - xSnakeIndent, boxtop+2*ySnakeIndent);
				canvas.drawRect(r, paint);
				r.set(boxleft, boxbottom - 2*ySnakeIndent, boxleft+2*xSnakeIndent, boxbottom - ySnakeIndent);
				canvas.drawRect(r,paint);
				r.set(boxright-2*xSnakeIndent, boxtop+ySnakeIndent, boxright-xSnakeIndent, boxbottom);
				canvas.drawRect(r,paint);
				r.set(boxleft+xSnakeIndent, boxbottom-ySnakeIndent, boxleft+2*xSnakeIndent, boxbottom);
				canvas.drawRect(r,paint);				
			} else if (orientation == Coordinate.CORNER3) {
				paint.setColor(SNAKE_EDGE);
				r.set(boxright-2*xSnakeIndent, boxtop+ySnakeIndent, boxright, boxtop+2*ySnakeIndent);
				canvas.drawRect(r, paint);
				r.set(boxleft + xSnakeIndent, boxbottom - 2*ySnakeIndent, boxright, boxbottom - ySnakeIndent);
				canvas.drawRect(r,paint);
				r.set(boxright-2*xSnakeIndent, boxtop, boxright-xSnakeIndent, boxtop + 2*ySnakeIndent);
				canvas.drawRect(r,paint);
				r.set(boxleft+xSnakeIndent, boxtop, boxleft+2*xSnakeIndent, boxbottom - ySnakeIndent);
				canvas.drawRect(r,paint);
			} else {
				paint.setColor(SNAKE_EDGE);
				r.set(boxright-2*xSnakeIndent, boxtop, boxright - xSnakeIndent, boxbottom - ySnakeIndent);
				canvas.drawRect(r, paint);
				r.set(boxleft, boxbottom - 2*ySnakeIndent, boxright - xSnakeIndent, boxbottom - ySnakeIndent);
				canvas.drawRect(r,paint);
				r.set(boxleft, boxtop + ySnakeIndent, boxleft + 2*xSnakeIndent, boxtop + 2*ySnakeIndent);
				canvas.drawRect(r,paint);
				r.set(boxleft+xSnakeIndent, boxtop, boxleft+2*xSnakeIndent, boxtop + ySnakeIndent);
				canvas.drawRect(r,paint);
			}
			if (orientation == Coordinate.CORNER1 || orientation == Coordinate.CORNER2) {
				paint.setColor(SNAKE_BODY);
				r.set(boxleft + 2*xSnakeIndent, boxbottom - ySnakeIndent, boxright-2*xSnakeIndent, boxbottom);
				canvas.drawRect(r, paint);
			}
			if (orientation == Coordinate.CORNER3 || orientation == Coordinate.CORNER4) {
				paint.setColor(SNAKE_BODY);
				r.set(boxleft + 2*xSnakeIndent, boxtop, boxright-2*xSnakeIndent, boxtop+ySnakeIndent);
				canvas.drawRect(r, paint);
			}
			if (orientation == Coordinate.CORNER1 || orientation == Coordinate.CORNER3) {
				paint.setColor(SNAKE_BODY);
				r.set(boxright-xSnakeIndent, boxtop+2*ySnakeIndent, boxright, boxbottom - 2*ySnakeIndent);
				canvas.drawRect(r, paint);
			}
			if (orientation == Coordinate.CORNER2 || orientation == Coordinate.CORNER4) {
				paint.setColor(SNAKE_BODY);
				r.set(boxleft, boxtop+2*ySnakeIndent, boxleft+xSnakeIndent, boxbottom - 2*ySnakeIndent);
				canvas.drawRect(r, paint);
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
		
		this.w = w;
		this.h =h;
				
		xGridCount = GameState.X_COUNT;
		yGridCount = GameState.Y_COUNT;
        xCellSize = w / xGridCount;
        int temp = xCellSize / 10;
        xSnakeIndent = 1 * temp;
        yCellSize = h / yGridCount;
        temp = yCellSize / 10;
        ySnakeIndent = 1 * temp;
        xOffset = (w - xGridCount*xCellSize) / 2;
        yOffset = (h - yGridCount*yCellSize) / 2;	
        

    }
	
	

}
