package com.github.j0hncena.chess;




import com.github.j0hncena.chess.movement.FirstMoveRules;
import com.github.j0hncena.chess.movement.Move;
import com.github.j0hncena.chess.movement.Spot;
import com.github.j0hncena.chess.pieces.Bishop;
import com.github.j0hncena.chess.pieces.King;
import com.github.j0hncena.chess.pieces.Knight;
import com.github.j0hncena.chess.pieces.Pawn;
import com.github.j0hncena.chess.pieces.Piece;
import com.github.j0hncena.chess.pieces.Queen;
import com.github.j0hncena.chess.pieces.Rook;

public class MoveManager {
	private Board chessBoard;
	private int turnCounter;
	private Move lastMove;
	private Game game = null;

	public MoveManager(Board chessBoard, Game game) {
		this.chessBoard = chessBoard;
		this.game = game;
		turnCounter = 0;
	}
	
	/**
	 * @param move
	 * @return
	 */
	public boolean makeMove(Move move) {
		return makeMove(move, true);
	}

	/* (non-Javadoc)
	 * @see com.github.j0hncena.chess.RemoteManager#makeMove(com.github.j0hncena.chess.movement.Move)
	 */
	public boolean makeMove(Move move, boolean checkPromote) {
		//don't do what I'm doing here
		if(basicCheck(move)) {
			boolean attackerColor = move.getAttacker().getWhiteness();
			if(!checkPromotion(move)) {
				if(!checkEnPassant(move) && !checkCastle(move)) {
					//it's easier to use the same isValid method instead of refactoring all the isValid methods to use the move class as its argument
					if(move.getAttacker().isValid(chessBoard.getBoard(), move.getOrigin().getX(), move.getOrigin().getY(), move.getDestination().getX(), move.getDestination().getY())) {
						if(move.getAttacker() instanceof FirstMoveRules) {
							((FirstMoveRules)move.getAttacker()).setMoved(true);
						}
						chessBoard.setPiece(move.getDestination(), move.getAttacker());
						chessBoard.setPiece(move.getOrigin(), null);
						return checkIfInCheckAfterMove(move, attackerColor);
					}
				} 
						
			} else {
				Piece promotedPiece = checkPromote ? convertStringToPiece(game.queryPiece(), move.getAttacker().getWhiteness()) : move.getAttacker();
				chessBoard.setPiece(move.getDestination(), promotedPiece);
				chessBoard.setPiece(move.getOrigin(), null);
				return checkIfInCheckAfterMove(move, move.getAttacker().getWhiteness());
			}
		}
		return false;
	}
	
	public Piece convertStringToPiece(String pieceName, boolean color) {
		switch (pieceName) {
		case "Pawn":
			return new Pawn(color);
		case "Bishop":
			return new Bishop(color);
		case "Rook":
			return new Rook(color);
		case "Knight":
			return new Knight(color);
		case "Queen":
			return new Queen(color);
		case "King": 
			return new King(color);
		default:
			throw new IllegalArgumentException("There are no pieces with that name");
		}
	}
	
