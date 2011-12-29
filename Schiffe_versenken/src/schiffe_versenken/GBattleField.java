package schiffe_versenken;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class GBattleField extends JPanel {
	
	// das Spielfeld besteht aus Feldern mit dem Typ: WATER, SHIP, HIT oder FAIL
	public Field[][] board = new Field[10][10];
	
	public GBattleField() {
		
		this.initLayout();
		this.initBattleField();
		
	}
	
	private void initLayout() {
		this.setLayout(new GridLayout(board.length, board.length, 0, 0));
	}
	
	private void initBattleField() {
		for(int x = 0; x < board.length; x++) {
			for(int y = 0; y < board.length; y++) {
				board[x][y] = new Field(x, y);
				this.add(board[x][y].button);
			}
		}
	}
																		// auf Partner Battlefield!
	public void setSchuss(Schuss schuss, boolean bool) {				// wir haben geschossen, warten auf Antwort und setzten dann Hit oder Fail
		if(bool == true){												
			board[schuss.x][schuss.y].setHit();
		} else {
			board[schuss.x][schuss.y].setFail();
		}
		
	}
																		// auf My Battlefield!
	public boolean getSchuss(Schuss schuss) {							// wir erhalten einen Schuss, schauen nach ob getroffen wurde, senden Antwort zurück und setzten Hit oder Fail 
		if(board[schuss.x][schuss.y].status == Field.Status.SHIP) {
			board[schuss.x][schuss.y].setHit();
			return true;
		} else {
			board[schuss.x][schuss.y].setFail();
			return false;
		}
	
	}

}

