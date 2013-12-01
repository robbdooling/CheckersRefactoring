/**
 * Facade.java
 *
 * Version	
 *   $Id: Facade.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *   $Log: Facade.java,v $
 *   Revision 1.1  2002/10/22 21:12:52  se362
 *   Initial creation of case study
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;

/**
 * An interface between the GUI and the kernel classes in a checkers game.
 * 
 * @author
 */

public class Facade extends Component {

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;

    public static String update       = "update";
    public static String playerSwitch = "switch";
    public static String ID           = "facade";
    
    public Driver      theDriver;
    public LocalPlayer theLocalPlayer;
    public Board       theBoard;
    public Player      passivePlayer;
    public Player      activePlayer;		
    
    private int startSpace = 99; // Starting space for current move
    private int endSpace   = 99; // Ending space for current move
    
    
    private ActionListener actionListener;
      
    /**
     * Constructor for the facade.  Initializes the data members.
     * 
     * @param newBoard  Board  object Facade will manipulate.
     * @param newDriver Driver object that will communicate 
     *                  with the Facade.
     */
    public Facade( Board newBoard, Driver newDriver ){
	
	theBoard = newBoard;
	theDriver = newDriver;
	
    }
    
    /**
     * Return an int indicating which player's turn it is.
     * ( e.g. 1 for player 1 )
     *
     * @return int   The number of the player whose turn it is.
     * 
     * @pre game is in progress
     */
    public int whosTurn(){
	
	// Return the integer value of the activePlayer object
	int turn;
	turn = activePlayer.getNumber();
	
	return turn;
    }
    
    /**
     * Set which player's turn it is.
     * 
     * @param active  The active player
     * @param passive The passive player
     */
    public void setPlayerModes( Player active, Player passive ){
	
	activePlayer = active;
	passivePlayer = passive;
	
	// Tell GUI to update
	generateActionPerformed( update );
    }
    
    /**
     *
     * This method should be called to select a space on the board, 
     * either as the starting point or the ending point for a move.  
     * The Facade will interpret this selection and send a move on to 
     * the kernel when two spaces have been selected.
     *
     * @param space an int indicating which space to move to, 
     *              according to the standard checkers numbering 
     *              scheme, left to right and top to bottom.
     */
    public void selectSpace( int space ){  
	
	// When button is click, take info from the GUI
	if( startSpace == 99 ){
	    
	    // Set startSpace to space
	    startSpace = space;
	    
	}else if( startSpace != 99 && endSpace == 99 ){
	    if( space == startSpace ){
		
		// Viewed as un-selecting the space selected
		// Set startSpace to predetermined unselected value
		startSpace = 99;
		
	    }else{
		// The endSpace will be set to space
		endSpace = space;
		makeLocalMove();
	    }
	}
	
	generateActionPerformed( "update" );   
	
    }
    
    /**
     * Send a move on to the kernel, i.e. call makeMove() in 
     * the LocalPlayer and inform it whose turn it is.
     *
     * @pre startSpace is defined
     * @pre endSpace is defined
     */
    private void makeLocalMove(){
	
	//make sure startSpace and endSpace are defined
	if( startSpace != 99 && endSpace!= 99 ){
	    // Takes the information of a move and calls makeMove() 
	    // in a localPlayer
	    boolean result = activePlayer.makeMove( startSpace, endSpace );
	}
	
	// Reset startSpace and endSpace to 99
	startSpace = 99;
	endSpace   = 99;
	
    }
    
    /**
     * Given a player number, returns the name associated 
     * with that number.
     * 
     * @param  playerNum the number of a player
     * @return string    the name associated with playerNum
     *
     * @pre playerNum is a valid player number
     */
    public String getPlayerName( int playerNum ){
	String retString = null;
	
	try{
	    // Checks to see that playerNum is valid
	    if( playerNum == 1 || playerNum == 2 ){
		// checks both Player objects to see which one is 
		// associated with the legal number returns the name of 
                // the player asscociated with the number
		if( activePlayer.getNumber() == playerNum ){
		    retString = activePlayer.getName();
		}else{
		    retString = passivePlayer.getName();
		}
	    }		   
	}catch( Exception e ){
	    
	    System.out.println( e.getMessage() );
	  
	    // If playerNum is illegal an exception will be thrown
	}

	return retString;
    }
    
    
    /**
     * Adds an action listener to the facade
     */
    public void addActionListener( ActionListener a ){
	actionListener = AWTEventMulticaster.add( actionListener, a );
	//Adds an action listener to the facade
    }
    
    
    /**
     * Generates an action. This is inhereted from Component
     * 
     */    
    public void generateActionPerformed(){

	if ( actionListener != null ) {
	    actionListener.actionPerformed( 
               new ActionEvent( this, ActionEvent.ACTION_PERFORMED, ID ) );
	    // Fires event associated with timer, or a move made on GUI
	}

    }
    
    /**
     * Generates an action. This is inhereted from Componen
     */    
    private void generateActionPerformed( String command ){
	
	if ( actionListener != null ) {
	    actionListener.actionPerformed( 
              new ActionEvent( this, ActionEvent.ACTION_PERFORMED, command ) );
	    // Fires an event associated with timer, or move made on GUI
	}
    }

}// Facade.java