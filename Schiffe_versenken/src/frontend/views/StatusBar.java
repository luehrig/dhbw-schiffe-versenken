package frontend.views;

import java.awt.Color;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JLabel;

public class StatusBar extends JLabel {
	
	public StatusBar() {
		this.setForeground(Color.WHITE);
		this.setBackground(Color.DARK_GRAY);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		
		InetAddress ipAdr = null;
		try {
			ipAdr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.setText("My IP: " + ipAdr.getHostAddress());
	}

}
