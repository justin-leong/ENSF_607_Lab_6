
import java.io.*;

/**
 * This class represents the game tic-tac-toe. The game consists of a tic-tac-toe board, the two players, and
 * a referee to ensure the game is played fairly.
 * There are methods that create a game board, appoint a referee and starts the game.
 * This class also implements a set of marker characters from the Constants interface
 * 
 * @author Justin Leong
 * @version 1.0
 * @since November 3, 2020
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
	
	private Player xPlayer;
	private Player oPlayer;
	
	private BufferedReader pXSocketIn;
	private PrintWriter pXSocketOut;
	private BufferedReader pOSocketIn;
	private PrintWriter pOSocketOut;
	
	
	/**
	 * Constructs a Game object and creates an instance of a tic-tac-toe board
	 */
    public Game() {
        theBoard  = new Board();
        
        //this.pXSocketIn = pXSocketIn;
        //this.pXSocketOut = pXSocketOut;
        //this.pOSocketIn = pOSocketIn;
        //this.pOSocketOut = pOSocketOut;
	}
    
    public Game(BufferedReader pXSocketIn, PrintWriter pXSocketOut, BufferedReader pOSocketIn, PrintWriter pOSocketOut) {
        theBoard  = new Board();
        
        this.pXSocketIn = pXSocketIn;
        this.pXSocketOut = pXSocketOut;
        this.pOSocketIn = pOSocketIn;
        this.pOSocketOut = pOSocketOut;
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
    
    public void setPXSocketIn(BufferedReader pXSocketIn) {
    	this.pXSocketIn = pXSocketIn;
    }
    
    public void setPXSocketOut(PrintWriter pXSocketOut ) {
    	this.pXSocketOut = pXSocketOut;
    }

    public void setPOSocketIn(BufferedReader pOSocketIn) {
    	this.pOSocketIn = pOSocketIn;
    }

    public void setPOSocketOut(PrintWriter pOSocketOut) {
    	this.pOSocketOut = pOSocketOut;
    }
    
    /*
    public void createPlayerX() {
    	xPlayer = new Player("", LETTER_X, pXSocketIn, pXSocketOut);
    	Thread t = new Thread(xPlayer);
    	
    	t.start();
    }
    
    public void createPlayerO() {
    	oPlayer = new Player("", LETTER_O, pOSocketIn, pOSocketOut);
    	Thread t = new Thread(oPlayer);
    	
    	t.start();
    }*/
    
    public void runGame() {
    	
    	Referee theRef;
    	Player xPlayer = null, oPlayer = null;
    	
    	try {
			String pXName = pXSocketIn.readLine();
			String pOName = pOSocketIn.readLine();
			pXSocketOut.println(LETTER_X);
			pOSocketOut.println(LETTER_O);
			
			xPlayer = new Player(pXName, LETTER_X, pXSocketIn, pXSocketOut);
			xPlayer.setBoard(theBoard);
			oPlayer = new Player(pOName, LETTER_O, pOSocketIn, pOSocketOut);
			oPlayer.setBoard(theBoard);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    	theRef = new Referee();
		theRef.setBoard(theBoard);
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
		
        try {
			appointReferee(theRef);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
		/*
		System.out.println("Game Started!!!");
		System.out.println("Player X Name: " + xPlayer.getName());
		System.out.println("Player O Name: " + oPlayer.getName());
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
