package schiffe_versenken;

import java.io.*;
import java.net.*;

public class Client implements Runnable, NetworkConnection {

	private String ip;
	private int port;
	private Socket clientSocket;
	private PrintWriter writerOut;
	private BufferedReader readerIn;

	/*
	 * creates client object including a connection to server
	 **/
	public Client( String iv_ip, int iv_port) {
		this.ip = iv_ip;
		this.port = iv_port;
	}
	
	/*
	 * get bus from client to server
	 **/
	public PrintWriter getOutbound() {
		return this.writerOut;
	}
	
	/*
	 * get bus from server to client
	 **/
	public BufferedReader getInbound() {
		return this.readerIn;
	}
	
	/*
	 * initiate connection to specified server
	 **/
	private Socket initiate(String iv_ip, int iv_port) {
		Socket rr_clientSocket = null;
		try {
			rr_clientSocket = new Socket(iv_ip, iv_port);
			this.writerOut = new PrintWriter(rr_clientSocket.getOutputStream(),
					true);
			this.readerIn = new BufferedReader(new InputStreamReader(
					rr_clientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: " + iv_ip);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: "
					+ iv_ip + iv_port);
			System.exit(1);
		}

		System.out.println("Connection to " + iv_ip + " on port " + iv_port + " established!");
		
		return rr_clientSocket;
	}

	/*
	 * make sure that after a game whole connection is closed
	 */
	public void finalize() {
		try {
			this.writerOut.close();
			this.readerIn.close();
			this.clientSocket.close();
		} catch (IOException e) {
			System.out.println("Could not close socket");
			System.exit(-1);
		}
		
		System.out.println("Connection to host lost...");
	}

	@Override
	public void run() {
		this.clientSocket = this.initiate(this.ip, this.port);
		
		for(int i = 0; i < 90000; i++) {
			
		}
		
		this.finalize();
	}
}