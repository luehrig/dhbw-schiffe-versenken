package frontend.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionController;

public class StatusBar extends JPanel {
	
	private static Font STATUS_FONT = new Font("Arial", Font.BOLD, 16);
	
	private JLabel infoLabel;
	private JLabel playerLabel;
	private JLabel timeLabel;
	private JLabel ipLabel;	
	private ActionController actController;
	
	
	/**
	 * 
	 */
	public StatusBar(ActionController actController ) {
		
		this.actController = actController;
		this.setLayout(null);
		this.setBackground(Color.DARK_GRAY);
		
		this.setPreferredSize(new Dimension(600, 30));
		this.setSize(getMaximumSize());
		setInfo();
		setPlayer();
		setTime();
		setIpAdr();		
		
	
		
	}
	
	public void setInfo(){
		infoLabel = new JLabel("", JLabel.LEFT);
		
		
		infoLabel.setForeground(Color.WHITE);
		infoLabel.setBackground(Color.DARK_GRAY);
		infoLabel.setFont(STATUS_FONT);
		infoLabel.setOpaque(true);
		infoLabel.setBounds(0, 0, 150, 30);
		infoLabel.setAlignmentY(LEFT_ALIGNMENT);

		
		infoLabel.setText("Info");
		
		this.add(infoLabel);

	}
	
	public void setPlayer(){
		String currentPlayer;
		
		playerLabel = new JLabel("");
		playerLabel.setForeground(Color.WHITE);
		playerLabel.setBackground(Color.DARK_GRAY);
		playerLabel.setFont(STATUS_FONT);
		playerLabel.setOpaque(true);
		playerLabel.setBounds(150, 0, 150, 30);
		
		currentPlayer = actController.getCurrentPlayerName();
		
		playerLabel.setText("Player");
		
//		if(currentPlayer != null)
//			playerLabel.setText("It's " + currentPlayer + "'s turn");
		
		this.add(playerLabel);
	}
	
	public void setTime(){
		timeLabel = new JLabel("", JLabel.CENTER);
		
		timeLabel.setForeground(Color.WHITE);
		timeLabel.setBackground(Color.DARK_GRAY);
		timeLabel.setFont(STATUS_FONT);
		timeLabel.setOpaque(true);
		timeLabel.setBounds(300, 0, 100, 30);
		
		timeLabel.setText("Time");
		
		this.add(timeLabel);
		
	}
	
	public void setIpAdr(){
		ipLabel = new JLabel("", JLabel.CENTER);
		ipLabel.setForeground(Color.WHITE);
		ipLabel.setBackground(Color.DARK_GRAY);
		ipLabel.setFont(STATUS_FONT);
		ipLabel.setOpaque(true);
		ipLabel.setBounds(400, 0, 200, 30);
		
		InetAddress ipAdr = null;
		try {
			ipAdr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ipLabel.setText("My IP: " + ipAdr.getHostAddress() + " ");
		this.add(ipLabel);
	}
	
	

}
