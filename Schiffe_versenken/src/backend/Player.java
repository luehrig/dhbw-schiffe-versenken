package backend;


public class Player {

	private String name;
	private String ip;
	private Battlefield battlefield;
	private Ship[] ships = {new ships.AircraftCarrier(),
							new ships.Destroyer(),
							new ships.PatrolBoat(),
							new ships.Submarine(),
							new ships.BattleShip()};
	
	private boolean[] isShipSet = { false, false, false, false, false };
	public int aircraftcarrier = 0;
	public int destroyer = 1;
	public int patrolboat = 2;
	public int submarine = 3;
	public int battleship = 4;
	
	public Ship selectedShip;
	
	public boolean isMouseListenerActive = true;
							
	// Constructor
	public Player(String iv_name) {
		this.name = iv_name;
		this.battlefield = new Battlefield();
	}
	
	
	// returns Name of Player
	public String getName() {
		return this.name;
	}
	
	// returns IP of Player
	public String getIP() {
		return this.ip;
	}
	
	// returns Battlefield of Player
	public Battlefield getBattlefield() {
		return this.battlefield;
	}
	
	// sets a Shot on Player`s battlefield
	public boolean setShot(Shot shot) {
			return this.battlefield.setShot(shot);
	}
	
	// set IP for player
	public void setIP(String iv_ip) {
		this.ip = iv_ip;
	}
	
	// returns an array of ships
	public Ship[] getShips() {
		return this.ships;
	}
	
	// checks if Ship is set on battlefield
	public boolean isShipSet(int ship) {
		if(isShipSet[ship] == true) {
			return true;
		} else {
			return false;
		}
	}
	
	// sets ship as set on battlefield
	public void setShip(int ship) {
		this.isShipSet[ship] =  true;
	}
	
	// checks, if all Ships of the player are set
	public boolean areAllShipsSet() {
		boolean bool = true;
		for(int i = 0; i < 5; i++) {
			if(isShipSet[i] == false) {
				bool = false;
			}
		}
		return bool;
	}
	
	public Ship getShip(Ship.Type shiptype) {
		
		for(int i = 0; i < this.ships.length; i++) {
			if(this.ships[i].getShipType() == shiptype) {
				return this.ships[i];
			}
		}
		return null;
	}
	
	
	
	
}
