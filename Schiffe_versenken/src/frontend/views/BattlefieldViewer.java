package frontend.views;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import backend.ActionController;
import backend.Tile;

public class BattlefieldViewer extends JPanel implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8865229600593407050L;
	private ActionController actController;
	private Thread thread;
	
	/*
	 * BattlefieldViewer constructor
	 */
	public BattlefieldViewer(ActionController actController) {
		this.actController = actController;
		this.initBattlefieldViewer2();
	}
	
	/*
	 * initialize Battlefieldviewer with battlefieldpanel
	 * of local and remote player 
	 */
	private void initBattlefieldViewer2() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		
		// add local player battlefieldpanel
		c.gridx = 0;
		c.gridy = 0;
		this.actController.getLocalPlayer().getBattlefield().setBattlefieldNotShotable();
		this.add(this.actController.getLocalPlayer().getBattlefield().getPanel(), c);
		
		// add remote player battlefieldpanel
		c.gridx = 1;
		c.gridy = 1;
		this.actController.getRemotePlayer().getBattlefield().setBattlefieldNotShotable();
		this.add(this.actController.getRemotePlayer().getBattlefield().getPanel(), c);
		
		this.addTilesListener();
	}
	
	/*
	 * add every Tile of local battlefield mouselistener for setting ships
	 * add every Tile of remote battlefield actionlistener for setting shots
	 */
	private void addTilesListener() {
		for(int i = 0; i < 10; i++) {
			for(int j = 0; j < 10; j++) {
				this.actController.getLocalPlayer().getBattlefield().getBoard()[i][j].addMouseListener(this);
				this.actController.getRemotePlayer().getBattlefield().getBoard()[i][j].addActionListener(new ActionListener( ) {
					public void actionPerformed(ActionEvent e) {
						actController.handleShot(e);
					}
					
				});
			}
		}
	}
	

	public void winner(){
		this.thread = new Thread();
		thread.start();
		try {
			thread.sleep(40);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i = 0; i < 10; i ++) {
			for(int j = 0; j < 10; j++) {
				this.actController.getLocalPlayer().getBattlefield().getBoard()[i][j].setWin();
				this.actController.getRemotePlayer().getBattlefield().getBoard()[j][i].setWin();
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		thread.interrupt();
	}

	
	/*******************************************
	 * Mouselistener Methods
	 */
	
	// if mouse clicked on tile
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() instanceof Tile) {
			this.actController.handleBattlefieldViewerMouseClicked(e);
		}
	}

	// if mouse entered a tile
	public void mouseEntered(MouseEvent e) {
		if(e.getSource() instanceof Tile) {
			this.actController.handleBattlefieldViewerMouseEntered(e);
		}
	}
	
	// if mouse exits a tile
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof Tile) {
			this.actController.handleBattlefieldViewerMouseExited(e);
		}
	}

	// not used
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
