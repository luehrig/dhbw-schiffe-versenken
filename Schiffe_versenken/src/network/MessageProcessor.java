package network;

import java.awt.AWTEvent;
import java.awt.event.ActionListener;

import schiffe_versenken.Action;
import schiffe_versenken.Helper;

/*
 * The Message Processor handles the logic (e.g. generate messages for clients, process messages from clients)
 *  
 */
public class MessageProcessor extends NetworkObject implements Runnable {

	private boolean powerSwitch = true;
	
	/*
	 * constructor that add new object as observer in call object
	 */
	public MessageProcessor(NetworkObject ir_origin) {
		ir_origin.addObserver(ir_origin);
		
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
