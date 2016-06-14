package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;

import javax.swing.JPanel;

import com.github.j0hncena.chess.movement.BoardBoundaryChecker;
import com.github.j0hncena.chess.movement.CollisionChecker;
import com.github.j0hncena.chess.movement.MovementChecker;
import com.github.j0hncena.chess.movement.SameColorPieceCheck;
import com.github.j0hncena.chess.movement.SameMoveChecker;

/**
 *abstract class for the pieces
 */
public abstract class Piece implements MovementChecker {

	//I am too lazy to make an enum
	private boolean isWhite;
	private boolean topSide;
	private BoardBoundaryChecker boundaryChecker;
	private SameMoveChecker moveChecker;
	private SameColorPieceCheck colorChecker;
	private CollisionChecker collisionChecker;
	
	/**Constructor that creates the universal movement checkers
	 * @param isWhite the whiteness of the piece
	 */
	public Piece(boolean isWhite) {
		boundaryChecker = new BoardBoundaryChecker();
		moveChecker = new SameMoveChecker();
		colorChecker = new SameColorPieceCheck();
		collisionChecker = new CollisionChecker();
		this.isWhite = isWhite;
		this.topSide = this.isWhite ? false : true;
	}
	
	/**
	 * @return if the piece is white
	 */
	public boolean getWhiteness() {
		return this.isWhite;
	}
	
	/**
	 * @return 1 if the piece is white 0 if not
	 */
	protected int getColorNumber() {
		return isWhite ? 1 : 0;
	}
	
	/**
	 * @param color the color
	 * @return if the piece is the same color
	 */
	public boolean sameColor(boolean color) {
		return this.isWhite == color;
	}

	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.movement.MovementChecker#isValid(Piece[][], int, int, int, int)
	 */
	@Override
	public boolean isValid(Piece [][] board, int fromX, int fromY, int toX, int toY) {
		return (boundaryChecker.isValid(board, fromX, fromY, toX, toY) && moveChecker.isValid(board, fromX, fromY, toX, toY) && colorChecker.isValid(board, fromX, fromY, toX, toY) && collisionChecker.checkCollision(board, this, fromX, fromY, toX, toY));
	}

	/**Draws Piece on the JPanel
	 * @param g the graphics
	 * @param x the x position on the jpanel
	 * @param y the y position on the jpanel
	 * @param pieceNumber the piece number that determines the location from the piece.png file
	 * @param colorNumber the color number the color of the piece
	 * @param panel the jpanel to draw the piece on
	 */
	public void drawPiece(Graphics g, int x, int y, int pieceNumber, int colorNumber, JPanel panel) {
		g.drawImage(PieceConstants.CHESS_PIECES, x * PieceConstants.SQUARE_SIZE, y * PieceConstants.SQUARE_SIZE, (x+1) * PieceConstants.SQUARE_SIZE, (y+1) * PieceConstants.SQUARE_SIZE, pieceNumber * PieceConstants.PIXEL_SIZE, colorNumber * PieceConstants.PIXEL_SIZE, (pieceNumber + 1) * PieceConstants.PIXEL_SIZE, (colorNumber + 1) * PieceConstants.PIXEL_SIZE, panel);
	}
	
	/**Child classes use this method to call the piece classes drawPiece
	 * @param g the graphics
	 * @param x the x coordinate
	 * @param y the y coordinate 
	 * @param panel the jpanel
	 */
	public abstract void paint(Graphics g, int x, int y, JPanel panel);
	
	/**
	 * @return if the piece is on the topside
	 */
	public boolean isOnTopSide() {
		return this.topSide;
	}
}
