package com.github.j0hncena.chess;


import javax.swing.JFrame;

public class Game {

	public Game() {
	}

	public static void main(String[] args) {
		JFrame chessBoard = new JFrame("Chess");
		Board g = new Board();
		chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chessBoard.add(g);
		g.setVisible(true);
		chessBoard.setVisible(true);
			
	}

}
