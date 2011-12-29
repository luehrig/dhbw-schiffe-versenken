package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class GuiWest extends JLabel {
	
	public GuiWest() {
		this.setText("Chat???");
		this.initLayout();
		
	}
	
	private void initLayout() {
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
	}

}
