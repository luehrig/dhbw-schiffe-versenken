package backend;

import java.awt.AWTEvent;
import java.util.concurrent.Semaphore;

import network.Server;

/*
 * The Message Processor handles the logic (e.g. generate messages for clients, process messages from clients)
 *  
 */
public class MessageProcessor implements Runnable {

	private boolean powerSwitch = true;
	private Game game;
	private Server observer;
	private static Semaphore initialize = new Semaphore(1);

	/*
	 * constructor that add new object as observer in call object
	 */
	public MessageProcessor(Server ir_origin) {
		ir_origin.addObserver(ir_origin);
		observer = ir_origin;
		// create game instance to manage whole game
		this.game = new Game();
	}

	public void switchOff() {
		this.powerSwitch = false;
	}

	/*
	 * forward event to observer
	 */
	public void fireEvent(AWTEvent ir_event) {
		this.observer.handleEvent(ir_event);
	}

	/*
	 * react on event
	 */
	public void handleEvent(AWTEvent ir_event) {
		Action action;
		action = Helper.awtEventToAction(ir_event);

		// decide with key the process route
		switch (action.getKey()) {
		/*
		 * fire command received
		 * 1. check which player sent command and is this player allowed to send fire command at the the moment
		 * 2. "fire" command to battlefield of other player
		 * 3. receive result of "fire" and build message that includes result
		 * 4. fire event broadcast to all clients
		 * 
		 */
		case Helper.fire:
			System.out.println("Fire-Event received by Message Processcor!");
			
			// accept shot only if current player is same as origin
			if(action.getOrigin().equals(this.game.getCurrentPlayer().getIP())) {
				
				// get battlefield of enemy
				Battlefield workingBattlefield = this.game.getEnemiesBattlefield(this.game.getCurrentPlayer());
				Shot shot = new Shot(action.getXPos(), action.getYPos());
				
				boolean result = workingBattlefield.setShot(shot);
				
				String cmd = "";
				if(result == true) {
					// hit
					// get ship kind
					Tile workingTile[][] = workingBattlefield.getBoard();
					Ship.Type shipKind = workingTile[action.getXPos()][action.getYPos()].getShipStatus(); 
					
					cmd = Helper.server + "," + Helper.hit + "," + Integer.toString(action.getXPos()) + "," + Integer.toString(action.getYPos()) + "," + shipKind.toString() + "," + this.game.getSuspendedPlayer().getName();
					this.fireEvent(Helper.commandToEvent(cmd));
				}
				else {
					//no hit : misc include player name of source and destination
					cmd = Helper.server + "," + Helper.nohit + "," + Integer.toString(action.getXPos()) + "," + Integer.toString(action.getYPos()) + "," + this.game.getSuspendedPlayer().getName();
					this.fireEvent(Helper.commandToEvent(cmd));
					
					this.game.setCurrentPlayer( this.game.getSuspendedPlayer() );
					
					cmd = Helper.server + "," + Helper.start + ",0,0,"
							+ this.game.getCurrentPlayer().getName();
					this.fireEvent(Helper.commandToEvent(cmd));
					// switch game status to "started"
					this.game.setGameStarted();
				}
				
			}
			else {
				System.err.println(action.getOrigin() + " is NOT allowed to send fire event at the moment!");
			}
			
			
			
			break;
		case Helper.transmit:
			System.out.println("Battlefield received by Message Processor!");

			// create player instance
			// TODO: name
			//Player player = new Player(action.getOrigin());
			
			Player player = new Player("Player");
			player.setIP(action.getOrigin());

			// build battlefield with received data
			Battlefield rcvBattlefield = new Battlefield(action.getXPos(),
					action.getYPos(), action.getMisc());

			// critical section begin
			initialize.acquireUninterruptibly();
			// set received data objects to game
			game.addPlayer(player, rcvBattlefield);

			// check if game is ready for first round
			if (game.isReady() == true) {
				// select by random which player starts the first round
				this.game.setCurrentPlayer(this.calculatePlayerToStart());
				// send broadcast message to all clients and publish the
				// decision
				String cmd = Helper.server + "," + Helper.start + ",0,0,"
						+ this.game.getCurrentPlayer().getIP();	// getName()
				this.fireEvent(Helper.commandToEvent(cmd));
				// switch game status to "started"
				this.game.setGameStarted();
			}
			// critical section end
			initialize.release();

			break;
		default:
			System.err
					.println("no specified Event received by Message Processor!");
		}

	}

	/*
	 * get random player that is allowed to start the first round
	 */
	private Player calculatePlayerToStart() {
		int random = Helper.getRandomInteger(1, 2);
		if (random == 1) {
			return this.game.getPlayerOne();
		} else {
			return this.game.getPlayerTwo();
		}
	}

	@Override
	public void run() {

		while (this.powerSwitch == true) {

		}

	}

}