	public boolean checkMate(boolean color) {
		//the amount of brute force is real
		if(isInCheck(color, kingLocation(color))) {
			Piece[][] board = chessBoard.getBoard().clone();
			Board theBoard = new Board();
			theBoard.setBoard(board);
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j<board[0].length; j++) {
					Piece testPiece = theBoard.getPiece(i, j);
					if(testPiece != null && testPiece.getWhiteness() == color) {
						for(int r = 0; r < board.length; r++) {
							for(int c = 0; c < board[0].length; c++) {
								if(makeMove(new Move(new Spot(i, j), new Spot(r, c), testPiece, theBoard.getPiece(r, c)), false)) {
									return false;
								}
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean checkPromotion(Move move) {
		if(move.getAttacker() instanceof Pawn) {
			if(move.getAttacker().isOnTopSide()) {
				return (move.getDestination().getY() == 0);
			} else {
				return (move.getDestination().getY() == 7);
			}
		}
		return false;
	}
	
	public boolean checkIfInCheckAfterMove(Move move, boolean attackerColor) {
		if(isInCheck(attackerColor, null)){
			chessBoard.setPiece(move.getDestination(), move.getDefender());
			chessBoard.setPiece(move.getOrigin(), move.getAttacker());
			lastMove = null;
			return false;
		} else {
			turnCounter++;
			lastMove = move;
			chessBoard.repaint();
			return true;
		}
	}
	
	/**
	 * @param move
	 * @return true if the piece on the spot is not null and the spot is in bounds
	 */
	public boolean basicCheck(Move move) {
		return (move.getOrigin().getX() >= 0 && move.getOrigin().getX() <= 7 && move.getOrigin().getY() >= 0 && move.getOrigin().getY() <= 7) && chessBoard.getPiece(move.getOrigin()) != null && (turnCounter % 2 == 1 ? move.getAttacker().getWhiteness() : !move.getAttacker().getWhiteness());
	}

	public boolean checkCastle(Move move) {
		if((move.getAttacker() instanceof King) && !((King)move.getAttacker()).getMoved() && !isInCheck(move.getAttacker().getWhiteness(), null)) {
			if(Math.abs(move.getDestination().getX() - move.getOrigin().getX()) == 2 && move.getDestination().getY() == move.getOrigin().getY()) {
				int increment = (int)Math.signum(move.getDestination().getX() - move.getOrigin().getX());

				for(int i = 1; i < 3; i++) {
					if(!move.getAttacker().isValid(chessBoard.getBoard(), move.getOrigin().getX() + increment * i - increment, move.getOrigin().getY(), move.getOrigin().getX() + increment * i, move.getDestination().getY()) || isInCheck(move.getAttacker().getWhiteness(), new Spot(move.getOrigin().getX() + increment * i, move.getOrigin().getY()))) {
						return false;
					}
				}

				if(increment < 0) {
					if(chessBoard.getPiece(new Spot(0, move.getOrigin().getY())) != null && chessBoard.getPiece(new Spot(0, move.getOrigin().getY())) instanceof Rook && !((Rook)chessBoard.getPiece(new Spot(0, move.getOrigin().getY()))).getMoved()) {
						chessBoard.setPiece(new Spot(move.getDestination().getX() + 1, move.getDestination().getY()), chessBoard.getPiece(new Spot(0, move.getOrigin().getY())));
						chessBoard.setPiece(new Spot(0, move.getOrigin().getY()), null);
					} else {
						return false;
					}
				} else {
					if(chessBoard.getPiece(new Spot(7, move.getOrigin().getY())) != null && chessBoard.getPiece(new Spot(7, move.getOrigin().getY())) instanceof Rook && !((Rook)chessBoard.getPiece(new Spot(7, move.getOrigin().getY()))).getMoved()) {
						chessBoard.setPiece(new Spot(move.getDestination().getX() - 1, move.getDestination().getY()), chessBoard.getPiece(new Spot(7, move.getOrigin().getY())));
						chessBoard.setPiece(new Spot(7, move.getOrigin().getY()), null);
					} else {
						return false;
					}
				}
				((King)(move.getAttacker())).setMoved(true);
				chessBoard.setPiece(move.getDestination(), move.getAttacker());
				chessBoard.setPiece(move.getOrigin(), null);
				turnCounter++;
				lastMove = move;
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}


	/**
	 * @param move the move to check
	 * @return true if the move is an en passant
	 */
	public boolean checkEnPassant(Move move) {
		if(chessBoard.getPiece(move.getOrigin()) instanceof Pawn && checkDiagonalMovement(move)) {
			if(!chessBoard.getPiece(move.getOrigin()).isOnTopSide()) {
				if(move.getDestination().getY() - 1 <= 7 && move.getDestination().getY() - 1 >= 0 && chessBoard.getPiece(move.getDestination().getX(), move.getDestination().getY() - 1) != null && chessBoard.getPiece(move.getDestination().getX(), move.getDestination().getY() - 1) instanceof Pawn && ((Pawn)chessBoard.getPiece(move.getDestination().getX(), move.getDestination().getY() - 1)).isEnPassantable()) {
					move.setDefender(chessBoard.getPiece(new Spot(move.getDestination().getX(), move.getDestination().getY() - 1)));
					chessBoard.setPiece(new Spot(move.getDestination().getX(), move.getDestination().getY() - 1), null);
					chessBoard.setPiece(move.getDestination(), move.getAttacker());
					chessBoard.setPiece(move.getOrigin(), null);
					if(isInCheck(move.getAttacker().getWhiteness(), null)) {
						chessBoard.setPiece(move.getOrigin(), move.getAttacker());
						chessBoard.setPiece(move.getDestination(), null);
						chessBoard.setPiece(new Spot(move.getDestination().getX(), move.getDestination().getY() - 1), move.getDefender());
					} else {
						turnCounter++;
					}
					return true;
				}
			} else {
				if(move.getDestination().getY() + 1 <= 7 && move.getDestination().getY() + 1 >= 0 && chessBoard.getPiece(move.getDestination().getX(), move.getDestination().getY() + 1) != null && chessBoard.getPiece(move.getDestination().getX(), move.getDestination().getY() + 1) instanceof Pawn && ((Pawn)chessBoard.getPiece(move.getDestination().getX(), move.getDestination().getY() + 1)).isEnPassantable()) {
					move.setDefender(chessBoard.getPiece(new Spot(move.getDestination().getX(), move.getDestination().getY() + 1)));
					chessBoard.setPiece(new Spot(move.getDestination().getX(), move.getDestination().getY() + 1), null);
					chessBoard.setPiece(move.getDestination(), move.getAttacker());
					chessBoard.setPiece(move.getOrigin(), null);
					if(isInCheck(move.getAttacker().getWhiteness(), null)) {
						chessBoard.setPiece(move.getOrigin(), move.getAttacker());
						chessBoard.setPiece(move.getDestination(), null);
						chessBoard.setPiece(new Spot(move.getDestination().getX(), move.getDestination().getY() + 1), move.getDefender());
					} else {
						turnCounter++;
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @param move the information about the move
	 * @return true if the piece moves like a pawn
	 */
	public boolean checkDiagonalMovement(Move move) {
		if(move.getAttacker().isOnTopSide()) {
			if((move.getOrigin().getY() - move.getDestination().getY()) == 1 && Math.abs(move.getOrigin().getX() - move.getDestination().getX()) == 1) {
				return true;
			}
		} else {
			if(move.getDestination().getY() - move.getOrigin().getY() == 1 && Math.abs(move.getOrigin().getX() - move.getDestination().getX()) == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param color white if true black if false
	 * @param location the location of the king
	 * @return true if the king of that color is in check
	 */
	public boolean isInCheck(boolean color, Spot location) {
		//Spot opposingKing = kingLocation(color);
		Spot opposingKing = (location == null ? kingLocation(color) : location);
		for(int x = 0; x<8; x++) {
			for(int y = 0; y<8; y++) {
				if(chessBoard.getPiece(x, y) != null) {
					if(!chessBoard.getPiece(x, y).sameColor(color)) {
						if(chessBoard.getPiece(x, y).isValid(chessBoard.getBoard(), x, y, opposingKing.getX(), opposingKing.getY())) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * @param color white if true black if false
	 * @return the spot of the king of that particular color
	 */
	public Spot kingLocation(boolean color) {
		for(int x = 0; x<8; x++) {
			for(int y = 0; y<8; y++) {
				if(chessBoard.getPiece(x, y) instanceof King && chessBoard.getPiece(x, y).sameColor(color)) {
					return new Spot(x, y);
				}
			}
		}
		throw new AssertionError("There are no kings of that color on board");
	}

	/**
	 * increase the turn counter by one
	 */
	public void incTurnCounter() {
		turnCounter++;
	}

	/**
	 * decrease the turn counter by one
	 */
	public void decTurnCounter() {
		turnCounter--;
	}

	/**
	 * @return the turn counter
	 */
	public int getTurnCounter() {
		return turnCounter;
	}

	/**
	 * @return the lastMove
	 */
	public Move getLastMove() {
		return lastMove;
	}

	/**
	 * @param lastMove the lastMove to set
	 */
	public void setLastMove(Move lastMove) {
		this.lastMove = lastMove;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**Notify the overall game of a move if it is the turn of the player using the gamemanager and it is a remote game
	 * @param move
	 */
	public void notifyGame(Move move) {
		if(game != null && chessBoard.getTurn()) {
			chessBoard.setTurn(false);
		}
	}

}
