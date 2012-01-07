package schiffe_versenken;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class East extends JPanel {
	
	Player player1;
	Player player2;
	
	public East(final Player player1, final Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.DARK_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = c.HORIZONTAL;
		
		player1.getShips()[0].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.aircraftcarrier];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 0;	
		this.add(player1.getShips()[player1.aircraftcarrier].button, c);
		
		player1.getShips()[1].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.destroyer];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 1;
		this.add(player1.getShips()[player1.destroyer].button, c);
		
		player1.getShips()[2].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.patrolboat];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 2;	
		this.add(player1.getShips()[player1.patrolboat].button, c);
		
		player1.getShips()[3].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.submarine];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 3;
		this.add(player1.getShips()[player1.submarine].button, c);
		
		player1.getShips()[4].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				player1.selectedShip = player1.getShips()[player1.battleship];
				player1.isMouseListenerActive = true;
			}
		});
		c.gridy = 4;	
		this.add(player1.getShips()[player1.battleship].button, c);
		
		
		
		final JButton ready = new JButton("Ready");
		ready.setBackground(Color.ORANGE);
		ready.setForeground(Color.DARK_GRAY);
		
		
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	player1.areAllShipsSet()) {
					
					player1.getBattlefield().setButtonsDisable();
					player2.getBattlefield().setButtonsEnable();
					ready.setEnabled(false);
					
					for(int i = 0; i < 10; i ++) {
						for(int j = 0; j < 10; j++) {
							player2.getBattlefield().getBoard()[i][j].isBoardShootable = true;
						}
					}	
				}
			}
		});
		c.gridy = 6;
		this.add(ready, c);
		
	}

}
