package schiffe_versenken;

import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Battlefield {
	
	private Tile[][] board;
	public JPanel panel = new JPanel();
	private final int row = 10;
	private final int col = 10;
	
	public Battlefield() {
		board = new Tile[10][10];
		panel.setLayout(new GridLayout(row, col, 0, 0));
		for(int x = 0; x < row; x ++) {
			for(int y = 0; y < col; y ++) {
				board[x][y] = new Tile(x, y, Tile.Status.WATER);
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
	
	public Tile[][] getBoard() {
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
	
	
	public Point getTileCoords(Tile tile) {
		for(int x = 0; x < row; x ++) {
			for(int y = 0; y < col; y ++) {
				if(tile == board[x][y]) {
					return new Point(x,y);
				}
			}
		}
		return new Point(-1, -1);
	}
	
	/*
	 * transform battlefield to one String
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String rv_result = "";
		String singleTile = null;
		
		
		for(int x_pos = 0; x_pos < this.row; x_pos++) {
			for(int y_pos = 0; y_pos < this.col; y_pos++) {
				if(rv_result == "") {
					singleTile = Integer.toString(x_pos) + "," + Integer.toString(y_pos) + "," + this.board[x_pos][y_pos].getStatus();
				}
				else {
					singleTile = "," + Integer.toString(x_pos) + "," + Integer.toString(y_pos) + "," + this.board[x_pos][y_pos].getStatus();	
				}
				rv_result = rv_result.concat(singleTile);
			}
		}
		
		
		return rv_result;
	}
	
	/*
	 * return number of battlefield rows 
	 */
	public int getRowNumber() {
		return this.row;
	}
	
	/*
	 * return number of battlefield cols 
	 */
	public int getColNumber() {
		return this.col;
	}
}

