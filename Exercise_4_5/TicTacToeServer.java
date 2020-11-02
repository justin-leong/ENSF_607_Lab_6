import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
			serverSocket = new ServerSocket(7777);
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
				
				p2Socket = serverSocket.accept();
				p2SocketIn = new BufferedReader(new InputStreamReader(p1Socket.getInputStream()));
				p2SocketOut = new PrintWriter(p2Socket.getOutputStream());
				
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
