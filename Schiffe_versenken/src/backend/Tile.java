package backend;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Tile extends JButton {

	private int x;
	private int y;
	private Status status;
	private boolean isBoardShotable = false;
	private Ship.Type shipStatus;

	// Constructor
	public Tile(int iv_x, int iv_y, Status iv_status) {
		this.x = iv_x;
		this.y = iv_y;
		this.initTile(iv_status);
		this.shipStatus = Ship.Type.NOSHIP;
	}
	
	public Tile(Status iv_status) {
		this.initTile(iv_status);
		this.shipStatus = Ship.Type.NOSHIP;
	}

	private void initTile(Status iv_status) {
		//this.setWater();
		switch (iv_status) {
		case FAIL:
			this.setFail();
			break;
		case WATER:
			this.setWater();
			break;
		case HIT:
			this.setHit();
			break;
		case SHIP:
			this.setShip(Ship.Type.SUBMARINE);
			break;
		// appended to solve first line!
		default:
			this.setWater();
			break;
		}
	}

	
	// sets Tile Water
	public void setWater() {
		this.setBackground(Color.BLUE);
		this.status = Status.WATER;
	}
	
	// returns true if Tile is Status Ship
	public boolean isShip() {
		if (this.status == Status.SHIP) {
			return true;
		} else {
			return false;
		}
	}

	// sets Tile Ship
	public void setShip(Ship.Type shipstatus) {
		this.setBackground(Color.BLACK);
		this.status = Status.SHIP;
		this.shipStatus = shipstatus;
	}
	
	// sets Tile Hit
	public void setHit() {
		this.setBackground(Color.RED);
		this.status = Status.HIT;
		this.setEnabled(false);
	}
	
	// sets Tile Fail
	public void setFail() {
		this.setBackground(Color.WHITE);
		this.status = Status.FAIL;
		this.setEnabled(false);
	}

	// sets Tile Selected (for MouseMoveListener in Class BattleShipViewer)
	public void setSelected() {
		this.setBackground(Color.LIGHT_GRAY);
		this.status = Status.SELECTED;
	}
	
	// returns true if Tile Status is Selected
	public boolean isSelected() {
		if (this.status == Status.SELECTED) {
			return true;
		} else {
			return false;
		}
	}
	
	// returns ShipStatus
	public Ship.Type getShipStatus() {
		return this.shipStatus;
	}
	
	// returns Status
	public String getStatus() {
		return this.status.name().toString();
	}
	
	public boolean isBoardShotable() {
		return this.isBoardShotable;
	}
	
	public void setBoardShotable() {
		this.isBoardShotable = true;
	}
	
	public void setBoardNotShotable() {
		this.isBoardShotable = false;
	}
	
	public void setWin() {
		int random = (int) (Math.random() * 10);
		switch(random) {
			case 0: this.setBackground(Color.BLACK); break;
			case 1: this.setBackground(Color.BLUE); break;
			case 2: this.setBackground(Color.CYAN); break;
			case 3: this.setBackground(Color.GREEN); break;
			case 4: this.setBackground(Color.MAGENTA); break;
			case 5: this.setBackground(Color.PINK); break;
			case 6: this.setBackground(Color.RED); break;
			case 7: this.setBackground(Color.WHITE); break;
			case 8: this.setBackground(Color.YELLOW); break;
			case 9: this.setBackground(Color.ORANGE); break;
		}
	}
	
	// Enum to mark Tile as Water, Ship, Hit, Fail, Selected
	public enum Status {
		WATER, 
		SHIP,
		HIT, 
		FAIL, 
		SELECTED;
	}
	
//	// Enum to mark a ShipTile as Dest, Batt, Patr, Airc, Subm
//	public enum ShipStatus {
//		DESTROYER,
//		BATTLESHIP,
//		PATROLBOAT,
//		AIRCRAFTCARRIER,
//		SUBMARINE,
//		NOSHIP;
//	}
}
