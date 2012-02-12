package network;

import java.io.*;
import java.net.*;

import backend.ActionController;
import backend.Helper;
import backend.exceptions.ConnectionIssueException;
import backend.exceptions.ConnectionLostException;

public class Client extends NetworkObject implements Runnable {

	/*
	 * inner class that realize a keep alive service
	 */
	private class KeepAliveThread extends Thread {

		@SuppressWarnings("unused")
		private long max_timeout;
		private PrintWriter outboundPlug;

		/*
		 * constructor
		 */
		public KeepAliveThread(PrintWriter ir_outbound) {
			super("KeepAliveThread");
			this.outboundPlug = ir_outbound;
		}

		/*
		 * send "PING" to partner every 10 seconds
		 * 
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			while (powerSwitch == true) {
				this.outboundPlug.println("PING");
				//System.out.println("Client: " + ip + " Ping");
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private String ip;
	private int communicationPort;
	private Socket communicationSocket;
	private boolean powerSwitch = true;
	private KeepAliveThread keepAliveThread = null;
	private String receivedCommand;
	private int errorCount;
	private ActionController actController;

	private final int maxErrorCount = 2;

	/*
	 * creates client object including a connection to server
	 */
	public Client(String iv_ip, int iv_port) {
		this.ip = iv_ip;
		this.communicationPort = iv_port;
	}

	/*
	 * return IP of client
	 */
	public String getIP() {
		return this.ip;
	}
	
	/*
	 * add ActionController reference for event handling to client object
	 */
	public void addController(ActionController actController) {
		this.actController = actController;
	}

	/*
	 * this method shutdown the client
	 */
	public void switchOff() {
		this.powerSwitch = false;
	}

	/*
	 * initiate connection to specified server
	 */
	private Socket initiateCommunicationSocket() throws ConnectionIssueException {
		Socket rr_clientSocket = null;
		try {
			rr_clientSocket = new Socket(this.ip, this.communicationPort);
			this.writerOut = new PrintWriter(rr_clientSocket.getOutputStream(),
					true);
			this.readerIn = new BufferedReader(new InputStreamReader(
					rr_clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			throw new ConnectionIssueException("Don't know about host: " + this.ip);
			//System.err.println("Don't know about host: " + this.ip);
			//System.exit(1);
		} catch (IOException e) {
			throw new ConnectionIssueException("Couldn't get I/O for the connection to: " + this.ip + this.communicationPort);
			//System.err.println("Couldn't get I/O for the connection to: "
			//		+ this.ip + this.communicationPort);
			//System.exit(1);
		}

		// start keep alive thread that pings server every 10 seconds
		keepAliveThread = new KeepAliveThread(this.writerOut);
		keepAliveThread.start();

		System.out.println("Connection to " + this.ip + " on port "
				+ this.communicationPort + " established!");
		
		InetAddress ipAdr = null;
		try {
			ipAdr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(!ipAdr.getHostAddress().equals(this.ip))
			this.actController.setInfoOnStatusbar("You have connected to " + this.ip + "!");
			
		
		
		return rr_clientSocket;
	}

	// listen for commands from partner
	private void listen() throws ConnectionLostException, ConnectionIssueException {
		// poll commands
		while (this.powerSwitch == true) {
			try {
				try {
					this.receivedCommand = this.readerIn.readLine();
					this.errorCount = 0;
				} catch (SocketException | SocketTimeoutException e) {
					if (this.errorCount < this.maxErrorCount) {
						this.errorCount++;
					} else {
						//System.err.println("connection to server lost...");
						throw new ConnectionLostException();
						//break;
					}
				}
//				System.out.println("Client received command: "
//						+ this.receivedCommand);
				
				
				switch(this.receivedCommand) {
				case Helper.resend:
					this.sendCommand(sendBuffer);
					break;
				case Helper.ping:
					break;
				case Helper.bye:
					this.writerOut.println("BYE");
					break;
				default:
					// fire event to to GUI
					this.actController.handleEvent(Helper.commandToEvent(receivedCommand));
				}
			} catch (IOException e) {
				throw new ConnectionIssueException();
			}
		}
	}

	/*
	 * make sure that after a game whole connection is closed
	 */
	public void finalize() {
		try {
			this.writerOut.close();
			this.readerIn.close();
			this.communicationSocket.close();
		} catch (IOException e) {
			System.err.println("Could not close socket");
			System.exit(-1);
		}

		System.out.println("Connection to host lost...");
	}

	@Override
	public void run() {
		try {
			this.communicationSocket = this.initiateCommunicationSocket();
		
			this.listen();
		} catch (ConnectionLostException e) {
			this.actController.handleException(e);
		} catch (ConnectionIssueException e) {
			this.actController.handleException(e);
		}
	}

}