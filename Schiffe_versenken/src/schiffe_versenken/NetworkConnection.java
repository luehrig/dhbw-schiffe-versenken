package schiffe_versenken;

public interface NetworkConnection {

	/*
	 * send command
	 * */
	public void sendCommand(String iv_command);

	
	/*
	 * receive command
	 * @return null if no command was received
	 * */
	public String receiveCommand();
	
	/*
	 * method to check if incoming command has correct hash
	 * */
	public boolean isCommandValid(String iv_command);
}
