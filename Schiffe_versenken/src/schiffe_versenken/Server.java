package schiffe_versenken;

import java.net.*;
import java.io.*;

public class Server implements Runnable, NetworkConnection {

	private int port;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter writerOut;
	private BufferedReader readerIn;

	/*
	 * Creates server and starts server socket in additional thread
	 */
	public Server(int iv_port) {
		this.port = iv_port;
	}

	/*
	 * get bus from server to client
	 */
	public PrintWriter getOutbound() {
		return this.writerOut;
	}

	/*
	 * get bus from client to server
	 */
	public BufferedReader getInbound() {
		return this.readerIn;
	}

	/*
	 * Set up server socket for incoming clients
	 */
	private ServerSocket initiate() {
		ServerSocket rr_serverSocket = null;
		try {
			rr_serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + this.port);
			System.exit(1);
		}

		System.out.println("Server successfully initiated");
		
		return rr_serverSocket;
	}

	/*
	 * listen to specified socket for clients
	 */
	private void listen() {
		// wait for client
		while (this.clientSocket == null) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e) {
				System.err.println("Accept failed.");
				System.exit(1);
			}
		}

		// prepare communication bus
		try {
			this.writerOut = new PrintWriter(
					this.clientSocket.getOutputStream(), true);
			this.readerIn = new BufferedReader(new InputStreamReader(
					clientSocket.getInputStream()));
		} catch (IOException e) {
			// Something went wrong while building communication bus
			e.printStackTrace();
		}

		while (true) {
			try {
				if (this.readerIn.readLine().equals("BYE")) {

					this.writerOut.write("BYE");

					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		this.finalize();
	}

	/*
	 * make sure that after a game whole connection is closed
	 */
	public void finalize() {
		try {
			this.writerOut.close();
			this.readerIn.close();
			this.clientSocket.close();
			this.serverSocket.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		this.serverSocket = this.initiate();
		this.listen();
	}
}
