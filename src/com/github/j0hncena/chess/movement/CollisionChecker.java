package com.github.j0hncena.chess.movement;

import com.github.j0hncena.chess.pieces.Bishop;
import com.github.j0hncena.chess.pieces.Piece;
import com.github.j0hncena.chess.pieces.Queen;
import com.github.j0hncena.chess.pieces.Rook;

public class CollisionChecker {

	public CollisionChecker() {
	}

	public boolean checkCollision(Piece[][] chessboard, Piece p, int startX, int startY, int endX, int endY) {
		if(p != null) {
			if(p instanceof Bishop || p instanceof Rook || p instanceof Queen) {
				int xIncrement = Math.abs(startX - endX) != 0 ? (endX - startX) / Math.abs(startX - endX) : 0;
				int yIncrement = Math.abs(startY - endY) != 0 ? (endY - startY) / Math.abs(startY - endY) : 0;
				int counter = Math.abs(startX - endX) > Math.abs(startY - endY) ? Math.abs(startX - endX) : Math.abs(startY - endY);
				for(int i = 0; i<counter-1; i++) {
					if(chessboard[startX += xIncrement][startY += yIncrement] != null) {
						return false;
					}
				}
			}
		}

		return true;
	}

}
