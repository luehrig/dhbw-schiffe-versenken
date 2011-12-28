package schiffe_versenken;

public class Player {

	private String name;
	private Battlefield battlefield;
	private String ipAddress;
	private Client client;
	
	public Player( String iv_name, Battlefield iv_battlefield, String iv_ip, Client ir_client) {
		this.name = iv_name;
		this.battlefield = iv_battlefield;
		this.ipAddress = iv_ip;
		this.client = ir_client;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	
	
	
}
