package inlab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	private Socket socket;
	private PrintWriter socketOut;
	private BufferedReader socketIn;
	private BufferedReader stdIn;
	
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
	
	public void communicate() {
		
		String selection = "";
		String response = "";
		boolean running = true;
		
		while(running) {
			try {
				System.out.println("Please select an option (DATE/TIME) or QUIT to exit");
				selection = stdIn.readLine();
				
				socketOut.println(selection);
				response = socketIn.readLine();
				
				if(!selection.equals("QUIT")) {
					System.out.println(response);
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Client myClient = new Client("localhost", 9090);
		myClient.communicate();
	}
	
}
