package schiffe_versenken;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

public class GuiCenter extends JPanel {
	
	private GBattleField myBattlefield;
	private GBattleField partnerBattleField;
	
	public GuiCenter() {
		this.initLayout();
		this.initCenter();
	}
	
	// initialisiert das Center Layout
	public void initLayout() {
		this.setBackground(Color.LIGHT_GRAY);
		this.setLayout(new GridBagLayout());
	}
	
	// initialisiert beide Spielfelder
	private void initCenter() {
		myBattlefield = new GBattleField();
		partnerBattleField = new GBattleField();
		
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		
		this.add(myBattlefield, c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		this.add(partnerBattleField, c);
	}
	
	// gibt unserer Spielfeld zurück
	public GBattleField getMyBattleField() {
		return this.myBattlefield;
	}
	
	// gibt das Spielfeld des Gegners zurück
	public GBattleField getPartnerBattleField() {
		return this.partnerBattleField;
	}

}
