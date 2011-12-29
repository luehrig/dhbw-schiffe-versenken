package schiffe_versenken;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

/**
 * 
 */

public class BattleshipGame extends JFrame {
	
	public GuiCenter center;


	private static final long serialVersionUID = 1L;
	
	public BattleshipGame(){
		super("Battleship Game");
		this.initLayout();
		this.initMenu();
	}
	
	// initialisiert den ganzen Frame
	private void initLayout() {

		this.setLayout(new BorderLayout());
		this.add(center = new GuiCenter(), BorderLayout.CENTER);
		this.add(new GuiNorth(), BorderLayout.NORTH);
		this.add(new GuiEast(), BorderLayout.EAST);
		this.add(new GuiSouth(), BorderLayout.SOUTH);
		this.add(new GuiWest(), BorderLayout.WEST);
		
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setBounds(200, 200, 800, 600);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	// initialisiert due Menüleiste
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(Color.ORANGE);
		JMenu fileMenu = new JMenu( "Optionen" );
		menuBar.add( fileMenu );
		this.setJMenuBar( menuBar );
			
		fileMenu.add( new JMenuItem("Neues Spiel") );
		fileMenu.add( new JMenuItem("Neue Verbindung herstellen") );
		fileMenu.add( new JMenuItem("Verbindung abbrechen") );
		fileMenu.addSeparator();
		fileMenu.add( new JMenuItem("Beenden") );
	}
	
	
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// startet das Spiel
		BattleshipGame game = new BattleshipGame();
		
		
		
		// Vielleicht gibt es einen anderen Weg auf die Battlefields zuzugreifen?
		Schuss schuss = new Schuss(2, 2);
		
		game.center.getPartnerBattleField().setSchuss(schuss, false);
		game.center.getMyBattleField().setSchuss(schuss, true);

		
	}

}
