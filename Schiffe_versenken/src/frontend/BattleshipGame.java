package frontend;

import frontend.views.BattlefieldViewer;
import frontend.views.Chat;
import frontend.views.Header;
import frontend.views.SetupView;
import frontend.views.StatusBar;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.InetAddress;
import java.net.UnknownHostException;


import javax.swing.*;

import backend.Action;
import backend.Helper;
import backend.Player;
import backend.Shot;
import backend.Tile;

import network.Client;
import network.Server;

/**
 * 
 */

public class BattleshipGame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JFrame popupFrame;
	Popup popup;
	
	Player localPlayer;
	Player remotePlayer;
	Server server;
	Client client;
	Thread clientThread;
	Thread serverThread;
	SetupView east;
	
	String ipAddress;
	JTextField ipTextField;
	
	// in later versions direct access to text label to switch text
	String currentPlayer;
	
	// BattleshipGame Constructor
	public BattleshipGame() {
		super("Battleship Game");
		try {
			this.newPlayer();
		} catch (UnknownHostException e) {
			System.err.println("error while initializing players!");
		}
		this.initGUI();
		this.initMenu();
		this.addTileActionListener();
		
	}// BattleshipGame Constructor

	/*
	 * creates new player objects
	 * 
	 * player1 represents local player and is initialized with local IP address as name
	 * player2 represents remote player
	 */
	private void newPlayer() throws UnknownHostException {
		localPlayer = new Player( InetAddress.getLocalHost().getHostAddress().toString() );
		remotePlayer = new Player("remote");
	}
	
	
	/*
	 * adds every Tile in the Battlefied of the remote player an ActionListener that is used to
	 * fire by player 1.
	 */
	private void addTileActionListener() {

		for(int x = 0; x < 10; x++ ) {
			for(int y = 0; y < 10; y++ ) {
				remotePlayer.getBattlefield().getBoard()[x][y].addActionListener(new ActionListener() 
				{
					public void actionPerformed(ActionEvent e) 
					{
						Point point = remotePlayer.getBattlefield().getTileCoords((Tile)e.getSource());
						if(remotePlayer.getBattlefield().getBoard()[point.x][point.y].isBoardShootable) {
							
							Action fireAction = null;
							try {
								fireAction = new Action( InetAddress.getLocalHost().getHostAddress().toString(), Helper.fire, point.x, point.y, "");
							} catch (UnknownHostException e1) {
								e1.printStackTrace();
							}
							AWTEvent rr_event = new ActionEvent(fireAction, 0, Helper.fire);
							
							String cmd = Helper.eventToCommand(rr_event);
							// send action string to server
							client.sendCommand(cmd);
						}
					}
				});
				
			}
 		}
	}
	
	
	
	/*
	 * react on event from Server
	 * 
	 */
	public void handleEvent(AWTEvent ir_event) {
		System.err.println("GUI instance received Action from Server!");
		
		Action action = Helper.awtEventToAction(ir_event);
		
		switch(action.getKey()) {
		case Helper.start:
			// if IP address in message is local IP, start game
			if(action.getMisc().equals( this.localPlayer.getName() ) == true ) {
				this.remotePlayer.getBattlefield().setButtonsEnable();
			}
			// set current Player
			currentPlayer = action.getMisc();
			
			break;
		case Helper.hit:
			System.out.println("Shot hit ship!");
			
			Shot shot0 = new Shot(action.getXPos(), action.getYPos());
			if(action.getMisc().equals(this.localPlayer.getName())) {
				this.localPlayer.getBattlefield().setShot(shot0);
			}
			else {
				this.remotePlayer.getBattlefield().setShotInGUI(shot0);
			}
			
			
			break;
		case Helper.nohit:
			System.out.println("Shot hit NO ship!");
			
			Shot shot1 = new Shot(action.getXPos(), action.getYPos());
			if(action.getMisc().equals(this.localPlayer.getName())) {
				this.localPlayer.getBattlefield().setShot(shot1);
			}
			else {
				this.remotePlayer.getBattlefield().setFailInGUI(shot1);
			}
			
			break;
		}
	}
	
	/*
	 * add new observer to current object
	 */
	public void addObserver(Client ir_object) {
		//observer.add(ir_object);
	}
	
	/*
	 * forward event to all observers
	 */
	public void fireEvent(AWTEvent ir_event) {
//		for(Server object:observer) {
//			object.handleEvent(ir_event);
//		}
	}
	
	
	
	
	/**********************************************
	 * ACTION LISTENER SECTION FOR GUI EVENTS
	 * 
	 */
	
	/*
	 * ActionListener for create game button in options menu
	 */
	private ActionListener actionListenerCreateGameButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			createServer();
		}

	};
	
	/*
	 * ActionListener for connect button in options menu
	 * 
	 * When button is pushed, connect to entered IP address
	 */
	private ActionListener actionListenerConnectButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ipAddress = ipTextField.getText();
			if(ipAddress != null) {
				popupFrame.setVisible(false);
				connectToPartner(ipAddress, 6200);
			}
			else {
				System.err.println("No IP address was entered!"); 
			}
			
		}
		
	};
	
	/*
	 * ActionListener for "connect to partner" button in connect popup
	 */
	private ActionListener actionListenerInitializeConnectionButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			popupFrame.setVisible(true);
		}
	};
	
	
	/*
	 * ActionListener for cancel button in options menu
	 */
	private ActionListener actionListenerCancelButton = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			cancelGame();
		}
	};
	
	/*
	 * ActionListener for exit game button in options menu
	 */
	private ActionListener actionListenerExitButton = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	};

	/**********************************************
	 * METHOD SECTION
	 * 
	 */
	
	/*
	 * transmit local initialized battlefield to server
	 */
	private void transmitBattlefield() {
		Action transmitAction;
		AWTEvent rr_event;
		String battlefieldAsString;
		String cmd;
		
		// convert local battlefield to string
		battlefieldAsString = this.localPlayer.getBattlefield().toString();
		
		// build action, pack into event that should be transfered to server 
		transmitAction = null;
		try {
			transmitAction = new Action( InetAddress.getLocalHost().getHostAddress().toString(), Helper.transmit, this.localPlayer.getBattlefield().getColNumber(), this.localPlayer.getBattlefield().getRowNumber(), battlefieldAsString);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		rr_event = new ActionEvent(transmitAction, 0, Helper.transmit);
		
		// convert event to command and transfer to server
		cmd = Helper.eventToCommand(rr_event);
		client.sendCommand(cmd);
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
		// unlock all GUI elements
		whenConnectionIsSetButtonsEnable();
	}

	/*
	 * connect to partner and save reference for Client object
	 */
	private void connectToPartner(String iv_ip, int iv_port) {	
		// try to connect to server side
		Runnable client = new Client( iv_ip, iv_port );
		// start new thread
		clientThread = new Thread(client);
		clientThread.start();
		// save reference to client object for later processing
		this.client = (Client) client;
		// add GUI to client to receive broadcast events from server
		this.client.addGUI(this);
		// unlock all GUI elements
		whenConnectionIsSetButtonsEnable();
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
		// call finalize methods to make sure that all objects are thrown away
		client.finalize();
		server.finalize();
	}
	
	
	// initialisiert den ganzen Frame
	private void initGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 680, 450);

		this.setLayout(new BorderLayout());
		this.add(new Header(), BorderLayout.NORTH);
		this.add(east = new SetupView(localPlayer, remotePlayer), BorderLayout.EAST);
		this.addReadyButtonActionListener();
		this.add(new StatusBar(), BorderLayout.SOUTH);
		this.add(new Chat(), BorderLayout.WEST);
		this.add(new BattlefieldViewer(localPlayer, remotePlayer), BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	
	public void whenConnectionIsSetButtonsEnable() {
		for(int i = 0; i < 5; i++) {
			localPlayer.getShips()[i].button.setEnabled(true);
		}
	}
	
	public void disableAllButtons() {
		// TODO implement this method to disable all Buttons after a connection closed!
	}
	


	// initializes taskbar
	private void initMenu() {
		// init JMenuBar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.ORANGE);
		JMenu fileMenu = new JMenu("Optionen");
		menuBar.add(fileMenu);
		this.setJMenuBar(menuBar);

		// Menu New Game
		JMenuItem newGame = new JMenuItem("Neues Spiel");
		newGame.addActionListener( actionListenerCreateGameButton );
		fileMenu.add(newGame);

		// Menu new Connection
		
		popupFrame = new JFrame("Verbindung herstellen");
		popupFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		popupFrame.setBounds(this.getX(), this.getY(), 180, 100);
		
		JPanel popupPanel = new JPanel();
		
		ipTextField = new JTextField("127.0.0.1");

		JButton connectButton = new JButton("verbinden");
		connectButton.addActionListener( actionListenerConnectButton );
		popupPanel.add(ipTextField);
		popupPanel.add(connectButton);
		popupFrame.add(popupPanel);
		
		JMenuItem newConnection = new JMenuItem("Neue Verbindung herstellen");
		newConnection.addActionListener( actionListenerInitializeConnectionButton );
		fileMenu.add(newConnection);
		
		
		// Menu cancel Connection
		JMenuItem cancelConnection = new JMenuItem("Verbindung abbrechen");
		// Action Listener definition for cancel connection
		cancelConnection.addActionListener( actionListenerCancelButton );
		fileMenu.add(cancelConnection);
		

		// Menu Exit Game
		fileMenu.addSeparator();
		JMenuItem exitGame = new JMenuItem("Beenden");
		exitGame.addActionListener(actionListenerExitButton);
		fileMenu.add(exitGame);
	}// initMenu
	
	
	// adds ready button on east panel an action listener
	public void addReadyButtonActionListener() {
		east.ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	localPlayer.areAllShipsSet()) {
					
					localPlayer.getBattlefield().setButtonsDisable();
					remotePlayer.getBattlefield().setButtonsDisable();
					east.ready.setEnabled(false);
					
					for(int i = 0; i < 10; i ++) {
						for(int j = 0; j < 10; j++) {
							remotePlayer.getBattlefield().getBoard()[i][j].isBoardShootable = true;
						}
					}
					
					// transfer local battlefield to server instance
					transmitBattlefield();
				}
			}
		});
	}

	/**
	 * method to start to game BattleShip
	 *  
	 * @param args
	 */
	public static void main(String[] args) {
		// startet das Spiel
		BattleshipGame game = new BattleshipGame();
	}

}
