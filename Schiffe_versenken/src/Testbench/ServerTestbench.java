package Testbench;

import schiffe_versenken.Server;

public class ServerTestbench {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Runnable server = new Server( Integer.parseInt( args[0] ) );
		new Thread(server).start();


	}

}
