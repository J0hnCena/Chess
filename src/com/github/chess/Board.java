package com.github.chess;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {

	private static final long serialVersionUID = 6411499808530678723L;
	private static final int squareSize = 32;
	private static String chessBoard[][] = {
			{"r","k","b","q","a","b","k","r"},
	        {"p","p","p","p","p","p","p","p"},
	        {" "," "," "," "," "," "," "," "},
	        {" "," "," "," "," "," "," "," "},
	        {" "," "," "," "," "," "," "," "},
	        {"P"," "," "," "," "," "," "," "},
	        {" ","P","P","P","P","P","P","P"},
	        {"R","K","B","Q","A","B","K","R"}
	};
	public Board() {
	}
	
	public void drawBoard(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.YELLOW);
		for(int i = 0; i<64; i+=2) {
			g.setColor(Color.WHITE);
			g.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
			g.setColor(Color.GRAY);
			g.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		drawBoard(g);
		
		//this.setBackground(Color.yellow);
		//g.setColor(Color.PINK);
		Image chessPieces = new ImageIcon("ChessPieces.png").getImage();
		g.drawImage(chessPieces, 0, 0, 32, 32, 0, 0, 64, 64, this);
		//g.drawImage(chessPieces, x, y, x+64, y+64, 64, 64, 128, 128, this);
	}


}
