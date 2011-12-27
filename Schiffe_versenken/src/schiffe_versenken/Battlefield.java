package schiffe_versenken;

import java.util.LinkedList;

/**
 * @author Max
 *
 */
public class Battlefield {

/*	example for battleship
 * 
 * 	 A B C D E F G H I J
 * 1
 * 2
 * 3
 * 4
 * 5
 * 6
 * 7
 * 8
 * 9
 * 10
 * 
 * */
	
	private String title;
	private boolean[][] tiles;
	private LinkedList<Ship> shipList;
	private LinkedList<Shot> shots;
	
	/*
	 * Constructor
	 * 
	 * Creates battlefield with custom count of columns and rows
	 * Includes linked list for ships that were set on battlefield
	 */
	public Battlefield( String iv_title, int iv_cols, int iv_rows ) {
		this.title = iv_title;
		this.tiles = new boolean[iv_cols][iv_rows];
		this.shipList = new LinkedList<Ship>();
	}
	
	public String getTitle() {
		return this.title;
	}
	
	/*
	 * Check if specific position on battlefield contains part of a ship
	 */
	public boolean isSet( int iv_col, int iv_row ) {
		if( this.tiles[iv_col][iv_row] == true ) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Set one ship to the battlefield
	 * @return true if set was successful
	 * @return false if position is already assigned
	 *
	 */
	public boolean setShip( Ship io_ship ) {
		// check if position is free (not set with other ship)
		for( int[] coordinate: io_ship.getPosition() ) {
			if( this.isSet(coordinate[0], coordinate[1]) != true ) {
				// position is already assigned with other ship
				return false;
			}
		}
		// set position as assigned
		for( int[] coordinate: io_ship.getPosition() ) {
			int x_pos = coordinate[0];
			int y_pos = coordinate[1];
			this.tiles[x_pos][y_pos] = true;
		}
		// put ship to linked list
		this.shipList.add(io_ship);
		
		// ship was successfully added to battlefield
		return true;
	}
	
	
}
