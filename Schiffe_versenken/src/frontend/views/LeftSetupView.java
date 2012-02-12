package frontend.views;

import java.awt.Color;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3513873600681449546L;
	private ActionController actController;
	private ActionListener actionListener;
	private JLabel enemyShipslabel;
	private JButton playAgainButton;
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
		this.c.fill = GridBagConstraints.HORIZONTAL;
	}
	
	/*
	 * initialize panel 
	 */
	private void initRightSetupView() {
		
		// initialize ActionListener
		this.setActionListener();
		
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
		this.playAgainButton = new JButton("Play Again!");
		this.playAgainButton.setForeground(Color.DARK_GRAY);
		this.playAgainButton.setBackground(Color.ORANGE);
		this.playAgainButton.setName("playAgain");
		this.playAgainButton.addActionListener(this.actionListener);
		this.add(playAgainButton, c);
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
	
	/*
	 * actionlistener for all button of this view
	 */
	private void setActionListener() {
		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actController.handleLeftSetupViewAction(e);
			}
		};
	}
	
	public void setEnemyName(String name) {
		this.enemyShipslabel.setText(name);
	}

}
