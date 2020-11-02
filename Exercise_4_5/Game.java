
import java.io.*;

/**
 * This class represents the game tic-tac-toe. The game consists of a tic-tac-toe board, the two players, and
 * a referee to ensure the game is played fairly.
 * There are methods that create a game board, appoint a referee and starts the game.
 * This class also implements a set of marker characters from the Constants interface
 * 
 * @author Justin Leong
 * @version 1.0
 * @since October 1, 2020
 *
 */
public class Game implements Constants, Runnable {
	/**
	 * The tic-tac-toe board
	 */
	private Board theBoard;
	
	/**
	 * The referee for the game
	 */
	private Referee theRef;
	
	/**
	 * Constructs a Game object and creates an instance of a tic-tac-toe board
	 */
	
	private BufferedReader p1SocketIn;
	private PrintWriter p1SocketOut;
	private BufferedReader p2SocketIn;
	private PrintWriter p2SocketOut;
	
    public Game(BufferedReader p1SocketIn, PrintWriter p1SocketOut, BufferedReader p2SocketIn, PrintWriter p2SocketOut) {
        theBoard  = new Board();
        
        this.p1SocketIn = p1SocketIn;
        this.p1SocketOut = p1SocketOut;
        this.p2SocketIn = p2SocketIn;
        this.p2SocketOut = p2SocketOut;
	}
    
    /**
     * Sets the referee for the game and starts the game
     * @param r the referee for the game
     * @throws IOException throws exception if caught
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    	theRef.runTheGame();
    }
    
    public void runGame() {
    	/*
    	Referee theRef;
		Player xPlayer, oPlayer;
		BufferedReader stdin;
		Game theGame = new Game();
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\nPlease enter the name of the \'X\' player: ");
		String name= stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}

		xPlayer = new Player(name, LETTER_X);
		xPlayer.setBoard(theGame.theBoard);
		
		System.out.print("\nPlease enter the name of the \'O\' player: ");
		name = stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}
		
		oPlayer = new Player(name, LETTER_O);
		oPlayer.setBoard(theGame.theBoard);
		
		theRef = new Referee();
		theRef.setBoard(theGame.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
        
        theGame.appointReferee(theRef);
        */
    }
    
    @Override
	public void run() {
		runGame();
	}
	
    /**
     * The start of the program where players and referees are initialized to begin the game
     * @param args input parameters for program
     * @throws IOException throws exception if caught
     */
	public static void main(String[] args) throws IOException {
		
		/*
		Referee theRef;
		Player xPlayer, oPlayer;
		BufferedReader stdin;
		Game theGame = new Game();
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("\nPlease enter the name of the \'X\' player: ");
		String name= stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}

		xPlayer = new Player(name, LETTER_X);
		xPlayer.setBoard(theGame.theBoard);
		
		System.out.print("\nPlease enter the name of the \'O\' player: ");
		name = stdin.readLine();
		while (name == null) {
			System.out.print("Please try again: ");
			name = stdin.readLine();
		}
		
		oPlayer = new Player(name, LETTER_O);
		oPlayer.setBoard(theGame.theBoard);
		
		theRef = new Referee();
		theRef.setBoard(theGame.theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
        
        theGame.appointReferee(theRef);
        */
	}

	
	

}
