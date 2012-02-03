package backend;

import java.awt.Color;
import java.util.Stack;

import javax.swing.JButton;

public abstract class Ship {

	// variables for all child classes
	private String name;
	private int size;
	private JButton button;
	private Type shiptype;
	private Stack<Object> sections = new Stack<Object>(); 
	
	public Ship( String iv_name, int iv_size, Ship.Type shiptype ) {
		this.size = iv_size;
		this.name = iv_name;
		this.shiptype = shiptype;
		this.setShipButton();
		// fill section stack with elements like ship size to make sure that the server
		// can interpret single shots on ship
		for(int i = 0; i < iv_size; i++) {
			this.sections.add(new Object());
		}
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
	
	// delete one section for ship
	public void decrementSection() {
		this.sections.pop();
	}
	
	public int getSectionCount() {
		return this.sections.size();
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
