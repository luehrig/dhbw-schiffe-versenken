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
	
	public Tile(int x, int y, Status status) {
		this.x = x;
		this.y = y;
		this.initTile();
	}
	
	private void initTile() {
		this.setWater();
		switch(status) {
		case FAIL: this.setFail(); break;
		case WATER: this.setWater(); break;
		case HIT: this.setHit(); break;
		}
	}
	
	public void setWater() {
		this.setBackground(Color.BLUE);
		this.status = Status.WATER;
	}
	
	public boolean isShip() {
		if(this.status == Status.SHIP) {
			return true;
		} else {
			return false;
		}
	}
	
	public void setShip(ShipStatus shipstatus) {
		this.setBackground(Color.BLACK);
		this.status = Status.SHIP;
		this.shipStatus = shipstatus;
	}
	
	public void setHit() {
		this.setBackground(Color.RED);
		this.status = Status.HIT;
	}
	
	public void setFail() {
		this.setBackground(Color.WHITE);
		this.status = Status.FAIL;
	}
	
	public void setSelected() {
		this.setBackground(Color.LIGHT_GRAY);
		this.status = Status.SELECTED;
	}
	
	public boolean isSelected() {
		if(this.status == Status.SELECTED) {
			return true;
		} else {
			return false;
		}
	}
	
	public ShipStatus getShipStatus() {
		return this.shipStatus;
	}
	
	public String getStatus() {
		return this.status.name().toString();
	}
	
	public enum Status {
		WATER,
		SHIP,
		HIT,
		FAIL,
		SELECTED;
	}
	
	public enum ShipStatus {
		DESTROYER,
		BATTLESHIP,
		PATROLBOAT,
		AIRCRAFTCARRIER,
		SUBMARINE;
		
	}
}
