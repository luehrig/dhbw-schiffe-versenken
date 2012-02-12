package backend.exceptions;

/*
 * Exception for problems while connecting to server
 */

public class ConnectionIssueException extends NetworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8541733958747499287L;

	public ConnectionIssueException() {
		super();
	}
	
	public ConnectionIssueException(String iv_message) {
		super(iv_message);
	}
	
}
