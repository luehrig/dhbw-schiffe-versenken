package backend;

import java.awt.Color;

import javax.swing.JButton;

public abstract class Ship {

	// variables for all child classes
	private String name;
	private int size;
	private JButton button;
	
	public Ship( String iv_name, int iv_size ) {
		this.size = iv_size;
		this.name = iv_name;
		this.button = new JButton(iv_name);
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
	
}
