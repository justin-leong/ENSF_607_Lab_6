import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class acts as a GUI client that connects to a tic-tac-toe server to play a game. This class uses swing components
 * to display the game state to the client and has event listeners to interact with the server.
 * 
 * @author Justin Leong
 * @version 1.0
 * @since November 3, 2020
 *
 */
public class TicTacToeClientGUI extends JFrame implements ActionListener {
	
	private Socket socket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	
	private Board myBoard;
	private char myMark;
	private char opponentMark;
	private String playerName;
	
	private boolean myTurn = false;
	private boolean madeMove = false;
	private String selectedRow = "";
	private String selectedCol = "";
	
	private JTextField playerNameField;
	private JTextField playerMarkerField;
	private JTextArea messageWindow;
	private JButton btn1;
	private JButton btn2;
	private JButton btn3;
	private JButton btn4;
	private JButton btn5;
	private JButton btn6;
	private JButton btn7;
	private JButton btn8;
	private JButton btn9;
	
	/**
	 * Constructs a TicTacToeClientGUI by setting up the connection to the socket via server name and port number.
	 * Sets up the GUI for client to interact with the server.
	 * @param serverName ip of the server or 'localhost' if client is on the same machine as server
	 * @param portNumber port to connect to
	 */
	public TicTacToeClientGUI(String serverName, int portNumber) {
		// Connect to server
		try {
			socket = new Socket(serverName, portNumber);
			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			myBoard = new Board();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// builds GUI that the client will interact with
		buildGUI();
	}
	
	/**
	 * Creates and displays swing components for tic-tac-toe GUI
	 */
	public void buildGUI() {
		// Creating GUI elements
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		
		JPanel layersPanel = new JPanel();
		layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
		
		// Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		// Buttons Component
		btn1 = new JButton("");
		btn2 = new JButton("");
		btn3 = new JButton("");
		btn4 = new JButton("");
		btn5 = new JButton("");
		btn6 = new JButton("");
		btn7 = new JButton("");
		btn8 = new JButton("");
		btn9 = new JButton("");
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		btn4.addActionListener(this);
		btn5.addActionListener(this);
		btn6.addActionListener(this);
		btn7.addActionListener(this);
		btn8.addActionListener(this);
		btn9.addActionListener(this);
		
		JPanel gridPanel = new JPanel();
		gridPanel.setPreferredSize(new Dimension(250, 250));
		gridPanel.setLayout(new GridLayout(3, 3));
		gridPanel.add(btn1);
		gridPanel.add(btn2);
		gridPanel.add(btn3);
		gridPanel.add(btn4);
		gridPanel.add(btn5);
		gridPanel.add(btn6);
		gridPanel.add(btn7);
		gridPanel.add(btn8);
		gridPanel.add(btn9);
		
		// Message Component
		messageWindow = new JTextArea("WELCOME TO THE GAME", 15, 25);
		JScrollPane scrollPane = new JScrollPane(messageWindow, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		messageWindow.setLineWrap(true);
		JPanel messagePanel = new JPanel();
		messagePanel.setLayout(new BorderLayout());
		messagePanel.add("Center", new JLabel("Message Window"));
		messagePanel.add("South",scrollPane);
		
		topPanel.add( gridPanel);
		topPanel.add(Box.createRigidArea(new Dimension(30, 0)));
		topPanel.add( messagePanel);
		
		// Bottom Panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		
		// Marker Component
		JPanel markerPanel = new JPanel();
		markerPanel.setLayout(new BoxLayout(markerPanel, BoxLayout.X_AXIS));
		JLabel markerLabel = new JLabel("Marker: ");
		playerMarkerField = new JTextField("",5);
		markerPanel.add(markerLabel);
		markerPanel.add(playerMarkerField);
		
		// Player Name Component
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		JLabel nameLabel = new JLabel("Player Name: ");
		playerNameField = new JTextField("",15);
		playerNameField.addActionListener(this);
		namePanel.add(nameLabel);
		namePanel.add(playerNameField);
		
		bottomPanel.add(markerPanel);
		bottomPanel.add(namePanel);
		
		
		// Organizing component positions
		layersPanel.add(topPanel);
		layersPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		layersPanel.add(bottomPanel);
		
		contentPane.add("Center",layersPanel);
		
		setTitle("Tic-Tac-Toe Game");
		setSize(800, 400);
		setVisible(true);
	}
	
	/**
	 * Performs actions based on events triggered from GUI components
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(myTurn == true && playerName != null) {
			if(e.getSource() == btn1) {
				selectSpace("0","0");
			} else if(e.getSource() == btn2) {
				selectSpace("0","1");
			} else if(e.getSource() == btn3) {
				selectSpace("0","2");
			} else if(e.getSource() == btn4) {
				selectSpace("1","0");
			} else if(e.getSource() == btn5) {
				selectSpace("1","1");
			} else if(e.getSource() == btn6) {
				selectSpace("1","2");
			} else if(e.getSource() == btn7) {
				selectSpace("2","0");
			} else if(e.getSource() == btn8) {
				selectSpace("2","1");
			} else if(e.getSource() == btn9) {
				selectSpace("2","2");
			}
		}
		
		if(playerName == null && e.getSource() == playerNameField) {
			playerName = playerNameField.getText();
		}
	}
	
	/**
	 * Displays message to GUI message component
	 * @param message the message to display to the client on the GUI
	 */
	public void displayMessage(String message) {
		messageWindow.append("\n" + message);
	}
	
	/**
	 * Tic-tac-toe space selected from client
	 * @param row selected row to place marker
	 * @param col selected column to place marker
	 */
	public void selectSpace(String row, String col) {
		try {
			socketOut.println(row);
			socketIn.readLine();
			socketOut.println(col);
			socketIn.readLine();
			
			selectedRow = row;
			selectedCol = col;
			madeMove = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the GUI element for the selected tic-tac-toe space with a marker
	 * @param row the row of the tic-tac-toe grid space to place marker
	 * @param col the column of the tic-tac-toe grid space to place marker
	 * @param mark the marker to display on the GUI component
	 */
	public void updateCell(String row, String col, char mark) {
		if(row.equals("0") && col.equals("0")) {
			btn1.setText(Character.toString(mark));
		} else if(row.equals("0") && col.equals("1")) {
			btn2.setText(Character.toString(mark));
		} else if(row.equals("0") && col.equals("2")) {
			btn3.setText(Character.toString(mark));
		} else if(row.equals("1") && col.equals("0")) {
			btn4.setText(Character.toString(mark));
		} else if(row.equals("1") && col.equals("1")) {
			btn5.setText(Character.toString(mark));
		} else if(row.equals("1") && col.equals("2")) {
			btn6.setText(Character.toString(mark));
		} else if(row.equals("2") && col.equals("0")) {
			btn7.setText(Character.toString(mark));
		} else if(row.equals("2") && col.equals("1")) {
			btn8.setText(Character.toString(mark));
		} else if(row.equals("2") && col.equals("2")) {
			btn9.setText(Character.toString(mark));
		}
	}
	
	
	/**
	 * Communicates with the server to play tic-tac-toe between another client. Handles setup of game information and 
	 * starts game play.
	 */
	public void communicate() {
		// prompts user to enter name in GUI application
		setupPlayerName();
		
		// prompts player to make move or to wait for opponent to make move
		playGame();
		
		// disconnect socket connections once game is over
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Prompts user to enter name and sends information to server
	 */
	public void setupPlayerName() {
		displayMessage("Please enter your name to begin");
		
		try {
			while(playerName == null) {
				// wait until user enters a player name
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			// sends player name info to server
			socketOut.println(playerName);
			
			displayMessage("Waiting for opponent to connect");
			
			// get marker from server and set markers for self and opponent
			myMark = socketIn.readLine().charAt(0);
			playerMarkerField.setText(Character.toString(myMark));
			if(myMark == 'X') {
				opponentMark = 'O';
			}else {
				opponentMark = 'X';
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Manages the user input and server requests for the tic-tac-toe game. Displays the game state after each turn and
	 * updates the front end of this client.
	 */
	public void playGame() {
		String gameStatus = "Your Turn";
		
		while(gameStatus.equals("Your Turn") || gameStatus.equals("Opponents Turn")) {
			try {
				gameStatus = socketIn.readLine();
				
				if(gameStatus.equals("Your Turn")) {
					// waits for player to make move and sends move selection to server
					performMyTurnTasks();
				} else if(gameStatus.equals("Opponents Turn")) {
					// waits for opponent to make move and receives opponent move from server
					performOpponentsTurnTasks();
				} else {
					break;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		myTurn = false;
		displayMessage("\n" + gameStatus);
		displayMessage("GAME OVER!");
	}
	
	/**
	 * Prompts player that it is their turn to make a move and handles sending move selection to server.
	 * Updates board after player make a valid selection.
	 */
	public void performMyTurnTasks() {
		myTurn = true;

		displayMessage(playerName + " it is your turn to make a move.");
		String playerMove = "Invalid";
		
		while(playerMove.equals("Invalid")) {
			
			while(!madeMove) {
				// wait for player to make a move
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			madeMove = false;
			
			try {
				playerMove = socketIn.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(playerMove.equals("Invalid")) {
				displayMessage("Space already taken. Please select an empty space!");
			}
		}
		
		myBoard.addMark(Integer.parseInt(selectedRow), Integer.parseInt(selectedCol), myMark);
		updateCell(selectedRow, selectedCol, myMark);
	}
	
	/**
	 * Waits for opponent to select move and receives move selection from server. Updates board after section is made.
	 */
	public void performOpponentsTurnTasks() {
		myTurn = false;
		displayMessage("Waiting for opponent to make a move");

		int opponentRow = -1;
		int opponentCol = -1; 
		try {
			opponentRow = socketIn.read();
			opponentCol = socketIn.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		myBoard.addMark(opponentRow, opponentCol, opponentMark);
		updateCell(Integer.toString(opponentRow), Integer.toString(opponentCol), opponentMark);
	}
	
	/**
	 * main method that creates the client object and begins communication with server
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		try {
			TicTacToeClientGUI myClient = new TicTacToeClientGUI("localhost", 7777);
			myClient.communicate();
		} catch (Exception e){
			System.err.println(e);
		}
	}	
}
