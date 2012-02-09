package frontend.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.ActionController;
import backend.Ship;

public class LeftSetupView extends JPanel {
	
	private ActionController actController;
	private JLabel enemyShipslabel;
	private JButton giveUpButton;
	private GridBagConstraints c;
	
	/*
	 * LeftSetupView constructor
	 */
	public LeftSetupView(ActionController actController) {	
		this.actController = actController;
		this.initLayout();
		this.initRightSetupView();
	}
	
	/*
	 * initialize layout
	 */
	private void initLayout() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.DARK_GRAY);
		this.c = new GridBagConstraints();
		this.c.fill = c.HORIZONTAL;
	}
	
	/*
	 * initialize panel 
	 */
	private void initRightSetupView() {
		
		//label with text: Enemy Ships
		this.c.gridy = 0;
		this.enemyShipslabel = new JLabel("Enemy Ships");
		this.enemyShipslabel.setForeground(Color.WHITE);
		this.enemyShipslabel.setBackground(Color.DARK_GRAY);
		this.enemyShipslabel.setOpaque(true);
		this.add(enemyShipslabel, c);
		
		this.initShipButtons();
		
		//give up button
		this.c.gridy = 6;
		this.giveUpButton = new JButton("Play Again!");
		this.giveUpButton.setForeground(Color.DARK_GRAY);
		this.giveUpButton.setBackground(Color.ORANGE);
		this.giveUpButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Wollen wir diesen Button nutzen?
				
			}
			
		});
		this.add(giveUpButton, c);
	}
	
	/*
	 * take remote ship buttons without actionlistener
	 */
	private void initShipButtons() {
		
		// aircraftcarrier button
		this.c.gridy = 1;
		this.add(this.actController.getRemoteShipButton(Ship.Type.AIRCRAFTCARRIER), c);
		
		// battleship button
		this.c.gridy = 2;
		this.add(this.actController.getRemoteShipButton(Ship.Type.BATTLESHIP), c);
		
		// destroyer button
		this.c.gridy = 3;
		this.add(this.actController.getRemoteShipButton(Ship.Type.DESTROYER), c);
		
		//patrolboat button
		this.c.gridy = 4;
		this.add(this.actController.getRemoteShipButton(Ship.Type.PATROLBOAT), c);
		
		// submarine button
		this.c.gridy = 5;
		this.add(this.actController.getRemoteShipButton(Ship.Type.SUBMARINE), c);
	}
	
	public void setShipSink(String shiptype) {
		
		switch(shiptype) {
			
		case "AIRCRAFTCARRIER":
			// aircraftcarrier button
			this.actController.getRemoteShipButton(Ship.Type.AIRCRAFTCARRIER).setBackground(Color.RED);
			break;
		case "BATTLESHIP":
			// battleship button
			this.actController.getRemoteShipButton(Ship.Type.BATTLESHIP).setBackground(Color.RED);
			break;
		case "DESTROYER":
			// destroyer button
			this.actController.getRemoteShipButton(Ship.Type.DESTROYER).setBackground(Color.RED);
			break;
		case "PATROLBOAT":
			//patrolboat button
			this.actController.getRemoteShipButton(Ship.Type.PATROLBOAT).setBackground(Color.RED);
			break;
		case "SUBMARINE":
			// submarine button
			this.actController.getRemoteShipButton(Ship.Type.SUBMARINE).setBackground(Color.RED);
			break;
		}

	}

}
