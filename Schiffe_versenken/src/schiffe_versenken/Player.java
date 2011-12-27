package schiffe_versenken;

public class Player {

	private String name;
	private Battlefield battlefield;
	private String ipAddress;
	
	public Player( String iv_name, Battlefield iv_battlefield, String iv_ip) {
		this.name = iv_name;
		this.battlefield = iv_battlefield;
		this.ipAddress = iv_ip;
	}
	
	
	public String getName() {
		return this.name;
	}
	
}
