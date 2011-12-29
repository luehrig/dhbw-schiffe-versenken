package schiffe_versenken;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Field implements ActionListener {

	public int x;
	public int y;
	public Status status;
	public JButton button;
	
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
		this.initButton();
		this.setWater();
	}
	
	private void initButton(){
		button = new JButton();
		this.button.setMargin(new Insets(1, 1, 1, 1));
		this.button.addActionListener(this);
	}
	
	public void setWater(){
		this.button.setBackground(Color.BLUE);
		this.button.setText("W");
		this.status = Status.WATER;
	}
	
	public void setHit(){
		this.button.setBackground(Color.RED);
		this.button.setText("H");
		this.status = Status.HIT;
	}
	
	public void setShip(){
		this.button.setBackground(Color.BLACK);
		this.button.setText("S");
		this.status = Status.SHIP;
	}
	
	public void setFail(){
		this.button.setBackground(Color.WHITE);
		this.button.setText("F");
		this.status = Status.FAIL;
	}
	
	public enum Status {
		WATER,
		HIT,
		FAIL,
		SHIP;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		new Shot(this.x, this.y);
	}

}
