package frontend.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import backend.ActionController;

public class Menu extends JMenuBar {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1447485767871605534L;
	
	private JMenu option;
	private JMenuItem newGame;
	private JMenuItem newServer;
	private JMenu newConnect;
	private JMenuItem exitConnect;
	private JMenuItem exitGame;
	@SuppressWarnings("unused")
	private JMenuItem ipItem;
	
	private JMenu help;
	private JMenu description;
	private JMenu support;
	private JMenu developedBy;
	
	private Dimension dim = new Dimension(200, 50); 
	private Font font = new Font("Arial", Font.BOLD, 10);
	
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
		this.initHelp();
	}
	
	private void initHelp() {
		this.help = new JMenu("Help");
		this.add(help);
		this.initDescription();
		this.initSupport();
		//this.initDevelopedBy();
		
		
		JMenuItem devTeam = new JMenuItem("Development Team");
		
		ActionListener actListenerTeam = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog dlg = new AboutDialog(actController.getUIInstance(), "Development Team", "<html>This game was developed through a case study of the Cooperative State University of Mannheim <br> by Jana Keim, Erol Gül and Max Lührig.</html>");
			}
		};
		
		devTeam.addActionListener(actListenerTeam);
		
		this.help.add(devTeam);
		
		
		
	}
	
	private void initDescription() {
		this.description = new JMenu("Description");
		
		JLabel d = new JLabel();
		d.setFont(font);
		d.setText(															"<html>	Many students are familiar with the childhood game Battleship." +
																			"This is a electronic version of this game which is designd and implemented" +
																			"for two players in a network." +
																			"In focus are the Cooperative State students ." +
																			"</html>");
		d.setPreferredSize(dim);
		d.setMaximumSize(dim);

		this.description.add(d);
		
		this.help.add(this.description);
	}
	
	private void initSupport() {
		this.support = new JMenu("Support");
		
		JLabel d = new JLabel();
		d.setFont(font);
		d.setText(															"<html>	bla bla line 1 " +
																			"<br>	bla bla line 1 " +
																			"<br>	bla bla line 2 " +
																			"<br>	bla bla line 3 " +
																			"</html>");
		d.setPreferredSize(dim);
		d.setMaximumSize(dim);
		
		this.support.add(d);
		
		this.help.add(this.support);
	}
	
	private void initDevelopedBy() {
		/*this.developedBy = new JMenu("Developed By");
		
		JLabel d = new JLabel();
		d.setFont(font);
		d.setText(															"<html>	Many students are familiar with the childhood game Battleship. <br> " +
																			"<br>	This is a electronic version of this game which is designd and implemented" +
																			"<br>	for two players in a network." +
																			"<br>	In focus are the Cooperative State students ." +
																			"</html>");
		d.setPreferredSize(dim);
		d.setMaximumSize(dim);
		
		
		this.developedBy.add(d);
		
		this.help.add(this.developedBy); */
	}
	
	/*
	 * create item option with other items
	 */
	private void initOption() {
		this.option = new JMenu("Options");
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
		this.newGame.setEnabled(false);
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
		this.exitConnect.setEnabled(false);
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
	
	public void disableItems() {
		this.newServer.setEnabled(false);
		this.newConnect.setEnabled(false);
		this.exitConnect.setEnabled(true);
		this.newGame.setEnabled(true);
	}
	
	public void disableItemsServer() {
		this.newServer.setEnabled(true);
		this.newConnect.setEnabled(false);
		this.exitConnect.setEnabled(false);
		this.newGame.setEnabled(true);
	}
	
	public void enableItems() {
		this.newServer.setEnabled(true);
		this.newConnect.setEnabled(true);
		this.exitConnect.setEnabled(false);
		this.newGame.setEnabled(false);
	}
	
	public void disableNewGame() {
		this.newGame.setEnabled(false);
	}
	
	public void enableNewGame() {
		this.newGame.setEnabled(true);
	}

	public void startServer() {
		this.newServer.setText("New Server");
	}
	
	public void stopServer() {
		this.newServer.setText("Close Server");
	}

}
