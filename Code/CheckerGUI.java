/*
 * CheckerGUI.java
 * 
 * The actual board.
 *
 * Created on January 25, 2002, 2:34 PM
 * 
 * Version
 * $Id: CheckerGUI.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 * 
 * Revisions
 * $Log: CheckerGUI.java,v $
 * Revision 1.1  2002/10/22 21:12:52  se362
 * Initial creation of case study
 *
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.net.*;

/**
 *
 * @author robbdooling@gmail.com
 * @version 
 */

public class CheckerGUI extends JFrame implements ActionListener{
    
    //the facade for the game
    
    private static Facade theFacade; //the facade
    private static Driver theDriver; //the Driver
    private Vector<JButton> possibleSquares = new Vector<JButton>();//a vector of the squares
    private int timeRemaining;//the time remaining
    
    private JButton[][] allSquares = new JButton[8][8];
    private JLabel PlayerOnelabel;
    private JLabel playerTwoLabel;
    private JLabel timeRemainingLabel;
    private JLabel secondsLeftLabel;
    private JButton ResignButton;
    private JButton DrawButton;
    private JLabel warningLabel, whosTurnLabel;
    
    //the names and time left
    private static String playerOnesName="", playerTwosName="", timeLeft="";

    /** 
     *
     * Constructor, creates the GUI and all its components
     *
     * @param facade the facade for the GUI to interact with
     * @param name1 the first players name
     * @param name2 the second players name
     *
     */

    public CheckerGUI( Facade facade, String name1, String name2, Driver driver ) {

        super("Checkers");

	//long names mess up the way the GUI displays
	//this code shortens the name if it is too long
        String nameOne="", nameTwo="";
        if(name1.length() > 7 ){
            nameOne = name1.substring(0,7);
        }else{
            nameOne = name1;
        }
        if(name2.length() > 7 ){
            nameTwo = name2.substring(0,7);
        }else{
            nameTwo = name2;
        }
                
        playerOnesName = nameOne;
        playerTwosName = nameTwo;
        theFacade = facade;
        theDriver = driver;
        register();
        
        initComponents ();
        pack ();
        update();
        //updateTime();
    }
    
    
    /*
     * This method handles setting up the timer
     */
    
    private void register() {
	
        try{
	    theFacade.addActionListener( this );
	  
        }catch( Exception e ){
            
            System.err.println( e.getMessage() );
         
        }
    }
    
