package project;

import javax.swing.*;

/*****************************************************************
 * The main driver class. This class calls the 'MineSweeperPanel' 
 * class to setup the board.
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
public class MineSweeper {
	
	/*****************************************************************
	 * The driver method that prompts the user for the size of the 
	 * board and the number of mines in the board.
	 * @param args
	 *****************************************************************/
	public static void main (String[] args) {
		
		// Instantiate JFrame
		JFrame frame = new JFrame ("Mine Sweeper");
		// Exit on close
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		int size = 0, num = 0, m = 0;
		
		// Retrieve board size data from user
		do {
			// Prompt the user for size of board
			String x = JOptionPane.showInputDialog(null, "Enter in the size of the board between 3 and 30: ");
			
			try {
				size = Integer.parseInt(x);
				
				// Determine if size is appropriate
				if (size >= 3 && size <= 30) {
					m = 1;
				} else {
					JOptionPane.showMessageDialog(null, "The number you entered was not valid.");
					m = 0;
				}
				
			// Catch NumberFormatException
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "The number you entered was not valid.");
				m = 0;
			}
		
		// Continue until user response is valid
		} while (m == 0);
		
		// Retrieve number of mines data from user
		do {
			// Prompt the user for number of mines
			String x = JOptionPane.showInputDialog(null, "Enter in the number of mines on the board: ");
			
			try {
				num = Integer.parseInt(x);
				
				// Determine if number is appropriate
				if (num <= size*size) {
					m = 1;
				} else {
					JOptionPane.showMessageDialog(null, "The number you entered exceeded the cell count.");
					m = 0;
				}
				
			// Catch NumberFormatException
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(null, "The number you entered was not valid.");
				m = 0;
			}
			
		} while (m == 0);
		
		// Instantiate new MineSweeperPanel with new user-defined board parameters
		MineSweeperPanel panel = new MineSweeperPanel(size, num);
		// Add panel to existing JFrame
		frame.getContentPane().add(panel);
		// The user can't resize it
		frame.setResizable(false);
		// pack
		frame.pack();
		// Set JFrame as visible
		frame.setVisible(true);
	}
}
