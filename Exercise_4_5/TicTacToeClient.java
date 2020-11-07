import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class acts as a client that connects to a tic-tac-toe server to play a game. Additionally, this class
 * interacts with the client through the command line outputting the state of the game and requesting for user input.
 * 
 * @author Justin Leong
 * @version 1.0
 * @since November 3, 2020
 *
 */
public class TicTacToeClient {
	
	// socket connections to communicate with server
	private Socket socket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private BufferedReader stdIn;
	
	// front end components to display tic-tac-toe game information
	private Board myBoard;
	private char myMark;
	private char opponentMark;
	private String playerName;
	
	/**
	 * Constructs a TicTacToeClient by setting up the connection to the socket via server name and port number.
	 * @param serverName ip of the server or 'localhost' if client is on the same machine as server
	 * @param portNumber port to connect to
	 */
	public TicTacToeClient(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			myBoard = new Board();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Communicates with the server to play tic-tac-toe between another client. Handles setup of game information and 
	 * starts game play.
	 */
	public void communicate() {
		// displays game board and welcomes player to the game
		myBoard.display();
		System.out.println("WELCOME TO THE GAME!");
		
		// prompts user to enter name and sends info to server
		setupPlayerName();
		
		// manages game moves and game state
		playGame();
		
		// closes all socket connections once game is over
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Prompts user for name and sends information to server
	 */
	public void setupPlayerName() {
		// prompts player for name and sends info to server
		System.out.println("Please enter your name: ");
		try {
			playerName = stdIn.readLine();
			socketOut.println(playerName);
			System.out.println("Waiting for opponent to connect");
			
			myMark = socketIn.readLine().charAt(0);
			if(myMark == 'X') {
				opponentMark = 'O';
			}else {
				opponentMark = 'X';
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Manages the user input and server requests for the tic-tac-toe game. Displays the game state after each turn and
	 * updates the front end of this client.
	 */
	public void playGame() {
		String gameStatus = "Your Turn";
		
		// runs while there are still tic-tac-toe moves to be made
		while(gameStatus.equals("Your Turn") || gameStatus.equals("Opponents Turn")) {
			try {
				// gets info about whose turn it is from server
				gameStatus = socketIn.readLine();
				
				if(gameStatus.equals("Your Turn")) { // prompts player to make move if it is this players turn
					System.out.println("\n" + playerName + " it is your turn to make a move.\n");
					String playerMove = "Invalid";
					String row = "";
					String col = "";
					
					// prompts user to enter a row and column to place marker until valid space is selected
					while(playerMove.equals("Invalid")) {
						String rowValid = "Invalid";
						String colValid = "Invalid";
						
						
						while(rowValid.equals("Invalid")) {
							System.out.println(playerName + " what row should your next mark be placed in?");
							row = stdIn.readLine();
							socketOut.println(row);
							rowValid = socketIn.readLine();
							
							if(rowValid.equals("Invalid")) {
								System.out.println("Invalid row selected, please select a valid row number.");
							}
						}
						
						while(colValid.equals("Invalid")) {
							System.out.println(playerName + " what column should your next mark be placed in?");
							col = stdIn.readLine();
							socketOut.println(col);
							colValid = socketIn.readLine();
							
							if(colValid.equals("Invalid")) {
								System.out.println("Invalid col selected, please select a valid col number.");
							}
						}
						
						playerMove = socketIn.readLine();
						if(playerMove.equals("Invalid")) {
							System.out.println("\nSpace already taken. Please select an empty space!\n");
						}
					}
					
					myBoard.addMark(Integer.parseInt(row), Integer.parseInt(col), myMark);
					myBoard.display();
					
					
				} else if(gameStatus.equals("Opponents Turn")) { // waits for opponent to make move and gets move from server
					// waits for opponent to make move and updates board accordingly
					System.out.println("\nWaiting for opponent to make move\n");

					int opponentRow = socketIn.read();
					int opponentCol = socketIn.read();
					
					myBoard.addMark(opponentRow, opponentCol, opponentMark);
					myBoard.display();
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// outputs the game status once a player has won or the game ends in a tie
		System.out.println("\n" + gameStatus);
	}
	
	/**
	 * main method that creates the client object and begins communication with server
	 * @param args
	 */
	public static void main(String[] args) {
		TicTacToeClient myClient = new TicTacToeClient("localhost", 7777);
		myClient.communicate();
	}
}
