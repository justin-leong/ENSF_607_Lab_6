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
	
	private Board myBoard;
	private char myMark;
	private char opponentMark;
	
	public TicTacToeClient(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			myBoard = new Board();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void communicate() {
		String name = "";

		System.out.println("Please enter your name: ");
		try {
			name = stdIn.readLine();
			socketOut.println(name);
			System.out.println("Waiting for opponent to connect");
			myMark = socketIn.readLine().charAt(0);
			System.out.println("My marker: " + myMark);
			if(myMark == 'X') {
				opponentMark = 'O';
			}else {
				opponentMark = 'X';
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String gameStatus = "Your Turn";
		
		while(gameStatus.equals("Your Turn") || gameStatus.equals("Opponents Turn")) {
			try {
				//System.out.println("Waiting for gameStatus");
				gameStatus = socketIn.readLine();
				
				if(gameStatus.equals("Your Turn")) {
					System.out.println("\n" + name + " it is your turn to make a move.\n");
					String playerMove = "Invalid";
					String row = "";
					String col = "";
					
					while(playerMove.equals("Invalid")) {
						String rowValid = "Invalid";
						String colValid = "Invalid";
						
						
						while(rowValid.equals("Invalid")) {
							System.out.println(name + " what row should your next mark be placed in?");
							row = stdIn.readLine();
							socketOut.println(row);
							rowValid = socketIn.readLine();
						}
						
						while(colValid.equals("Invalid")) {
							System.out.println(name + " what column should your next mark be placed in?");
							col = stdIn.readLine();
							socketOut.println(col);
							colValid = socketIn.readLine();
						}
						
						playerMove = socketIn.readLine();
					}
					
					myBoard.addMark(Integer.parseInt(row), Integer.parseInt(col), myMark);
					myBoard.display();
					//System.out.println("Row: " + row);
					//System.out.println("Col: " + col);
					
					
					
				} else if(gameStatus.equals("Opponents Turn")) {
					System.out.println("Waiting for opponent to make move");
					//String opponentsMove = socketIn.readLine();
					//System.out.println("Opponent's Move: " + opponentsMove);
					int opponentRow = socketIn.read();
					int opponentCol = socketIn.read();
					
					myBoard.addMark(opponentRow, opponentCol, opponentMark);
					myBoard.display();
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(gameStatus);
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		TicTacToeClient myClient = new TicTacToeClient("localhost", 7777);
		myClient.communicate();
	}
}
