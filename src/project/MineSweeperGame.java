package project;

import java.util.*;

/*****************************************************************
 * Represents a Minesweeper game through the use of the 'Cell' 
 * class. This class sets specific cells of the Minesweeper board 
 * to certain characteristics. Is dependent upon the 'Cell' class.
 * 
 * Project template provided by Dr. Ferguson. This code was created 
 * with the help of Dr. Ferguson's instruction.
 * 
 * This code has been revised from its original submission.
 * 
 * @author Thomas Brown
 * @Course CIS 163-01
 * @due 2/12/2014
 * @Instructor Dr. Ferguson
 *****************************************************************/
public class MineSweeperGame {
	
	/**The 2-dimensional size of the mine field. Total number of cells in the mine field is "size X size".*/
	private int size;
	
	/**The total number of mines in the mine field.*/
	private int totalMineCount;
	
	/**Represents the cells in the mine field that are not touching any mines. 
	 * True is assigned if cell has been checked by method select; False otherwise.*/
	private boolean[][] alreadyChecked;
	
	/**Represents the cells in the mine field.*/
	private Cell[][] board;
	
	/** The current status of the game */
	private GameStatus status;
	
	/**Number of exposed cells*/
	private int numberOfExposed;
	
	/*****************************************************************
	 * Constructor that sets the instance variable "size" to the 
	 * parameter "size" and the instance variable "totalMineCount" to 
	 * the parameter "num".
	 * 
	 * The constructor instantiates the instance array "board" by 
	 * calling the Cell constructor (size X size) times.
	 * 
	 * The constructor calls the private method generateMineField() to 
	 * generate "num" number of mines on the board.
	 * 
	 * The constructor calls the private method countTouchingMines() 
	 * to count the number of mines each cell of the Cell "board" array 
	 * is touching.
	 * 
	 * @param size The integer size of the Cell "board" array.
	 * @param num The number of mines within the Cell array.
	 *****************************************************************/
	public MineSweeperGame(int size, int num) {
		
		this.size = size;
		this.totalMineCount = num;
		status = GameStatus.NotOverYet;
		board = new Cell[size][size];
		
		// Set initially to 0
		numberOfExposed = 0;
		
		// Create new 2D array of Cells
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				board[row][col] = new Cell(0, false, false, false);
			}
		}
		
		// Instantiate alreadyChecked 2D array
		alreadyChecked = new boolean[size][size];
		// Set entire 2D array as false
		for (int i = 0; i < size; i++)
			Arrays.fill(alreadyChecked[i], false);
		
		generateMineField();
		countTouchingMines();
	}
	
	/*****************************************************************
	 * Method that generates "num" number of mines in the mine field 
	 * by setting random cells in the Cell "board" array to true.
	 * This method is only called by the constructor.
	 *****************************************************************/
	private void generateMineField() {
		int i = 0;
		Random random = new Random();
		
		// Continually determine a new random spot for a mine
		do {
			int row = random.nextInt(size);
			int col = random.nextInt(size);
			
			// Check to make sure there isn't a mine that already exists in this spot
			if (!board[row][col].isMine()) {
				board[row][col].setMine(true);
				i++;
			}
		} while (i < totalMineCount);
	}
	
	/*****************************************************************
	 * Method that sets each Cell of the "board" array to the number 
	 * of mines each Cell touches.
	 * This method is only called by the constructor.
	 *****************************************************************/
	private void countTouchingMines() {
		
		int i,j,k;
		int[] cycleOrderRows, cycleOrderColumns;
		
		int numberOfTouchingMines = 0;
		
		// Iterate through entire grid
		for (i = 0; i < size; i++) {
			for (j = 0; j < size; j++) {
				for (k = 1; k < 9; k++) {
					
					// Cycle clockwise around current cell
					cycleOrderRows = new int[] {i,i-1,i-1,i,i+1,i+1,i+1,i,i-1};
					cycleOrderColumns = new int[] {j,j,j+1,j+1,j+1,j,j-1,j-1,j-1};
					
					// Ensure that the adjacent cell of interest exists and doesn't extend beyond the grid boundaries
					if (cycleOrderRows[k] >= 0 && cycleOrderRows[k] < size && cycleOrderColumns[k] >= 0 && cycleOrderColumns[k] < size) {
						
						// If the adjacent cell of interest is a mine and the current cell isn't a mine,
						// then increase the number of touching mines
						if (board[cycleOrderRows[k]][cycleOrderColumns[k]].isMine() && !board[i][j].isMine())
							numberOfTouchingMines++;	
					}
				}
				
				// If the current cell is not a mine
				if (!board[i][j].isMine())
					board[i][j].setCount(numberOfTouchingMines);
				// If the current cell is a mine, disregard number of touching mines value
				else if (board[i][j].isMine())
					board[i][j].setCount(-1);
				
				// Clear number of mines for next go around
				numberOfTouchingMines = 0;
			}
		}
	}
	
	/*****************************************************************
	 * Checks to see if all non-mine cells have been exposed by the
	 * user.
	 *****************************************************************/
	private boolean checkGameStatus() {
		// The game has been won
		if ((size*size - numberOfExposed) == totalMineCount) {
			return true;
		} else {
			return false;
		}
	}
	
	/*****************************************************************
	 * Exposes a given cell. If the exposed cell does not touch any
	 * adjacent mines, then the entire mine-less field is "exposed",
	 * recursively.
	 * @param row The row index of the cell to be exposed.
	 * @param col The column index of the cell to be exposed.
	 *****************************************************************/
	public void expose(int row, int col) {
		
		int i = row, j = col;
		
		// If not a mine and the cell hasn't been flagged
		if (!board[row][col].isMine() && !board[row][col].isFlagged()) {
			
			// Set cell to exposed
			board[row][col].setExposed(true);
			// Likewise, increment number of exposed
			numberOfExposed++;
			
			/* If cell isn't touching any mines, then expose the entire mine-less field. This is the recursive portion */
			if (board[row][col].getMineCount() == 0) {
				
				// Iterate all around the cell
				for (int k = 0; k < 8; k++) {
					
					// The cycle clockwise pattern around a specific cell
					int[] cycleOrderRows = new int[] {i-1,i-1,i,i+1,i+1,i+1,i,i-1};
					int[] cycleOrderColumns = new int[] {j,j+1,j+1,j+1,j,j-1,j-1,j-1};
					
					// Ensure that the adjacent cell of interest exists and doesn't extend beyond the grid boundaries
					if (cycleOrderRows[k] >= 0 && cycleOrderRows[k] < size && cycleOrderColumns[k] >= 0 && cycleOrderColumns[k] < size) {
						
						// If the adjacent cell of interest isn't a mine or flagged
						// and isn't already exposed
						if (!board[cycleOrderRows[k]][cycleOrderColumns[k]].isMine() &&
							!board[cycleOrderRows[k]][cycleOrderColumns[k]].isFlagged() &&
							!board[cycleOrderRows[k]][cycleOrderColumns[k]].isExposed()) {
							
							// recursively call
							expose(cycleOrderRows[k], cycleOrderColumns[k]);
						}
					}
				}
				
				// Check game status
				if (checkGameStatus()) {
					status = GameStatus.Won;
					return;
				}
			
			// Simply return when the specific cell is touching a mine,
			// no need to expose any other adjacent cells.
			// End recursion.
			} else {
				
				// Check game status
				if (checkGameStatus()) {
					status = GameStatus.Won;
				}
				
				return;
			}
	
		// A mine has been exposed; thus, the user loses.
		// Don't need to worry that this else-statement will be executed during recursion.
		} else if (!board[row][col].isFlagged()) {
			status = GameStatus.Lost;
		}
	}
	
	/*****************************************************************
	 * Toggles the presence of a flag on the specified cell.
	 * @param row The row index of the specified cell.
	 * @param col The column index of the specified cell.
	 *****************************************************************/
	public void toggleFlag(int row, int col) {
		
		// Will toggle the flag of the specific cell
		board[row][col].setFlagged(!board[row][col].isFlagged());
	}
	
	/*****************************************************************
	 * Returns the Cell in the Cell "board" array at index "row" and 
	 * "col".
	 * @param row Integer value representing the row index of the Cell 
	 * to be returned
	 * @param col Integer value representing the col index of the Cell 
	 * to be returned.
	 * @return The Cell at index row and col in the Cell 'board' array.
	 *****************************************************************/
	public Cell getCell(int row, int col) {
		return board[row][col];
	}
	
	/*****************************************************************
	 * Resets the current MineSweeperGame to its initial settings.
	 * Called after previous game has ended.
	 *****************************************************************/
	public void reset() {
		
		// Iterate through grid
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				
				// Reset cells
				board[row][col] = new Cell(0, false, false, false);
			}
		}

		// Set entire 2D array as false
		for (int i = 0; i < size; i++)
			Arrays.fill(alreadyChecked[i], false);
		
		// Reset number of exposed cells to 0
		numberOfExposed = 0;
		
		generateMineField();
		countTouchingMines();
		status = GameStatus.NotOverYet;
	}
	
	/*****************************************************************
	 * Returns the status of the game. Results can be Lost, Won, or, 
	 * NotOverYet.
	 * @return The enumerated type GameStatus 'status'
	 *****************************************************************/
	public GameStatus getGameStatus() {
		return status;
	}
}
