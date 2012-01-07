package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

public class North extends JLabel {
	
	public North() {
		this.setForeground(Color.DARK_GRAY);
		this.setBackground(Color.ORANGE);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setText("BattleShip");
	}

}
