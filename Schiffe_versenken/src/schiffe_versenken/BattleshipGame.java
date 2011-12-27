package schiffe_versenken;

import javax.swing.*;

public class BattleshipGame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static JMenuBar paintMenu() {
		JMenuBar menubar = new JMenuBar();
		JMenu menuGame = new JMenu("Game");
		JMenu menuInfo = new JMenu("Info");

		menubar.add(menuGame);
		menubar.add(menuInfo);
		
		return menubar;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JFrame basic = new JFrame("DHBW Battleship");
		
		basic.setVisible(true);
		basic.setSize( 200, 300 );
		basic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		basic.add( paintMenu() );

		
	}

}
