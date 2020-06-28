package project;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*****************************************************************
 * A GUI representation of a minesweeper game. Is dependent upon 
 * the classes 'MineSweeperGame' and 'Cell'.
 * 
 * Project template provided by Dr. Ferguson. This code was 
 * created with the help of Dr. Ferguson's instruction.
 * 
 * This code has been revised from its original submission.
 * 
 * @author Thomas Brown
 * @Course CIS 163-01
 * @Due 2/12/2014
 * @Instructor Dr. Ferguson
 *****************************************************************/
public class MineSweeperPanel extends JPanel {
	
	/***/
	private static final long serialVersionUID = 1L;

	/**The GUI representation of the mine field.*/
	private JButton[][] board;
	
	/**Quit button to exit GUI*/
	private JButton quitButton;
	
	/**Button that triggers the ability to flag cells*/
	private JRadioButton flag;
	
	/**Button that triggers the ability to see the contents of all the cells*/
	private JRadioButton instructorMode;
	
	/**Label for the 'flag' JRadioButton*/
	private JLabel flagName;
	
	/**Label for the 'instructorMode' JLabel*/
	private JLabel instructorModeName; 
	
	/**Displays number of wins.*/
	private JLabel winsName; 
	
	/**Displays number of losses.*/
	private JLabel lossesName;
	
	/**Number of wins.*/
	private int wins;
	
	/**Number of losses.*/
	private int losses;
	
	/**JPanel containing the options for the game.*/
	private JPanel top;
	
	/**JPanel containing the board.*/
	private JPanel bottom;
	
	/**The MineSweeperGame object used for the GUI.*/
	private MineSweeperGame game;
	
	/**The Cell object representing the cells of the board.*/
	private Cell iCell;
	
	/**The size of the height and width of the board.*/
	private int size;
	
	/**Boolean value used to trigger the flagging capability.*/
	private boolean toggle; 
	
	/**Boolean value used to trigger the instructor mode or cheat mode.*/
	private boolean toggleInstructor;
	
	/** The image icon of the mine */
	private ImageIcon mineImage, flagImage;
	
