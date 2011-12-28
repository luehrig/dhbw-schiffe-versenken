package schiffe_versenken;

import javax.swing.*;
import java.awt.*;


public class GBattleField {
	
	private GField[][] battlefield;
	private GBattleField.Frame battlefieldFrame;
	public Owner owner;
	public final static int BOARD_SIZE = 10;
	
	public GBattleField(GBattleField.Owner owner) {
		this.owner = owner;
		battlefield = new GField[BOARD_SIZE][BOARD_SIZE];
	}
	
	private class Frame extends JPanel {
		
		private Frame(GField[][] battlefield) {
			this.initLayout();
			this.initBattleField();
		}
		
		private void initLayout() {
			this.setLayout( new GridLayout(battlefield.length, battlefield.length, 0, 0));
		}
		
		private void initBattleField() {
			for(int x = 0; x < battlefield.length; x++) {
				for(int y = 0; y < battlefield.length; y++) {
					battlefield[x][y].setButton();
					this.add(battlefield[x][y].getButton());
				}
			}
		}
		
	}
	
	public void setEmptyBattleField() {
		for(int x = 0; x < battlefield.length; x++) {
			for(int y = 0; y < battlefield.length; y++) {
				GField gField = new GField(x, y);
				gField.setFieldWater();
				battlefield[x][y] = gField;
			}
		}
		this.setFrame();
	}
	
	public void setFrame() {
		this.battlefieldFrame = new Frame(this.battlefield);
	}
	
	public JPanel getFrame() {
		return battlefieldFrame;
	}
	
	public enum Owner {
		MY,
		PARTNER;
	}

}
