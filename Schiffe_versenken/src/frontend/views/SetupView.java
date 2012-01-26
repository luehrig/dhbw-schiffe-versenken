package frontend.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.Player;


public class SetupView extends JPanel {
	
	private Player player1;
	private Player player2;
	private GridBagConstraints c;
	private JButton ready;
	
	
	// East Constructor
	public SetupView(final Player player1, final Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.initLayout();
		this.initEast();
	} // East Constructor
	
	
	// initializes Layout
	private void initLayout() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.DARK_GRAY);
		c = new GridBagConstraints();
		c.fill = c.HORIZONTAL;
	} // initLayout
	
	
	// initializes East
	private void initEast() {
		c.gridy = 0;
		JLabel myShips = new JLabel();
		myShips.setForeground(Color.WHITE);
		myShips.setBackground(Color.DARK_GRAY);
		myShips.setFont(new Font("Arial", Font.BOLD, 12));
		myShips.setOpaque(true);
		myShips.setText("My Ships:");
		this.add(myShips, c);
		
		// adds aircraftcarrier button to east panel
		player1.getShips()[player1.aircraftcarrier].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.aircraftcarrier];
				player1.isMouseListenerActive = true;	
			}
		});
		c.gridy = 1;	
		this.add(player1.getShips()[player1.aircraftcarrier].getButton(), c);
		
		// adds destroyer button to east panel
		player1.getShips()[player1.destroyer].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.destroyer];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 2;
		this.add(player1.getShips()[player1.destroyer].getButton(), c);
		
		// adds patrolboat button to east panel
		player1.getShips()[player1.patrolboat].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.patrolboat];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 3;	
		this.add(player1.getShips()[player1.patrolboat].getButton(), c);
		
		// adds submarine button to east panel
		player1.getShips()[player1.submarine].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.submarine];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 4;
		this.add(player1.getShips()[player1.submarine].getButton(), c);
		
		// adds battleship button to east panel
		player1.getShips()[player1.battleship].getButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.battleship];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 5;	
		this.add(player1.getShips()[player1.battleship].getButton(), c);
		
		// adds ready button to east panel
		ready = new JButton("Ready");
		ready.setBackground(Color.ORANGE);
		ready.setForeground(Color.DARK_GRAY);	
		c.gridy = 6;
		this.add(ready, c);
	} // initEast
	
	public JButton getReadyButton() {
		return this.ready;
	}
	
	
} // East
