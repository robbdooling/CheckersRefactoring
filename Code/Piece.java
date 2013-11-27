/**
 * Piece.java
 *
 * Version:
 *   $Id: Piece.java,v 1.1 2002/10/22 21:12:53 se362 Exp $
 *
 * Revisions:
 *   $Log: Piece.java,v $
 *   Revision 1.1  2002/10/22 21:12:53  se362
 *   Initial creation of case study
 *
 */

/**
 * This is an abstract class representing any piece that
 * know about it's color and possible moves and captures
 *
 * @author
 *
 */

import java.awt.*;

public class Piece {

    // Static variables
    public static final int SINGLE = 0; // Single Piece Type
    public static final int KING = 1;   // King Piece Type
    
    private static int ID = 0;          // Increment with each new ID
    
    // Instance variables
    private int id;
    private int location;
    private int type;
    private Color color;
    private Board board;
    private RuleSet rules;

    /**
     * The constructor for this piece
     * 
     * @param c - the color for this piece
     * @deprecated Use {@link #Piece(Color,int, Board)} instead
     */
    public Piece(Color c) {
        this(c, -9, null);
    }

    /**
     * The constructor for this piece
     * 
     * @param c - the color for this piece
     * @param location - where this piece is on the board
     * @param board - the board this piece belongs to
     */
    public Piece(Color c, int location, Board board) {
        this.id = ID++;
        this.location = location;
        this.type = SINGLE;
        this.color = c;
        this.board = board;
        this.rules = new SingleRuleSet();
    }
    
    public int getId() { return this.id; }
    
    public int getLocation() { return this.location; }

    /**
     * The method which is abstract
     * 
     * @return the type of the piece
     */
    public int getType() { return this.type; }

    /**
     * This method returns the color of this piece
     * 
     * @return the color for this piece
     */
    public Color getColor() { return color; }
    
    /**
     * Upgrade the type of piece this is.
     */
    public void upgrade() { this.type = KING; }
    
    public boolean validateMove(Move move) {
        return this.rules.validateMove(move, this.board);
    }
    
    public boolean canJump() {
        return this.rules.canJump(this, this.board);
    }

}// Piece
