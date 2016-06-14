package com.github.j0hncena.chess.movement;

import com.github.j0hncena.chess.pieces.Piece;

public class SameColorPieceCheck implements MovementChecker{

	/* (non-Javadoc)
	 * @see com.github.chess.movement.MovementChecker#isValid(com.github.chess.pieces.Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece[][] board, int fromX, int fromY, int toX, int toY) {
		if(board[toX][toY] != null && board[fromX][fromY] != null) {
			if(board[toX][toY].getWhiteness() == board[fromX][fromY].getWhiteness())
				return false;
		}
		return true;
	}

}