	/*****************************************************************
	 * Constructor that instantiates all instance variables including 
	 * all JButtons that represent the Cell array. All Buttons and 
	 * Labels are added to the panels and the panels are added to the 
	 * larger JPanel. A new MineSweeperGame is instantiated in this 
	 * constructor.
	 * @param size	Integer value representing the size of the board's 
	 * height and width.
	 * @param num 	Integer value representing the number of mines in 
	 * the on the board.
	 *****************************************************************/
	public MineSweeperPanel(int size, int num) {	
		
		wins = 0;
		losses = 0;
		toggle = false;
		toggleInstructor = false;
		top = new JPanel();
		bottom = new JPanel();
		
		flag = new JRadioButton();
		instructorMode = new JRadioButton();
		
		flagName = new JLabel("Flag");
		instructorModeName = new JLabel("Instructor Mode");
		winsName = new JLabel("Wins: ");
		lossesName = new JLabel("Losses: ");
		quitButton = new JButton("Quit");
		ButtonListener listener = new ButtonListener();
		QuoteListener list = new QuoteListener();
		quitButton.addActionListener(listener);
		flag.addActionListener(list);
		instructorMode.addActionListener(list);
		
		/* Get new image and resize */
		// From: https://stackoverflow.com/questions/2856480/resizing-a-imageicon-in-a-jbutton
		// Answered by tim_yates
		// Referenced from: https://coderanch.com/t/331731/java/Resize-ImageIcon
		try {	
		 	Image img = ImageIO.read(getClass().getResource("mine.png"));
		 	// Create new instance based on scaled dimensions
		 	Image newimg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		 	// Create new image icon
		 	mineImage = new ImageIcon(newimg);
		} catch (Exception e) {
			
		}
		
		try {	
		 	Image img = ImageIO.read(getClass().getResource("flag.png"));
		 	// Create new instance based on scaled dimensions
		 	Image newimg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		 	// Create new image icon
		 	flagImage = new ImageIcon(newimg);
		} catch (Exception e) {
			
		}
		
		this.size = size;
		
		// Set top layout as borderlayout
		this.setLayout(new BorderLayout());
		
		// Set bottom panel as flowlayout
		bottom.setLayout(new FlowLayout());
		
		// Cell Grid Panel
		JPanel cellGrid = new JPanel();
		// Set Layout
		cellGrid.setLayout(new GridLayout(size, size));
		
		board = new JButton[size][size];

		for (int row = 0; row < size; row++)
			for (int col = 0; col < size; col++) 
			{
				board[row][col] = new JButton();
				board[row][col].addActionListener(listener);
				
				// Remove margins of JButtons (this is necessary for the JButton text to be visible)
				// From: https://stackoverflow.com/questions/2712637/how-can-i-make-a-button-exactly-the-same-size-of-its-text/2713814#2713814
				// Answered by Eugene Ryzhikov
				board[row][col].setMargin(new Insets(0,0,0,0));				
				// Set the size of the cell
				board[row][col].setPreferredSize(new Dimension(25, 25));
				// Add the JButton to the cellGrid JPanel
				cellGrid.add(board[row][col]);
			}
		
		// Add the cellGrid JPanel to the Bottom Panel
		bottom.add(cellGrid);
		
		top.add(flag);
		top.add(flagName);
		
		top.add(instructorMode);
		top.add(instructorModeName);
		
		top.add(winsName);
		top.add(lossesName);
		top.add(quitButton);
		
		add(top, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		game = new MineSweeperGame(size, num);
		
		displayBoard();
	}
	
	/******************************************************************
	 * Updates and displays the GUI board after each button press the 
	 * user makes.
	 ******************************************************************/
	private void displayBoard() {
			
		// Iterate through 2D grid
		for (int row = 0; row < size; row++) {
			for (int col = 0; col < size; col++) {
				
				// Retrieve Cell
				iCell = game.getCell(row, col);
				
				/* Disable appropriate JButton if Cell has been exposed */
				if (iCell.isExposed() && !iCell.isMine() && !iCell.isFlagged())
					board[row][col].setEnabled(false);
				else
					board[row][col].setEnabled(true);
				
				/* Assign Images and numbers as needed */
				// Set flag image
				if (iCell.isFlagged()) {
					board[row][col].setIcon(flagImage);
					board[row][col].setText("");
				} else if (!iCell.isFlagged()) {
					board[row][col].setIcon(null);
					board[row][col].setText("");
				}
				
				/* Assign mine image */
				// Set mine image
				if (iCell.isMine() && !iCell.isFlagged() && (toggleInstructor || game.getGameStatus() == GameStatus.Lost)) {
					board[row][col].setIcon(mineImage);
					board[row][col].setText("");
			 	// If it is a mine and the above conditions aren't met, then delete image from JButton
				} else if (iCell.isMine() && !iCell.isFlagged()){
					board[row][col].setIcon(null);
					board[row][col].setText("");
				}
				
				/* Write number */
				// Set text
				if ((iCell.getMineCount() > 0) && !iCell.isFlagged() && 
					((iCell.isExposed() && !iCell.isMine()) || toggleInstructor)) {
					
					board[row][col].setText(iCell.getMineCount() + "");
					
				// Delete text if above conditions aren't met
				} else if (iCell.getMineCount() > 0 && !iCell.isFlagged()) {
					board[row][col].setText("");
				}
				
				/* Delete text */
				if (iCell.getMineCount() == 0 && !iCell.isMine()) {
					board[row][col].setText("");
				}
				
			}
		}			
	}
	
	/*****************************************************************
	 * Check the status of the game and show a dialog box if necessary.
	 *****************************************************************/
	public void checkGameStatus() {

		// If user loses
		if (game.getGameStatus() == GameStatus.Lost) {
			JOptionPane.showMessageDialog(null, "You Lose");
			losses++;
			lossesName.setText("Losses: " + losses);
			toggle = false;
			toggleInstructor = false;
			game.reset();
			
			// Reset field
			displayBoard();
			
		// Else if user wins
		} else if (game.getGameStatus() == GameStatus.Won) {
			JOptionPane.showMessageDialog(null, "You Won");
			wins++;
			winsName.setText("Wins: " + wins);
			toggle = false;
			toggleInstructor = false;
			game.reset();
			
			// Reset field
			displayBoard();
		}
	}
	
	/*****************************************************************
	 * ButtonListener class that listens for the 'board' array 
	 * JButtons and the 'quitButton' JButton.
	 *****************************************************************/
	private class ButtonListener implements ActionListener {
		
		/*****************************************************************
		 * Takes the event passed to it and determines where to go next.
		 *****************************************************************/
		public void actionPerformed(ActionEvent e) {
			
			// Iterate through 2D grid
			for (int row = 0; row < size; row++) {
				for (int col = 0; col < size; col++) {
					
					// If cell JButton was pressed
					if (board[row][col] == e.getSource()) {
						
						// Determine type of button press
						if (!toggle) {
							game.expose(row, col);
						} else {
							game.toggleFlag(row, col);
						}
					}
				}
			}
			
			// Simply Exit
			if (quitButton == e.getSource())
				System.exit(0);
			
			// Refresh JPanel
			displayBoard();
			
			checkGameStatus();
		}
	}
	
	/*****************************************************************
	 * QuoteListener class that listens for 'flag' JRadiobutton and 
	 * the 'instructorMode' JRadioButton.
	 *****************************************************************/
	private class QuoteListener implements ActionListener {
		
		/*****************************************************************
		 * Takes the event passed to it and determines where to go next.
		 *****************************************************************/
		public void actionPerformed(ActionEvent e) {
			
			// Toggle between user creating flags or user exposing cells
			if (e.getSource() == flag)
				toggle = !toggle;
			
			// Toggle between instructor mode and user mode
			if (e.getSource() == instructorMode) {
				
				toggleInstructor = !toggleInstructor;
				
				// Refresh JPanel to expose numbers and mines
				displayBoard();
			}
		}
	}
}
