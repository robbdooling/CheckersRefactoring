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

/**
 * Handles information related to individual pieces on a checkers board.
 * @author Ty
 *
 */
public class Piece {

    /** Represents a Single-type piece. */
    public static final int SINGLE = 0;
    /** Represents a King-type piece. */
    public static final int KING = 1;
    
    private static int ID = 0;  // Increment with each new ID
    
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

    public int getType() { return this.type; }

    public Color getColor() { return color; }
    
    void move(int location) {
        this.location = location;
    }

    /**
     * Upgrade the type of piece this is.
     * 
     * If this a Single-type, it internally updates itself to a King-type. If 
     * it's already a King-type, nothing happens.
     */
    public void upgrade() { 
        this.type = KING;
	    this.rules = new KingRuleSet();
    }
    
    public boolean validateMove(Move move) {
        return this.rules.validateMove(move, this.board);
    }
    
    public boolean canJump() {
        return this.rules.canJump(this, this.board);
    }

}// Piece.java
