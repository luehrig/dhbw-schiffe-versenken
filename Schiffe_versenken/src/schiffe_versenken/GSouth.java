package schiffe_versenken;

import javax.swing.*;
import java.awt.*;


public class GSouth {
	
	private GSouth.Frame southFrame;
	
	private class Frame extends JLabel {
		
		private Frame() {
			this.initLayout();
			this.setText("Statusleiste (Man ist dran, Dauer des Spiels, ...)");
		}
		
		private void initLayout() {
			this.setFont(new Font("Arial", Font.BOLD, 20));
			this.setOpaque(true);
			this.setForeground(Color.WHITE);
			this.setBackground(Color.DARK_GRAY);
		}
		
	}
	
	public JLabel setFrame() {
		this.southFrame = new GSouth.Frame();
		return this.southFrame;
	}

}
