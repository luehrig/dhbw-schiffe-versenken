package schiffe_versenken;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

public class BattlefieldViewer extends JPanel implements MouseListener {
	
	Player player1;
	Player player2;
	
	private boolean horizontal = true;
	
	
	
	public BattlefieldViewer(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
		
		for(int x = 0; x < 10; x ++) {
			for(int y = 0; y < 10; y ++) {
				player1.getBattlefield().getBoard()[x][y].addMouseListener(this);
			}
		}
		
		this.setLayout(new GridBagLayout());
		this.setBackground(Color.LIGHT_GRAY);
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		this.player1.getBattlefield().setButtonsEnable();
		this.add(this.player1.getBattlefield().panel, c);
		
		c.gridx = 1;
		c.gridy = 1;
		
		this.player2.getBattlefield().setButtonsDisable();
		this.add(this.player2.getBattlefield().panel, c);
		
	}
	
	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getButton()== MouseEvent.BUTTON1) {
			if(e.getSource() instanceof Field) {
				
				Point point =player1.getBattlefield().getFieldCoords((Field)e.getSource());
				
				if(player1.selectedShip instanceof ships.AircraftCarrier && !player1.isShipSet(player1.aircraftcarrier)) {
					
					if(horizontal) {
						if(point.y <= 5) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+3].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+4].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.aircraftcarrier);
						player1.getShips()[player1.aircraftcarrier].button.setEnabled(false);
						}
					} else if(!horizontal){
						if(point.x <= 5 && (point.x + 4) <= 10) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+3][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+4][point.y].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.aircraftcarrier);
						player1.getShips()[player1.aircraftcarrier].button.setEnabled(false);
						}
					}
				}
				
				if(player1.selectedShip instanceof ships.PatrolBoat && !player1.isShipSet(player1.patrolboat)) {
					if(horizontal) {
						if(point.y <= 8) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.patrolboat);
						player1.getShips()[player1.patrolboat].button.setEnabled(false);
						}
					} else if(!horizontal) {
						if(point.x <= 8) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.patrolboat);
						player1.getShips()[player1.patrolboat].button.setEnabled(false);
						}
					}
				}
				
				if(player1.selectedShip instanceof ships.Submarine && !player1.isShipSet(player1.submarine)) {
					if(horizontal) {
						if(point.y <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.submarine);
						player1.getShips()[player1.submarine].button.setEnabled(false);
						}
					} else if(!horizontal) {
						if(point.x <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.submarine);
						player1.getShips()[player1.submarine].button.setEnabled(false);
						}
					}
				}
				
				if(player1.selectedShip instanceof ships.Destroyer && !player1.isShipSet(player1.destroyer)) {
					if(horizontal) {
						if(point.y <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.destroyer);
						player1.getShips()[player1.destroyer].button.setEnabled(false);
						}
					} else if(!horizontal) {
						if(point.x <= 7) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.destroyer);
						player1.getShips()[player1.destroyer].button.setEnabled(false);
						}
					}

				}
				
				if(player1.selectedShip instanceof ships.BattleShip && !player1.isShipSet(player1.battleship)) {
					if(horizontal) {
						if(point.y <= 6) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+1].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+2].setShip();
						player1.getBattlefield().getBoard()[point.x][point.y+3].setShip();
						
						player1.isMouseListenerActive = false;
						player1.isShipSet(player1.battleship);
						player1.getShips()[player1.battleship].button.setEnabled(false);
						}
					} else if (!horizontal) {
						if(point.x <= 6) {
						player1.getBattlefield().getBoard()[point.x][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+1][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+2][point.y].setShip();
						player1.getBattlefield().getBoard()[point.x+3][point.y].setShip();
						
						player1.isMouseListenerActive = false;
						player1.setShip(player1.battleship);
						player1.getShips()[player1.battleship].button.setEnabled(false);
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
	if(player1.isMouseListenerActive) {
	
		
		if(e.getSource() instanceof Field && !((Field)e.getSource()).isShip()) {
			
			Point point =player1.getBattlefield().getFieldCoords((Field)e.getSource());
			
			if(player1.selectedShip instanceof ships.AircraftCarrier) {
				
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
			
			if(player1.selectedShip instanceof ships.PatrolBoat) {
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
			
			if(player1.selectedShip instanceof ships.Submarine) {
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
			
			if(player1.selectedShip instanceof ships.Destroyer) {
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
			
			if(player1.selectedShip instanceof ships.BattleShip) {
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
