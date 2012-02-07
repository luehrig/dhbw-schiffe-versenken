package Testbench;

import java.net.InetAddress;
import java.net.UnknownHostException;

import network.Server;

public class ServerTestbench {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
    Runnable server = null;
	try {
		server = new Server( Integer.parseInt( args[0] ) );
	} catch (NumberFormatException | UnknownHostException e) {
		System.err.println("Error occurred while setting up game server!");
	}
    new Thread(server).start();


  }

}
