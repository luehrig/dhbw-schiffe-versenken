package backend;


public class Game {

	private Player playerOne;
	private Player playerTwo;
	private Battlefield battlefieldPlayerOne;
	private Battlefield battlefieldPlayerTwo;
	private gameStatus status;
	private Player currentPlayer;
	
	public enum gameStatus { ready, initialized, started, finished; };
	
	
	/*
	 * constructor that initialized a game with no players and battlefields
	 */
	public Game() {
		this.status = gameStatus.initialized;
	}
	
	/*
	 * return reference to player 1
	 */
	public Player getPlayerOne() {
		return this.playerOne;
	}
	
	/*
	 * return reference to player 2
	 */
	public Player getPlayerTwo() {
		return this.playerTwo;
	}
	
	/*
	 * return battlefield of player 1
	 */
	public Battlefield getPlayerOneBattlefield() {
		return this.battlefieldPlayerOne;
	}
	
	/*
	 * return battlefield of player 2
	 */
	public Battlefield getPlayerTwoBattlefield() {
		return this.battlefieldPlayerTwo;
	}
	
	/*
	 * return current game status as string
	 */
	public String getGameStatus() {
		return this.status.toString();
	}
	
	/*
	 * return current player
	 */
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	/*
	 * return player that is suspended
	 */
	public Player getSuspendedPlayer() {
		Player currentPlayer = this.currentPlayer;
		if(currentPlayer.equals(this.playerOne)) {
			return this.playerTwo;
		}
		else {
			return this.playerOne;
		}
	}
	
	/*
	 * return Player by name
	 * return NULL if no player with name exist in game
	 */
	public Player getPlayerByName(String iv_name) {
		if(this.playerOne.getName().equals(iv_name)) {
			return this.playerOne;
		}
		else if(this.playerTwo.getName().equals(iv_name)) {
			return this.playerTwo;
		}
		else {
			return null;
		}
	}
	
	/*
	 * return enemies battlefield
	 */
	public Battlefield getEnemiesBattlefield(Player ir_player) {
		if(this.playerOne.equals(ir_player)) {
			return this.battlefieldPlayerTwo;
		}
		else {
			return this.battlefieldPlayerOne;
		}
	}
	
	
	/*
	 * add player to game (incl. battlefield with ship selection)
	 */
	public void addPlayer(Player ir_player, Battlefield ir_battlefield) {
		// first player in game
		if(this.playerOne == null) {
			this.playerOne = ir_player;
			this.battlefieldPlayerOne = ir_battlefield;
		}
		// second player in game
		else {
			this.playerTwo = ir_player;
			this.battlefieldPlayerTwo = ir_battlefield;
			// set status to "ready"
			this.setGameReady();
		}
	}
	
	/*
	 * return true if game is ready
	 * return false if player missing
	 */
	public boolean isReady() {
		if(this.status == gameStatus.ready) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * set game as ready to play
	 */
	private void setGameReady() {
		this.status = gameStatus.ready;
	}
	
	/*
	 * set game as started
	 */
	public void setGameStarted() {
		this.status = gameStatus.started;
	}
	
	/*
	 * set game as finished
	 */
	public void setGameFinished() {
		this.status = gameStatus.finished;
	}
	
	/*
	 * set player as current player
	 */
	public void setCurrentPlayer(Player ir_player) {
		this.currentPlayer = ir_player;
	}
}
