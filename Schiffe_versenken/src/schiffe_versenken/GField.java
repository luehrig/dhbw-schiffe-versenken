package schiffe_versenken;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GField {
	
	private int x;
	private int y;
	private Status STATUS;
	private GField.Button fieldButton;
	
	public GField(int x, int y) {
		this.x = x;
		this.y = y;
	} 
	
	
	private class Button extends JButton {
		
		
		private Button(GField gField) {
			this.initLayout();
			this.initField(gField);
		}
		
		private void initLayout() {
			this.setMargin( new Insets(1, 1, 1, 1));
		}
		
		private void initField(GField gField){
			switch(gField.STATUS) {
			case  HIT:
				this.setButtonHit();
				break;
			case FAIL:
				this.setButtonFail();
				break;
			case WATER:
				this.setButtonWater();
				break;
			case SHIP:
				this.setButtonShip();
				break;
			default:
				this.setButtonWater();
			}
		}
		
		private void setButtonFail(){
			this.setText("F");
			this.setBackground(Color.WHITE);
		}
		
		private void setButtonWater(){
			this.setText("W");
			this.setBackground(Color.BLUE);
		}
		
		private void setButtonShip(){
			this.setText("S");
			this.setBackground(Color.BLACK);
		}
		
		private void setButtonHit(){
			this.setText("H");
			this.setBackground(Color.RED);
		}
		
	}
	
	public void setFieldFail(){
		this.STATUS = GField.Status.FAIL;
	}
	
	public void setFieldWater(){
		this.STATUS = GField.Status.WATER;
	}
	
	public void setFieldShip(){
		this.STATUS = GField.Status.SHIP;
	}
	
	public void setFieldHit(){
		this.STATUS = GField.Status.HIT;
	}
	
	public void setButton() {
		this.fieldButton = new GField.Button(this);
	}
	
	public JButton getButton() {
		return this.fieldButton;
	}
	
	public enum Status {
		WATER,
		HIT,
		FAIL,
		SHIP;
	}

}
