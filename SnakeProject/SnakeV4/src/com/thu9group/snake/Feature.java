package com.thu9group.snake;

public class Feature {

	public static final int FOOD = 3;
	public static final int OBSTACLE = 4;
	public static final int SIZE_INCREASE = 5;
	public static final int SIZE_DECREASE = 6;
	
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