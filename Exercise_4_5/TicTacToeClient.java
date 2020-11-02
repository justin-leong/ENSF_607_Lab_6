import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TicTacToeClient {
	
	private Socket socket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private BufferedReader stdIn;
	
	public TicTacToeClient(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			stdIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void communicate() {
		String name = "";
		String response = "";

		System.out.println("Please enter your name: ");
		try {
			name = stdIn.readLine();
			socketOut.println(name);
			response = socketIn.readLine();
			System.out.println("Response: " + response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TicTacToeClient myClient = new TicTacToeClient("localhost", 7777);
		myClient.communicate();
	}
}
