package com.github.chess.movement;

public interface MovementBehavior {
	public boolean isValid(int fromX, int fromY, int toX, int toY);
}
