/**
 * This class represents a tic-tac-toe board through a two dimensional array of characters of size 3x3.
 * There are methods to draw/visualize the state of the board, check whether the board is full, 
 * check if there is a winner, draw markers on the board, and clear the board.
 * This class also implements a set of marker characters from the Constants interface
 * 
 * @author Justin Leong
 * @version 1.0
 * @since October 1, 2020
 *
 */
public class Board implements Constants {
	/**
	 * The tic-tac-toe board that holds the markers
	 */
	private char theBoard[][];
	
	/**
	 * The number of markets currently on the board
	 */
	private int markCount;
	
	/**
	 * Constructs a Board object and fills each container position in the board with a space character 
	 */
	public Board() {
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}
	
	/**
	 * Gets the marker character at a specific container position in the board
	 * @param row the row position of the desired marker
	 * @param col the column position of the desired marker
	 * @return the character of the row and column position in the board
	 */
	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	/**
	 * Checks whether the board is full of markers
	 * @return true if the board is completely full of markers (i.e all 9 positions are filled), otherwise false 
	 */
	public boolean isFull() {
		return markCount == 9;
	}

	/**
	 * Checks whether the player using the x marker has won or not
	 * @return true if the player has won, otherwise false
	 */
	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Checks whether the player using the o marker has won or not
	 * @return true if the player has won, otherwise false
	 */
	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	/**
	 * Prints the current representation of the tic-tac-toe board with row and column labels
	 */
	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			System.out.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				System.out.print("|  " + getMark(row, col) + "  ");
			System.out.println("|");
			addSpaces();
			addHyphens();
		}
	}

	/**
	 * Updates the board with the provided marker at the position specified by row and column
	 * @param row the row position where the marker should be placed on the board
	 * @param col the col position where the marker should be placed on the board
	 * @param mark the new character being placed on the board
	 */
	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	/**
	 * Resets the board by resetting the mark count and setting all the marker characters to a space on the board
	 */
	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	/**
	 * Checks if a certain marker has won based on whether the marker appears consecutively in a row, col, or diagonally
	 * @param mark the marker being checked for whether it has won
	 * @return true if the marker appears three times consecutively in a row, col, or diagonally, otherwise false 
	 */
	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	/**
	 * Prints the column headers of the tic-tac-toe board
	 */
	void displayColumnHeaders() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|col " + j);
		System.out.println();
	}
	
	/**
	 * Prints hyphens to construct board
	 */
	void addHyphens() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("+-----");
		System.out.println("+");
	}

	/**
	 * Prints spaces for constructing board
	 */
	void addSpaces() {
		System.out.print("          ");
		for (int j = 0; j < 3; j++)
			System.out.print("|     ");
		System.out.println("|");
	}
}
