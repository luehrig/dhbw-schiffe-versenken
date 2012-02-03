package frontend;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import frontend.views.*;



import backend.ActionController;

public class BattleShipGame extends JFrame {
	
	private ActionController actController;
	
	private Header header;
	private RightSetupView rightSV;
	private StatusBar statusBar;
	private LeftSetupView leftSV;
	private BattlefieldViewer battlefieldV;
	private Menu menu;
	
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
		
		this.header = new Header(this.actController);
		this.rightSV = new RightSetupView(this.actController);
		this.statusBar = new StatusBar(this.actController);
		this.leftSV = new LeftSetupView(this.actController);
		this.battlefieldV = new BattlefieldViewer(this.actController);
		this.menu = new Menu(this.actController);

		this.actController.setBattleShipGame(this);
		
		// set Border Layout
		this.setLayout(new BorderLayout());
		this.add(this.header		, BorderLayout.NORTH);
		this.add(this.rightSV		, BorderLayout.EAST);
		this.add(this.statusBar		, BorderLayout.SOUTH);
		this.add(this.leftSV		, BorderLayout.WEST);
		this.add(this.battlefieldV	, BorderLayout.CENTER);
		this.setJMenuBar(this.menu);
		this.setVisible(true);
	}
	
	public StatusBar getStatusBar(){
		return this.statusBar;
	}
	

	
	// TODO: sollen die Player �berhaupt ihren Namen eingeben? 
	private void newPlayer() {
		this.actController.setPlayer("Erol");
	}
	
	/*
	 * main method starts the Game
	 */
	public static void main(String[] args) {
		new BattleShipGame();
	}
	
	

}