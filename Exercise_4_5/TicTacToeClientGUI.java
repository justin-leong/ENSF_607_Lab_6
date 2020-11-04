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

public class TicTacToeClientGUI extends JFrame implements ActionListener {
	
	private Socket socket;
	private BufferedReader socketIn;
	private PrintWriter socketOut;
	private BufferedReader stdIn;
	
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
	
	public TicTacToeClientGUI(String serverName, int portNumber) {
		// Connect to server
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
	
	public void displayMessage(String message) {
		messageWindow.append("\n" + message);
	}
	
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
	
	
	public void communicate() {
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
			socketOut.println(playerName);
			displayMessage("Waiting for opponent to connect");
			
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
		
		String gameStatus = "Your Turn";
		
		while(gameStatus.equals("Your Turn") || gameStatus.equals("Opponents Turn")) {
			try {
				gameStatus = socketIn.readLine();
				
				if(gameStatus.equals("Your Turn")) {
					myTurn = true;

					displayMessage(playerName + " it is your turn to make a move.");
					String playerMove = "Invalid";
					String row = "";
					String col = "";
					
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
						
						playerMove = socketIn.readLine();
						if(playerMove.equals("Invalid")) {
							displayMessage("Space already taken. Please select an empty space!");
						}
					}
					
					myBoard.addMark(Integer.parseInt(selectedRow), Integer.parseInt(selectedCol), myMark);
					updateCell(selectedRow, selectedCol, myMark);
					
					
					
				} else if(gameStatus.equals("Opponents Turn")) {
					myTurn = false;
					displayMessage("Waiting for opponent to make a move");

					int opponentRow = socketIn.read();
					int opponentCol = socketIn.read();
					
					myBoard.addMark(opponentRow, opponentCol, opponentMark);
					updateCell(Integer.toString(opponentRow), Integer.toString(opponentCol), opponentMark);
					
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
		
		try {
			socketIn.close();
			socketOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		try {
			TicTacToeClientGUI myClient = new TicTacToeClientGUI("localhost", 7777);
			myClient.communicate();
		} catch (Exception e){
			System.err.println(e);
		}
		
	}

	
}
