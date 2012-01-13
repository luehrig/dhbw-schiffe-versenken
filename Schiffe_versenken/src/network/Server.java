package network;

import java.net.*;
import java.util.LinkedList;
import java.awt.AWTEvent;
import java.io.*;

import backend.Helper;
import backend.MessageProcessor;


public class Server implements Runnable {

	/*
	 * inner class that represents single server thread
	 * 
	 * this thread is talking with a single client
	 */
	private class ServerThread extends NetworkObject implements Runnable {
		private Server parent;
		private Socket socket = null;
		private int threadID;
		//private PrintWriter writerOut;
		//private BufferedReader readerIn;
		
		private String receivedCommand;
		private int errorCount;

		private final int maxErrorCount = 2;

		/*
		 * constructor buffer socket reference in object
		 */
		public ServerThread(Server ir_parent, int iv_threadID, Socket ir_socket) {
			this.threadID = iv_threadID;
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
			serverThreads[this.threadID] = null;
			clientCounter--;
		}

		public void run() {
			// do some TCP protocol settings
			try {
				// set connection timeout to 30sec
				this.socket.setSoTimeout(30000);

				writerOut = new PrintWriter(this.socket.getOutputStream(), true);
				readerIn = new BufferedReader(new InputStreamReader(
						this.socket.getInputStream()));

				// report success
				System.out.println("Client: " + this.socket.getInetAddress()
						+ " successfully connected!");
			} catch (IOException e) {
				System.err.println("An error occurred during connecting...!");
				return;
			}

			// poll commands
			while (powerSwitch == true) {
				// try {
				try {
					this.receivedCommand = this.readerIn.readLine();
				} catch (IOException e) {
					System.err.println("Could not read from network!");
					if (this.errorCount < this.maxErrorCount) {
						this.errorCount++;
					} else {
						this.writerOut.println("BYE");
						break;
					}
				}

				this.errorCount = 0;

				System.out.println("Client received command: "
						+ this.receivedCommand);
				// close connection if partner sends "BYE"
				if (this.receivedCommand.equals("BYE")) {

					this.writerOut.println("BYE");
					break;
				}
				// send "PING" back if "PING" was send
				else if (this.receivedCommand.equals("PING")) {
					this.writerOut.println("PING");
//					System.out
//							.println("Server received Ping command and sent it back!");
				} 
				else if (this.receivedCommand.equals( Helper.resend )) {
					this.sendCommand(sendBuffer);				
				}
				else {
					AWTEvent event = Helper.commandToEvent(receivedCommand);
					msgProcessor.handleEvent(event);
				}

				// command handling is still missing

			}

			try {
				this.socket.close();
			} catch (IOException e) {
				System.err.println("Could not shutdown server thread "
						+ this.threadID);
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
	private ServerThread[] serverThreads = new ServerThread[this.maxClients];

	protected MessageProcessor msgProcessor;
	private LinkedList<Server> observer = new LinkedList<Server>();
	
	
	private final int maxClients = 2;

	/*
	 * Creates server and starts server socket in additional thread
	 */
	public Server(int iv_port) {
		this.communicationport = iv_port;
		this.msgProcessor = this.getMessageProcessorInstance();
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
			this.switchOff();
		}

		System.out.println("Communication Server successfully initiated");

		return rr_serverSocket;
	}

	/*
	 * this method shutdown the server that listens for new clients
	 */
	public void switchOff() {
		this.powerSwitch = false;
	}

	/*
	 * send broadcast message to all connected clients
	 */
	public void sendBroadcastMessage(String iv_command) {
		for (int i = 0; i < this.clientCounter; i++) {
			this.serverThreads[i].sendCommand(iv_command);
		}
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
					this.serverThreads[this.clientCounter] = new ServerThread(
							this,
							this.clientCounter,
							this.communicationSocket.accept());
					new Thread(this.serverThreads[this.clientCounter]).start();
					this.clientCounter++;
				} catch (IOException e) {
					// if an error occurred escape whole server thread
					System.err.println("Accept failed.");
					return;
				}
			}

		}
	}

	/*
	 * create Message Processor instance
	 */
	public MessageProcessor getMessageProcessorInstance() {
		MessageProcessor msgProcessor = new MessageProcessor(this);
		return msgProcessor;
	}
	
	/*
	 * react on event from Message Processor
	 * only send broadcast message to all connected clients
	 */
	public void handleEvent(AWTEvent ir_event) {
		this.sendBroadcastMessage( Helper.eventToCommand(ir_event) );
	}
	
	/*
	 * add new observer to current object
	 */
	public void addObserver(Server ir_object) {
		observer.add(ir_object);
	}
	
	/*
	 * forward event to all observers
	 */
	public void fireEvent(AWTEvent ir_event) {
		for(Server object:observer) {
			object.handleEvent(ir_event);
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
			return;
		}
	}

	@Override
	public void run() {
		this.communicationSocket = this.initiateCommunicationSocket();
		this.listen();
		this.finalize();
	}

}
