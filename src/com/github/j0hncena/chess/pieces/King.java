package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.github.j0hncena.chess.movement.FirstMoveRules;

public class King extends Piece implements FirstMoveRules{

	private int colorNumber;
	private boolean moved;
	
	private static final long serialVersionUID = 1L;

	public King(boolean isSuperior) {
		super(isSuperior);
		moved = false;
	}

	/* (non-Javadoc)
	 * @see com.github.chess.pieces.Piece#isValid(int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece [][]board, int fromX, int fromY, int toX, int toY) {
		return ((Math.abs(fromX - toX) <= 1 && Math.abs(fromY - toY) <= 1) && super.isValid(board, fromX, fromY, toX, toY));
	}

	/* (non-Javadoc)
	 * @see com.github.chess.pieces.Piece#paint(java.awt.Graphics, int, int, javax.swing.JPanel)
	 */
	@Override
	public void paint(Graphics g, int x, int y, JPanel panel) {
		super.drawPiece(g, x, y, PieceConstants.KING, getColorNumber(), panel);
	}

	@Override
	public Piece clone() {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.movement.FirstMoveRules#setMoved(boolean)
	 */
	@Override
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.movement.FirstMoveRules#getMoved()
	 */
	@Override
	public boolean getMoved() {
		return moved;
	}



	

}
