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

public class RightSetupView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4407417703284296206L;
	private ActionController actController;
	private ActionListener actionListener;
	private JLabel myShipslabel;
	private JButton readyButton;
	private GridBagConstraints c;
	
	/*
	 * RightSetupView constructor
	 */
	public RightSetupView(ActionController actController) {
		this.actController = actController;
		this.initLayout();
		this.initRightSetupView();
	}
	
	/*
	 * initialize Layout
	 */
	private void initLayout() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.DARK_GRAY);
		this.c = new GridBagConstraints();
		this.c.fill = GridBagConstraints.HORIZONTAL;
	}
	
	/*
	 * initialize Panel
	 */
	private void initRightSetupView() {
		this.setActionListener();
		
		//label with text: My Ships
		this.c.gridy = 0;
		this.myShipslabel = new JLabel("My Ships");
		this.myShipslabel.setForeground(Color.WHITE);
		this.myShipslabel.setBackground(Color.DARK_GRAY);
		this.myShipslabel.setOpaque(true);
		this.add(myShipslabel, c);
		
		this.initShipButtons();
		
		//ready button
		this.c.gridy = 6;
		this.readyButton = new JButton("Ready!");
		this.readyButton.setForeground(Color.DARK_GRAY);
		this.readyButton.setBackground(Color.ORANGE);
		this.readyButton.addActionListener(actionListener);
		this.add(readyButton, c);
	}
	
	/*
	 * take local ship buttons and add actionlistener
	 */
	private void initShipButtons() {
		
		// aircraftcarrier button
		this.c.gridy = 1;
		this.actController.getLocalShipButton(Ship.Type.AIRCRAFTCARRIER).addActionListener(this.actionListener);
		this.add(this.actController.getLocalShipButton(Ship.Type.AIRCRAFTCARRIER), c);
		
		// battleship button
		this.c.gridy = 2;
		this.actController.getLocalShipButton(Ship.Type.BATTLESHIP).addActionListener(this.actionListener);
		this.add(this.actController.getLocalShipButton(Ship.Type.BATTLESHIP), c);
		
		// destroyer button
		this.c.gridy = 3;
		this.actController.getLocalShipButton(Ship.Type.DESTROYER).addActionListener(this.actionListener);
		this.add(this.actController.getLocalShipButton(Ship.Type.DESTROYER), c);
		
		//patrolboat button
		this.c.gridy = 4;
		this.actController.getLocalShipButton(Ship.Type.PATROLBOAT).addActionListener(this.actionListener);
		this.add(this.actController.getLocalShipButton(Ship.Type.PATROLBOAT), c);
		
		// submarine button
		this.c.gridy = 5;
		this.actController.getLocalShipButton(Ship.Type.SUBMARINE).addActionListener(this.actionListener);
		this.add(this.actController.getLocalShipButton(Ship.Type.SUBMARINE), c);
	}
	
	
	public void setShipSink(String shiptype) {
		
		switch(shiptype) {
			
		case "AIRCRAFTCARRIER":
			// aircraftcarrier button
			this.actController.getLocalShipButton(Ship.Type.AIRCRAFTCARRIER).setBackground(Color.RED);
			break;
		case "BATTLESHIP":
			// battleship button
			this.actController.getLocalShipButton(Ship.Type.BATTLESHIP).setBackground(Color.RED);
			break;
		case "DESTROYER":
			// destroyer button
			this.actController.getLocalShipButton(Ship.Type.DESTROYER).setBackground(Color.RED);
			break;
		case "PATROLBOAT":
			//patrolboat button
			this.actController.getLocalShipButton(Ship.Type.PATROLBOAT).setBackground(Color.RED);
			break;
		case "SUBMARINE":
			// submarine button
			this.actController.getLocalShipButton(Ship.Type.SUBMARINE).setBackground(Color.RED);
			break;
		}

	}
	
	
	/*
	 * actionlistener for all button of this class
	 */
	private void setActionListener() {
		this.actionListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actController.handleRightSetupViewAction(e);
			}
		};
	}

}
