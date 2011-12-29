package schiffe_versenken;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;



public class GBattleField extends JPanel {
	
	// das Spielfeld besteht aus Feldern mit dem Typ: WATER, SHIP, HIT oder FAIL
	public Field[][] board;
	private int col = 10;
	private int row = 10;
	
	public GBattleField() {
		this.initLayout();
		this.initBattleField();
		
	}
	
	public GBattleField(int col, int row) {
		this.col = col;
		this.row = row;
		this.initLayout();
		this.initBattleField();
	}
	
	private void initLayout() {
		this.setLayout(new GridLayout(col, row, 0, 0));
	}
	
	private void initBattleField() {
		this.board = new Field[col][row];
		for(int x = 0; x < col; x++) {
			for(int y = 0; y < row; y++) {
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
		board[schuss.x][schuss.y].button.setEnabled(false);
	}
																		// auf My Battlefield!
	public boolean getSchuss(Schuss schuss) {							// wir erhalten einen Schuss, schauen nach ob getroffen wurde, senden Antwort zurück und setzten Hit oder Fail 
		if(board[schuss.x][schuss.y].status == Field.Status.SHIP) {
			board[schuss.x][schuss.y].setHit();
			board[schuss.x][schuss.y].button.setEnabled(false);
			return true;
		} else {
			board[schuss.x][schuss.y].setFail();
			board[schuss.x][schuss.y].button.setEnabled(false);
			return false;
		}
	}
}

