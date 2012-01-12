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
		case Helper.fire:
			System.out.println("Fire-Event received by Message Processcor!");
			break;
		case Helper.transmit:
			System.out.println("Battlefield received by Message Processor!");

			// create player instance
			Player player = new Player(action.getOrigin());

			// build battlefield with received data
			Battlefield rcvBattlefield = new Battlefield(action.getXPos(),
					action.getYPos(), action.getMisc());

			// critical section begin
			initialize.acquireUninterruptibly();
			// set received data objects to game
			game.addPlayer(player, rcvBattlefield);

			// check if game is ready for first round
			if (game.isReady() == true) {
				// send broadcast message to all clients and publish the
				// decision
				String cmd = Helper.server + "," + Helper.start + ",0,0,"
						+ this.calculatePlayerToStart().getName();
				this.fireEvent(Helper.commandToEvent(cmd));
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
