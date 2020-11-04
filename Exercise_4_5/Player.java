import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * This class represents a player in tic-tac-toe. The player has certain properties such as a name and 
 * the marker they will use during the game. The player is also able to view the tic-tac-toe board and 
 * the player they are playing against.
 * 
 * @author Justin Leong
 * @version 1.0
 * @since October 1, 2020
 *
 */
public class Player {
	/**
	 * The name of the player
	 */
	private String name;
	
	/**
	 * The tic-tac-toe board the player is playing on
	 */
	private Board board;
	
	/**
	 * The opponent of the player
	 */
	private Player opponent;
	
	/**
	 * The marker character that the player will use during the game
	 */
	private char mark;
	
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	/**
	 * Constructs a player object setting the name of the player and the marker character they will be using
	 * @param name the name of the player
	 * @param mark the character that the player will be using for the game
	 */
	public Player(String name, char mark, BufferedReader socketIn, PrintWriter socketOut) {
		setName(name);
		setMark(mark);
		this.socketIn = socketIn;
		this.socketOut = socketOut;
	}
	
	/**
	 * Gets the player to start playing the game by making moves and displaying the board after each move the passing
	 * the turn to the players opponent
	 */
	public void play() {
		
		// runs as long as there is no winner and the board is not full
		while(board.xWins() == false && board.oWins() == false && board.isFull() == false) {
			this.socketOut.println("Your Turn");
			opponent.socketOut.println("Opponents Turn");
			makeMove();
			
			// display board after player makes move
			//board.display();
			
			// checks if there is a winner
			if(board.xWins() || board.oWins() || board.isFull()) {
				break;
			}
			
			// passes the turn to the next player
			this.socketOut.println("Opponents Turn");
			opponent.socketOut.println("Your Turn");
			opponent.makeMove();
			
			// display board after opponent makes move
			//board.display();
		}
		
		// announces that the game is over and displays name of the winner/tie
		//System.out.print("THE GAME IS OVER: ");
		
		
		if(board.xWins()) {
			//System.out.println(name + " is the winner!");
			this.socketOut.println(name + " is the winner!");
			opponent.socketOut.println(name + " is the winner!");
		}else if(board.oWins()) {
			//System.out.println(opponent.getName() + " is the winner!");
			this.socketOut.println(opponent.getName() + " is the winner!");
			opponent.socketOut.println(opponent.getName() + " is the winner!");
		}else {
			//System.out.println(" it is a tie!");
			this.socketOut.println("It is a tie!");
			opponent.socketOut.println("It is a tie!");
		}
	}
	
	/**
	 * Prompts the player to enter row and column values on where to place their marker on the board
	 */
	public void makeMove() {
		
		// prompts the user to select a row and col to place their marker, continues to prompt user until valid input is entered
		boolean validSpace = false;
		while(validSpace == false) {
			// prompt player to enter a row number
			int row = -1;
			while(row < 0 || row > 2) {
				try {
					//row = scan.nextInt();
					row = Integer.parseInt(this.socketIn.readLine());
					
					// checks that the number entered is within the board grid
					if(row < 0 || row > 2) {
						//System.out.print("Please enter a valid row number (0, 1, 2): ");
						this.socketOut.println("Invalid");
					} else {
						this.socketOut.println("Valid");
					}
					
				} catch(Exception e) {
					//System.out.print("Please enter a valid row number (0, 1, 2): ");
					this.socketOut.println("Invalid");
					row = -1;
				}
				
			}
			
			// prompt player to enter a col number
			int col = -1;
			while(col < 0 || col > 2) {
				try {
					col = Integer.parseInt(this.socketIn.readLine());
					
					// checks that the number entered is within the board grid
					if(col < 0 || col > 2) {
						//System.out.print("Please enter a valid col number (0, 1, 2): ");
						this.socketOut.println("Invalid");
					} else {
						this.socketOut.println("Valid");
					}
					
				} catch(Exception e) {
					//System.out.print("Please enter a valid col number (0, 1, 2): ");
					this.socketOut.println("Invalid");
					col = -1;
				}
			}
			//System.out.println();
			
			// adds a marker to the space if empty
			if(board.getMark(row, col) != ' ') {
				//System.out.println("Space is already taken. Please enter a row and col of an empty space!");
				this.socketOut.println("Invalid");
			}else {
				board.addMark(row, col, mark);
				validSpace = true;
				this.socketOut.println("Valid");
				opponent.socketOut.write(row);
				opponent.socketOut.write(col);
			}
			
		}
		
	}
	
	/**
	 * Sets the opponent of the player
	 * @param opponent the opponent of the player
	 */
	public void setOpponent(Player opponent) {
		this.opponent = opponent;
	}
	
	/**
	 * Sets the board that the player is playing on
	 * @param theBoard the tic-tac-toe board being played on
	 */
	public void setBoard(Board theBoard) {
		this.board = theBoard;
	}

	/**
	 * Gets the name of the player
	 * @return the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player
	 * @param name the name of the player
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the marker that the player is using
	 * @return marker character of player
	 */
	public char getMark() {
		return mark;
	}

	/**
	 * Sets the marker character that the player will use
	 * @param mark the marker character that the player will use
	 */
	public void setMark(char mark) {
		this.mark = mark;
	}
	
}
