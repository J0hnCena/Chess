package com.github.j0hncena.chess.movement;

import javafx.scene.effect.Light.Spot;

public class Move {
	private Spot origin;
	private Spot destination;
	public Move(Spot origin, Spot destination) {
		this.origin = origin;
		this.destination = destination;
	}

}
