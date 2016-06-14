package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Bishop extends Piece{

	private static final long serialVersionUID = -2320117955315970030L;

	public Bishop(boolean isSuperior) {
		super(isSuperior);
	}

	/* (non-Javadoc)
	 * @see com.github.chess.pieces.Piece#isValid(int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece [][]board, int fromX, int fromY, int toX, int toY) {
		return checkDiagonalMovement(fromX, fromY, toX, toY) && super.isValid(board, fromX, fromY, toX, toY);
	}
	
	public boolean checkDiagonalMovement(int fromX, int fromY, int toX, int toY) {
		return Math.abs(toX - fromX) == Math.abs(toY - fromY); 
	}
	@Override
	public void paint(Graphics g, int x, int y, JPanel panel) {
		super.drawPiece(g, x, y, PieceConstants.BISHOP, getColorNumber(), panel);
	}

	@Override
	public Piece clone() {
		return null;
	}


}
