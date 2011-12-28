package schiffe_versenken;

import javax.swing.*;
import java.awt.*;


public class GCenter {
	
	private GBattleField myBattleField;
	private GBattleField partnerBattleField;
	private GCenter.Frame centerFrame;
	
	public GCenter() {
		myBattleField = new GBattleField(GBattleField.Owner.MY);
		myBattleField.setEmptyBattleField();
		
		partnerBattleField = new GBattleField(GBattleField.Owner.PARTNER);
		partnerBattleField.setEmptyBattleField();
		
	}
	
	
	
	private class Frame extends JPanel {
		
		private Frame(GBattleField b1, GBattleField b2) {
			this.initLayout();
			this.initCenter(b1);
			this.initCenter(b2);
		}
		
		private void initLayout() {
			this.setLayout(new GridBagLayout());
			this.setBackground(Color.GRAY);
		}
		
		private void initCenter(GBattleField battlefield) {
			GridBagConstraints c = new GridBagConstraints();
			
			c.fill = GridBagConstraints.BOTH;
			switch(battlefield.owner) {
				case MY:
					c.ipadx = 10;
					c.ipady = 10;
					c.gridx = 0;
					c.gridy = 0;
					this.add(battlefield.getFrame(), c);
					break;
				case PARTNER:
					c.ipadx = 50;
					c.ipady = 50;
					c.gridx = 1;
					c.gridy = 1;
					this.add(battlefield.getFrame(), c);
					break;
			}
			
			
		}
		
	}
	
	
	public JPanel setFrame() {
		this.centerFrame = new GCenter.Frame(myBattleField, partnerBattleField);
		return this.centerFrame;
	}
	
}
