package schiffe_versenken;

import javax.swing.*;
import java.awt.*;


public class GEast {
	
	private Frame eastFrame;
	
	
	private class Frame extends JLabel {
		
		private Frame() {
			this.initLayout();
			this.setText("Schiffe mit Drag & Drop rüber");
		}
		
		private void initLayout() {
			this.setOpaque(true);
			this.setBackground(Color.DARK_GRAY);
			this.setForeground(Color.WHITE);
			this.setFont(new Font("Arial", Font.BOLD, 20));
		}
		
	}
	
	public JLabel setFrame(){
		this.eastFrame = new Frame();
		return this.eastFrame;
	}

}
