package backend;

import java.awt.AWTEvent;

import network.Server;



/*
 * The Message Processor handles the logic (e.g. generate messages for clients, process messages from clients)
 *  
 */
public class MessageProcessor implements Runnable {

	private boolean powerSwitch = true;
	private Game game;
	
	/*
	 * constructor that add new object as observer in call object
	 */
	public MessageProcessor(Server ir_origin) {
		ir_origin.addObserver(ir_origin);
		// create game instance to manage whole game
		this.game = new Game();
	}

	public void switchOff() {
		this.powerSwitch = false;
	}

	/*
	 * react on event
	 */
	public void handleEvent(AWTEvent ir_event) {
		Action action;
		action = Helper.awtEventToAction(ir_event);
		
		// decide with key the process route
		switch( action.getKey() ) {
		case Helper.fire:
			System.out.println("Fire-Event received by Message Processcor!");
			break;
		case Helper.transmit:
			System.out.println("Battlefield received by Message Processor!");
			
			// create player instance
			Player player = new Player(action.getOrigin());
		
			// build battlefield with received data
			Battlefield rcvBattlefield = new Battlefield( action.getXPos(), action.getYPos(), action.getMisc() );
			
			// set received data objects to game 
			game.addPlayer(player, rcvBattlefield);
			
			// check if game is ready for first round
			if(game.isReady() == true) {
				// TODO: calculate player that starts the first round
				//		 send broadcast message to all clients and publish the decision
				
			}
			
			break;
		default:
			System.err.println("no specified Event received by Message Processor!");
		}
		
	}
	
	
	@Override
	public void run() {
		
		while (this.powerSwitch == true) {		
			
		}

	}

}
