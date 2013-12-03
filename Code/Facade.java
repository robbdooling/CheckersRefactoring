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
     * Adds an action listener to the facade
     */
    public void addActionListener( ActionListener a ){
	actionListener = AWTEventMulticaster.add( actionListener, a );
	//Adds an action listener to the facade
    }
    
    
    /**
     * Generates an action. This is inherited from Component
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
     * Generates an action. This is inherited from Component
     */    
    private void generateActionPerformed( String command ){
	
	if ( actionListener != null ) {
	    actionListener.actionPerformed( 
              new ActionEvent( this, ActionEvent.ACTION_PERFORMED, command ) );
	    // Fires an event associated with timer, or move made on GUI
	}
    }

}// Facade.java