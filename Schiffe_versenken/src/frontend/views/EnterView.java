package frontend.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import backend.ActionController;

public class EnterView extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1994360852635051193L;
	private ActionController actController;
	private GridBagConstraints c;
	private ActionListener actionListener;
	
	private JButton nextButton;
	private JTextField playerName;
	private JLabel imageLabel;
	private JPanel enterNamePanel;
	
	public EnterView(ActionController actController) {
		this.actController = actController;
		this.setActionListener();
		this.initEnterView();
	}
	
	private void initEnterView() {
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.DARK_GRAY);
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		this.imageLabel = new JLabel();
		this.imageLabel.setIcon(new ImageIcon("src/frontend/logo.png"));
		this.imageLabel.setPreferredSize(new Dimension(340, 300));
		this.imageLabel.setMaximumSize(new Dimension(340, 60));;
		this.imageLabel.setOpaque(true);
		
		
		this.playerName = new JTextField("Your`e Name");
		this.playerName.setPreferredSize(new Dimension(100, 20));
		this.playerName.setMaximumSize(new Dimension(100, 20));
		
		this.nextButton = new JButton();
		this.nextButton.setPreferredSize(new Dimension(100, 20));
		this.nextButton.setMaximumSize(new Dimension(100, 20));
		this.nextButton.setText("Play");
		this.nextButton.setBackground(Color.ORANGE);
		this.nextButton.addActionListener(this.actionListener);
		
		
		this.enterNamePanel = new JPanel();
		this.enterNamePanel.setBackground(Color.DARK_GRAY);
		this.enterNamePanel.setPreferredSize(new Dimension(300, 30));
		this.enterNamePanel.setMaximumSize(new Dimension(300, 30));
		this.enterNamePanel.add(this.playerName);
		this.enterNamePanel.add(this.nextButton);
		
		c.gridy = 0;
		this.add(this.imageLabel, c);
		
		c.gridy = 1;
		this.add(this.enterNamePanel, c);
	}
	
	private void setActionListener() {
		this.actionListener = new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					actController.setPlayer(playerName.getText());
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				actController.handleRightSetupViewAction(e);
			}
		};
	
	}
}
