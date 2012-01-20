package backend;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Tile extends JButton {
	
	public int x;
	public int y;
	public Status status;
	public boolean isBoardShootable = false;
	public ShipStatus shipStatus;
	
	// Constructor
	public Tile(int x, int y, Status status) {
		this.x = x;
		this.y = y;
		this.initTile();
	}
	
	// initiates Tile
	private void initTile() {
		this.setWater();
	}
	
	// sets Tile Water
	public void setWater() {
		this.setBackground(Color.BLUE);
		this.status = Status.WATER;
	}
	
	// returns true if Tile is Status Ship
	public boolean isShip() {
		if(this.status == Status.SHIP) {
			return true;
		} else {
			return false;
		}
	}
	
	// sets Tile Ship
	public void setShip(ShipStatus shipstatus) {
		this.setBackground(Color.BLACK);
		this.status = Status.SHIP;
		this.shipStatus = shipstatus;
	}
	
	// sets Tile Hit
	public void setHit() {
		this.setBackground(Color.RED);
		this.status = Status.HIT;
	}
	
	// sets Tile Fail
	public void setFail() {
		this.setBackground(Color.WHITE);
		this.status = Status.FAIL;
	}
	
	// sets Tile Selected (for MouseMoveListener in Class BattleShipViewer)
	public void setSelected() {
		this.setBackground(Color.LIGHT_GRAY);
		this.status = Status.SELECTED;
	}
	
	// returns true if Tile Status is Selected
	public boolean isSelected() {
		if(this.status == Status.SELECTED) {
			return true;
		} else {
			return false;
		}
	}
	
	// returns ShipStatus
	public ShipStatus getShipStatus() {
		return this.shipStatus;
	}
	
	// returns Status
	public String getStatus() {
		return this.status.name().toString();
	}
	
	// Enum to mark Tile as Water, Ship, Hit, Fail, Selected
	public enum Status {
		WATER,
		SHIP,
		HIT,
		FAIL,
		SELECTED;
	}
	
	// Enum to mark a ShipTile as Dest, Batt, Patr, Airc, Subm
	public enum ShipStatus {
		DESTROYER,
		BATTLESHIP,
		PATROLBOAT,
		AIRCRAFTCARRIER,
		SUBMARINE;
		
	}
}
