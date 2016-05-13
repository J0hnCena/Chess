package com.github.j0hncena.chess.movement;

import com.github.j0hncena.chess.pieces.Piece;

/**
 * Checks if the move is the same
 */
public class SameMoveChecker implements MovementChecker{


	/* (non-Javadoc)
	 * @see com.github.chess.movement.MovementChecker#isValid(com.github.chess.pieces.Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece[][] board, int fromX, int fromY, int toX, int toY) {
		if(fromX == toX && fromY == toY) {
			return false;
		}
		return true;
	}


}
