package com.github.j0hncena.chess;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.github.j0hncena.chess.movement.Move;

public interface RemotePlayer extends Remote{
	public void makeRemoteMove(Move move) throws RemoteException;
	public Move getLastMove() throws RemoteException;
}
