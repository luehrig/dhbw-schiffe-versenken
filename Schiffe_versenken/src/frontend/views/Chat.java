package frontend.views;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import backend.Tile;

public class Chat extends JPanel {
	
	JButton button;
	GridBagConstraints c;
	
	public Chat() {
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		c.fill = c.HORIZONTAL;
		this.initChat();
		
	}
	
	private void initChat() {
		c.gridy = 0;
		JLabel enemyShips = new JLabel();
		enemyShips.setForeground(Color.WHITE);
		enemyShips.setBackground(Color.DARK_GRAY);
		enemyShips.setFont(new Font("Arial", Font.BOLD, 12));
		enemyShips.setOpaque(true);
		enemyShips.setText("Enemy Ships:");
		this.add(enemyShips, c);
		
		JButton aircraftcarrier = new JButton("Aircraftcarrier");
		c.gridy = 1;
		aircraftcarrier.setEnabled(false);
		aircraftcarrier.setBackground(Color.BLACK);
		this.add(aircraftcarrier, c);
		
		JButton destroyer = new JButton("Destroyer");
		c.gridy = 2;
		destroyer.setEnabled(false);
		destroyer.setBackground(Color.BLACK);
		this.add(destroyer, c);
		
		JButton patrolboat = new JButton("Patrolboat");
		c.gridy = 3;
		patrolboat.setEnabled(false);
		patrolboat.setBackground(Color.BLACK);
		this.add(patrolboat, c);
		
		JButton submarine = new JButton("Submarine ");
		c.gridy = 4;
		submarine.setEnabled(false);
		submarine.setBackground(Color.BLACK);
		this.add(submarine , c);
		
		JButton battleship = new JButton("Battleship");
		c.gridy = 5;
		battleship.setEnabled(false);
		battleship.setBackground(Color.BLACK);
		this.add(battleship, c);
		
		
		c.gridy = 6;
		JButton newGame = new JButton("New Game");
		newGame.setForeground(Color.DARK_GRAY);
		newGame.setBackground(Color.ORANGE);
		this.add(newGame, c);
	}
	
	private JButton getNewButton() {
		return button = new Tile((Tile.Status.SHIP));
	}

}
