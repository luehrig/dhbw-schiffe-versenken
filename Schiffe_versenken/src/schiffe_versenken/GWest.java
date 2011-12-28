package schiffe_versenken;

import javax.swing.*;
import java.awt.*;


public class GWest {
		
	private Frame frame;		
		
	private class Frame extends JLabel {
			
		private Frame() {
			this.initLayout();
			this.setText("Chat???");
		}
			
		private void initLayout() {
				
			this.setFont(new Font("Arial", Font.BOLD, 20));
			this.setOpaque(true);
			this.setForeground(Color.WHITE);
			this.setBackground(Color.DARK_GRAY);
			
		}
			
	}
	
	public JLabel setFrame() {
		this.frame = new GWest.Frame();
		return this.frame;
	}

}
