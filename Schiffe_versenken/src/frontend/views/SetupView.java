package frontend.views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import backend.Player;


public class SetupView extends JPanel {
	
	private Player player1;
	private Player player2;
	private GridBagConstraints c;
	public JButton ready;
	
	
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
		
		// adds aircraftcarrier button to east panel
		player1.getShips()[player1.aircraftcarrier].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.aircraftcarrier];
				player1.isMouseListenerActive = true;	
			}
		});
		c.gridy = 0;	
		this.add(player1.getShips()[player1.aircraftcarrier].button, c);
		
		// adds destroyer button to east panel
		player1.getShips()[player1.destroyer].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.destroyer];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 1;
		this.add(player1.getShips()[player1.destroyer].button, c);
		
		// adds patrolboat button to east panel
		player1.getShips()[player1.patrolboat].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.patrolboat];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 2;	
		this.add(player1.getShips()[player1.patrolboat].button, c);
		
		// adds submarine button to east panel
		player1.getShips()[player1.submarine].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.submarine];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 3;
		this.add(player1.getShips()[player1.submarine].button, c);
		
		// adds battleship button to east panel
		player1.getShips()[player1.battleship].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.battleship];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 4;	
		this.add(player1.getShips()[player1.battleship].button, c);
		
		// adds ready button to east panel
		ready = new JButton("Ready");
		ready.setBackground(Color.ORANGE);
		ready.setForeground(Color.DARK_GRAY);	
		c.gridy = 6;
		this.add(ready, c);
	} // initEast
	
	
} // East
