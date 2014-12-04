package com.sis_aliba;

import com.sis_aliba.SnakePosition;

public class SnakePosition {
	int x = 0;
	int y = 0;
	SnakePosition frontSnakePos = null;
	
	public SnakePosition(int x, int y, SnakePosition frontSnakePos){
		this.x = x;
		this.y = y;
		this.frontSnakePos = frontSnakePos;
	}
	
	public boolean equals(SnakePosition tsPos){
		return this.x == tsPos.x && this.y == tsPos.y;
	}
}
