package schiffe_versenken;

public class Player {

	private String name;
	private Battlefield battlefield;
	private String ipAddress;
	private Ship[] ships = {new ships.AircraftCarrier(),
							new ships.Destroyer(),
							new ships.PatrolBoat(),
							new ships.Submarine(),
							new ships.BattleShip()};
							
							
							
//	private Client client;
	
//	public Player( String iv_name, Battlefield iv_battlefield, String iv_ip, Client ir_client) {
//		this.name = iv_name;
//		this.battlefield = iv_battlefield;
//		this.ipAddress = iv_ip;
//		this.client = ir_client;
//	}
	
	public Player(String iv_name) {
		this.name = iv_name;
		this.battlefield = new Battlefield();
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public Battlefield getBattlefield() {
		return this.battlefield;
	}
	
	public boolean setShot(Shot shot) {
			return this.battlefield.setShot(shot);
	}
	
	public Ship[] getShips() {
		return this.ships;
	}
	
	
	
	
}
