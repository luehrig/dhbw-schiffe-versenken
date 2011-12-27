package schiffe_versenken;

public class Game {

	private Player playerOne;
	private Player playerTwo;
	private String status;
	
	public enum gameStatus { ready, initialized, started, ended; };
	
	public Game( Player iv_playerOne, Player iv_playerTwo ) {
		this.playerOne = iv_playerOne;
		this.playerTwo = iv_playerTwo;
		this.status = gameStatus.ready.toString();
	}
	
	
	public Player getPlayerOne() {
		return this.playerOne;
	}
	
	public Player getPlayerTwo() {
		return this.playerTwo;
	}
}
