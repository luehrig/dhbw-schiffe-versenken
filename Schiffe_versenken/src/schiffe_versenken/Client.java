package schiffe_versenken;

import java.io.*;
import java.net.*;
import java.security.*;

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
		
		this.writerOut.print("BYE");
		
		this.finalize();
	}

	/*
	 * send command
	 * */
	public void sendCommand(String iv_command) {
		this.writerOut.print(iv_command);
	}

	/*
	 * receive command
	 * @return null if no command was received
	 * */
	public String receiveCommand() {
		String rv_command = null;
		try {
			rv_command = this.readerIn.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return rv_command;
	}


	@Override
	public boolean isCommandValid(String iv_command) {
		
		MessageDigest hashProcessor;
		String commandToHash;
		String receivedHashValue;
		String calculatedHashValue;
		String[] receivedHash;
		byte[] bytesOfCommand;
		byte[] calculatedHashedValues;
		
		// extract hash from received command
		receivedHash = iv_command.split( "," ); 
		receivedHashValue = receivedHash[receivedHash.length-1];
		
		commandToHash = iv_command.replace( "," + receivedHash[receivedHash.length-1] , "");
		
		// do new hashing
		try {
			// get instance of hash processor for MD5
			hashProcessor = MessageDigest.getInstance("MD5");
			hashProcessor.reset();
			// convert incoming command to single bytes
			bytesOfCommand = commandToHash.getBytes("UTF-8");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			return false;
		} 
		
		// calculate hash
		calculatedHashedValues = hashProcessor.digest(bytesOfCommand); 
		calculatedHashValue = "" + Helper.ByteArraytoInt(calculatedHashedValues);
		
		if( receivedHashValue.equals( calculatedHashValue ) ) {
			return true;
		}
		else {
			return false;
		}
	}
}