import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private Socket palinSocket;
	private ServerSocket serverSocket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	
	public Server(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) throws IOException {
		Server myServer = new Server(8099);
		System.out.println("Server is now running...");
		
		// Establishing a connection
		try {
			myServer.palinSocket = myServer.serverSocket.accept();
			System.out.println("Connection accepted by the server!");
			
			myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.palinSocket.getInputStream()));
			myServer.socketOut = new PrintWriter(myServer.palinSocket.getOutputStream(), true);
			
			myServer.socketIn.close();
			myServer.socketOut.close();
			System.out.println("Ending Connection... Good Bye!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
