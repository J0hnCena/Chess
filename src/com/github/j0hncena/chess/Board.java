package com.github.j0hncena.chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;

import javax.swing.JPanel;

import com.github.j0hncena.chess.movement.Move;
import com.github.j0hncena.chess.movement.Spot;
import com.github.j0hncena.chess.pieces.Bishop;
import com.github.j0hncena.chess.pieces.King;
import com.github.j0hncena.chess.pieces.Knight;
import com.github.j0hncena.chess.pieces.Pawn;
import com.github.j0hncena.chess.pieces.Piece;
import com.github.j0hncena.chess.pieces.Queen;
import com.github.j0hncena.chess.pieces.Rook;

/**
 * This class is the gui representation of the chessboard
 */
public class Board extends JPanel {

	private static final long serialVersionUID = 6411499808530678723L;
	private static final int squareSize = 32;
	private static int currentPosX, currentPosY;
	private static int newPosX, newPosY;
	private static boolean makingMove = false;
	private static Piece chessBoard[][];
	private boolean isInitialized = false;
	private boolean isTurn = false;
	private MoveManager manager;
	private Player remotePlayer;
	private boolean isRemoteGame = false;

	/**
	 * Constructor that initializes the board and adds a mouse listener
	 */
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
				if (!makingMove && (isRemoteGame ? isTurn : true)) {
					currentPosX = e.getX() / 32;
					currentPosY = e.getY() / 32;
					makingMove = true;
				} else if ((isRemoteGame ? isTurn : true)){
					newPosX = e.getX() / 32;
					newPosY = e.getY() / 32;
					makingMove = false;
					if(notifyManager(manager, currentPosX, currentPosY, newPosX, newPosY)) {
						getManager().getGame().toggleTurn();
						if(isRemoteGame) {
							notifyRemoteGame(currentPosX, currentPosY, newPosX, newPosY);
							isTurn = false;
						}
					}
				}
			}

		});
	}

	public Board(Player player, boolean remoteGame) {
		this();
		this.remotePlayer = player;
		this.isRemoteGame = remoteGame;
	}

	/**
	 * initializes a new chessboard
	 */
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

	/**
	 * @return if it is the players turn
	 */
	public boolean getTurn() {
		return isTurn;
	}

	/**Sets the turn to taketurn
	 * @param takeTurn boolean to set isTurn to
	 */
	public void setTurn(boolean takeTurn) {
		isTurn = takeTurn;
	}

	/**
	 * @return the manager
	 */
	public MoveManager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(MoveManager manager) {
		this.manager = manager;
	}

	public boolean notifyManager(MoveManager manager, int oldX, int oldY, int newX, int newY) {
		return manager.makeMove(new Move(new Spot(oldX, oldY), new Spot(newX, newY), this.getPiece(oldX, oldY), this.getPiece(newX, newY)));
	}

	public void notifyRemoteGame(int oldX, int oldY, int newX, int newY) {
		if(remotePlayer instanceof HumanPlayer) {
			try {
				((HumanPlayer) remotePlayer).makeRemoteMove(new Move(new Spot(oldX, oldY), new Spot(newX, newY), this.getPiece(oldX, oldY), this.getPiece(newX, newY)));
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(new Color(156, 93, 82));
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

	/**
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @return the piece at the x,y position
	 */
	public Piece getPiece(int x, int y) {
		return chessBoard[x][y];
	}

	/**
	 * @param spot the spot that the piece is at
	 * @return the piece at the spot
	 */
	public Piece getPiece(Spot spot) {
		return chessBoard[spot.getX()][spot.getY()];
	}

	/**
	 * @return the chessboard
	 */
	public Piece [][] getBoard() {
		return chessBoard;
	}

	/**deletes a piece
	 * @param x the x coordinate
	 * @param y the y coordinate
	 */
	public void delete(int x, int y) {
		chessBoard[x][y] = null;
	}

	/**Sets a piece at the location
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param piece the piece
	 * @deprecated use {@link #setPiece(Spot, Piece)}} instead
	 */
	@Deprecated
	public void setPiece(int x, int y, Piece piece) {
		chessBoard[x][y] = piece;
	}

	/**Sets a piece at the location
	 * @param spot the spot to put the piece
	 * @param piece the piece to put on the spot
	 */
	public void setPiece(Spot spot, Piece piece) {
		chessBoard[spot.getX()][spot.getY()] = piece;
	}
}
