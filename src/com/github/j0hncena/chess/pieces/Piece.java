package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.github.j0hncena.chess.movement.BoardBoundaryChecker;
import com.github.j0hncena.chess.movement.CollisionChecker;
import com.github.j0hncena.chess.movement.MovementChecker;
import com.github.j0hncena.chess.movement.SameColorPieceCheck;
import com.github.j0hncena.chess.movement.SameMoveChecker;

public abstract class Piece implements MovementChecker{

	private boolean isSuperior;
	private boolean topSide;
	private BoardBoundaryChecker boundaryChecker;
	private SameMoveChecker moveChecker;
	private SameColorPieceCheck colorChecker;
	private CollisionChecker collisionChecker;
	
	public Piece(boolean isSuperior) {
		boundaryChecker = new BoardBoundaryChecker();
		moveChecker = new SameMoveChecker();
		colorChecker = new SameColorPieceCheck();
		collisionChecker = new CollisionChecker();
		this.isSuperior = isSuperior;
		this.topSide = this.isSuperior ? false : true;
	}
	
	public boolean getSuperiority() {
		return this.isSuperior;
	}
	
	protected int getColorNumber() {
		return isSuperior ? 1 : 0;
	}

	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.movement.MovementChecker#isValid(Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece [][] board, int fromX, int fromY, int toX, int toY) {
		if(!boundaryChecker.isValid(board, fromX, fromY, toX, toY) || !moveChecker.isValid(board, fromX, fromY, toX, toY) || !colorChecker.isValid(board, fromX, fromY, toX, toY) || !collisionChecker.checkCollision(board, this, fromX, fromY, toX, toY))
			return false;
		return true;
	}

	public void drawPiece(Graphics g, int x, int y, int pieceNumber, int colorNumber, JPanel panel) {
		g.drawImage(PieceConstants.CHESS_PIECES, x * PieceConstants.SQUARE_SIZE, y * PieceConstants.SQUARE_SIZE, (x+1) * PieceConstants.SQUARE_SIZE, (y+1) * PieceConstants.SQUARE_SIZE, pieceNumber * PieceConstants.PIXEL_SIZE, colorNumber * PieceConstants.PIXEL_SIZE, (pieceNumber + 1) * PieceConstants.PIXEL_SIZE, (colorNumber + 1) * PieceConstants.PIXEL_SIZE, panel);
	}
	public abstract void paint(Graphics g, int x, int y, JPanel panel);
	
	public abstract Piece clone();
	
	public boolean isOnTopSide() {
		return this.topSide;
	}
}
