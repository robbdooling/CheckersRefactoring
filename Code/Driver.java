/**
 * Driver.java
 *
 * Version
 *    $Id: Driver.java,v 1.1 2002/10/22 21:12:52 se362 Exp $ 
 *
 * Revisions:
 *    $Log: Driver.java,v $
 *    Revision 1.1  2002/10/22 21:12:52  se362
 *    Initial creation of case study
 *
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import javax.swing.*;

/**
 *
 * This class is a part of the main functionality of the checkers 
 * game. This class contains the main method to start the game, it 
 * creates all necessary classes as information is provided. Its 
 * functions include knowing whose turn it is, remembering multiple 
 * jumps, relaying end of game conditions and ending the game.
 *
 * @author
 *
 */

public class Driver{

    public static int LOCALGAME  = 10000;
    public static int HOSTGAME   = 20000;
    public static int CLIENTGAME = 30000;
    
    private int startSpace = 99; // Starting space for current move
    private int endSpace   = 99; // Ending space for current move
    
    public static String update = "update";
    private ActionListener actionListener;
    public static String ID = "driver";

    public static String playerSwitch = "switch";
    
    private Player  playerOne;
    private Player  playerTwo;
    private int     gameType;
    private Player  activePlayer;
    private Player  passivePlayer;
    private boolean runningTimer;
    private Timer   theTimer;
    private Rules   theRules;
    private int      warningTime = 999;
    private Board   theBoard;
    
