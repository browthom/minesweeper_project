package project;

/***************************************************************************************************
 * Represents the individual cell of the minesweeper mine field.
 * 
 * Project template provided by Dr. Ferguson. This code was created with the help of Dr. Ferguson's
 * instruction.
 * 
 * This code has been revised from its original submission.
 * 
 * @author Thomas Brown
 * @Course CIS 163-01
 * @due 2/12/2014
 * @Instructor Dr. Ferguson
 ***************************************************************************************************/
public class Cell {
	
	/**The boolean value that demonstrates whether or not the Cell is exposed.*/
    private boolean exposed;
    
    /**The boolean value that demonstrates whether or not the Cell is a mine.*/
    private boolean mine;
    
    /**The boolean value that demonstrates whether or not the Cell has been flagged.*/
    private boolean flagged;
    
    /**The integer value that demonstrates how many mines the Cell is touching.*/
    private int mineCount;

    /***************************************************************************************************
     * Constructor that initializes the instance variables to the parameter values.
     * @param Count	The integer value that represents the number of mines touching the current Cell.
     * @param flagged The boolean value that represents whether or not the Cell has been flagged.
     * @param exposed The boolean value that represents whether or not the Cell has been exposed.
     * @param mine	The booelan value that represents whether or not the Cell is a mine.
     ***************************************************************************************************/
    public Cell(int Count, boolean flagged, boolean exposed, boolean mine) {
    	this.exposed = exposed;
    	this.mine = mine;
    	this.flagged = flagged;
    	mineCount = Count;
    }

    /***************************************************************************************************
     * Method that returns the boolean value isExposed. This value determines whether or not the Cell is exposed.
     * @return	The boolean value that represents whether or not the Cell is exposed.
     ***************************************************************************************************/
    public boolean isExposed() {
        return exposed;
    }

    /***************************************************************************************************
     * Sets the boolean instance variable "isExposed" to the boolean value passed to it.
     * @param exposed	The boolean value that represents whether or not the Cell is exposed.
     ***************************************************************************************************/
    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    /***************************************************************************************************
     * Returns the boolean value "isMine". This value determines whether or not the Cell is a mine.
     * @return	The boolean value that represents whether or not the Cell is a mine.
     ***************************************************************************************************/
    public boolean isMine() {
        return mine;
    }

    /***************************************************************************************************
     * Sets the boolean instance variable "isMine" to the boolean value passed to it.
     * Sets the Cell to either or a mine or not a mine.
     * @param mine	The boolean value that represents the whether or not the Cell is a mine.
     ***************************************************************************************************/
    public void setMine(boolean mine) {
        this.mine = mine;
    }
    
    /***************************************************************************************************
     * Returns the boolean value "isMine". This value determines whether or not the Cell is flagged.
     * @return	The boolean value that represents whether or not the Cell is flagged.
     ***************************************************************************************************/
    public boolean isFlagged() {
    	return flagged;
    }
    
    /***************************************************************************************************
     * Sets the boolean instance variable "flagged" to the boolean value passed to it.
     * Sets the Cell to either being flagged or not flagged.
     * @param flagged	The boolean value that represents whether or not the Cell is flagged.
     ***************************************************************************************************/
    public void setFlagged(boolean flagged) {
    	this.flagged = flagged;
    }
    
    /***************************************************************************************************
     * Returns the boolean value "mineCount". This value determines how many mines the Cell is touching.
     * @return	The integer value that represents the number of mines touching the Cell.
     ***************************************************************************************************/
    public int getMineCount() {
    	return mineCount;
    }
    
    /***************************************************************************************************
     * Sets the boolean instance variable "mineCount" to the boolean value passed to it.
     * Sets the Cell to either being flagged or not flagged.
     * @param count		The integer value that represents the number of mines touching the Cell.
     ***************************************************************************************************/
    public void setCount(int count) {
    	mineCount = count;
    }
}