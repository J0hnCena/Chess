package com.github.j0hncena.chess;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.j0hncena.chess.movement.Move;

public interface RemotePlayer extends Remote{
	public void makeMove(Move move) throws RemoteException;

}
