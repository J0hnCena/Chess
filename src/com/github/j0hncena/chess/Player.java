package com.github.j0hncena.chess;

public abstract class Player {
	private boolean isWhite;
	private GameManager manager;
	private Board board;
	public Player(boolean isWhite) {
		this.isWhite = isWhite;
		this.board = new Board();
		manager = new GameManager(board);
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
	public GameManager getManager() {
		return manager;
	}
	/**
	 * @param manager the manager to set
	 */
	public void setManager(GameManager manager) {
		this.manager = manager;
	}
	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}
	/**
	 * @param board the board to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

}
