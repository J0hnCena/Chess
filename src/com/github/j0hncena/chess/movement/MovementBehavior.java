package com.github.j0hncena.chess.movement;

public interface MovementBehavior {
	public boolean isValid(int fromX, int fromY, int toX, int toY);
}
