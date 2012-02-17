package frontend.views;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.reflect.Method;
import java.net.UnknownHostException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.JOptionPane;
import javax.swing.JTextField;

//import java.lang.reflect.Method;
//import java.util.Arrays;
//import javax.swing.JOptionPane;

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

		// Init Game Instruction
		JMenuItem instruct = new JMenuItem("Game Instruction");
		ActionListener actListenerInstruct = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog dlgInstr = new AboutDialog(actController.getUIInstance(), "Game Instruction",
										"<html>'Captain Morgan' is a variant of the classic game Battleship. <br>The target of the game is to destroy all enemy ships.<br>"+
												"Left up you can see your own battlefield. Right down you can see the enemy.<br><br>"+
												
												"Initialisation:<br>"+
												"Before game begins, you have to set your ship. On the right side you see your ships.<br>"+
												"Click there at one of your ships. If you move the mouse over your battlefield you see the shadow of the ship.<br>"+
												"With right-mouse-click you can change the alignment. Choose your favoured position and confirm with left-mouse-click.<br>"+
												"You can set each ship one time. The number of squares for each ship is determined by the type of the ship.<br>"+
												"The ships cannot overlap.<br><br>"+
												
												"Playing:<br>"+
												"If it´s your turn click on a square of the enemy battlefield, where you want to shoot.<br>"+
												"If you hit an enemy ship the square will be colored red, if not white.<br>"+
												"If you hit you can shoot again.<br><br>"+
												
												"End:<br>"+
												"The game is finished if someone destroys all enemy ships.<br><br>"+
												
												"Feature:<br>"+
												"If you think your game partner is fallen asleep,<br>"+
												"you can wake him up with the 'toggle' button on the left side. ;)<br></html>");
			}
		};
		instruct.addActionListener(actListenerInstruct);
		this.help.add(instruct);
		
		
		// Init support link
		JMenuItem devSupport = new JMenuItem("Support");
		ActionListener actListenerSupport = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openURL("www.battleship.bymaxe.de");  // if clicked on menu item "Support" this URL will be opened in default browser
			}
		};
		devSupport.addActionListener(actListenerSupport);
		this.help.add(devSupport);
		
		
		// Init Development Team
		JMenuItem devTeam = new JMenuItem("Development Team");
		ActionListener actListenerTeam = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog dlg = new AboutDialog(actController.getUIInstance(), "Development Team", "<html><br>This game was developed through a case study"+
																									 " of the Cooperative State University of Mannheim <br>"+
																									 "by Jana Keim, Erol Gül and Max Lührig.<br><br></html>");
			}
		};		
		devTeam.addActionListener(actListenerTeam);
		this.help.add(devTeam);
		
		
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
	
	
	// open URL in default browser 
	public void openURL(String url) {
		
		final String errMsg = "Error attempting to launch web browser";
		String osName = System.getProperty("os.name");
		try {
			if (osName.startsWith("Mac OS")) {
				Class fileMgr = Class.forName("com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[] {String.class});
				openURL.invoke(null, new Object[] {url});
			}
			else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + url);
			else { //assume Unix or Linux
				String[] browsers = {
						"firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(
							new String[] {"which", browsers[count]}).waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Could not find web browser");
				else
					Runtime.getRuntime().exec(new String[] {browser, url});
			}
		}
		catch (Exception e) {
//			JOptionPane.showMessageDialog(null, errMsg + ":\n" + e.getLocalizedMessage());
			AboutDialog dlgSupport = new AboutDialog(actController.getUIInstance(), "Support", 	"<html> Couldn't find web browser!"+ 
																								"<br> Please start your browser and type in the following URL:"+
																								"<br> www.battleship.bymaxe.de</html>");
		}
	}

	public void startServer() {
		this.newServer.setText("New Server");
	}
	
	public void stopServer() {
		this.newServer.setText("Close Server");
	}

}
