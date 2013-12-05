/**
 * RuleSet.java
 */

/**
 * Abstract class / inteface for pieces to check moves for themselves
 * @author Ty
 *
 */
public abstract class RuleSet {
    /** Check to see if the given move is valid. */
    protected boolean validateMove(Move move, Board board) {
        if (board.occupied(move.endLocation()))
            return false;

        int moveDistance = move.endLocation() - move.getPiece().getLocation();
        boolean flag = false;
        for (int i : moveLocations(move.getPiece())) {
            if (moveDistance == i)
                flag = true;
        }
        if (!flag)
            return false;

        if (moveDistance % 8 == 2 || moveDistance % 8 == 6)
            return canJump(move.getPiece(), board);
        else
            return true;
    }
    
    /** Check to see if this piece can make a valid jump. */
    protected boolean canJump(Piece piece, Board board) {
        int[] locations = moveLocations(piece);
        for (int i = 0; i < 2; i++) {
            if (locations[i] != Integer.MIN_VALUE)
                continue;
            if (locations[i + 2] == Integer.MIN_VALUE)
                continue;
            if (board.colorAt(piece.getLocation() + (locations[i + 2] / 2)) != piece
                    .getColor())
                return true;
        }
        return false;
    }
    
    /** 
     * Generate all valid moves for a given piece.
     * 
     * @param piece - the piece object that moves are being generated for.
     * 
     * @return integer array of all moves; {@link Integer#MIN_VALUE} is used for
     * invalid moves, while all other values are relative to the piece's 
     * position in the board.
     */
    abstract protected int[] moveLocations(Piece piece);
}//RuleSet.java
