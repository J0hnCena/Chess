package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

public class Knight extends Piece{

	private static final long serialVersionUID = -8110622428331271286L;

	public Knight(boolean isSuperior) {
		super(isSuperior);
	}

	@Override
	public void paint(Graphics g, int x, int y, JPanel panel) {
		super.drawPiece(g, x, y, PieceConstants.KNIGHT, getColorNumber(), panel);
	}

	/* (non-Javadoc)
	 * @see com.github.chess.pieces.Piece#isValid(com.github.chess.pieces.Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece[][] board, int fromX, int fromY, int toX, int toY) {
		return super.isValid(board, fromX, fromY, toX, toY) && LMovement(fromX, fromY, toX, toY);
	}
	
	public boolean LMovement(int fromX, int fromY, int toX, int toY) {
		int xDiff = Math.abs(fromX - toX);
		int yDiff = Math.abs(fromY - toY);
		return ((xDiff == 1) && (yDiff == 2)) || ((xDiff == 2) && (yDiff == 1));
	}
	@Override
	public Piece clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
