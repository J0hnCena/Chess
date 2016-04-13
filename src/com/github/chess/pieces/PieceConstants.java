package com.github.chess.pieces;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PieceConstants {

	private PieceConstants() {
		throw new AssertionError("This class should not be created");
	}
	
	public static final Image CHESS_PIECES = new ImageIcon("ChessPieces.png").getImage();
	
	public static final int PIXEL_SIZE = 64;

}
