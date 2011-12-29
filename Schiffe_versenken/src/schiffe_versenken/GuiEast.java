package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GuiEast extends JPanel {
	
	private Ship[] ships = 	{ 	new ShipAircraftCarrier(),
								new ShipBattleShip(),
								new ShipDestroyer(),
								new ShipPatrolBoat(),
								new ShipSubmarine()
							};
	private GBattleField shipField;
	
	public GuiEast() {
		this.initLayout();
		this.initShipsVertical();
		this.initShipsHorizontal();
	}
	
	private void initLayout() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	private void initShipsVertical() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.DARK_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		for(int i = 0; i < ships.length; i++) {
			this.shipField = new GBattleField(this.ships[i].getSize(), 1);
			for(int j = 0; j < this.ships[i].getSize(); j++) {
				this.shipField.board[j][0].setShip();
			}
			c.gridx = i;
			c.gridy = 0;
			panel.add(this.shipField, c);
		}
		this.add(panel);
	}
	
	private void initShipsHorizontal() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		panel.setBackground(Color.DARK_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		for(int i = 0; i < ships.length; i++) {
			this.shipField = new GBattleField(1, this.ships[i].getSize());
			for(int j = 0; j < this.ships[i].getSize(); j++) {
				this.shipField.board[0][j].setShip();
			}
			
			c.gridx = 0;
			c.gridy = i;
			panel.add(this.shipField, c);
		}
		this.add(panel);
	}

}
