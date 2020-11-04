import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TicTacToeServer {

	private Socket p1Socket;
	private Socket p2Socket;
	private ServerSocket serverSocket;
	private PrintWriter p1SocketOut;
	private PrintWriter p2SocketOut;
	private BufferedReader p1SocketIn;
	private BufferedReader p2SocketIn;
	private ExecutorService pool;
	
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
	
	public void runServer() {
		try {
			while(true) {
				
				p1Socket = serverSocket.accept();
				p1SocketIn = new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
				p1SocketOut = new PrintWriter(p1Socket.getOutputStream(), true);
				System.out.println("Player 1 connection accepted");
				
				p2Socket = serverSocket.accept();
				p2SocketIn = new BufferedReader(new InputStreamReader(p2Socket.getInputStream()));
				p2SocketOut = new PrintWriter(p2Socket.getOutputStream(), true);
				System.out.println("Player 2 connection accepted");
				
				Game game = new Game(p1SocketIn, p1SocketOut, p2SocketIn, p2SocketOut);
				pool.execute(game);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		TicTacToeServer myServer = new TicTacToeServer();
		myServer.runServer();
	}
}
