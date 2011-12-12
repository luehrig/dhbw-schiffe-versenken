package schiffe_versenken;

public class Fire extends Action {

	int[] target;
	
	// create action with array for target coordinates
	public Fire(String iv_name) {
		super(iv_name);
		target = new int[2];
	}
	
	// override for standard fire method
	public void fireAction() {
		
	}

}
