package schiffe_versenken;

public class Action {

	private String origin;
	private String key;
	private int x_pos;
	private int y_pos;
	private String misc;
	
	
	// constructor
	public Action( String iv_origin, String iv_key, int iv_xpos, int iv_ypos, String iv_misc ) {
		this.origin = iv_origin;
		this.key = iv_key;
		this.x_pos = iv_xpos;
		this.y_pos = iv_ypos;
		this.misc = iv_misc;
	}
	
	public String getOrigin() {
		return this.origin;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public int getXPos() {
		return this.x_pos;
	}
	
	public int getYPos() {
		return this.y_pos;
	}
	
	public String getMisc() {
		return this.misc;
	}
	
	public String toString() {
		String result = this.origin + "," + this.key + "," + this.x_pos + "," + this.y_pos;
		if(this.misc != null) {
			result.concat( "," + this.misc );
		}
		return result;
	}
	
}
