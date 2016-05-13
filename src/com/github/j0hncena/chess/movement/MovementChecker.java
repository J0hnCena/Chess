package com.github.j0hncena.chess.movement;

import com.github.j0hncena.chess.pieces.Piece;

/**
 * A class should inherit this if it is going to be checking if a move is valid
 */
public interface MovementChecker {
	
	/**
	 * @param board the chessboard to check if the move is valid
	 * @param fromX the previous X position
	 * @param fromY the previous Y position
	 * @param toX the X position to move to
	 * @param toY the Y position to move to
	 * @return true if the move is valid
	 */
	public boolean isValid(Piece [][] board, int fromX, int fromY, int toX, int toY);
}
