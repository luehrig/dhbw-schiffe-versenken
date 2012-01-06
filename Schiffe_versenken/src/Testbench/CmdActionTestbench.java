package Testbench;

import java.awt.AWTEvent;

import schiffe_versenken.Helper;

public class CmdActionTestbench {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String cmd = "127.0.0.1,FIRE,8,3";
		AWTEvent event = null;
		
		event = Helper.commandToEvent(cmd);
		
		System.out.println( Helper.eventToCommand(event) );
		
	}

}
