import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class acts as a server that manages a pool of thread connections which hosts tic-tac-toe games 
 * between every two clients that connect to the server.
 * 
 * @author Justin Leong
 * @version 1.0
 * @since November 3, 2020
 *
 */
public class TicTacToeServer {

	private Socket p1Socket;
	private Socket p2Socket;
	private ServerSocket serverSocket;
	private PrintWriter p1SocketOut;
	private PrintWriter p2SocketOut;
	private BufferedReader p1SocketIn;
	private BufferedReader p2SocketIn;
	private ExecutorService pool;
	
	/**
	 * Constructs a TicTacToeServer object that creates a socket using the port 7777 and a thread pool
	 * with 10 threads
	 */
	public TicTacToeServer() {
		try {
			//System.out.println(InetAddress.getLocalHost());
			serverSocket = new ServerSocket(7777);
			System.out.println("Server is running...");
			pool = Executors.newFixedThreadPool(10);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Manages tic-tac-toe games by waiting for two clients to connect to the server and assigning the players to
	 * a game running on one of the threads in the thread pool
	 */
	public void runServer() {
		try {
			// server runs continuously waiting for connections from clients
			while(true) {
				// waits for first player to connect and sets up socket connection for player 1
				p1Socket = serverSocket.accept();
				p1SocketIn = new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
				p1SocketOut = new PrintWriter(p1Socket.getOutputStream(), true);
				System.out.println("Player 1 connection accepted");
				
				// waits for second player to connect and sets up socket connection for player 2
				p2Socket = serverSocket.accept();
				p2SocketIn = new BufferedReader(new InputStreamReader(p2Socket.getInputStream()));
				p2SocketOut = new PrintWriter(p2Socket.getOutputStream(), true);
				System.out.println("Player 2 connection accepted");
				
				// assigns player to game and executes game on a thread from the thread pool
				Game game = new Game(p1SocketIn, p1SocketOut, p2SocketIn, p2SocketOut);
				pool.execute(game);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * main method that creates the server object and runs server operation
	 * @param args
	 */
	public static void main(String[] args) {
		TicTacToeServer myServer = new TicTacToeServer();
		myServer.runServer();
	}
}
