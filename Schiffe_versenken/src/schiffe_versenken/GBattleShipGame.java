package schiffe_versenken;

import javax.swing.*;

import java.awt.*;


public class GBattleShipGame {
	
	
	
	public GBattleShipGame() {
		
		new BattleShipGameGUI();
		
	}
	
	
	
	public class BattleShipGameGUI extends JFrame {
		
		public BattleShipGameGUI() {
			super("Battle Ship Game");
			this.initLayout();
			this.initMenu();
		}
		
		
		private void initLayout() {
			
			this.setLayout(new BorderLayout());
			this.add(new GNorth().setFrame()	, BorderLayout.NORTH);
			this.add(new GEast().setFrame()		, BorderLayout.EAST);
			this.add(new GSouth().setFrame()	, BorderLayout.SOUTH);
			this.add(new GWest().setFrame()		, BorderLayout.WEST);
			this.add(new GCenter().setFrame()	, BorderLayout.CENTER);
			
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setVisible(true);
			this.setBounds(200, 200, 1200, 800);
		}
		
		
		private void initMenu() {
			
			JMenuBar menuBar = new JMenuBar();
			menuBar.setBackground(Color.ORANGE);
			JMenu fileMenu = new JMenu( "Optionen" );
			menuBar.add( fileMenu );
			this.setJMenuBar( menuBar );
			
			fileMenu.add( new JMenuItem("Neu") );
			fileMenu.add( new JMenuItem("Verbinden") );
			fileMenu.add( new JMenuItem("Verbindung abbrechen") );
			fileMenu.addSeparator();
			fileMenu.add( new JMenuItem("Beenden") );
			
		}
		
	}
	

	public static void main(String[] args) {
		
		new GBattleShipGame();
		
		GBattleField field = new GBattleField(GBattleField.Owner.PARTNER);
		
		
	}

}
