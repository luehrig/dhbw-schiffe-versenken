package backend;

import java.awt.Color;

import javax.swing.JButton;

public abstract class Ship {

	// variables for all child classes
	private String name;
	private int size;
	private JButton button;
	private Type shiptype;
	
	public Ship( String iv_name, int iv_size, Ship.Type shiptype ) {
		this.size = iv_size;
		this.name = iv_name;
		this.shiptype = shiptype;
		this.setShipButton();
	}
	
	private void setShipButton() {
		this.button = new JButton(this.name);
		this.button.setBackground(Color.BLACK);
		this.button.setForeground(Color.WHITE);
		this.button.setEnabled(false);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getSize() {
		return this.size;
	}
	
	public JButton getButton() {
		return this.button;
	}
	
	public Ship.Type getShipType() {
		return this.shiptype;
	}
	
	public enum Type {
		DESTROYER,
		BATTLESHIP,
		PATROLBOAT,
		AIRCRAFTCARRIER,
		SUBMARINE,
		NOSHIP;
	}
	
}
