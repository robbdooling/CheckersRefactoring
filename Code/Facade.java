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
    

    

    
    
    

    
    

    
    
    

}// Facade.java