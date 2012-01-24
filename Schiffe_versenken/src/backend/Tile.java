package backend;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Tile extends JButton {

	public int x;
	public int y;
	public Status status;
	public boolean isBoardShootable = false;

	public Tile(int iv_x, int iv_y, Status iv_status) {
		this.x = x;
		this.y = y;
		this.initTile(iv_status);
	}

	private void initTile(Status iv_status) {
		//this.setWater();
		switch (iv_status) {
		case FAIL:
			this.setFail();
			break;
		case WATER:
			this.setWater();
			break;
		case HIT:
			this.setHit();
			break;
		case SHIP:
			this.setShip();
			break;
		// appended to solve first line!
		default:
			this.setWater();
			break;
		}
	}

	public void setWater() {
		this.setBackground(Color.BLUE);
		this.status = Status.WATER;
	}

	public boolean isShip() {
		if (this.status == Status.SHIP) {
			return true;
		} else {
			return false;
		}
	}

	public void setShip() {
		this.setBackground(Color.BLACK);
		this.status = Status.SHIP;
	}

	public void setHit() {
		this.setBackground(Color.RED);
		this.status = Status.HIT;
	}

	public void setFail() {
		this.setBackground(Color.WHITE);
		this.status = Status.FAIL;
	}

	public void setSelected() {
		this.setBackground(Color.LIGHT_GRAY);
		this.status = Status.SELECTED;
	}

	public boolean isSelected() {
		if (this.status == Status.SELECTED) {
			return true;
		} else {
			return false;
		}
	}

	public String getStatus() {
		return this.status.name().toString();
	}

	public enum Status {
		WATER, SHIP, HIT, FAIL, SELECTED;
	}
}
