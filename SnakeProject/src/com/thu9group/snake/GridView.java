package com.thu9group.snake;




import java.util.ArrayList;
import android.content.Context;
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
    

    /*
     * Constructor method
     */
	public GridView(Context context) {
		super(context);
		

		
		r = new RectF();
		paint = new Paint();
		setBackgroundColor(0xFF5D8960);
	}
    	
	/*
	 * Draw Canvas
	 * (non-Javadoc)
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
	
		// Draw the walls
		paint.setColor(Color.BLACK);
		RectF wall = new RectF();
		
		wall.set(0, 0, xOffset, h);
		canvas.drawRect(wall, paint);
		wall.set(0, yOffset, w, 0);
		canvas.drawRect(wall, paint);
		wall.set(w-xOffset, 0, w, h);
		canvas.drawRect(wall,paint);
		wall.set(0, h-yOffset, w, h);
		canvas.drawRect(wall, paint);

		
		// Draw the snake
		ArrayList<Coordinate> snakeList = gameState.getSnakeList();
		

		for(Coordinate c : snakeList) {
			drawSnakeBody(c, canvas);
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
			canvas.drawRect(r, paint);

		}		
	}
	
	
	private void drawSnakeBody(Coordinate c, Canvas canvas) {
		int orientation = c.orientation;
		paint.setColor(Color.DKGRAY);
		int bottom, top, left, right;
		
		if (orientation == Coordinate.LEFT || orientation == Coordinate.RIGHT) {
			left = c.x * xCellSize + xOffset;
			right = (c.x+1)*xCellSize + xOffset;
			top = c.y*yCellSize+ySnakeIndent + yOffset;
			bottom = (c.y+1)*yCellSize-ySnakeIndent + yOffset;
			r.set(left, top, right, bottom);
			canvas.drawRect(r, paint);
		} else if (orientation == Coordinate.UP || orientation == Coordinate.DOWN) {
			left = c.x * xCellSize+xSnakeIndent + xOffset;
			right = (c.x+1)*xCellSize-xSnakeIndent + xOffset;
			bottom = c.y*yCellSize + yOffset;
			top = (c.y+1)*yCellSize + yOffset;
			r.set(left, bottom, right, top);
			canvas.drawRect(r, paint);
		} else if (orientation >= Coordinate.CORNER1 && orientation <= Coordinate.CORNER4) {
			left = c.x * xCellSize+xSnakeIndent + xOffset;
			right = (c.x+1)*xCellSize-xSnakeIndent + xOffset;
			bottom = c.y*yCellSize+ySnakeIndent + yOffset;
			top = (c.y+1)*yCellSize-ySnakeIndent + yOffset;
			r.set(left, bottom, right, top);
			canvas.drawRect(r, paint);
			if (orientation == Coordinate.CORNER1 || orientation == Coordinate.CORNER2) {
				left = c.x * xCellSize+xSnakeIndent+ xOffset;
				right = (c.x+1)*xCellSize-xSnakeIndent+ xOffset;
				bottom = (c.y+1)*yCellSize-ySnakeIndent + yOffset;
				top = (c.y+1)*yCellSize + yOffset;
				r.set(left, bottom, right, top);
				canvas.drawRect(r, paint);
			}
			if (orientation == Coordinate.CORNER3 || orientation == Coordinate.CORNER4) {
				left = c.x * xCellSize+xSnakeIndent+ xOffset;
				right = (c.x+1)*xCellSize-xSnakeIndent+ xOffset;
				bottom = (c.y)*yCellSize + yOffset;
				top = (c.y)*yCellSize+ySnakeIndent + yOffset;
				r.set(left, bottom, right, top);
				canvas.drawRect(r, paint);
			}
			if (orientation == Coordinate.CORNER1 || orientation == Coordinate.CORNER3) {
				left = (c.x+1)*xCellSize-xSnakeIndent+ xOffset;
				right = (c.x+1)*xCellSize+ xOffset;
				bottom = c.y*yCellSize+ySnakeIndent + yOffset;
				top = (c.y+1)*yCellSize-ySnakeIndent + yOffset;
				r.set(left, bottom, right, top);
				canvas.drawRect(r, paint);
			}
			if (orientation == Coordinate.CORNER2 || orientation == Coordinate.CORNER4) {
				left = c.x*xCellSize+ xOffset;
				right = c.x*xCellSize+xSnakeIndent+ xOffset;
				bottom = c.y*yCellSize+ySnakeIndent + yOffset;
				top = (c.y+1)*yCellSize-ySnakeIndent + yOffset;
				r.set(left, bottom, right, top);
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
