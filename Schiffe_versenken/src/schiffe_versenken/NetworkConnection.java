package schiffe_versenken;

import java.io.BufferedReader;
import java.io.PrintWriter;

public interface NetworkConnection {

	/*
	 * get bus from me to connection partner
	 **/
	public PrintWriter getOutbound();
	
	/*
	 * get bus from connection partner to me
	 **/
	public BufferedReader getInbound();
		
}
