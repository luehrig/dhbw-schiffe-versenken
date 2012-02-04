package backend.exceptions;

/*
 * Exception for problems while connecting to server
 */

public class ConnectionIssueException extends NetworkException {

	public ConnectionIssueException() {
		super();
	}
	
	public ConnectionIssueException(String iv_message) {
		super(iv_message);
	}
	
}
