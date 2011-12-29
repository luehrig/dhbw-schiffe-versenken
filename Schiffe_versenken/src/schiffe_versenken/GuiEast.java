package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuiEast extends JLabel {
	
	public GuiEast() {
		this.initLayout();
		this.setText("Drag & Drop");
	}
	
	private void initLayout() {
		this.setOpaque(true);
		this.setBackground(Color.DARK_GRAY);
		this.setForeground(Color.WHITE);
		this.setFont(new Font("Arial", Font.BOLD, 20));
	}

}
