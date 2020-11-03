import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.*;

public class TicTacToeClientGUI extends JFrame implements ActionListener {
	
	private JTextField playerName;
	private JTextField playerMarker;
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
	
	public TicTacToeClientGUI() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
		
		JPanel layersPanel = new JPanel();
		layersPanel.setLayout(new BoxLayout(layersPanel, BoxLayout.Y_AXIS));
		
		// Top Panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
		
		// Buttons Component
		btn1 = new JButton("1");
		btn2 = new JButton("2");
		btn3 = new JButton("3");
		btn4 = new JButton("4");
		btn5 = new JButton("5");
		btn6 = new JButton("6");
		btn7 = new JButton("7");
		btn8 = new JButton("8");
		btn9 = new JButton("9");
		
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
		messagePanel.add("South", messageWindow);
		
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
		playerMarker = new JTextField("X",5);
		markerPanel.add(markerLabel);
		markerPanel.add(playerMarker);
		
		// Player Name Component
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		JLabel nameLabel = new JLabel("Player Name: ");
		playerName = new JTextField("Name",15);
		namePanel.add(nameLabel);
		namePanel.add(playerName);
		
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

	}
	
	public static void main(String[] args) {
		new TicTacToeClientGUI();
	}

	
}
