package frontend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

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
		this.add(this.header, BorderLayout.NORTH);
		this.add(this.rightSV, BorderLayout.EAST);
		this.add(this.statusBar, BorderLayout.SOUTH);
		this.add(this.leftSV, BorderLayout.WEST);
		this.add(this.battlefieldV, BorderLayout.CENTER);
		this.setJMenuBar(this.menu);
		this.setVisible(true);
	}

	/*
	 * clear BattleShip Game UI and initialize it for a new round (incl. set correct menu values)
	 */
	public void rebuild() {
		// clear battleship game UI
		this.remove(this.header);
		this.remove(this.rightSV);
		this.remove(this.statusBar);
		this.remove(this.battlefieldV);
		this.remove(this.leftSV);
		this.remove(this.menu);
		
		this.revalidate();


		// initialize new views
		this.header = new Header(this.actController);
		this.rightSV = new RightSetupView(this.actController);
		this.statusBar = new StatusBar(this.actController);
		this.leftSV = new LeftSetupView(this.actController);
		this.battlefieldV = new BattlefieldViewer(this.actController);
		this.menu = new Menu(this.actController);

		// setup initialized views to game UI
		this.setLayout(new BorderLayout());
		this.add(this.header, BorderLayout.NORTH);
		this.add(this.rightSV, BorderLayout.EAST);
		this.add(this.statusBar, BorderLayout.SOUTH);
		this.add(this.leftSV, BorderLayout.WEST);
		this.add(this.battlefieldV, BorderLayout.CENTER);
		this.setJMenuBar(this.menu);
		this.setVisible(true);

		// modify menu items
		this.menu.disableItems();

		// repaint all related components
		this.header.repaint();
		this.rightSV.repaint();
		this.statusBar.repaint();
		this.leftSV.repaint();
		this.battlefieldV.repaint();
		this.menu.repaint();

		this.revalidate();
		this.repaint();
	}

	public StatusBar getStatusBar() {
		return this.statusBar;
	}

	public RightSetupView getRightSetupView() {
		return rightSV;
	}

	public LeftSetupView getLeftSetupView() {
		return leftSV;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void winner() {

	}

	// TODO: sollen die Player überhaupt ihren Namen eingeben?
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
