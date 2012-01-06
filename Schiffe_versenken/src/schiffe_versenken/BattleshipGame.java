package schiffe_versenken;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.UnknownHostException;


import javax.swing.*;

import network.Client;
import network.Server;

/**
 * 
 */

public class BattleshipGame extends JFrame {

	private static final long serialVersionUID = 1L;

	Player player1;
	Player player2;
	Server server;
	Client client;
	Thread clientThread;
	Thread serverThread;

	public BattleshipGame() {
		super("Battleship Game");
		this.newPlayer();
		this.initGUI();
		this.initMenu();
	}

	private void newPlayer() {
		player1 = new Player("Erol");
		player2 = new Player("Max");
	}

	/*
	 * creates server on local machine (incl. save reference for Server object) 
	 * and connect local user to server
	 */
	private void createServer() {
		// create new server instance on port 6200 in separate thread
		Runnable server = new Server(6200);
		serverThread = new Thread(server);
		serverThread.start();
		this.server = (Server) server;
		try {
			connectToPartner( InetAddress.getLocalHost().getHostName() , 6200);
		} catch (UnknownHostException e) {
			System.err.println("local network error!");
			return;
		}
	}

	/*
	 * connect to partner and save reference for Client object
	 */
	private void connectToPartner(String iv_ip, int iv_port) {	
		Runnable client = new Client( iv_ip, iv_port );
		clientThread = new Thread(client);
		clientThread.start();
		this.client = (Client) client;
	}
	
	
	/*
	 * terminate client connection and shutdown local server if is running
	 */
	public void cancelGame() {
		// shutdown client
		client.switchOff();
		// if local server is running -> server shutdown
		if( server != null ) {
			server.switchOff();
		}
		client.finalize();
		server.finalize();
	}
	
	
	// initialisiert den ganzen Frame
	private void initGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 680, 450);

		FrameLayout border = new FrameLayout(player1, player2);
		this.setLayout(new BorderLayout());
		this.add(border.north, BorderLayout.NORTH);
		this.add(border.east, BorderLayout.EAST);
		this.add(border.south, BorderLayout.SOUTH);
		this.add(border.west, BorderLayout.WEST);
		this.add(border.center, BorderLayout.CENTER);
		this.setVisible(true);
	}


	// initialisiert die Menüleiste
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.ORANGE);
		JMenu fileMenu = new JMenu("Optionen");
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);

		// Menu New Game
		JMenuItem newGame = new JMenuItem("Neues Spiel");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createServer();
			}

		});
		fileMenu.add(newGame);

		// Menu new Connection
		fileMenu.add(new JMenuItem("Neue Verbindung herstellen"));
		JMenuItem cancelConnection = new JMenuItem("Verbindung abbrechen");
		// Action Listener definition for cancel connection
		cancelConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelGame();
			}
		});
		fileMenu.add(cancelConnection);

		
		fileMenu.addSeparator();

		JMenuItem exitGame = new JMenuItem("Beenden");
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		fileMenu.add(exitGame);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// startet das Spiel
		BattleshipGame game = new BattleshipGame();

	}

}
