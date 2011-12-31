package network;

import java.net.*;
import java.io.*;

import schiffe_versenken.Helper;
import schiffe_versenken.NetworkConnection;

public class Server implements Runnable {

	/*
	 * inner class that represents single server thread
	 * 
	 * this thread is talking with a single client
	 */
	private class ServerThread extends Thread implements NetworkConnection {
		private Socket socket = null;
		private PrintWriter writerOut;
		private BufferedReader readerIn;
		private String receivedCommand;
		private int errorCount;

		private final int maxErrorCount = 2;

		/*
		 * constructor buffer socket reference in object
		 */
		public ServerThread(Socket ir_socket) {
			super("ServerThread");
			this.socket = ir_socket;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#finalize()
		 */
		public void finalize() {
			this.writerOut.close();
			try {
				this.readerIn.close();
				this.socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			clientCounter--;
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

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * schiffe_versenken.NetworkConnection#isCommandValid(java.lang.String)
		 */
		public boolean isCommandValid(String iv_command) {
			return Helper.isCommandValid(iv_command);
		}

		public void run() {
			// do some TCP protocol settings
			try {
				// this.socket.setKeepAlive(true);
				// this.socket.setTcpNoDelay(true);
				// set connection timeout to 30sec
				this.socket.setSoTimeout(30000);

				writerOut = new PrintWriter(this.socket.getOutputStream(), true);
				readerIn = new BufferedReader(new InputStreamReader(
						this.socket.getInputStream()));

				// report success
				System.out.println("Client: " + this.socket.getInetAddress()
						+ " successfully connected!");

				// poll commands
				while (powerSwitch == true) {
					try {
						this.receivedCommand = this.readerIn.readLine();
						this.errorCount = 0;

						System.out.println("Client received command: "
								+ this.receivedCommand);
						// close connection if partner sends "BYE"
						if (this.receivedCommand.equals("BYE")) {

							this.writerOut.println("BYE");
							break;
						}
						// send "PING" back if "PING" was send
						if (this.receivedCommand.equals("PING")) {
							this.writerOut.println("PING");
							System.out
									.println("Server received Ping command and sent it back!");
						}

						// command handling is still missing

					} catch (SocketException e) {
						if (this.errorCount < this.maxErrorCount) {
							this.errorCount++;
						} else {
							this.writerOut.println("BYE");
							break;
						}
					}
				}

			} catch (IOException e) {
				System.err.println("An error occurred!");
			}
		}
	} // end inner class ServerThread

	/*
	 * class Server
	 * 
	 * the job of this class is to build a never ending listener for client
	 * connections and to dispatch these clients to different ServerThreads
	 * (inner class)
	 */

	private int communicationport;
	private ServerSocket communicationSocket;
	private int clientCounter = 0;
	private boolean powerSwitch = true;

	private final int maxClients = 2;

	/*
	 * Creates server and starts server socket in additional thread
	 */
	public Server(int iv_port) {
		this.communicationport = iv_port;
	}

	/*
	 * Set up server socket for incoming clients
	 */
	private ServerSocket initiateCommunicationSocket() {
		ServerSocket rr_serverSocket = null;
		try {
			rr_serverSocket = new ServerSocket(this.communicationport);
		} catch (IOException e) {
			System.err.println("Could not listen on port: "
					+ this.communicationport);
			System.exit(1);
		}

		System.out.println("Communication Server successfully initiated");

		return rr_serverSocket;
	}

	/*
	 * this method shutdown the server that listens for new clients
	 */
	public void switchServerOff() {
		this.powerSwitch = false;
	}

	/*
	 * listen to specified socket for clients
	 */
	private void listen() {
		// wait for clients
		while (this.powerSwitch == true) {
			if (this.clientCounter < this.maxClients) {
				try {
					// create new ServerThread for every incoming client
					new ServerThread(this.communicationSocket.accept()).start();
					this.clientCounter++;
				} catch (IOException e) {
					// if an error occurred escape whole server thread
					System.err.println("Accept failed.");
					System.exit(1);
				}
			}

		}

	}

	/*
	 * make sure that after a game whole connection is closed
	 */
	public void finalize() {
		try {
			this.communicationSocket.close();
		} catch (IOException e) {
			System.out.println("Could not close server socket");
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		this.communicationSocket = this.initiateCommunicationSocket();
		this.listen();
	}

}
