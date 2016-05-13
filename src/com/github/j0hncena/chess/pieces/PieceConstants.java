package com.github.j0hncena.chess.pieces;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PieceConstants {

	private PieceConstants() {
		throw new AssertionError("This class should not be created");
	}

	public static final Image CHESS_PIECES = new ImageIcon("assets/ChessPieces.png").getImage();

	public static final int PIXEL_SIZE = 64;
	
	public static final int SQUARE_SIZE = 32;
	
	public static final int WHITE = 0;
	
	public static final int BLACK = 1;
	
	public static final int KING = 0;
	
	public static final int QUEEN = 1;
	
	public static final int BISHOP = 3;
	
	public static final int KNIGHT = 4;
	
	public static final int ROOK = 2;
	
	public static final int PAWN = 5;

}
