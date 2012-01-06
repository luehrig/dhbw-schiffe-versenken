package network;


import java.awt.AWTEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;


import schiffe_versenken.Helper;

public abstract class NetworkObject {

	protected PrintWriter writerOut;
	protected BufferedReader readerIn;
	private LinkedList<NetworkObject> observer = new LinkedList();
	
	/*
	 * react on event
	 */
	private void handleEvent(AWTEvent ir_event) {
		// do something useful
	}
	
	/*
	 * add new observer to current object
	 */
	public void addObserver(NetworkObject ir_object) {
		observer.add(ir_object);
	}
	
	/*
	 * forward event to all observers
	 */
	public void fireEvent(AWTEvent ir_event) {
		for(NetworkObject object:observer) {
			object.handleEvent(ir_event);
		}
	}
	
	
	/*
	 * send command
	 * */
	public void sendCommand(String iv_command) {
		writerOut.println(iv_command);
	}

	
	/*
	 * receive command
	 * @return null if no command was received
	 * */
	public String receiveCommand() {
		String rv_command = null;
		try {
			rv_command = readerIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// check if message was correct, if not:
		// send command to partner and wait for next response
		if (this.isCommandValid(rv_command) != true) {
			this.sendCommand("RESEND");
			rv_command = this.receiveCommand();
		}

		this.sendCommand("OK");
		return rv_command;
	}
	
	/*
	 * method to check if incoming command has correct hash
	 * */
	public boolean isCommandValid(String iv_command) {
		return Helper.isCommandValid(iv_command);
	}
}
