package schiffe_versenken;

import javax.swing.*;

public class BattlefieldViewer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Battlefield localBattlefield;
	private Battlefield remoteBattlefield;
	
	
	public BattlefieldViewer( Battlefield iv_local, Battlefield iv_remote ) {
		this.localBattlefield = iv_local;
		this.remoteBattlefield = iv_remote;
	}
	
}
