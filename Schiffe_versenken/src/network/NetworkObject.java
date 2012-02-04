package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import backend.Helper;
import backend.exceptions.ConnectionIssueException;



public abstract class NetworkObject {

	protected PrintWriter writerOut;
	protected BufferedReader readerIn;
	protected String sendBuffer;

	/*
	 * send command
	 * */
	public void sendCommand(String iv_command) {
		this.writerOut.println(iv_command);
		this.sendBuffer = iv_command;
	}

	
	/*
	 * receive command
	 * @return null if no command was received
	 * */
	public String receiveCommand() throws ConnectionIssueException {
		String rv_command = null;
		try {
			rv_command = readerIn.readLine();
		} catch (IOException e) {
			throw new ConnectionIssueException();
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
