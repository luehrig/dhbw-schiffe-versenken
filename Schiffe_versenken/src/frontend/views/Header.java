package frontend.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

import backend.ActionController;

public class Header extends JLabel {
	
	private ActionController actController;
	
	/*
	 * Header constructor
	 */
	public Header(ActionController actController) {
		this.actController = actController;
		this.initHeader2();
	}
	
	/*
	 * initialize Header
	 */
	private void initHeader2() {
		this.setForeground(Color.DARK_GRAY);
		this.setBackground(Color.ORANGE);
		this.setFont(new Font("Arial", Font.BOLD, 20));
		this.setOpaque(true);
		this.setText("BattleShip Game");
	}

}
