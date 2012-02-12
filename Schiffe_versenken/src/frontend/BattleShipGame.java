package frontend;

import java.awt.BorderLayout;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import frontend.views.*;

import backend.ActionController;

public class BattleShipGame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7042402924584579509L;

	private ActionController actController;

	private Header header;
	private RightSetupView rightSV;
	private StatusBar statusBar;
	private LeftSetupView leftSV;
	private BattlefieldViewer battlefieldV;
	private Menu menu;
	private EnterView enterV;

	public BattleShipGame() {
		this.actController = new ActionController();

		
		this.initEnterGui();
//		this.initGUI();
	}

	/*
	 * initialize Main GUI
	 */
	public void initGUI() {
		
		this.remove(enterV);
		this.revalidate();

		this.rightSV = new RightSetupView(this.actController);
		this.leftSV = new LeftSetupView(this.actController);
		this.battlefieldV = new BattlefieldViewer(this.actController);
		this.menu = new Menu(this.actController);
		
		this.rightSV.repaint();
		this.leftSV.repaint();
		this.battlefieldV.repaint();
		this.menu.repaint();
		
		this.revalidate();
		this.repaint();


		// set Border Layout
		
		this.add(this.rightSV, BorderLayout.EAST);
		
		this.add(this.leftSV, BorderLayout.WEST);
		this.add(this.battlefieldV, BorderLayout.CENTER);
		this.setJMenuBar(this.menu);
		this.setVisible(true);
	}
	
	private void initEnterGui() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 600, 450);
//		this.setResizable(false);
		
		this.header = new Header(this.actController);
		this.statusBar = new StatusBar(this.actController);
		this.enterV = new EnterView(this.actController);
		
		this.actController.setBattleShipGame(this);
		
		this.setLayout(new BorderLayout());
		this.add(this.enterV, BorderLayout.CENTER);
		this.add(this.statusBar, BorderLayout.SOUTH);
		this.add(this.header, BorderLayout.NORTH);
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
	
	
	private class TimeThread extends Thread{
		@Override public void run() {
		    try {
		    	
		    	for(int i = 0; i < 10 ; i++) {
		    		setBounds(getX()-20, getY()-20, 650, 500);
		    		Thread.sleep( 20 );
		    		setBounds(getX()+20, getY()+20, 600, 450);
		    		Thread.sleep(20);
		    	}
		    		
		    		
		    		
		    		
		    } 
		    catch ( InterruptedException e ) { 
		    }
		  }
	}
	
	
	
	public void toggleGUI() {
		TimeThread tt = new TimeThread();
		tt.start();
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
	
	public BattlefieldViewer getBattlefieldViewer() {
		return this.battlefieldV;
	}

	public Menu getMenu() {
		return this.menu;
	}

	/*
	 * main method starts the Game
	 */
	public static void main(String[] args) {
		new BattleShipGame();
	}

}
