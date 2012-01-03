package schiffe_versenken;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Battlefield {
	
	private Field[][] board;
	public JPanel panel = new JPanel();
	private final int row = 10;
	private final int col = 10;
	
	public Battlefield() {
		board = new Field[10][10];
		panel.setLayout(new GridLayout(row, col, 0, 0));
		for(int x = 0; x < row; x ++) {
			for(int y = 0; y < col; y ++) {
				board[x][y] = new Field(x, y, Field.Status.WATER);
				panel.add(board[x][y]);
			}
		}
	}
	
	public void setButtonsDisable() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col ; j ++) {
				board[i][j].setEnabled(false);
				board[i][j].setMargin(new Insets(3,3,3,3));
			}
		}
	}
	
	public void setButtonsEnable() {
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col ; j ++) {
				board[i][j].setEnabled(true);
				board[i][j].setMargin(new Insets(6,6,6,6));
			}
		}
	}
	
	public Field[][] getBoard() {
		return this.board;
	}
	
	public boolean setShot(Shot schuss) {
		if(board[schuss.x][schuss.y].isShip()) {
			this.board[schuss.x][schuss.y].setHit();
			return true;
		} else {
			this.board[schuss.x][schuss.y].setFail();
			return false;
		}
	}
	
	
	public Point getFieldCoords(Field field) {
		for(int x = 0; x < row; x ++) {
			for(int y = 0; y < col; y ++) {
				if(field == board[x][y]) {
					return new Point(x,y);
				}
			}
		}
		return new Point(-1, -1);
	}
}

