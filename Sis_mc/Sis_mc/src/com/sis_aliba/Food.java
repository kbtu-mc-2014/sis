package com.sis_aliba;

import java.util.Random;

public class Food {
	int myXPos = 0;
	int myYPos = 0;
	Random rand = new Random();
	private int widthPlaces ;
	private int higthPlaces;
	
	public Food(int widthPlaces, int higthPlaces) {
		this.widthPlaces = widthPlaces;
		this.higthPlaces = higthPlaces;
		replaceMe();
	}
	
	public void replaceMe(){
		myXPos = rand.nextInt(widthPlaces);
		myYPos = rand.nextInt(higthPlaces);
		
	}
	
	
}
