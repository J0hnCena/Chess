package com.github.j0hncena.chess.movement;

import com.github.j0hncena.chess.pieces.Piece;

/**
 * Checks to see if the move is not out of bounds
 */
public class BoardBoundaryChecker implements MovementChecker {


	/* (non-Javadoc)
	 * @see com.github.chess.movement.MovementChecker#isValid(com.github.chess.pieces.Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece[][] board, int fromX, int fromY, int toX, int toY) {
		if(toX > 7 || toY > 7) {
			return false;
		}
		return true;
	}

}
