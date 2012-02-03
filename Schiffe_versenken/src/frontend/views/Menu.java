package frontend.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import backend.ActionController;

public class Menu extends JMenuBar {
	
	private JMenu option;
	private JMenuItem newGame;
	private JMenuItem newServer;
	private JMenu newConnect;
	private JMenuItem exitConnect;
	private JMenuItem exitGame;
	private JMenuItem ipItem;
	
	private ActionController actController;
	
	private ActionListener actListener;
	
	/*
	 * Menu constructor
	 */
	public Menu(ActionController actController) {
		this.actController = actController;
		this.initMenu();
	}
	
	/*
	 * initialize menubar
	 */
	private void initMenu() {
		this.setBackground(Color.ORANGE);
		this.setActionListener();
		this.initOption();
	}
	
	/*
	 * create item option with other items
	 */
	private void initOption() {
		this.option = new JMenu("Option");
		this.add(option);
		this.initNewGame();
		this.initNewServer();
		this.initNewConnect();
		this.initExitConnect();
		this.initExitGame();
	}
	
	// new game item
	private void initNewGame() {
		this.newGame = new JMenuItem("New Game");
		this.newGame.addActionListener(actListener);
		this.option.add(newGame);
	}
	
	// new server item
	private void initNewServer() {
		this.newServer = new JMenuItem("New Server");
		this.newServer.addActionListener(actListener);
		this.option.add(newServer);
	}
	
	// new connection item with textfield and keylistener
	private void initNewConnect() {
		this.newConnect = new JMenu("Connect To");
		
		// textfield for ip address with keylistener
		JTextField ipAddress = new JTextField("000.000.0.000");
        ipAddress.setPreferredSize(new Dimension(100,18));
        ipAddress.setMaximumSize(new Dimension(100,18));
		ipAddress.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if(key == KeyEvent.VK_ENTER) {
					try {
						actController.setRemoteIPadress(e);
					} catch (UnknownHostException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			public void keyReleased(KeyEvent e) {}
		});
	
		// add textfield to newconnect
        newConnect.add(ipAddress); 
        
        // add newconnect to option
		this.option.add(newConnect);
	}
	
	// close connection item
	private void initExitConnect() {
		this.exitConnect = new JMenuItem("Disconnect");
		this.exitConnect.addActionListener(actListener);
		this.option.add(exitConnect);
	}
	
	// exit game item
	private void initExitGame() {
		this.exitGame = new JMenuItem("Exit Game");
		this.exitGame.addActionListener(actListener);
		this.option.add(exitGame);
	}
	
	/*
	 * actionlistener for all menu items
	 */
	private void setActionListener() {
		actListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actController.handleMenuAction(e);
			}
		};
	}

}
