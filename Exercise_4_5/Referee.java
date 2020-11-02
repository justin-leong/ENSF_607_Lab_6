/**
 * This class represents a referee in a game of tic-tac-toe. The referee is in charge of running the
 * tic-tac-toe game and knows about the players playing the game. 
 * 
 * @author Justin Leong
 * @version 1.0
 * @since October 1, 2020
 *
 */
public class Referee {
	/**
	 * Player who uses the X marker in the game
	 */
	private Player xPlayer;
	
	/**
	 * Player who uses the O marker in the game
	 */
	private Player oPlayer;
	
	/**
	 * The tic-tac-toe board being using for the game
	 */
	private Board board;
	
	/**
	 * Construct a Referee object and sets all instance variables to their default value
	 */
	public Referee() {
		setxPlayer(null);
		setoPlayer(null);
		setBoard(null);
	}
	
	/**
	 * Sets up the game by assigning the players and triggers the start of the game
	 */
	public void runTheGame() {
		xPlayer.setOpponent(oPlayer);
		oPlayer.setOpponent(xPlayer);
		
		while(xPlayer.getName().equals("") || oPlayer.getName().equals("")) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\nReferee started the game...\n");
		board.display();
		
		//System.out.println("Player X: " + xPlayer);
		//System.out.println("Player O: " + oPlayer);
		//System.out.println("Board: " + board);
		xPlayer.play();
	}
	
	/**
	 * Sets the tic-tac-toe board being used
	 * @param board the tic-tac-toe board
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
	
	/**
	 * Sets the player who will be using the O marker in the game
	 * @param oPlayer the player using the O marker in the game
	 */
	public void setoPlayer(Player oPlayer) {
		this.oPlayer = oPlayer;
	}
	
	/**
	 * Sets the player who will be using the X marker in the game
	 * @param xPlayer the player using the X marker in the game
	 */
	public void setxPlayer(Player xPlayer) {
		this.xPlayer = xPlayer;
	}
}
