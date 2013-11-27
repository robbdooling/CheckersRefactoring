/**
 * KingRuleSet.java
 */

/**
 * @author Ty
 *
 */
public class KingRuleSet extends RuleSet {

    /* (non-Javadoc)
     * @see RuleSet#validateMove(Move, Board)
     */
    @Override
    boolean validateMove(Move move, Board board) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see RuleSet#canJump(Piece, Board)
     */
    @Override
    boolean canJump(Piece piece, Board board) {
        // TODO Auto-generated method stub
        return false;
    }
    
    private int[] moveLocations(Piece piece) {
	int locations = new int[] { -9,-7,7,9,-18,-14,14,18 };

	// Check vertical moves
	int pieceLocationVerMod = (piece.getLocation() / 8) % 8;
	if (pieceLocationVerMod < 2) {
		for (int i = 0; i < 4 + (2 * pieceLocationVerMod); i += 4) {
			locations[i]   = Integer.MIN_VALUE;
			locations[i+1] = Integer.MIN_VALUE;
		}
	}

	else if (pieceLocationVerMod > 5) {
		for (int i = 2; i < 4 + (4 * (pieceLocationVerMod - 6); i += 4) {
			locations[i] = Integer.MIN_VALUE;
			locations[i+1] = Integer.MIN_VALUE;
		}
	}
}
