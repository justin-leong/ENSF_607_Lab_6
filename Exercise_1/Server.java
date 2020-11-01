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
	
	public void communicate() {
		String line = null;
		
		while(true) {
			try {
				line = socketIn.readLine();

				if(line == null) {
					break;
				}
				
				boolean isPalindrome = palindrome(line);
				if(isPalindrome) {
					socketOut.println(line + " is a Palindrome.");
				} else {
					socketOut.println(line + " is not a Palindrome.");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// function to check if a string is a palindrome
	public boolean palindrome(String line) {
		// number of characters in string
		int n = line.length();
		
		// compare character pairs to check whether string is a palindrome
		for(int i = 0; i < n/2; i++) {
			if(line.charAt(i) != line.charAt(n-1-i)) {
				return false;
			}
		}
		
		return true;
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
			
			myServer.communicate();
			
			myServer.socketIn.close();
			myServer.socketOut.close();
			System.out.println("Ending Connection... Good Bye!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
