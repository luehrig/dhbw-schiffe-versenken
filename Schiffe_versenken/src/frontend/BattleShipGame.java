package frontend;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import frontend.views.*;



import backend.ActionController;

public class BattleShipGame extends JFrame {
	
	private ActionController actController;
	
	public BattleShipGame() {
		this.actController = new ActionController();
		this.newPlayer();
		this.initGUI();
	}
	
	/*
	 * initialize Main GUI
	 */
	private void initGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 600, 450);
		
		// set Border Layout
		this.setLayout(new BorderLayout());
		this.add(new Header(this.actController)				, BorderLayout.NORTH);
		this.add(new RightSetupView(this.actController)		, BorderLayout.EAST);
		this.add(new StatusBar()								, BorderLayout.SOUTH);
		this.add(new LeftSetupView(this.actController)			, BorderLayout.WEST);
		this.add(new BattlefieldViewer(this.actController)		, BorderLayout.CENTER);
		this.setJMenuBar(new Menu(this.actController));
		this.setVisible(true);
	}
	

	
	// TODO: sollen die Player überhaupt ihren Namen eingeben? 
	private void newPlayer() {
		this.actController.setPlayer("Erol ");
	}
	
	/*
	 * main method starts the Game
	 */
	public static void main(String[] args) {
		new BattleShipGame();
	}
	
	

}
