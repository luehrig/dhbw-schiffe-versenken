package backend;

import java.awt.AWTEvent;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;

import backend.Tile.Status;

public class Battlefield {

	private Tile[][] board;
	private JPanel panel = new JPanel();
	private final int row = 10;
	private final int col = 10;

	/*
	 * constructor that initialize whole battlefield with water tiles
	 */
	public Battlefield() {
		board = new Tile[10][10];
		panel.setLayout(new GridLayout(row, col, 0, 0));
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				board[x][y] = new Tile(x, y, Tile.Status.WATER);
				panel.add(board[x][y]);
			}
		}
	}

	/*
	 * constructor that initialize battlefield with ships passed as String
	 */
	public Battlefield(int iv_cols, int iv_rows,
			String iv_battlefieldDescription) {

		this.board = new Tile[iv_cols][iv_rows];

		// variables
		String[] singleArguments;
		int argCounter = 0;

		// split command to single values (separated by ',')
		singleArguments = iv_battlefieldDescription.split("\\,");

		for (int x_pos = 0; x_pos < iv_rows; x_pos++) {
			for (int y_pos = 0; y_pos < iv_cols; y_pos++) {
				this.board[Integer.parseInt(singleArguments[argCounter])][Integer
						.parseInt(singleArguments[argCounter + 1])] = new Tile(
						Integer.parseInt(singleArguments[argCounter]),
						Integer.parseInt(singleArguments[argCounter + 1]),
						Tile.Status.valueOf(singleArguments[argCounter + 2]));
				if ( Tile.ShipStatus.valueOf(singleArguments[argCounter + 3]) != Tile.ShipStatus.NOSHIP) {
					this.board[Integer.parseInt(singleArguments[argCounter])][Integer
							.parseInt(singleArguments[argCounter + 1])]
							.setShip(Tile.ShipStatus
									.valueOf(singleArguments[argCounter + 3]));
				}
				argCounter = argCounter + 4;
			}
		}

	}

	public JPanel getPanel() {
		return this.panel;
	}

	// Set all buttons on battlefield disabled
	public void setButtonsDisable() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j].setEnabled(false);
				board[i][j].setMargin(new Insets(3, 3, 3, 3));
			}
		}
	}

	// Set all buttons on battlefield enabled
	public void setButtonsEnable() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				board[i][j].setEnabled(true);
				board[i][j].setMargin(new Insets(6, 6, 6, 6));
			}
		}
	}

	// returns board
	public Tile[][] getBoard() {
		return this.board;
	}

	// set a shot on board and set the tile hit or fail
	// returns true, if is hit
	// returns false, if is not hit
	public boolean setShot(Shot schuss) {
		if (board[schuss.x][schuss.y].isShip()) {
			this.board[schuss.x][schuss.y].setHit();
			return true;
		} else {
			this.board[schuss.x][schuss.y].setFail();
			return false;
		}
	}

	/*
	 * set shot (hit) in GUI mode
	 */
	public void setShotInGUI(Shot ir_shot) {
		this.board[ir_shot.x][ir_shot.y].setHit();
	}

	/*
	 * set shot (fail) in GUI mode
	 */
	public void setFailInGUI(Shot ir_shot) {
		this.board[ir_shot.x][ir_shot.y].setFail();
	}

	// returns Coordinates of the tile which is asked
	public Point getTileCoords(Tile tile) {
		for (int x = 0; x < row; x++) {
			for (int y = 0; y < col; y++) {
				if (tile == board[x][y]) {
					return new Point(x, y);
				}
			}
		}
		return new Point(-1, -1);
	}

	/*
	 * transform battlefield to one String
	 * 
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String rv_result = "";
		String singleTile = null;

		for (int x_pos = 0; x_pos < this.row; x_pos++) {
			for (int y_pos = 0; y_pos < this.col; y_pos++) {
				// improvement to transmit only tiles that carries ship
				// if (this.board[x_pos][y_pos].getStatus() !=
				// Tile.Status.WATER.toString()) {
				if (rv_result == "") {
					singleTile = Integer.toString(x_pos) + ","
							+ Integer.toString(y_pos) + ","
							+ this.board[x_pos][y_pos].getStatus() + ","
							+ this.board[x_pos][y_pos].getShipStatus();
				} else {
					singleTile = "," + Integer.toString(x_pos) + ","
							+ Integer.toString(y_pos) + ","
							+ this.board[x_pos][y_pos].getStatus() + ","
							+ this.board[x_pos][y_pos].getShipStatus();
				}
				rv_result = rv_result.concat(singleTile);
				// }
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

	public Tile getTile(int iv_x, int iv_y) {
		return this.board[iv_x][iv_y];
	}
}
