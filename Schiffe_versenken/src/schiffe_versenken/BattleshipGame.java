package schiffe_versenken;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
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
	
	JFrame popupFrame;
	Popup popup;
	
	Player player1;
	Player player2;
	Server server;
	Client client;
	Thread clientThread;
	Thread serverThread;
	
	String ipAdress;
	JTextField ipTextField;

	public BattleshipGame() {
		super("Battleship Game");
		this.newPlayer();
		this.initGUI();
		this.initMenu();
		this.addTileActionListener();
	}

	private void newPlayer() {
		player1 = new Player("Erol");
		player2 = new Player("Max");
	}
	
	
	// adds every Tile in the Battlefied of Player 2 an ActionListener
	private void addTileActionListener() {

		for(int x = 0; x < 10; x++ ) {
			for(int y = 0; y < 10; y++ ) {
				player2.getBattlefield().getBoard()[x][y].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						Point point = player2.getBattlefield().getTileCoords((Tile)e.getSource());
						if(player2.getBattlefield().getBoard()[point.x][point.y].isBoardShootable) {
							new Shot(point.x, point.y);
						}
					}
				});
				
			}
 		}
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

		this.setLayout(new BorderLayout());
		this.add(new North(), BorderLayout.NORTH);
		this.add(new East(player1, player2), BorderLayout.EAST);
		this.add(new South(), BorderLayout.SOUTH);
		this.add(new West(), BorderLayout.WEST);
		this.add(new BattlefieldViewer(player1, player2), BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void whenConnectionIsSetButtonsEnable() {
		for(int i = 0; i < 5; i++) {
			player1.getShips()[i].button.setEnabled(true);
		}
	}
	
	
	


	// initialisiert die Menüleiste
	private void initMenu() {
		// init JMenuBar
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
		
		popupFrame = new JFrame("Verbindung herstellen");
		popupFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		popupFrame.setBounds(this.getX(), this.getY(), 180, 100);
		
		JPanel popupPanel = new JPanel();
		
		ipTextField = new JTextField("000.000.0.0");
		JButton connectButton = new JButton("verbinden");
		connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ipAdress = ipTextField.getText();
				popupFrame.setVisible(false);
				
			}
			
		});
		popupPanel.add(ipTextField);
		popupPanel.add(connectButton);
		popupFrame.add(popupPanel);
		
		JMenuItem newConnection = new JMenuItem("Neue Verbindung herstellen");
		newConnection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				popupFrame.setVisible(true);
			}
		});
		fileMenu.add(newConnection);
		
		
		// Menu cancel Connection
		JMenuItem cancelConnection = new JMenuItem("Verbindung abbrechen");
		// Action Listener definition for cancel connection
		cancelConnection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelGame();
			}
		});
		fileMenu.add(cancelConnection);

		
		

		// Menu Exit Game
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
