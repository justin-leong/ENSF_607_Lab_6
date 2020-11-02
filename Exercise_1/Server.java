import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class represents a server that waits for a request from a client to check whether a string is a 
 * palindrome or not. This server class communicates on port 8099 with a Java socket.
 * 
 * @author Justin Leong
 * @version 1.0
 * @since November 1, 2020
 * 
 */
public class Server {
	
	/**
	 * The palindrome socket to communicate with client
	 */
	private Socket palinSocket;
	
	/**
	 * The server socket used to communicate with client
	 */
	private ServerSocket serverSocket;
	
	/**
	 * Output connection of socket
	 */
	private PrintWriter socketOut;
	
	/**
	 * Input connection of socket
	 */
	private BufferedReader socketIn;
	
	/**
	 * Constructs the server by initializing the server socket at the specified port
	 * @param portNumber the port for the server socket to connect to
	 */
	public Server(int portNumber) {
		try {
			serverSocket = new ServerSocket(portNumber);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads client requests from the input connection of the socket and performs
	 * palindrome checking
	 */
	public void communicate() {
		String word = null;
		
		while(true) {
			try {
				// reads input from socket
				word = socketIn.readLine();
				
				// Disconnects server socket when client ends session
				if(word == null) {
					break;
				}
				
				// performs palindrome checking and sends output to client
				boolean isPalindrome = palindrome(word);
				if(isPalindrome) {
					socketOut.println(word + " is a Palindrome.");
				} else {
					socketOut.println(word + " is not a Palindrome.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Checks if the input string is a palindrome or not
	 * @param word the string to check if palindrome or not
	 * @return true if the word is a palindrome, otherwise false
	 */
	public boolean palindrome(String word) {
		// number of characters in word
		int n = word.length();
		
		// compare character pairs to check whether string is a palindrome
		for(int i = 0; i < n/2; i++) {
			if(word.charAt(i) != word.charAt(n-1-i)) {
				return false;
			}
		}
		
		return true;
	}
	
	public static void main(String[] args) throws IOException {
		// instantiating a Server object with port 8099
		TicTacToeServer myServer = new TicTacToeServer(8099);
		System.out.println("Server is now running...");
		
		// Establishing a connection
		try {
			// waiting for client connection
			myServer.palinSocket = myServer.serverSocket.accept();
			System.out.println("Connection accepted by the server!");
			
			// opens connections to socket
			myServer.socketIn = new BufferedReader(new InputStreamReader(myServer.palinSocket.getInputStream()));
			myServer.socketOut = new PrintWriter(myServer.palinSocket.getOutputStream(), true);
			
			// communicates with client and performs palindrome checking task
			myServer.communicate();
			
			// closes connections to socket
			myServer.socketIn.close();
			myServer.socketOut.close();
			System.out.println("Ending Connection... Good Bye!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
