package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GuiNorth extends JLabel {
	
	public GuiNorth() {
		this.setText("Battle Ship Game");
		this.initLayout();
	}
	
	
	private void initLayout() {
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setForeground(Color.DARK_GRAY);
		this.setBackground(Color.ORANGE);
	}

}
