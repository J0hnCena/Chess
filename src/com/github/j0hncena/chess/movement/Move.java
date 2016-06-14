package com.github.j0hncena.chess.movement;

import com.github.j0hncena.chess.pieces.Piece;

/**
 * Class that contains information about a particular move
 */
public class Move {
	private Spot origin;
	private Spot destination;
	private Piece attacker;
	private Piece defender;
	 
	/**Creates a new move
	 * @param origin the original spot
	 * @param destination the spot to move to
	 * @param attacker the attacking piece
	 * @param defender the defending piece
	 */
	public Move(Spot origin, Spot destination, Piece attacker, Piece defender) {
		this.origin = origin;
		this.destination = destination;
		this.attacker = attacker;
		this.defender = defender;
	}

	/**
	 * @return the origin
	 */
	public Spot getOrigin() {
		return origin;
	}

	/**
	 * @return the destination
	 */
	public Spot getDestination() {
		return destination;
	}

	/**
	 * @return the attacker
	 */
	public Piece getAttacker() {
		return attacker;
	}

	/**
	 * @return the defender
	 */
	public Piece getDefender() {
		return defender;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(Spot origin) {
		this.origin = origin;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(Spot destination) {
		this.destination = destination;
	}

	/**
	 * @param attacker the attacker to set
	 */
	public void setAttacker(Piece attacker) {
		this.attacker = attacker;
	}

	/**
	 * @param defender the defender to set
	 */
	public void setDefender(Piece defender) {
		this.defender = defender;
	}
	
}
