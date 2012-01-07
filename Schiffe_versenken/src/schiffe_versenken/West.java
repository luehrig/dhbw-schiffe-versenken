package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class West extends JLabel {
	
	public West() {
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setText("Chat");
	}

}
