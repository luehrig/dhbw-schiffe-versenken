package schiffe_versenken;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * 
 */

public class BattleshipGame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	Player player1;
	Player player2;
	
	public BattleshipGame(){
		super("Battleship Game");
		this.newPlayer();
		this.initGUI();
		this.initMenu();
	}
	
	private void newPlayer( ) {
		player1 = new Player("Erol");
		player2 = new Player("Max");
	}
	
	// initialisiert den ganzen Frame
	private void initGUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(100, 100, 680, 450);
		
		FrameLayout border = new FrameLayout(player1, player2);
		this.setLayout(new BorderLayout());
		this.add(border.north, BorderLayout.NORTH);
		this.add(border.east, BorderLayout.EAST);
		this.add(border.south, BorderLayout.SOUTH);
		this.add(border.west, BorderLayout.WEST);
		this.add(border.center, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	// initialisiert die Menüleiste
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.ORANGE);
		JMenu fileMenu = new JMenu( "Optionen" );
		menuBar.add( fileMenu );
		this.setJMenuBar( menuBar );
			
		// Menu New Game
		JMenuItem newGame = new JMenuItem("Neues Spiel");
		newGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new BattleshipGame();
			}
			
		});
		fileMenu.add(newGame);
		
		// Menu new Connection
		fileMenu.add(new JMenuItem("Neue Verbindung herstellen"));
		fileMenu.add( new JMenuItem("Verbindung abbrechen") );
		
		
		fileMenu.addSeparator();
		
		JMenuItem exitGame = new JMenuItem("Beenden");
		exitGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		fileMenu.add(exitGame);
	}
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// startet das Spiel
		BattleshipGame game = new BattleshipGame();

	}

}
