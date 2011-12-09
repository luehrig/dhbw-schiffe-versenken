package Testbench;

import schiffe_versenken.Client;

public class ClientTestbench {

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
