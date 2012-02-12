package Testbench;

import network.Client;

public class Client01Testbench {

	/**
	 * @param args[0] - IP
	 * @param args[1] - port
	 * 
	 */
	public static void main(String[] args) {
		
		Client thread = null;
		
		Runnable client = new Client( args[0], Integer.parseInt( args[1] ) );
		new Thread(client).start();
		
		
		
		String cmd = "127.0.0.1,FIRE,8,3";
				
		
		thread = (Client) client;
		thread.sendCommand(cmd);
		
	}

}
