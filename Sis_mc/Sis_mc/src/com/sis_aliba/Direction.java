package com.sis_aliba;

public enum Direction {
	LEFT(10), 
	RIGHT(11), 
	UP(1), 
	DOWN(0);
	
	public int direct;
	private Direction(int direct){
	this.direct = direct; 
	}
}
