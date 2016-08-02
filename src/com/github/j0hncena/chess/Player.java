package com.github.j0hncena.chess;

import com.github.j0hncena.chess.movement.Move;

public abstract class Player {
	private boolean isWhite;
	private MoveManager manager;
	private Board board;
	
	public Player(boolean isWhite) {
		this.isWhite = isWhite;
	}
	/**
	 * @return the isWhite
	 */
	public boolean isWhite() {
		return isWhite;
	}
	/**
	 * @param isWhite the isWhite to set
	 */
	public void setWhite(boolean isWhite) {
		this.isWhite = isWhite;
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
	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	
	public void makeMove(Move move) {
		this.getManager().makeMove(move);
	}
	
	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

}
