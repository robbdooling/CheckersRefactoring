/**
 * RuleSet.java
 */

/**
 * Interface for pieces to check moves for themselves
 * @author Ty
 *
 */
public abstract class RuleSet {
    
    // TODO Make Singleton?
    
    abstract boolean validateMove(Move move, Board board);
    abstract boolean canJump(Piece piece, Board board);
}
