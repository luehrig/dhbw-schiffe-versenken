package schiffe_versenken;

public abstract class Action {

	private String name;
	
	
	// constructor
	public Action( String iv_name ) {
		this.name = iv_name;
	}
	
	// get name of current action
	public String getName() {
		return this.name;
	}
	
	
	// fire action to trigger event
	public void fireAction() {
		
	}
	
}
