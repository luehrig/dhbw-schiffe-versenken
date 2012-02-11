package backend.exceptions;

public class ConnectionLostException extends NetworkException {

	public ConnectionLostException() {
		super();
	}
	
	public ConnectionLostException(String iv_message) {
		super(iv_message);
	}
	
}
