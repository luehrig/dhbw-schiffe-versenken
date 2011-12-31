package network;

import java.io.*;
import java.net.*;

import schiffe_versenken.Helper;
import schiffe_versenken.NetworkConnection;

public class Client implements Runnable, NetworkConnection {

	/*
	 * inner class that realize a keep alive service
	 */
	private class KeepAliveThread extends Thread {

		private long max_timeout;
		private PrintWriter outboundPlug;

		/*
		 * constructor
		 */
		public KeepAliveThread(long iv_max_timeout, PrintWriter ir_outbound) {
			super("KeepAliveThread");
			this.max_timeout = iv_max_timeout;
			this.outboundPlug = ir_outbound;
		}
		
		/*
		 * send "PING" to partner every 10 seconds
		 * 
		 * (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			while (powerSwitch == true) {
				this.outboundPlug.println("PING");
				System.out.println("Client: " + ip + " Ping");
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
	private PrintWriter writerOut;
	private BufferedReader readerIn;
	private boolean powerSwitch = true;
	private KeepAliveThread keepAliveThread = null;
	private String receivedCommand;
	private int errorCount;

	private final int maxErrorCount = 2;
	
	
	/*
	 * creates client object including a connection to server
	 */
	public Client(String iv_ip, int iv_port) {
		this.ip = iv_ip;
		this.communicationPort = iv_port;
	}

	/*
	 * this method shutdown the client
	 */
	public void switchServerOff() {
		this.powerSwitch = false;
	} 
	
	/*
	 * initiate connection to specified server
	 */
	private Socket initiateCommunicationSocket() {
		Socket rr_clientSocket = null;
		try {
			rr_clientSocket = new Socket(this.ip, this.communicationPort);
			this.writerOut = new PrintWriter(rr_clientSocket.getOutputStream(),
					true);
			this.readerIn = new BufferedReader(new InputStreamReader(
					rr_clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + this.ip);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: "
					+ this.ip + this.communicationPort);
			System.exit(1);
		}

		// start keep alive thread that pings server every 60 seconds
		keepAliveThread = new KeepAliveThread(60, this.writerOut);
		keepAliveThread.start();
		
		System.out.println("Connection to " + this.ip + " on port "
				+ this.communicationPort + " established!");

		return rr_clientSocket;
	}

	// listen for commands from partner
	private void listen() {
		// poll commands
		while (true) {
			try {
				try {
					this.receivedCommand = this.readerIn.readLine();
					this.errorCount = 0;
				} catch (SocketException | SocketTimeoutException e) {
					if(this.errorCount < this.maxErrorCount) {
						this.errorCount++;
					}
					else {
						System.err.println("connection to server lost...");
						break;
					}
				}
				System.out.println("Client received command: " + this.receivedCommand);
				if (receivedCommand.equals("BYE")) {
					this.writerOut.println("BYE");
					break;
				}

				if(receivedCommand.equals("PING")) {

				}
				
				// command handling is still missing

			} catch (IOException e) {
				e.printStackTrace();
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
			System.out.println("Could not close socket");
			System.exit(-1);
		}

		System.out.println("Connection to host lost...");
	}

	@Override
	public void run() {
		this.communicationSocket = this.initiateCommunicationSocket();


		this.listen();

		System.exit(0);
	}

	/*
	 * send command
	 */
	public void sendCommand(String iv_command) {
		this.writerOut.print(iv_command);
	}

	/*
	 * receive command
	 * 
	 * @return null if no command was received
	 */
	public String receiveCommand() {
		String rv_command = null;
		try {
			rv_command = this.readerIn.readLine();
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

	@Override
	public boolean isCommandValid(String iv_command) {
		return Helper.isCommandValid(iv_command);
	}
}