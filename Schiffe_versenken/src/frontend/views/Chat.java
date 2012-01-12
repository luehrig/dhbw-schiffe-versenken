package frontend.views;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class Chat extends JLabel {
	
	public Chat() {
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setText("Chat");
	}

}
