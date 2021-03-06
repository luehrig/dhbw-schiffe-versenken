package backend;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import backend.exceptions.ConnectionIssueException;
import backend.exceptions.NetworkException;
import backend.exceptions.ServerException;

import frontend.BattleShipGame;

import network.Client;
import network.Server;

public class ActionController {

	private Game game;
	private static boolean horizontal;
	private boolean firsttime = true;

	private Server server;
	private Client client;
	private Thread clientThread;
	private Thread serverThread;


	private String currentPlayerName;

	private Player local;
	private Player remote;

	private String ipAddress;

	private BattleShipGame bsg;
	

	/*
	 * ActionContoller constructor
	 */
	public ActionController() {
		game = new Game();
	}

	/************************************************************************************************
	 * Getter & Setter
	 */

	// returns local player
	public Player getLocalPlayer() {
		return this.game.getPlayerOne();
	}

	// returns remote player
	public Player getRemotePlayer() {
		return this.game.getPlayerTwo();
	}

	// returns name of active player
	public String getCurrentPlayerName() {
		return this.currentPlayerName;
	}

	// set BattleShipGame
	public void setBattleShipGame(BattleShipGame bsg) {
		this.bsg = bsg;
	}

	// returns local IP
	public String getLocalIP() {
		InetAddress ipAdr = null;
		try {
			ipAdr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ipAdr.getHostAddress();
	}

	// return main JFrame
	public JFrame getUIInstance() {
		return this.bsg;
	}

	// set current player
	public void setCurrentPlayer(String currentPlayerIP) {

		if (this.getLocalIP().equals(currentPlayerIP))

			this.bsg.getStatusBar().setPlayer("It�s your turn!");
		else if (this.getRemotePlayer().getIP().equals(currentPlayerIP))
			this.bsg.getStatusBar().setPlayer(
					"It�s " + this.remote.getName() + "'s turn!");
	}

	public void setNoHit(String shotTargetIP) {

		if (!this.getLocalIP().equals(shotTargetIP))
			this.bsg.getStatusBar().setGameInfo("You didn�t hit!");
		else
			this.bsg.getStatusBar().setGameInfo("");
	}

	// set info at statusbar
	public void setShipHit(String[] miscParts) {

		if (!miscParts[1].equals(this.getLocalIP())) { // an enemy ship is hit
			if (miscParts.length == 3) {
				this.bsg.getStatusBar().setGameInfo(
						this.remote.getName() + "'s " + miscParts[0]
								+ " sink!!!");
				this.bsg.getLeftSetupView().setShipSink(miscParts[0]);
			} else
				this.bsg.getStatusBar().setGameInfo(
						"You hit " + this.remote.getName() + "'s "
								+ miscParts[0] + "!!!");
		} else { // an own ship is hit
			if (miscParts.length == 3) {
				this.bsg.getStatusBar().setGameInfo(
						"Your " + miscParts[0] + " sink!");
				this.bsg.getRightSetupView().setShipSink(miscParts[0]);
			} else
				this.bsg.getStatusBar().setGameInfo(
						"Your " + miscParts[0] + " was hit!");
		}

	}

	// set info
	public void setInfoOnStatusbar(String info) {
		this.bsg.getStatusBar().setGeneralInfo(info);
	}

	// set player
	public void setPlayer(String name) throws UnknownHostException {
		// game.setPlayer(name);

		local = new Player(name);
		local.setIP(InetAddress.getLocalHost().getHostAddress().toString());
		remote = new Player("remote");
		game.addPlayer(local, local.getBattlefield());
		game.addPlayer(remote, remote.getBattlefield());

	}

	// returns ship button which is asked of local player
	public JButton getLocalShipButton(Ship.Type shiptype) {
		return this.game.getPlayerOne().getShip(shiptype).getButton();
	}

	// returns ship button which is asked of remote player
	public JButton getRemoteShipButton(Ship.Type shiptype) {
		return this.game.getPlayerTwo().getShip(shiptype).getButton();
	}

	/*************************************************************************************************
	 * Private Methods
	 */

	/*
	 * set all Buttons Enabled
	 */
	private void whenConnectionIsSetButtonsEnable() {
		for (int i = 0; i < 5; i++) {
			this.game.getPlayerOne().getShips()[i].getButton().setEnabled(true);
		}
	}

	/*
	 * set all buttons in correct state if client establish connection to server
	 */
	public void whenConnectionIsEstablished() {
		whenConnectionIsSetButtonsEnable();
		this.game.getPlayerOne().getBattlefield().setBattlefieldShotable();

		this.bsg.getRightSetupView().enableReadyButton();
		this.bsg.getMenu().disableNewGame();
		this.bsg.getMenu().disableItems();
	}

	/*
	 * transmit local initialized battlefield to server
	 */
	private void transmitBattlefield() {
		Action transmitAction;
		AWTEvent rr_event;
		String battlefieldAsString;
		String cmd;

		// convert local battlefield to string
		battlefieldAsString = this.game.getPlayerOne().getBattlefield()
				.toString();

		// build action, pack into event that should be transfered to server
		transmitAction = null;
		try {
			transmitAction = new Action(InetAddress.getLocalHost()
					.getHostAddress().toString(), Helper.transmit, this.game
					.getPlayerOne().getBattlefield().getColNumber(), this.game
					.getPlayerOne().getBattlefield().getRowNumber(),
					battlefieldAsString);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		rr_event = new ActionEvent(transmitAction, 0, Helper.transmit);

		// convert event to command and transfer to server
		cmd = Helper.eventToCommand(rr_event);
		client.sendCommand(cmd);
	}

	/*
	 * transmit local player name to server
	 */
	private void transmitPlayerName() {
		// build action, pack into event that should be transfered to server
		Action transmitAction = null;
		ActionEvent rr_event = null;
		String cmd = null;

		try {
			transmitAction = new Action(InetAddress.getLocalHost()
					.getHostAddress().toString(), Helper.playername, 0, 0, this
					.getLocalPlayer().getName());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		rr_event = new ActionEvent(transmitAction, 0, Helper.playername);

		// convert event to command and transfer to server
		cmd = Helper.eventToCommand(rr_event);
		this.client.sendCommand(cmd);
	}

	/*
	 * creates server on local machine (incl. save reference for Server object)
	 * and connect local user to server
	 */
	private void createServer() throws NetworkException {
		// create new server instance on port 6200 in separate thread
		Runnable server = null;
		try {
			server = new Server(6200, this);
		} catch (UnknownHostException e1) {
			this.handleException(new ServerException(e1.getMessage()));
		}
		serverThread = new Thread(server);
		serverThread.start();
		this.server = (Server) server;
		try {
			connectToPartner(InetAddress.getLocalHost().getHostAddress(), 6200);
		} catch (UnknownHostException e) {
			throw new NetworkException("local network error!");
		}
		// unlock all GUI elements
		whenConnectionIsSetButtonsEnable();
		this.game.getPlayerOne().getBattlefield().setBattlefieldShotable();

	}

	/*
	 * connect to partner and save reference for Client object
	 */
	private void connectToPartner(String iv_ip, int iv_port)
			throws UnknownHostException, ConnectionIssueException {
		// try to connect to server side
		Runnable client = new Client(iv_ip, iv_port);
		// start new thread
		clientThread = new Thread(client);
		clientThread.start();
		// save reference to client object for later processing
		this.client = (Client) client;
		// add GUI to client to receive broadcast events from server
		this.client.addController(this);
		// save ip in player object
		// TODO: Modify player name !!
		// this.game.getPlayerByName(this.getLocalPlayer().getName()).setIP(
		// InetAddress.getLocalHost().getHostAddress());

		// unlock all GUI elements
		// whenConnectionIsSetButtonsEnable();
		// this.game.getPlayerOne().getBattlefield().setBattlefieldShotable();

		// this.setInfoOnStatusbar("You have connected successfully");
	}

	/*
	 * terminate client connection and shutdown local server if is running
	 */
	private void cancelGame() {
		// shutdown client
		client.switchOff();
		// if local server is running -> server shutdown
		if (server != null) {
			server.switchOff();
		}
		// call finalize methods to make sure that all objects are thrown away
		client.finalize();
		if (server != null) {
			server.finalize();
		}

		// buffer local player
		Player localPlayer = this.game.getPlayerOne();

		this.game.forceDestroyGame();

		this.game.setPlayer(this.local.getName());
	}

	private void cancelGameByClient() {
		// buffer local player
		Player localPlayer = this.game.getPlayerOne();

		this.game.forceDestroyGame();
		this.game.setPlayer(this.local.getName());
	}

	/*
	 * send new game command to server
	 */
	private void transmitNewGame() {
		// build action, pack into event that should be transfered to server
		Action transmitAction = null;
		ActionEvent rr_event = null;
		String cmd = null;

		try {
			transmitAction = new Action(InetAddress.getLocalHost()
					.getHostAddress().toString(), Helper.newgame, 0, 0, this
					.getLocalPlayer().getIP());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}
		rr_event = new ActionEvent(transmitAction, 0, Helper.newgame);

		// convert event to command and transfer to server
		cmd = Helper.eventToCommand(rr_event);
		client.sendCommand(cmd);
	}

	/**************************************************************************************************
	 * ActionHandller
	 * 
	 * @throws UnknownHostException
	 * @throws ConnectionIssueException
	 */

	/*
	 * set IP Address of enemy
	 */
	public void setRemoteIPadress(KeyEvent e) throws UnknownHostException {
		JTextField tField;

		tField = (JTextField) e.getSource();
		this.ipAddress = tField.getText();
		try {
			connectToPartner(this.ipAddress, 6200);
		} catch (ConnectionIssueException e1) {
			this.handleException(e1);
			return;
		}

		/*
		 * whenConnectionIsSetButtonsEnable();
		 * this.game.getPlayerOne().getBattlefield().setBattlefieldShotable();
		 * 
		 * this.bsg.getMenu().disableNewGame();
		 * this.bsg.getMenu().disableItems();
		 */
	}

	/*
	 * react on event from Server
	 */
	public void handleEvent(AWTEvent ir_event) {
		System.err.println("GUI instance received Action from Server!");

		String[] miscParts;

		Action action = Helper.awtEventToAction(ir_event);

		switch (action.getKey()) {
		case Helper.start:
			miscParts = Helper.splitString(action.getMisc());

			if (miscParts[0].equals(this.getLocalPlayer().getIP())) {
				this.getRemotePlayer().setIP(miscParts[2]);
				this.getRemotePlayer().setName(miscParts[3]);
			} else {
				this.getRemotePlayer().setIP(miscParts[0]);
				this.getRemotePlayer().setName(miscParts[1]);
			}

			this.bsg.getLeftSetupView().setEnemyName(
					this.getRemotePlayer().getName() + "`s Ships:");

			this.bsg.getLeftSetupView().setToogleButtonEnabled();

			// set current Player
			this.setCurrentPlayer(miscParts[4]);

			if (miscParts[4].equals(this.getLocalPlayer().getIP()) == true) {
				// TODO
				// check if buttons were enabled too?
				this.game.getPlayerTwo().getBattlefield()
						.setBattlefieldShotable();
			}

			this.setInfoOnStatusbar("You play with "
					+ this.getRemotePlayer().getName() + "!");

			break;
		case Helper.hit:
			System.out.println("Shot hit ship!");

			Shot shot0 = new Shot(action.getXPos(), action.getYPos());

			miscParts = action.getMisc().split("\\,");

			// branch for local player
			if (miscParts[1].equals(this.game.getPlayerOne().getIP())) {
				this.game.getPlayerOne().getBattlefield().setShot(shot0);
				// TODO this.setShipHit(action.getMisc());
				if (miscParts.length == 3) {
					this.bsg.getRightSetupView().setShipSink(miscParts[0]);
				}

			}
			// branch for remote player
			else {
				this.game.getPlayerTwo().getBattlefield().setShotInGUI(shot0);

				// this.game.getPlayerTwo().getBattlefield().getTile(action.getXPos(),
				// action.getYPos()).setShip(Ship.Type.valueOf(miscParts[0]));
				// TODO this.setShipHit(action.getMisc());
				if (miscParts.length == 3) {
					this.bsg.getLeftSetupView().setShipSink(miscParts[0]);
				}
				// this should be redirected to gui, because it includes ship
				// kind that was hit
				// Ship.Type.valueOf(miscParts[0])

			}

			this.setShipHit(miscParts);

			break;
		case Helper.nohit:
			System.out.println("Shot hit NO ship!");

			Shot shot1 = new Shot(action.getXPos(), action.getYPos());
			// branch for local player handling
			if (action.getMisc().equals(this.game.getPlayerOne().getIP())) {
				this.game.getPlayerOne().getBattlefield().setShot(shot1);
				this.game.getPlayerTwo().getBattlefield()
						.setBattlefieldShotable();
			}
			// branch for remote player handling
			else {
				this.game.getPlayerTwo().getBattlefield().setFailInGUI(shot1);
				this.game.getPlayerTwo().getBattlefield()
						.setBattlefieldNotShotable();
			}

			this.setNoHit(action.getMisc());
			this.setCurrentPlayer(action.getMisc());

			break;
		case Helper.winner:
			System.out.println("one player wins the game");
			this.game.getPlayerOne().getBattlefield()
					.setBattlefieldNotShotable();
			this.game.getPlayerTwo().getBattlefield()
					.setBattlefieldNotShotable();

			this.bsg.getBattlefieldViewer().winner();
			this.bsg.getLeftSetupView().setToggleButtonDisabled();
			if (action.getMisc().equals(this.game.getPlayerOne().getIP()))
				this.bsg.getStatusBar().setGameInfo("You win!!! :)");
			else
				this.bsg.getStatusBar().setGameInfo("You lose!!! :(");

			this.bsg.getStatusBar().setPlayer("");

			break;
		case Helper.newgame:
			if (action.getMisc().equals(Helper.success)) {
				// buffer local name and ip to start new game
				String localIP = this.getLocalPlayer().getIP();
				String localName = this.getLocalPlayer().getName();

				// clear game instance (local player and remote battlefield)
				this.game.forceDestroyGame();

				// this.remote = null;
				// this.local = new Player(localName);

				// initialize new game instance
				try {
					this.setPlayer(localName);
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				this.game.getPlayerOne().setIP(localIP);

				// rebuild UI
				this.bsg.rebuild();

				// unlock all UI elements that are necessary for setting up
				// ships
				this.whenConnectionIsSetButtonsEnable();
				this.game.getPlayerOne().getBattlefield()
						.setBattlefieldShotable();
				if (this.server != null) {
					this.bsg.getMenu().disableItemsServer();
					this.bsg.getMenu().stopServer();
				}

				this.setInfoOnStatusbar("A new Game starts!");

			}
			break;
		case Helper.toggle:
			if (!action.getOrigin().equals(this.game.getPlayerOne().getIP())) {
				this.bsg.toggleGUI();

			}
			break;
		}
	}
	


	/*
	 * handle all exceptions that occurres while client is running
	 */
	public void handleException(Exception ir_exception) {
		// display exception in console
		System.err.println(ir_exception.getClass().getSimpleName()
				+ ir_exception.getMessage());

		// display exception on status bar
		this.bsg.getStatusBar().setError(ir_exception.getMessage());

	}

	public void resetUIAfterIssue() {
		this.cancelGame();
		this.bsg.rebuild();
		this.bsg.getRightSetupView().disableReadyButton();
		this.bsg.getMenu().enableItems();
	}

	public void resetUIAfterIssueByClient() {
		this.cancelGameByClient();
		this.bsg.rebuild();
		this.bsg.getRightSetupView().disableReadyButton();
		this.bsg.getMenu().enableItems();
	}

	/*
	 * handles all events of Menu
	 */
	public void handleMenuAction(ActionEvent e) {
		JMenuItem item = (JMenuItem) e.getSource();

		String button = item.getText();

		switch (button) {
		// if new game item
		case "New Game":
			this.transmitNewGame();
			this.bsg.getRightSetupView().disableReadyButton();
			break;
		// if new server
		case "New Server":
			try {
				this.createServer();
			} catch (NetworkException e1) {
				this.handleException(e1);
			}
			this.bsg.getMenu().disableItemsServer();
			this.bsg.getMenu().stopServer();
			this.bsg.getRightSetupView().enableReadyButton();
			break;
		// if close server
		case "Close Server":
			this.resetUIAfterIssue();
			// this.server.switchOff();
			this.bsg.getMenu().startServer();
			break;
		// if disconnect
		case "Disconnect":
			this.resetUIAfterIssue();
			this.game.getPlayerTwo().getBattlefield()
					.setBattlefieldNotShotable();
			break;
		// if exit game
		case "Exit Game":
			System.exit(0);
			break;
		}

	}

	/*
	 * handles shot of local player
	 */
	public void handleShot(ActionEvent e) {
		Point point = this.game.getPlayerTwo().getBattlefield()
				.getTileCoords((Tile) e.getSource());
		if (this.game.getPlayerTwo().getBattlefield().getBoard()[point.x][point.y]
				.isBoardShotable()) {

			Action fireAction = null;
			try {
				fireAction = new Action(InetAddress.getLocalHost()
						.getHostAddress().toString(), Helper.fire, point.x,
						point.y, "");
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			AWTEvent rr_event = new ActionEvent(fireAction, 0, Helper.fire);

			String cmd = Helper.eventToCommand(rr_event);
			// send action string to server
			client.sendCommand(cmd);
		}
	}

	/*
	 * handles all events of RightSetupView
	 */
	public void handleRightSetupViewAction(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		// if ready button
		if (button.getText().equals("Ready!")) {
			if (this.game.getPlayerOne().areAllShipsSet()) {

				if (this.firsttime == true) {
					// transmit Player name to server
					this.transmitPlayerName();
					this.firsttime = false;
				}

				this.game.getPlayerOne().getBattlefield()
						.setBattlefieldNotShotable();
				this.game.getPlayerTwo().getBattlefield()
						.setBattlefieldNotShotable();
				((JButton) e.getSource()).setEnabled(false);

				// activate tiles in enemies battlefield
				// for (int i = 0; i < 10; i++) {
				// for (int j = 0; j < 10; j++) {
				// this.game.getPlayerTwo().getBattlefield().getBoard()[i][j]
				// .setBoardShotable();
				// }
				// }

				// transfer local battlefield to server instance
				transmitBattlefield();
			}
		}

		// if destroyer button
		if (button.getText().equals("Destroyer")) {
			this.game.getPlayerOne().selectedShip = this.game.getPlayerOne()
					.getShips()[this.game.getPlayerOne().destroyer];
			this.game.getPlayerOne().isMouseListenerActive = true;
		}

		// if aircraftcarrier button
		if (button.getText().equals("Aircraftcarrier")) {
			this.game.getPlayerOne().selectedShip = this.game.getPlayerOne()
					.getShips()[this.game.getPlayerOne().aircraftcarrier];
			this.game.getPlayerOne().isMouseListenerActive = true;
		}

		// if battleship button
		if (button.getText().equals("Battleship")) {
			this.game.getPlayerOne().selectedShip = this.game.getPlayerOne()
					.getShips()[this.game.getPlayerOne().battleship];
			this.game.getPlayerOne().isMouseListenerActive = true;
		}

		// if submarine button
		if (button.getText().equals("Submarine")) {
			this.game.getPlayerOne().selectedShip = this.game.getPlayerOne()
					.getShips()[this.game.getPlayerOne().submarine];
			this.game.getPlayerOne().isMouseListenerActive = true;
		}

		// if patrolboat button
		if (button.getText().equals("PatrolBoat")) {
			this.game.getPlayerOne().selectedShip = this.game.getPlayerOne()
					.getShips()[this.game.getPlayerOne().patrolboat];
			this.game.getPlayerOne().isMouseListenerActive = true;
		}

		if (button.getText().equals("Play")) {
			this.bsg.initGUI();
		}

	}

	/*
	 * handles all events of LeftSetupView
	 */
	public void handleLeftSetupViewAction(ActionEvent e) {
		JButton button = (JButton) e.getSource();

		// if ready button
		if (button.getName().equals("toggle")) {
			// this.transmitNewGame();
			

			Action transmitAction = null;
			ActionEvent rr_event = null;
			String cmd = null;

			try {
				transmitAction = new Action(InetAddress.getLocalHost()
						.getHostAddress().toString(), Helper.toggle, 0, 0, "");
			} catch (UnknownHostException e1) {
				e1.printStackTrace();
			}
			rr_event = new ActionEvent(transmitAction, 0, Helper.toggle);

			// convert event to command and transfer to server
			cmd = Helper.eventToCommand(rr_event);
			client.sendCommand(cmd);

		}

	}

	/*********************************
	 * mouselistener of battlefield
	 */

	/*
	 * if left mouse button is clicked - set ship on this place - disable ship
	 * button
	 * 
	 * if right mouse button is clicked -set ship position horizontal/vertical
	 */
	public void handleBattlefieldViewerMouseClicked(MouseEvent e) {

		if (e.getButton() == MouseEvent.BUTTON1) {
			Point point = this.game.getPlayerOne().getBattlefield()
					.getTileCoords((Tile) e.getSource());

			if (this.game.getPlayerOne().selectedShip instanceof ships.AircraftCarrier
					&& !this.game.getPlayerOne().isShipSet(
							this.game.getPlayerOne().aircraftcarrier)) {

				if (horizontal) {
					if (point.y <= 5
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 1]
											.isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 2]
											.isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 3]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 4]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 1]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 2]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 3]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 4]
								.setShip(Ship.Type.AIRCRAFTCARRIER);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().aircraftcarrier);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().aircraftcarrier].getButton()
								.setEnabled(false);

					}
				} else if (!horizontal) {
					if (point.x <= 5
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 1][point.y]
											.isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 2][point.y]
											.isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 3][point.y]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x + 4][point.y]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 1][point.y]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 2][point.y]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 3][point.y]
								.setShip(Ship.Type.AIRCRAFTCARRIER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 4][point.y]
								.setShip(Ship.Type.AIRCRAFTCARRIER);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().aircraftcarrier);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().aircraftcarrier].getButton()
								.setEnabled(false);
					}
				}

			}

			if (this.game.getPlayerOne().selectedShip instanceof ships.PatrolBoat
					&& !this.game.getPlayerOne().isShipSet(
							this.game.getPlayerOne().patrolboat)) {
				if (horizontal) {
					if (point.y <= 8
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 1]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.PATROLBOAT);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 1]
								.setShip(Ship.Type.PATROLBOAT);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().patrolboat);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().patrolboat].getButton()
								.setEnabled(false);
					}
				} else if (!horizontal) {
					if (point.x <= 8
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x + 1][point.y]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.PATROLBOAT);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 1][point.y]
								.setShip(Ship.Type.PATROLBOAT);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().patrolboat);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().patrolboat].getButton()
								.setEnabled(false);
					}
				}

			}

			if (this.game.getPlayerOne().selectedShip instanceof ships.Submarine
					&& !this.game.getPlayerOne().isShipSet(
							this.game.getPlayerOne().submarine)) {
				if (horizontal) {
					if (point.y <= 7
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 1]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 2]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.SUBMARINE);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 1]
								.setShip(Ship.Type.SUBMARINE);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 2]
								.setShip(Ship.Type.SUBMARINE);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().submarine);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().submarine].getButton()
								.setEnabled(false);
					}
				} else if (!horizontal) {
					if (point.x <= 7
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 1][point.y]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x + 2][point.y]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.SUBMARINE);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 1][point.y]
								.setShip(Ship.Type.SUBMARINE);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 2][point.y]
								.setShip(Ship.Type.SUBMARINE);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().submarine);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().submarine].getButton()
								.setEnabled(false);
					}
				}

			}

			if (this.game.getPlayerOne().selectedShip instanceof ships.Destroyer
					&& !this.game.getPlayerOne().isShipSet(
							this.game.getPlayerOne().destroyer)) {
				if (horizontal) {
					if (point.y <= 7
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 1]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 2]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.DESTROYER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 1]
								.setShip(Ship.Type.DESTROYER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 2]
								.setShip(Ship.Type.DESTROYER);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().destroyer);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().destroyer].getButton()
								.setEnabled(false);
					}
				} else if (!horizontal) {
					if (point.x <= 7
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 1][point.y]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x + 2][point.y]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.DESTROYER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 1][point.y]
								.setShip(Ship.Type.DESTROYER);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 2][point.y]
								.setShip(Ship.Type.DESTROYER);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().destroyer);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().destroyer].getButton()
								.setEnabled(false);
					}
				}

			}

			if (this.game.getPlayerOne().selectedShip instanceof ships.BattleShip
					&& !this.game.getPlayerOne().isShipSet(
							this.game.getPlayerOne().battleship)) {
				if (horizontal) {
					if (point.y <= 6
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 1]
											.isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x][point.y + 2]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 3]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.BATTLESHIP);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 1]
								.setShip(Ship.Type.BATTLESHIP);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 2]
								.setShip(Ship.Type.BATTLESHIP);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y + 3]
								.setShip(Ship.Type.BATTLESHIP);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().battleship);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().battleship].getButton()
								.setEnabled(false);
					}
				} else if (!horizontal) {
					if (point.x <= 6
							&& (!this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 1][point.y]
											.isShip()
									&& !this.game.getPlayerOne()
											.getBattlefield().getBoard()[point.x + 2][point.y]
											.isShip() && !this.game
									.getPlayerOne().getBattlefield().getBoard()[point.x + 3][point.y]
									.isShip())) {
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x][point.y]
								.setShip(Ship.Type.BATTLESHIP);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 1][point.y]
								.setShip(Ship.Type.BATTLESHIP);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 2][point.y]
								.setShip(Ship.Type.BATTLESHIP);
						this.game.getPlayerOne().getBattlefield().getBoard()[point.x + 3][point.y]
								.setShip(Ship.Type.BATTLESHIP);

						this.game.getPlayerOne().isMouseListenerActive = false;
						this.game.getPlayerOne().setShip(
								this.game.getPlayerOne().battleship);
						this.game.getPlayerOne().getShips()[this.game
								.getPlayerOne().battleship].getButton()
								.setEnabled(false);
					}
				}

			}
		}

		if (e.getButton() == MouseEvent.BUTTON3) {
			if (horizontal) {
				horizontal = false;
				this.handleBattlefieldViewerMouseExited(e);
				this.handleBattlefieldViewerMouseEntered(e);
			} else if (!horizontal) {
				horizontal = true;
				this.handleBattlefieldViewerMouseExited(e);
				this.handleBattlefieldViewerMouseEntered(e);
			}
		}

	}// mouseclicked

	/*
	 * if mouse enter a tile show "ship shadow" on this position
	 */
	public void handleBattlefieldViewerMouseEntered(MouseEvent e) {

		if (this.game.getPlayerOne().isMouseListenerActive) {
			if (e.getSource() instanceof Tile
					&& !((Tile) e.getSource()).isShip()) {

				Point point = this.game.getPlayerOne().getBattlefield()
						.getTileCoords((Tile) e.getSource());

				if (this.game.getPlayerOne().selectedShip instanceof ships.AircraftCarrier) {

					if (horizontal) {
						if (point.y <= 5
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 1]
												.isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 2]
												.isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 3]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y + 4]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 1]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 2]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 3]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 4]
									.setSelected();
						}
					} else if (!horizontal) {
						if (point.x <= 5
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 1][point.y]
												.isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 2][point.y]
												.isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 3][point.y]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x + 4][point.y]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 1][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 2][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 3][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 4][point.y]
									.setSelected();
						}
					}
				}

				if (this.game.getPlayerOne().selectedShip instanceof ships.PatrolBoat) {
					if (horizontal) {
						if (point.y <= 8
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y + 1]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 1]
									.setSelected();
						}
					} else if (!horizontal) {
						if (point.x <= 8
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x + 1][point.y]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 1][point.y]
									.setSelected();
						}
					}

				}

				if (this.game.getPlayerOne().selectedShip instanceof ships.Submarine) {
					if (horizontal) {
						if (point.y <= 7
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 1]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y + 2]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 1]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 2]
									.setSelected();
						}
					} else if (!horizontal) {
						if (point.x <= 7
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 1][point.y]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x + 2][point.y]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 1][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 2][point.y]
									.setSelected();
						}
					}

				}

				if (this.game.getPlayerOne().selectedShip instanceof ships.Destroyer) {
					if (horizontal) {
						if (point.y <= 7
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 1]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y + 2]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 1]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 2]
									.setSelected();
						}
					} else if (!horizontal) {
						if (point.x <= 7
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 1][point.y]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x + 2][point.y]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 1][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 2][point.y]
									.setSelected();
						}
					}

				}

				if (this.game.getPlayerOne().selectedShip instanceof ships.BattleShip) {
					if (horizontal) {
						if (point.y <= 6
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 1]
												.isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x][point.y + 2]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y + 3]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 1]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 2]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y + 3]
									.setSelected();
						}
					} else if (!horizontal) {
						if (point.x <= 6
								&& (!this.game.getPlayerOne().getBattlefield()
										.getBoard()[point.x][point.y].isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 1][point.y]
												.isShip()
										&& !this.game.getPlayerOne()
												.getBattlefield().getBoard()[point.x + 2][point.y]
												.isShip() && !this.game
										.getPlayerOne().getBattlefield()
										.getBoard()[point.x + 3][point.y]
										.isShip())) {
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x][point.y].setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 1][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 2][point.y]
									.setSelected();
							this.game.getPlayerOne().getBattlefield()
									.getBoard()[point.x + 3][point.y]
									.setSelected();
						}
					}

				}

			}
		}

	}// mouse entered

	/*
	 * if mouse exit the tile which was entered show water again
	 */
	public void handleBattlefieldViewerMouseExited(MouseEvent e) {

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (this.game.getPlayerOne().getBattlefield().getBoard()[i][j]
						.isSelected()) {
					this.game.getPlayerOne().getBattlefield().getBoard()[i][j]
							.setWater();
				}
			}
		}

	}
}