    /**
     * This method is called from within the constructor to
     * initialize the form. It initializes the components
     * adds the buttons to the Vector of squares and adds
     * an action listener to the components 
     *
     */
    private void initComponents() {
        
	this.setResizable( false );
	
	// initialize squares matrix
	
	 // sets the layout and adds listener for closing window
    getContentPane().setLayout(new GridBagLayout());
	 GridBagConstraints gridBagConstraints1;
	
	for (int i = 0; i < 8; i++) {
	    for (int j = 0; j < 8; j++) {
		allSquares[i][j] = new JButton();
		possibleSquares.add(allSquares[i][j]);
		allSquares[i][j].addActionListener(this);
		
		allSquares[i][j].setPreferredSize(new Dimension(80, 80));
		allSquares[i][j].setActionCommand(String.valueOf((i*8)+j));
		
		// color white or black
		// even row and odd column = white
		if (i % 2 == 0 && j % 2 == 1) {
		    allSquares[i][j].setBackground(Color.white);
		}
		else {
		    allSquares[i][j].setBackground(new Color(204, 204, 153));
		}
		
		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = j;
		gridBagConstraints1.gridy = i+1;
		getContentPane().add(allSquares[i][j], gridBagConstraints1);
	    }
	}
	
        PlayerOnelabel = new JLabel();
        playerTwoLabel = new JLabel();
	whosTurnLabel = new JLabel();
        
        warningLabel = new JLabel();
        timeRemainingLabel = new JLabel();
        secondsLeftLabel = new JLabel();
	
        ResignButton = new JButton();
        ResignButton.addActionListener( this );
		
        DrawButton = new JButton();
        DrawButton.addActionListener( this );
    
	//add window listener
	addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        }
			  );
        
    	   		    
        PlayerOnelabel.setText("Player 1:     " + playerOnesName );
        PlayerOnelabel.setForeground( Color.black );
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 2;
        gridBagConstraints1.gridy = 0;
        gridBagConstraints1.gridwidth = 4;
        getContentPane().add(PlayerOnelabel, gridBagConstraints1);
        
        playerTwoLabel.setText("Player 2:     " + playerTwosName );
        playerTwoLabel.setForeground( Color.black );
		
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 2;
        gridBagConstraints1.gridy = 9;
        gridBagConstraints1.gridwidth = 4;
        getContentPane().add(playerTwoLabel, gridBagConstraints1);
        
        whosTurnLabel.setText("");
        whosTurnLabel.setForeground( new Color( 0, 100 , 0 ) );
        
        gridBagConstraints1.gridx=8;
        gridBagConstraints1.gridy=1;
        getContentPane().add(whosTurnLabel, gridBagConstraints1 );
        
        warningLabel.setText( "" );
        warningLabel.setForeground( Color.red );
		
        gridBagConstraints1.gridx = 8;
        gridBagConstraints1.gridy = 2;
        getContentPane().add( warningLabel, gridBagConstraints1 );
        
        timeRemainingLabel.setText("Time Remaining:");
        timeRemainingLabel.setForeground( Color.black );
		
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 8;
        gridBagConstraints1.gridy = 3;
        getContentPane().add(timeRemainingLabel, gridBagConstraints1);
        
        secondsLeftLabel.setText( timeLeft + " sec.");
        secondsLeftLabel.setForeground( Color.black );
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 8;
        gridBagConstraints1.gridy = 4;
        getContentPane().add(secondsLeftLabel, gridBagConstraints1);
        
        ResignButton.setActionCommand("resign");
        ResignButton.setText("Resign");
        
	gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 8;
        gridBagConstraints1.gridy = 7;
        getContentPane().add(ResignButton, gridBagConstraints1);
        
        DrawButton.setActionCommand("draw");
        DrawButton.setText("Draw");
        
        gridBagConstraints1 = new java.awt.GridBagConstraints();
        gridBagConstraints1.gridx = 8;
        gridBagConstraints1.gridy = 6;
        getContentPane().add(DrawButton, gridBagConstraints1);
	
    }
    
    /** 
     * 
     * Exit the Application
     * 
     * @param the window event
     * 
     */
    private void exitForm(java.awt.event.WindowEvent evt) {
        theDriver.endInQuit(theDriver.getActivePlayer());
        
    }

    /**
     * Takes care of input from users, handles any actions performed
     *
     * @param e  the event that has occurred
     */
    
    public void actionPerformed( ActionEvent e ) {
        
	try{
	    //if a square gets clicked
	    if( e.getActionCommand().equals(  "1" ) ||
		e.getActionCommand().equals(  "3" ) || 
		e.getActionCommand().equals(  "5" ) ||
		e.getActionCommand().equals(  "7" ) ||
		e.getActionCommand().equals(  "8" ) ||
		e.getActionCommand().equals( "10" ) ||
		e.getActionCommand().equals( "12" ) ||
		e.getActionCommand().equals( "14" ) ||
		e.getActionCommand().equals( "17" ) ||
		e.getActionCommand().equals( "19" ) ||
		e.getActionCommand().equals( "21" ) ||
		e.getActionCommand().equals( "23" ) ||
		e.getActionCommand().equals( "24" ) ||
		e.getActionCommand().equals( "26" ) ||
		e.getActionCommand().equals( "28" ) ||
		e.getActionCommand().equals( "30" ) ||
		e.getActionCommand().equals( "33" ) ||
		e.getActionCommand().equals( "35" ) ||
		e.getActionCommand().equals( "37" ) ||
		e.getActionCommand().equals( "39" ) ||
		e.getActionCommand().equals( "40" ) ||
		e.getActionCommand().equals( "42" ) ||
		e.getActionCommand().equals( "44" ) ||
		e.getActionCommand().equals( "46" ) ||
		e.getActionCommand().equals( "49" ) ||
		e.getActionCommand().equals( "51" ) ||
		e.getActionCommand().equals( "53" ) ||
		e.getActionCommand().equals( "55" ) ||
		e.getActionCommand().equals( "56" ) ||
		e.getActionCommand().equals( "58" ) ||
		e.getActionCommand().equals( "60" ) ||
		e.getActionCommand().equals( "62" ) ) {
		
		//call selectSpace with the button pressed
		theFacade.selectSpace(
				   Integer.parseInt( e.getActionCommand() ) );
		
		//if draw is pressed
	    }else if( e.getActionCommand().equals( "draw" ) ){
		//does sequence of events for a draw
		theDriver.pressDraw();
		
		//if resign is pressed
	    }else if( e.getActionCommand().equals( "resign" ) ) {
		//does sequence of events for a resign
		theDriver.endInQuit(theDriver.getActivePlayer());
		
		//if the source came from the facade
	    }else if( e.getSource().equals( theFacade ) ) {
		
		//if its a player switch event
		if ( (e.getActionCommand()).equals(theFacade.playerSwitch) ) {
		    //set a new time
		    timeRemaining = theDriver.getTimer();
		    //if it is an update event
		} else if ( (e.getActionCommand()).equals(theFacade.update) ) {
		    //update the GUI
		    update();
		} else {
		    throw new Exception( "unknown message from facade" );
		}
	    }
	    //catch various Exceptions
	}catch( NumberFormatException excep ){
	    System.err.println(
		     "GUI exception: Error converting a string to a number" );
	}catch( NullPointerException exception ){
	    System.err.println( "GUI exception: Null pointerException "
				+ exception.getMessage() );
	    exception.printStackTrace();
	}catch( Exception except ){
	    System.err.println( "GUI exception: other: "
				+ except.getMessage() );
	    except.printStackTrace();
	}
	
    }
    

    /**
     * Updates the GUI reading the pieces in the board
     * Puts pieces in correct spaces, updates whose turn it is
     *
     * @param the board
     */
    
    private void update(){
	
	
	if( checkEndConditions() ){
	    
	    theDriver.endGame(" ");
	}
	//the board to read information from
	Board board = theDriver.stateOfBoard();
	//a temp button to work with
	JButton temp =  new JButton();
	
	//go through the board
	for( int i = 1; i < board.sizeOf(); i++ ){
	    
	    // if there is a piece there
	    if( board.occupied( i ) ){
		
		//check to see if color is blue
		if( board.colorAt( i ) == Color.blue ){

		    //if there is a  single piece there
		    if((board.getPieceAt(i)).getType() == board.SINGLE){

			//show a blue single piece in that spot board
			temp = (JButton)possibleSquares.get(i);

			//get the picture from the web
			try{
			    temp.setIcon(
			      new ImageIcon( new URL("file:BlueSingle.gif") ));
			}catch( MalformedURLException e ){
			    System.out.println(e.getMessage());
			}

			//if there is a kinged piece there
		    }else if((board.getPieceAt(i)).getType() == board.KING ){

			//show a blue king piece in that spot board
			temp= (JButton)possibleSquares.get(i);

			//get the picture from the web
			try{
			    temp.setIcon(
			      new ImageIcon(new URL("file:BlueKing.gif") ) );
			}catch( Exception e ){}
			
		    }

		    //check to see if the color is white        
		}else if( board.colorAt( i ) == Color.white ){

		    //if there is a single piece there
		    if((board.getPieceAt(i)).getType() == board.SINGLE){

			//show a blue single piece in that spot board
			temp = (JButton)possibleSquares.get(i);

			//get the picture from the web
			try{
			    temp.setIcon(
			      new ImageIcon(new URL("file:WhiteSingle.gif")));
			}catch( Exception e ){}
			
			//if there is a kinged piece there
		    }else if((board.getPieceAt(i)).getType() == board.KING ){

			//show a blue king piece in that spot board
			temp = (JButton)possibleSquares.get(i);

			//get the picture from the web
			try{
			    temp.setIcon(
			      new ImageIcon(new URL("file:WhiteKing.gif") ) );
			}catch( Exception e ){}
		    }
                                //if there isn't a piece there        
		}
	    }else {
		//show no picture
		temp = (JButton)possibleSquares.get(i);
		temp.setIcon( null );
	    }
	}
	
	//this code updates whos turn it is
	if(theDriver.whosTurn() == 2 ){
	    playerTwoLabel.setForeground( Color.red );
	    PlayerOnelabel.setForeground(Color.black );
	    whosTurnLabel.setText( playerTwosName + "'s turn ");
	}else if( theDriver.whosTurn() == 1 ){
	    PlayerOnelabel.setForeground( Color.red );
	    playerTwoLabel.setForeground(Color.black );
	    whosTurnLabel.setText( playerOnesName + "'s turn" );
	}
    }
    
    /**
     *
     * Update the timer
     *
     */
        
    public void updateTime() {            
            
	if ( theDriver.getTimer() > 0 ) {
                
	    // if the time has run out but not in warning time yet
	    // display warning and count warning time
	    if ( timeRemaining <= 0 && ( warningLabel.getText() ).equals( "" ) ) {
		timeRemaining = theDriver.getTimerWarning();
		warningLabel.setText( "Time is running out!!!" );
                    
                // if the time has run out and it was in warning time quit game
	    } else if ( timeRemaining <= 0 &&
			!( warningLabel.getText() ).equals( "" ) ) {
                  
		theDriver.endInQuit(theDriver.getActivePlayer());
                    
	    } else {
                    
		timeRemaining--;
                    
	    }
                
	    secondsLeftLabel.setText( timeRemaining + " sec." );
                    
	} else {
	    secondsLeftLabel.setText( "*****" );
	}
    }
    
    /**
     * Checks the ending condotions for the game
     * see if there a no pieces left
     *
     * @return the return value for the method
     *           true if the game should end
     *           false if game needs to continue 
     */
 
        public boolean checkEndConditions(){
           
	    //the return value
            boolean retVal = false;
            try{
		//the number of each piece left
		int whitesGone = 0 , bluesGone = 0;
		
		//the board to work with
		Board temp = theDriver.stateOfBoard();
		
		//go through all the spots on the board
		for( int i=1; i<temp.sizeOf(); i++ ){
		    //if there is a piece there
		    if( temp.occupied( i  ) ){
			//if its a blue piece there
			if( (temp.getPieceAt( i )).getColor() == Color.blue ){
			    // increment number of blues
			    bluesGone++;
			    //if the piece is white
			}else if( (temp.getPieceAt( i )).getColor()
				  == Color.white ){
			    //increment number of whites
			    whitesGone++;
			}
		    }
		}//end of for loop
		
		//if either of the number are 0
		if( whitesGone == 0 || bluesGone == 0 ){
		    retVal = true;
		}

            }catch( Exception e ){
                
                System.err.println( e.getMessage() );            
                
            }
            return retVal;
            
        }//checkEndConditions

}//checkerGUI.java
