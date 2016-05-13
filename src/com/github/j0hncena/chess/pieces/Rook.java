package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Rook extends Piece{

	public Rook(boolean isSuperior) {
		super(isSuperior);
	}

	@Override
	public void paint(Graphics g, int x, int y, JPanel panel) {
		super.drawPiece(g, x, y, PieceConstants.ROOK, getColorNumber(), panel);
	}

	/* (non-Javadoc)
	 * @see com.github.chess.pieces.Piece#isValid(com.github.chess.pieces.Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece[][] board, int fromX, int fromY, int toX, int toY) {
		return super.isValid(board, fromX, fromY, toX, toY) && checkStraightMovement(fromX, fromY, toX, toY);
	}

	public boolean checkStraightMovement(int fromX, int fromY, int toX, int toY) {
		if(fromX == toX || fromY == toY) {
			return true;
		}
		return false;
	}
	
	@Override
	public Piece clone() {
		return null;
	}


}
