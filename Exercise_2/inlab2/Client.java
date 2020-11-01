package inlab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
/***
 * This class represents a client that sends a request for the date or time to a server and
 * displays the response to the console. This client class communicates on port 9090 of the socket.
 * 
 * @author Justin Leong
 * @version 1.0
 * @since November 1, 2020
 *
 */
public class Client {
	/**
	 * The socket to communicate with the server
	 */
	private Socket socket;
	/**
	 * Output connection of socket
	 */
	private PrintWriter socketOut;
	/**
	 * Input connection of socket
	 */
	private BufferedReader socketIn;
	/**
	 * Input from console
	 */
	private BufferedReader stdIn;
	
	/**
	 * Constructs a client that connects to a server on a desired port
	 * @param serverName name of the server 
	 * @param portNumber port to connect to
	 */
	public Client(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			stdIn = new BufferedReader(new InputStreamReader(System.in));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sends request to server and prints response to console
	 */
	public void communicate() {
		
		String selection = "";
		String response = "";
		boolean running = true;
		
		while(running) {
			try {
				// read input from console
				System.out.println("Please select an option (DATE/TIME) or QUIT to exit");
				selection = stdIn.readLine();
				
				// sends selection to server 
				socketOut.println(selection);
				// receives server response
				response = socketIn.readLine();
				
				// displays response or ends client session depending on selection
				if(!selection.equals("QUIT")) {
					System.out.println(response);
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// closes appropriate sockets to end connection
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Run the client
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Client myClient = new Client("localhost", 9090);
		myClient.communicate();
	}
	
}
