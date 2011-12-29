package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GuiSouth extends JLabel {
	
	public GuiSouth() {
		this.setText("Statusleiste (Man ist dran, Dauer des Spiels, ...)");
		this.initLayout();
	}
	
	private void initLayout() {
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
	}

}
