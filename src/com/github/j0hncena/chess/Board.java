package com.github.j0hncena.chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import com.github.j0hncena.chess.movement.FirstMoveRules;
import com.github.j0hncena.chess.pieces.Bishop;
import com.github.j0hncena.chess.pieces.King;
import com.github.j0hncena.chess.pieces.Knight;
import com.github.j0hncena.chess.pieces.Pawn;
import com.github.j0hncena.chess.pieces.Piece;
import com.github.j0hncena.chess.pieces.Queen;
import com.github.j0hncena.chess.pieces.Rook;

public class Board extends JPanel {

	private static final long serialVersionUID = 6411499808530678723L;
	private static final int squareSize = 32;
	private static int currentPosX, currentPosY;
	private static int newPosX, newPosY;
	private static boolean makingMove = false;
	private static Piece chessBoard[][];
	private boolean isInitialized = false;

	public Board() {
		chessBoard = new Piece[8][8];
		if(!isInitialized) {
			initialize();
			isInitialized = true;
		}

		this.addMouseListener(new MouseAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mousePressed(java.awt.event.MouseEvent)
			 */
			@Override
			public void mousePressed(MouseEvent e) {
				if (!makingMove) {
					currentPosX = e.getX() / 32;
					currentPosY = e.getY() / 32;
					makingMove = true;
				} else {
					newPosX = e.getX() / 32;
					newPosY = e.getY() / 32;
					makingMove = false;
					System.out.println(newPosX + " " + newPosY);
					makeMove(currentPosX, currentPosY, newPosX, newPosY);
				}
			}

		});
	}

	private void initialize() {
		for(int i = 0; i< chessBoard.length; i++) {
			for(int j = 0; j<chessBoard[0].length; j++) {
				chessBoard[i][j] = null;
			}
		}

		for(int i = 0; i<8; i++) {
			chessBoard[i][1] = new Pawn(true);
			chessBoard[i][6] = new Pawn(false);
		}

		chessBoard[1][0] = new Knight(true);
		chessBoard[6][0] = new Knight(true);
		chessBoard[1][7] = new Knight(false);
		chessBoard[6][7] = new Knight(false);

		chessBoard[0][0] = new Rook(true);
		chessBoard[7][0] = new Rook(true);
		chessBoard[0][7] = new Rook(false);
		chessBoard[7][7] = new Rook(false);

		chessBoard[2][0] = new Bishop(true);
		chessBoard[5][0] = new Bishop(true);
		chessBoard[2][7] = new Bishop(false);
		chessBoard[5][7] = new Bishop(false);

		chessBoard[3][0] = new Queen(true);
		chessBoard[3][7] = new Queen(false);

		chessBoard[4][0] = new King(true);
		chessBoard[4][7] = new King(false);
	}

	/**Draws the chess board
	 * @param g the graphics to draw
	 */
	public void drawBoard(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.YELLOW);
		for (int i = 0; i < 64; i += 2) {
			g.setColor(Color.WHITE);
			g.fillRect((i % 8 + (i / 8) % 2) * squareSize, (i / 8) * squareSize, squareSize, squareSize);
			g.setColor(Color.GRAY);
			g.fillRect(((i + 1) % 8 - ((i + 1) / 8) % 2) * squareSize, ((i + 1) / 8) * squareSize, squareSize, squareSize);
		}
		for(int i = 0; i< 8; i++) {
			for(int j = 0; j<8; j++) {
				if(chessBoard[i][j] != null) {
					chessBoard[i][j].paint(g, i, j, this);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		drawBoard(g);
	}

	/**moves the piece
	 * @param oldX the old X coordinate
	 * @param oldY the old Y coordinate
	 * @param newX the new X coordinate
	 * @param newY the new Y coordinate
	 */
	public void makeMove(int oldX, int oldY, int newX, int newY) {
		if(oldX <= 7 && oldX >= 0 && oldY <=7 && oldY >= 0) {
			if(chessBoard[oldX][oldY] != null) {
				if(!checkEnPassant(oldX, oldY, newX, newY)) {
					if(chessBoard[oldX][oldY].isValid(chessBoard, oldX, oldY, newX, newY)) {
						if(chessBoard[oldX][oldY] instanceof FirstMoveRules)
							((FirstMoveRules)chessBoard[oldX][oldY]).setMoved(true);
						chessBoard[newX][newY] = chessBoard[oldX][oldY];
						chessBoard[oldX][oldY] = null;
					}
				}
			}
		}
		repaint();
	}

	public boolean checkEnPassant(int oldX, int oldY, int newX, int newY) {
		if(chessBoard[oldX][oldY] instanceof Pawn) {
			if(checkDiagonalMovement(chessBoard[oldX][oldY], oldX, oldY, newX, newY)) {
				if(!chessBoard[oldX][oldY].isOnTopSide()) {
					if(newY - 1 <= 7 && newY - 1 >= 0 && chessBoard[newX][newY - 1] != null && chessBoard[newX][newY - 1] instanceof Pawn && ((Pawn)chessBoard[newX][newY - 1]).isEnPassantable()) {
						chessBoard[newX][newY - 1] = null;
						chessBoard[newX][newY] = chessBoard[oldX][oldY];
						chessBoard[oldX][oldY] = null;
						return true;
					}
				} else {
					if(newY + 1 <= 7 && newY + 1 >= 0 && chessBoard[newX][newY + 1] != null && chessBoard[newX][newY + 1] instanceof Pawn && ((Pawn)chessBoard[newX][newY + 1]).isEnPassantable()) {
						chessBoard[newX][newY + 1] = null;
						chessBoard[newX][newY] = chessBoard[oldX][oldY];
						chessBoard[oldX][oldY] = null;
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkDiagonalMovement(Piece piece, int fromX, int fromY, int toX, int toY) {
		if(piece.isOnTopSide()) {
			if((fromY - toY) == 1 && Math.abs(fromX-toX) == 1) {
				return true;
			}
		} else {
			if((toY - fromY) == 1 && Math.abs(fromX-toX) == 1) {
				return true;
			}
		}
		return false;
	}

	public void delete(int x, int y) {
		chessBoard[x][y] = null;
	}

}
