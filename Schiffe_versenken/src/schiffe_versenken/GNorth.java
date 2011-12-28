package schiffe_versenken;

import javax.swing.*;

import java.awt.*;


public class GNorth {
	
	private GNorth.Frame northFrame;
	
	private class Frame extends JLabel {
	
		private Frame() {
			this.initLayout();
			this.setText("Battle Ship Game");
		}
		
		private void initLayout() {
			
			this.setFont(new Font("Arial", Font.BOLD, 20));
			this.setOpaque(true);
			this.setForeground(Color.DARK_GRAY);
			this.setBackground(Color.ORANGE);
		
		}
		
	}
	
	public JLabel setFrame(){
		northFrame = new GNorth.Frame();
		return northFrame;
	}

}