    /**
     * Constructor
     *
     * Create the driver, which in turn creates the rest of 
     * the system.
     */
    public Driver(){
    // Create the board       
    theBoard = new Board();
    
    // Create the rules passing in the board
    theRules = new Rules( theBoard, this );
    
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
     * This method is called after a move has been checked. 
     * Changes active player when a final successful jump has 
     * been made, resets the timer when appropriate, and tells 
     * the appropriate player whose turn it is to make a move.
     *
     * @param player The player whose turn it will now be
     * @param space  The space on the board from which a multiple 
     *               jump has to be made
     *
     * @pre  a players has made a move
     * @post a player has been told to make a move
     */
    public void endTurn( Player player, int space ){

    // Check to see if player passed in was the active player
    // If player passed in was active player, check for multiple
    // jump (space is none negative)
    if ( activePlayer == player ){
        
        // Inform the player that the move was not valid,
        // or to make another jump
        if ( space < 0 ){
        JOptionPane.showMessageDialog( null,
                   activePlayer.getName() + " made an illegal move",
                       "Invalid Move", JOptionPane.INFORMATION_MESSAGE );
        } else {
        JOptionPane.showMessageDialog( null,
               activePlayer.getName() + " please make" +
                   " another jump", "Multiple Jump Possible",
               JOptionPane.INFORMATION_MESSAGE );
        
        // Get the GUI to update
        setPlayerModes( activePlayer, passivePlayer );
        
        // If game is networked tell networked player to send 
        // the move
        if ( gameType == HOSTGAME 
             || gameType == CLIENTGAME ) {
            ( (NetworkPlayer) activePlayer ).sendMove();
        }
        }
    } else if ( passivePlayer == player ) {
        // If game is networked, tell networked player to send move
        if ( gameType == HOSTGAME 
         || gameType == CLIENTGAME ) {
        ((NetworkPlayer)activePlayer).sendMove();
        ((NetworkPlayer)activePlayer).waitForPlayer();
        }
        
        // Inform the other player to make a move and
        // tell facade to update any listening GUIs and
        // reset the timer
        
        Player tempHold = activePlayer;
        activePlayer    = passivePlayer;
        passivePlayer   = tempHold;
        
        setPlayerModes( activePlayer, passivePlayer );
    }
    
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
     * This method returns the currently active player.
     */
    public Player getActivePlayer(){
             return activePlayer;
    }

    /**
     * This method returns player1.
     */
    public Player getPlayer1(){
              return playerOne;
    }

    /**
     * This method returns player2.
     */
    public Player getPlayer2(){
              return playerTwo;
    }
    
    /**
     * This method ends the checkers game due to whatever reason neccissary
     * ie. a draw, someone quitting, or a victory.
     *
     * @param message  the message to send to all players regarding the 
     *                 reason for ending the game
     *
     * @pre  the criteria for ending a game has been met, depending on why 
     *       the game ended
     * @post the game has been ended for both players and the game is ready 
     *       to exit
     */
    public void endGame( String message ){
    
    // Call endOfGame on both players with the given message
    playerOne.endOfGame( message );
    playerTwo.endOfGame( message );
    
    // When players have acknowledged the end of game 
    // call System.exit()
    System.exit( 0 );
    }
    
    /**
     * This method creates the correct players for a game.
     *
     * @param type the type of player to be created (0 - local, 1 - network)
     * @param name the name of the player
     * @param num  the player's number
     *
     * @pre   less than 2 players exist
     * @post  a player with correct name has been created  
     */
    public void createPlayer( int num, int type, String name ){
    Player temp = null;

    if ( type == Player.LOCALPLAYER ) {
        temp = new LocalPlayer( num, theRules, this );
        temp.setName( name );
    } else if ( type == Player.NETWORKPLAYER ) {
        temp = new NetworkPlayer( num, theRules, this );
        temp.setName( name );
    }
    
    if ( num == 1 ) {
        playerOne = temp;
    } else {
        playerTwo = temp;
    }
    }
    
    /**
     * Set the name for the player using the passed in values.
     * 
     * @param num  The player's number (1 or 2)
     * @param name The name to assign to the player.
     */
    public void setPlayerName( int num, String name ){
    if ( num == 1 ) {
        playerOne.setName( name );
    } else {
        playerTwo.setName( name );
    }
    }
    
    /** 
     * Set the color for a player using the passed in value.
     *
     * @param num   The player's number (1 or 2)
     * @param color The color to assign to the player.
     */
    public void setPlayerColor( int num, Color color ) {
        if ( num == 1 ) {
            playerOne.setColor( color );
        } else {
            playerTwo.setColor( color );
        }
    }
    
    /**
     * This method ends the game in a draw, alerting both players 
     * that the draw has taken place
     *
     * @pre  both players have agreed to a draw
     * @post the game has ended and both players have been notified 
     *       of the draw
     */
    public void endInDraw( Player player ){
    // Calls endOfGame with a message that game ended in a draw.
    endGame( player.getName() + "'s draw offer was accepted. \n\n"
         + "Game ended in a draw." );
    }

    /**
     * This method is called if a draw has been offered
     * 
     * @param the player who offered the draw
     * 
     */    
    public void drawOffered( Player player ){
    
    if( player.getNumber() == playerOne.getNumber() ){
        playerTwo.acceptDraw( player );
    }else if( player.getNumber() == playerTwo.getNumber() ){
        playerOne.acceptDraw( player );
    }
    
    }

    /**
     * Tell the kernel that the user has requested a draw.
     */
    public void pressDraw(){

    // Alerts both players and the kernel that one person 
    // has offered a draw calls offerDraw() on both players
    activePlayer.offerDraw( activePlayer );

    }
    
    /** 
     * The offer for a draw has been made.  This method declines
     * that offer, meaning the game will continue.
     *
     * @param player The player declining the draw.
     */
    public void declineDraw( Player player ){
    if ( gameType == LOCALGAME ) {
        player.endInDeclineDraw( player );
    } else {
        playerOne.endInDeclineDraw( player );
        playerTwo.endInDeclineDraw( player );
    }
    }
    
    /**
     * Ends the game as a result of a player quitting, notifying 
     * each player
     * 
     * @param the player who quit
     */
    public void endInQuit( Player player ){
    playerOne.endOfGame( player.getName() + " quit the game" );
    playerTwo.endOfGame( player.getName() + " quit the game" );
    }
    
    /**
     * This method creates the timer to be used, if one is desired 
     * to be used. It will also set the number of seconds for each 
     * turn.
     *
     * @param   time    : the number of seconds for each turn
     * @param   warning : whether or not a player will be warned 
     *                    that their turn is going to end
     *
     * @pre  It has been selected to use a timer in the game setup
     * @post The timer has been created and the appropriate time 
     *       restraints are in place
     */
    public void setTimer( int time, int warning ) throws Exception{
    // Checks to see that time is in between the necessary frame
    // If values are negative, set runningTimer to false
    // If they are positive values, create the Timer and 
    // notifier with the times
       if( ( time == -1 ) || ( ( time >= 10 || time <= 300 ) 
                && ( warning >= 10 || warning <= 300 ) ) ){
        if ( time < 0 ) {
             runningTimer = false;
        } else {
             runningTimer = true;
             theTimer = new Timer();
        }
       } else {
        throw new Exception( "Invalid timer settings" );
       }
        
    }
        
    /**
     * This method sets the colors of pieces that each player 
     * will be
     *
     * @pre the game has been started, and there are 2 players
     * @post each player has their colors
     */
    private void selectColors(){
    // Randomly select color for each player and call the 
    // setColor() method of each
    if ( Math.random() > .5 ) {
        playerOne.setColor( Color.blue );
        playerTwo.setColor( Color.white );
    } else {
        playerOne.setColor( Color.white );
        playerTwo.setColor( Color.blue );
    }
    }
    
    /**
     * This method will start the game play. Letting the first person 
     * move their piece and so on
     *
     * @pre  There are 2 players to play, and all pre-game conditions are 
     *       in place
     * @post The first person is able to make their first move
     */
    public void startGame(){
    selectColors();
       
    if ( gameType == HOSTGAME ) {
        ( (NetworkPlayer)playerTwo).waitForConnect();
        //( (NetworkPlayer)playerTwo).waitForConnect();
    } else if ( gameType == CLIENTGAME ) {
        //( (NetworkPlayer)playerOne).connectToHost();
        ( (NetworkPlayer)playerOne).connectToHost();
    }
    
    // Tell player with the correct color to make a move
    if ( playerOne.getColor() == Color.white ) {
        activePlayer  = playerOne;
        passivePlayer = playerTwo;
    } else {
        activePlayer  = playerTwo;
        passivePlayer = playerOne;
    }
    
    setPlayerModes( activePlayer, passivePlayer );
    }
    
    /**
     * This method sets the host the player will play against in case of 
     * a game over a network.
     *
     * @param  host the host of the game to be played
     *
     * @pre  There is a person to host the game, both players are 
     *       networkedPlayers
     * 
     * @post The players are connected to play
     */
    public void setHost( URL host ){
    // Call connectToHost in player two with the URL
       if( host != null ){
         ((NetworkPlayer)playerOne).setHost( host );
         ((NetworkPlayer)playerTwo).setHost( host );
       }
    }
    
    /**
     * Return the player whose turn it is not
     *
     * @return the player whose turn it is not
     *
     * @pre there are 2 valid players and the game has started 
     * @post this method has not altered anything
     */
    public Player getOppositePlayer(){
    // Returns the player whose getTurnStatus is false
    return passivePlayer;
    }

    /**
     * Notifies everything of the state of the board
     * 
     * @return a Board object which is the state of the board
     * 
     */
    public Board stateOfBoard(){
    // Return the board so GUI can go through and update itself
    return theBoard;
    }

    
    /**
     * Whether the current game uses a timer
     *
     * @return true if a timer is being set in the game, otherwise 
     *         false
     *
     * @pre the game has started 
     * @post this method has not altered anything
     */
    public boolean timerRunning(){
    return runningTimer;
    }
    
   
    /**
     * Select the type of game
     *
     * @param mode the mode (local, host, client) of the game
     *
     * @pre  Players have not been created
     * @post Mode is set
     */
    public void setGameMode( int newMode ) throws Exception{
    // Set the value of mode
       if( newMode == LOCALGAME || newMode == HOSTGAME || newMode == CLIENTGAME ){
           gameType = newMode;
       }else {
           throw new Exception( "Invalid Game Mode" );
       }
    }
    
    /**
     * Return the integer representing the type of game
     *
     * @return the type of game
     *
     * @pre  Game has started
     * @post This method has changed nothing
     */
    public int getGameMode(){
    return gameType;
    }
    
    /**
     * Return the notifier of the Timer
     *
     * @return the notifier for the Timer
     *
     * @pre  The game is running
     * @post This method has changed nothing
     */
    public Notifier getTimerNotifier(){
    // Return the timers notifier, by asking the timer 
    // for its notifier
    Notifier timer = null;
    
    if ( theTimer != null ) {
        timer = theTimer.getNotifier();
    }
    
    return timer;
    }

    /**
     * Returns the timer value, how long each player get to take a turn
     * 
     * @return the amount of time each player has for a turn 
     * 
     * @pre there has been a timer set for the current game
     * 
     */
    public int getTimer(){
    int retval = 0;

    // Makes sure there is a timer for this game
    if( timerRunning() ){
        retval = theTimer.getTime();
    }

    // Returns the timer value
    return retval;
    }

    /**
     * Returns the amount of time chosen for a warning that a player is 
     * near the end of his/her turn.
     * 
     * @return the amount of warning time a player has
     * 
     * @pre there has been a timer set for the current game  
     */
    public int getTimerWarning(){
    int retval = -1;

    // Makes sure there is a timer for this game
    if( warningTime != 999 ){
        retval = warningTime;
    }

    // Returns the timer value (class variable: warningTime )
    return retval;
    }
    
    public void generateActionPerformed(){

    if ( actionListener != null ) {
        actionListener.actionPerformed( 
               new ActionEvent( this, ActionEvent.ACTION_PERFORMED, ID ) );
        // Fires event associated with timer, or a move made on GUI
    }

    }
    
    private void generateActionPerformed( String command ){
        
    if ( actionListener != null ) {
        actionListener.actionPerformed( 
              new ActionEvent( this, ActionEvent.ACTION_PERFORMED, command ) );
        // Fires an event associated with timer, or move made on GUI
    }

    }
    
    /**
     * Adds an action listener to the facade
     */
    //NOT SURE IF NEEDED
    public void addActionListener( ActionListener a ){
    actionListener = AWTEventMulticaster.add( actionListener, a );
    //Adds an action listener to the facade
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
    
}//Driver.java
