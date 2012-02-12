package backend.exceptions;

public class ConnectionLostException extends NetworkException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5204465933413710862L;

	public ConnectionLostException() {
		super();
	}
	
	public ConnectionLostException(String iv_message) {
		super(iv_message);
	}
	
}
