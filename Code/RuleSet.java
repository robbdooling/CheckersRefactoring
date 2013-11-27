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
    // TODO Look at inheriting classes for common methods; maybe create template
    //      method with them?
    abstract boolean validateMove(Move move, Board board);
    abstract boolean canJump(Piece piece, Board board);
}
