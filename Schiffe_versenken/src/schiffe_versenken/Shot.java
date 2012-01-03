package schiffe_versenken;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;

public class Shot extends AbstractAction {

	private int[] position;
	private Player fromPlayer;
	private Player toPlayer;
	public int x;
	public int y;
	
	public Shot() {
		// TODO Auto-generated constructor stub
	}

	public Shot(String iv_id, int[] iv_coordinates, Player iv_source, Player iv_destination) {
		super(iv_id);
		this.position = iv_coordinates;
		this.fromPlayer = iv_source;
		this.toPlayer = iv_destination;
	}
	
	public Shot(int x, int y) {
		this.x = x;
		this.y = y;
		System.out.println("Shot: X = " + this.x + " + Y = " + this.y);
	}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
