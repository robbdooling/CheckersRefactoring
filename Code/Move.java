/**
 * Move.java
 *
 * Version:
 *    $Id: Move.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *    $Log: Move.java,v $
 *    Revision 1.1  2002/10/22 21:12:52  se362
 *    Initial creation of case study
 *
 */

/**
 * An object representation of a move.
 *
 * @author
 */
 public class Move {
	private Piece piece;            // piece being moved 
	private int startingLocation;	// the starting location
	private int endingLocation;	    // the ending location

	/**
	 *  The player that this move is intended for.
	 */
	public Player thePlayer;

     
	/**
	 * Create a move with the starting location and 
	 * ending location passed in as paremeters.
	 *	
	 * @param startLoc The starting point of the move
	 * @param endLoc   The ending point of the move
	 * 
	 * @pre startLoc and endLoc are valid locations
         * @deprecated Use {@link Move(Piece, location)} instead
	 */
	public Move( Player player, int startLoc, int endLoc ) {
	
		thePlayer = player;
		startingLocation = startLoc;
		endingLocation = endLoc;
		this.piece = null;
	}

        public Move(Piece piece, int location) {
		this.piece = piece;
		this.endingLocation = location;
		this.thePlayer = null;
		this.startingLocation = Integer.MIN_VALUE;
	}

	/**
	 * Return the player who made this move
	 * 
	 * @return the player who made this move
	 * 
	 * @post nothing has changed 
	 * 
	 */
	public Player getPlayer() {
		return thePlayer;
	}

	public Piece getPiece() { return this.piece; }
     
	/**
	 * Return the starting location of this move.
	 *
	 * @return The starting point of the move.
	 * 
	 * @post nothing has changed
 	 */
	public int startLocation() {
	
		return startingLocation;
	}

     
	/**
	 * Return the ending location of this move.
	 *
	 * @return The ending point of this location.
	 * 
	 * @post Nothing has changed
	 */
	public int endLocation() {
		
		return endingLocation;
	}
     
} //Move.java
