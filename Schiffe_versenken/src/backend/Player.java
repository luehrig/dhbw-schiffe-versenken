package backend;


public class Player {

	private String name;
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
	
	public boolean isShipSet(int ship) {
		if(isShipSet[ship] == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setShip(int ship) {
		this.isShipSet[ship] =  true;
	}
	
	public boolean areAllShipsSet() {
		boolean bool = true;
		for(int i = 0; i < 5; i++) {
			if(isShipSet[i] == false) {
				bool = false;
			}
		}
		return bool;
	}
	
	
	
	
}