package schiffe_versenken;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameLayout implements MouseListener {
	
	public JLabel north = new JLabel();
	public JPanel east = new JPanel();
	public JLabel south = new JLabel();
	public JLabel west = new JLabel();
	public JPanel center = new JPanel();
	
	private int aircraftcarrier = 0;
	private int destroyer = 1;
	private int patrolboat = 2;
	private int submarine = 3;
	private int battleship = 4;
	
	private boolean[] isShipSet = {false, false, false, false, false};
	
	private boolean isMouseListenerActive = true;
	
	private Ship selectedShip;
	private boolean horizontal = true;
	
	Player player1;
	Player player2;
	
	public FrameLayout(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		this.initEast();
		this.initNorth();
		this.initSouth();
		this.initWest();
		this.initCenter();
	}
	
	// initialize the North
	private void initNorth() {
		north.setForeground(Color.DARK_GRAY);
		north.setBackground(Color.ORANGE);
		north.setFont(new Font("Arial", Font.BOLD, 20));
		north.setOpaque(true);
		north.setText("BattleShip");
	}
	
	// initialize the East
	// sets Ship Buttons and a Ready Button
	private void initEast() {
		final int i;
		east.setLayout(new GridBagLayout());
		east.setBackground(Color.DARK_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		c.fill = c.HORIZONTAL;
		
		player1.getShips()[0].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShip = player1.getShips()[aircraftcarrier];
				isMouseListenerActive = true;
			}
		});
		c.gridy = 0;	
		east.add(player1.getShips()[aircraftcarrier].button, c);
		
		player1.getShips()[1].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShip = player1.getShips()[destroyer];
				isMouseListenerActive = true;
			}
		});
		c.gridy = 1;
		east.add(player1.getShips()[destroyer].button, c);
		
		player1.getShips()[2].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShip = player1.getShips()[patrolboat];
				isMouseListenerActive = true;
			}
		});
		c.gridy = 2;	
		east.add(player1.getShips()[patrolboat].button, c);
		
		player1.getShips()[3].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShip = player1.getShips()[submarine];
				isMouseListenerActive = true;
			}
		});
		c.gridy = 3;
		east.add(player1.getShips()[submarine].button, c);
		
		player1.getShips()[4].button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				selectedShip = player1.getShips()[battleship];
				isMouseListenerActive = true;
			}
		});
		c.gridy = 4;	
		east.add(player1.getShips()[battleship].button, c);
		
		
		
		final JButton ready = new JButton("Ready");
		ready.setBackground(Color.ORANGE);
		ready.setForeground(Color.DARK_GRAY);
		
		
		ready.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(	isShipSet[aircraftcarrier] 	== true &&
					isShipSet[patrolboat] 		== true &&	
					isShipSet[submarine] 		== true &&
					isShipSet[battleship]		== true &&
					isShipSet[destroyer]		== true) {
					
					player1.getBattlefield().setButtonsDisable();
					player2.getBattlefield().setButtonsEnable();
					ready.setEnabled(false);
					
					for(int i = 0; i < 10; i ++) {
						for(int j = 0; j < 10; j++) {
							player2.getBattlefield().getBoard()[i][j].isBoardShootable = true;
						}
					}	
				}
			}
		});
		c.gridy = 6;
		east.add(ready, c);
	}
	
	// initialize the South
	private void initSouth() {
		south.setForeground(Color.WHITE);
		south.setBackground(Color.DARK_GRAY);
		south.setFont(new Font("Arial", Font.BOLD, 20));
		south.setOpaque(true);
		south.setText("Statusleiste");
	}
	
	// initialize the West
	private void initWest() {
		west.setForeground(Color.WHITE);
		west.setBackground(Color.DARK_GRAY);
		west.setFont(new Font("Arial", Font.BOLD, 20));
		west.setOpaque(true);
		west.setText("Chat");
	}
	
	// initialize the Center
	private void initCenter() {
		for(int x = 0; x < 10; x ++) {
			for(int y = 0; y < 10; y ++) {
				player1.getBattlefield().getBoard()[x][y].addMouseListener(this);
			}
		}
		
		center.setLayout(new GridBagLayout());
		center.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.player1.getBattlefield().setButtonsEnable();
		center.add(this.player1.getBattlefield().panel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		this.player2.getBattlefield().setButtonsDisable();
		center.add(this.player2.getBattlefield().panel, c);
	
	}

	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton()== MouseEvent.BUTTON1) {
			if(e.getSource() instanceof Field) {
				
				Point point =player1.getBattlefield().getFieldCoords((Field)e.getSource());
				
				if(selectedShip instanceof ships.AircraftCarrier && !isShipSet[aircraftcarrier]) {
					
					if(horizontal) {
						if(point.y <= 5) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+3].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+4].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[aircraftcarrier] = true;
						player1.getShips()[aircraftcarrier].button.setEnabled(false);
						}
					} else if(!horizontal){
						if(point.x <= 5 && (point.x + 4) <= 10) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+3][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+4][point.y].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[aircraftcarrier] = true;
						player1.getShips()[aircraftcarrier].button.setEnabled(false);
						}
					}
				}
				
				if(selectedShip instanceof ships.PatrolBoat && !isShipSet[patrolboat]) {
					if(horizontal) {
						if(point.y <= 8) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[patrolboat] = true;
						player1.getShips()[patrolboat].button.setEnabled(false);
						}
					} else if(!horizontal) {
						if(point.x <= 8) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[patrolboat] = true;
						player1.getShips()[patrolboat].button.setEnabled(false);
						}
					}
				}
				
				if(selectedShip instanceof ships.Submarine && !isShipSet[submarine]) {
					if(horizontal) {
						if(point.y <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[submarine] = true;
						player1.getShips()[submarine].button.setEnabled(false);
						}
					} else if(!horizontal) {
						if(point.x <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[submarine] = true;
						player1.getShips()[submarine].button.setEnabled(false);
						}
					}
				}
				
				if(selectedShip instanceof ships.Destroyer && !isShipSet[destroyer]) {
					if(horizontal) {
						if(point.y <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[destroyer] = true;
						player1.getShips()[destroyer].button.setEnabled(false);
						}
					} else if(!horizontal) {
						if(point.x <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[destroyer] = true;
						player1.getShips()[destroyer].button.setEnabled(false);
						}
					}

				}
				
				if(selectedShip instanceof ships.BattleShip && !isShipSet[battleship]) {
					if(horizontal) {
						if(point.y <= 6) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+3].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[battleship] = true;
						player1.getShips()[battleship].button.setEnabled(false);
						}
					} else if (!horizontal) {
						if(point.x <= 6) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+3][point.y].setShip();
						
						isMouseListenerActive = false;
						this.isShipSet[battleship] = true;
						player1.getShips()[battleship].button.setEnabled(false);
						}
					}
	
				}
				
			}
			
			
			
		}
		
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(horizontal) {
				horizontal = false;
				mouseExited(e);
				mouseEntered(e);
			} else if (!horizontal) {
				horizontal = true;
				mouseExited(e);
				mouseEntered(e);
				}
		
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	if(isMouseListenerActive) {
	
		
		if(e.getSource() instanceof Field && !((Field)e.getSource()).isShip()) {
			
			Point point =player1.getBattlefield().getFieldCoords((Field)e.getSource());
			
			if(selectedShip instanceof ships.AircraftCarrier) {
				
				if(horizontal) {
					if(point.y <= 5 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+1].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+2].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+3].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+4].isShip() 
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+1].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+2].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+3].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+4].setSelected();
					}
				} else if(!horizontal){
					if(point.x <= 5 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+1][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+2][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+3][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+4][point.y].isShip() 
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+1][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+2][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+3][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+4][point.y].setSelected();
					}
				}
			}
			
			if(selectedShip instanceof ships.PatrolBoat) {
				if(horizontal) {
					if(point.y <= 8 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+1].isShip() 	
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+1].setSelected();
					}
				} else if(!horizontal) {
					if(point.x <= 8 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+1][point.y].isShip() 	
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+1][point.y].setSelected();
					}
				}
				
			}
			
			if(selectedShip instanceof ships.Submarine) {
				if(horizontal) {
					if(point.y <= 7 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+1].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+2].isShip()
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+1].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+2].setSelected();
					}
				} else if(!horizontal) {
					if(point.x <= 7 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+1][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+2][point.y].isShip()
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+1][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+2][point.y].setSelected();
					}
				}
				
			}
			
			if(selectedShip instanceof ships.Destroyer) {
				if(horizontal) {
					if(point.y <= 7 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+1].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+2].isShip()
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+1].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+2].setSelected();
					}
				} else if(!horizontal) {
					if(point.x <= 7 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+1][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+2][point.y].isShip()
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+1][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+2][point.y].setSelected();
					}
				}
				
			}
			
			if(selectedShip instanceof ships.BattleShip) {
				if(horizontal) {
					if(point.y <= 6 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+1].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+2].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x][point.y+3].isShip()
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+1].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+2].setSelected();
					player1.getBattlefield().getBoard()[point.x][point.y+3].setSelected();
					}
				} else if (!horizontal) {
					if(point.x <= 6 && ( 	!player1.getBattlefield().getBoard()[point.x][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+1][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+2][point.y].isShip() 	&&
											!player1.getBattlefield().getBoard()[point.x+3][point.y].isShip()
										)) {
					player1.getBattlefield().getBoard()[point.x][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+1][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+2][point.y].setSelected();
					player1.getBattlefield().getBoard()[point.x+3][point.y].setSelected();
					}
				}
				
			}
			
		}
	}
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource() instanceof Field) {
			for(int i = 0; i < 10; i ++ ) {
				for(int j = 0; j < 10; j++) {
					if(player1.getBattlefield().getBoard()[i][j].isSelected()) {
						player1.getBattlefield().getBoard()[i][j].setWater();
					}
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
