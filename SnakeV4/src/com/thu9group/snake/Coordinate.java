package com.thu9group.snake;


public class Coordinate {
    public int x;
    public int y;
    public int orientation = 0;
    
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int CORNER1 = 4;
    public static final int CORNER2 = 5;
    public static final int CORNER3 = 6;
    public static final int CORNER4 = 7;

    public Coordinate(int newX, int newY) {
        x = newX;
        y = newY;
    }
    
    public Coordinate(int newX, int newY, int newOrientation) {
        x = newX;
        y = newY;
        orientation = newOrientation;
    }
    
    public boolean equals(Coordinate other) {
        if (x == other.x && y == other.y) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Coordinate))return false;
        Coordinate c = (Coordinate)other;
        
        return (this.x == c.x && this.y == c.y);
    }

    @Override
    public String toString() {
        return "Coordinate: [" + x + "," + y + "]";
    }
}