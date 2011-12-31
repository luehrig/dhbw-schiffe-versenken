package Testbench;

import network.Client;

public class Client02Testbench {

	/**
	 * @param args[0] - IP
	 * @param args[1] - port
	 * 
	 */
	public static void main(String[] args) {
		
		Runnable client = new Client( args[0], Integer.parseInt( args[1] ) );
		new Thread(client).start();

	}

}
