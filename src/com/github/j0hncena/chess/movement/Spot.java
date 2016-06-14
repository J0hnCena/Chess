package com.github.j0hncena.chess.movement;

public class Spot {
	
	private int x, y;
	
	public Spot(int x, int y) {
		if(x > 7 || x < 0 || y > 7 || y < 0)
			throw new IndexOutOfBoundsException("The spot being created is out of bounds");
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

}
