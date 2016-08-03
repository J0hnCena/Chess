package com.github.j0hncena.chess;



import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;



/**
 *
 */
public class Game {
	private Registry registry = null;
	public static final int REGISTRY_PORT = 1099;
	private MoveManager localManager = null;
	private HumanPlayer remote = null;
	private HumanPlayer local = null;
	private Board board;
	public static final String FIND_HOST = "Sam Golden is a faggot";
	private boolean remoteGame;
	private boolean isHost;
	private boolean isWhite;
	private JFrame chessBoard;
	private JLabel gameLabel;
	
	/**
	 * starts the game
	 */
	public void start() {
		setUp();
		if(remoteGame) {
			initializeRemoteGame();
		} else {
			initializeGame();
		}
	}

	/**
	 * set up the options for the chess game
	 */
	public void setUp() {
		chessBoard = new JFrame("Chess");
		chessBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		JOptionPane options = new JOptionPane("Choose an option");
		options.setOptions(new String[]{"Local", "Network"});
		options.createDialog(chessBoard, "The Chess Game").setVisible(true);
		try {
			while(options.getValue().toString().equals("UNINITIALIZED_VALUE")) {

				//the user exited
				if(options.getValue() == null) {
					System.exit(0);
				}
				Thread.sleep(25); 
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			System.exit(0);
		}
		remoteGame = options.getValue().toString().equals("Network");
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	/**
	 * Game loop for multiplayer
	 */
	public void loop() {
		
		//this is while true for now because I haven't made a method to determine checkmate
		//TODO add checkmate method
		while(true) {
			if(local.isTurn()) {
				
			} else {
				
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * initializes a local game
	 */
	public void initializeGame() {
		board = new Board();
		localManager = new MoveManager(board, this);
		board.setManager(localManager);
		setUpGui(board);
	}

	/**
	 * initializes a multiplayer game
	 */
	public void initializeRemoteGame() {
		JFrame frame = new JFrame("Connecting");
		JOptionPane options= new JOptionPane("Setting up");
		options.setOptions(new String[]{"Host", "Join"});
		options.createDialog(frame, "Setting up connection").setVisible(true);
		try {
			while(options.getValue().toString().equals("UNINITIALIZED_VALUE")) {

				Thread.sleep(25); //don't hog cpu if the user is being a dick and waits forever
			}
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		} catch (NullPointerException ex) {
			System.exit(0);
		}
		isHost = options.getValue().toString().equals("Host");

		if(isHost) {
			isWhite = new Random().nextBoolean();
			local = new HumanPlayer(isWhite);
			try {
				//HumanPlayer stub = (HumanPlayer)UnicastRemoteObject.exportObject(local, 0);
				registry = LocateRegistry.createRegistry(REGISTRY_PORT);
				registry.bind("Host", local);
			} catch (RemoteException ex) {
				ex.printStackTrace();
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
			}
			UDPListener listener = new UDPListener();
			Thread listeningThread = new Thread(listener);
			listeningThread.start();
			while(remote == null) {
				try {
					remote = (HumanPlayer)registry.lookup("Client");
					Thread.sleep(25);
				} catch (RemoteException | NotBoundException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			if(listeningThread != null) {
				listener.terminate();
				try {
					listeningThread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			//spam the a UDP packet to all computers on the network saying that it is looking for a host
			try (DatagramSocket socket = new DatagramSocket()){
				socket.setBroadcast(true);
				byte [] passcode = FIND_HOST.getBytes();
				//send to default broadcast address
				DatagramPacket packet = new DatagramPacket(passcode, passcode.length, InetAddress.getByName("255.255.255.255"), 8888);
				socket.send(packet);
				
				//find the broadcast address using network interface
				Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
				while(interfaces.hasMoreElements()) {
					NetworkInterface networkInterface = interfaces.nextElement();
					if(!networkInterface.isLoopback() && networkInterface.isUp()) {
						for(InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
							InetAddress broadcast = address.getBroadcast();
							if(broadcast != null) {
								DatagramPacket datagramPacket = new DatagramPacket(passcode, passcode.length, broadcast, 8888);
								socket.send(datagramPacket);
							}
						}
					}
				}
				
				byte response [] = new byte[15000];
				DatagramPacket responsePacket = new DatagramPacket(response, response.length);
				socket.receive(responsePacket);
				String hostIP = responsePacket.getAddress().toString();
				remote = (HumanPlayer)LocateRegistry.getRegistry(hostIP).lookup("Host");
				local = new HumanPlayer(!remote.isWhite());
				LocateRegistry.getRegistry(hostIP).bind("Client", local);
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NotBoundException e) {
				e.printStackTrace();
			} catch (AlreadyBoundException e) {
				e.printStackTrace();
			}
			frame.dispose();
			board = new Board(remote, isWhite);
			localManager = new MoveManager(board, this);
			local.setBoard(board);
			setUpGui(board);
		}
	}
	
	public void setUpGui(Board board) {
		gameLabel = new JLabel("White's Turn");
		gameLabel.setPreferredSize(new Dimension(100, 50));
		board.setPreferredSize(new Dimension(400, 100));
		chessBoard.add(BorderLayout.EAST, gameLabel);
		chessBoard.add(BorderLayout.CENTER, board);
		chessBoard.pack();
		chessBoard.setVisible(true);
	}
	
	public void gameOver(boolean color) {
		gameLabel.setText(color ? "White wins!" : "Black wins!");
		board.setGameOver(true);
	}
	
	public String queryPiece() {
		JOptionPane options= new JOptionPane("Choose a piece to promote");
		options.setOptions(new String[]{"Queen", "Rook", "Bishop", "Knight"});
		JDialog dialog = new JDialog(chessBoard, "Choose a piece to promote", true);
		dialog.setContentPane(options);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.addWindowListener(new WindowAdapter() {

			/* (non-Javadoc)
			 * @see java.awt.event.WindowAdapter#windowClosed(java.awt.event.WindowEvent)
			 */
			@Override
			public void windowClosed(WindowEvent e) {
				
			}
			
		});
		options.addPropertyChangeListener(event -> {
			if(dialog.isVisible() && event.getSource() == options && event.getPropertyName().equals(JOptionPane.VALUE_PROPERTY)) {
				dialog.setVisible(false);
			}
		});
		dialog.pack();
		dialog.setVisible(true);
		return options.getValue().toString();
	}
	
	public void toggleTurn() { 
		gameLabel.setText(gameLabel.getText() == "White's Turn" ? "Black's Turn" : "White's Turn");
	}
	
	private class UDPListener implements Runnable {
		private volatile boolean running = true;

		public void terminate() {
			running = false;
		}
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
				try (DatagramSocket socket = new DatagramSocket(8888, InetAddress.getLocalHost())){

					socket.setBroadcast(true);
					while(running) {
						byte[] buffer = new byte[15000];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						socket.receive(packet);
						String message = new String(packet.getData()).trim();
						if(message.equals("Sam Golden is a faggot")) {
							byte [] response = InetAddress.getLocalHost().getAddress();
							DatagramPacket responsePacket = new DatagramPacket(response, response.length, packet.getAddress(), packet.getPort());
							socket.send(responsePacket);
						}
						
						Thread.sleep(25);
					}
				} catch (Exception e) {
					e.printStackTrace();
			}
		}

	}
	

}
