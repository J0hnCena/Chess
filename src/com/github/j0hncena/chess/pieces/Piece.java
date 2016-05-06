package com.github.j0hncena.chess.pieces;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import com.github.j0hncena.chess.movement.MovementBehavior;

public abstract class Piece {
	private boolean isSuperior;

	public Piece() {
	}

	public abstract void draw(Graphics g);

	public boolean check(ArrayList<MovementBehavior> behaviors, int fromX,
			int fromY, int toX, int toY) {
		for (MovementBehavior behavior : behaviors) {
			if (!behavior.isValid(fromX, fromY, toX, toY)) {
				return false;
			}
		}
		return true;
	}
}
