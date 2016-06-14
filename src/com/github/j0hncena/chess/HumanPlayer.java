package com.github.j0hncena.chess;

import java.io.Serializable;
import java.rmi.RemoteException;

import com.github.j0hncena.chess.movement.Move;

public class HumanPlayer extends Player implements RemotePlayer, Serializable{

	private static final long serialVersionUID = -2809038082893031393L;

	public HumanPlayer(boolean isWhite) {
		super(isWhite);
	}

	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.RemotePlayer#makeMove(com.github.j0hncena.chess.movement.Move)
	 */
	@Override
	public void makeMove(Move move) throws RemoteException {
		getManager().makeMove(move);
	}
	
	/**Sets the turn
	 * @param takeTurn
	 */
	public void setTurn(boolean takeTurn) {
		getBoard().setTurn(takeTurn);
	}
	
	/**
	 * @return true if it is the players turn
	 */
	public boolean isTurn() {
		return this.isWhite() ? getManager().getTurnCounter() % 2 == 1 : getManager().getTurnCounter() % 2 == 0;
	}


}
