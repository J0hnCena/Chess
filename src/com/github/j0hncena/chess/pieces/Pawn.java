package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.github.j0hncena.chess.movement.FirstMoveRules;

public class Pawn extends Piece implements FirstMoveRules{

	//rip Physics C:Electricity and Magnetism AP
	private static final long serialVersionUID = -7279947316496904579L;
	private boolean moved;
	private boolean en_passantable;

	public Pawn(boolean isSuperior) {
		super(isSuperior);
		moved = false;
		en_passantable = false;
	}

	@Override
	public void paint(Graphics g, int x, int y, JPanel panel) {
		super.drawPiece(g, x, y, PieceConstants.PAWN, getColorNumber(), panel);
	}

	public boolean isEnPassantable() {
		return this.en_passantable;
	}

	public void setEnPassantable(boolean isEnPassantable) {
		this.en_passantable = isEnPassantable;
	}

	/* (non-Javadoc)
	 * @see com.github.chess.pieces.Piece#isValid(com.github.chess.pieces.Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece[][] board, int fromX, int fromY, int toX, int toY) {

		return super.isValid(board, fromX, fromY, toX, toY) && checkPawnMove(board, fromX, fromY, toX, toY);
	}

	public boolean checkPawnCapture(Piece[][]board, int fromX, int fromY, int toX, int toY) {
			if(board[toX][toY] != null) {
				return true;
			}

		return false;
	}

	public boolean checkPawnMove(Piece [][] board, int fromX, int fromY, int toX, int toY) {
		if(board[toX][toY] != null) {
			return checkDiagonalMovement(fromX, fromY, toX, toY);
		}

		if(checkDiagonalMovement(fromX, fromY, toX, toY)) {
			return checkPawnCapture(board, fromX, fromY, toX, toY);
		}
			boolean move = !this.isOnTopSide() ? (this.moved ? ((toY - fromY) == 1 && (fromX == toX)) : (((toY - fromY) == 1 || (toY - fromY) == 2 && (fromX == toX)))) : (this.moved ? ((fromY - toY) == 1 && (fromX == toX)) : ((((fromY - toY) == 1 || (fromY - toY) == 2) && (fromX == toX))));
			if(move) {
				if(Math.abs(toY - fromY) == 2) {
					this.setEnPassantable(true);
					System.out.println("HARRO");
				} else {
					this.setEnPassantable(false);
				}
			}
			return move;
	}

	public Piece clone() {
		return new Pawn(super.getSuperiority());
	}

	public boolean checkDiagonalMovement(int fromX, int fromY, int toX, int toY) {
		if(this.isOnTopSide()) {
			if((fromY - toY) == 1 && Math.abs(fromX-toX) == 1) {
				return true;
			}
			return false;
		} else {
			if((toY - fromY) == 1 && Math.abs(fromX-toX) == 1) {
				return true;
			}
			return false;
		}
	}


	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.movement.FirstMoveRules#setMoved(boolean)
	 */
	@Override
	public void setMoved(boolean moved) {
		this.moved = moved;
	}

}
