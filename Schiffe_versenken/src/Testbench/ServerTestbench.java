package Testbench;

import java.net.UnknownHostException;

import backend.ActionController;
import network.Server;

public class ServerTestbench {

  /**
   * @param args
   */
  public static void main(String[] args) {
    
	ActionController ac = new ActionController();  
    Runnable server = null;
	try {
		server = new Server( Integer.parseInt( args[0] ), ac );
	} catch (NumberFormatException | UnknownHostException e) {
		System.err.println("Error occurred while setting up game server!");
	}
    new Thread(server).start();


  }

}
