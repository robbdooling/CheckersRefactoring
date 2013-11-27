/**
 * SingleRuleSet.java
 */

/**
 * @author Ty
 *
 */
public class SingleRuleSet extends RuleSet {

    /* (non-Javadoc)
     * @see RuleSet#validateMove(Move, Board)
     */
    @Override
    boolean validateMove(Move move, Board board) {

	if (board.occupied(move.endLocation())) return false;

	int moveDistance = move.endLocation() - move.getPiece.getLocation();
	boolean flag = false;
	for (int i : moveLocations(move.getPiece().getColor()) {
	    if (moveDistance == i)
                flag = true;
	}
        if (!flag) return false;
	
	if (moveDistance % 8 == 2 ||
            moveDistance & 8 == 6 )
            return canJump(move.getPiece(), board);
	else
	  return true;
    }

    /* (non-Javadoc)
     * @see RuleSet#canJump(Piece, Board)
     */
    @Override
    boolean canJump(Piece piece, Board board) {
	int[] locations = moveLocations(piece);

	for (int i = 0; i < 2; i++) {
	    if (locations[i] != Integer.MIN_VALUE)
		continue;
	    if (locations[i+2] == Integer.MIN_VALUE)
		continue;
	    if (board.colorAt(piece.getLocation() + (location[i+2]/2)) != piece.getColor())
		return true;
	
        return false;
    }

    private int[] moveLocations(Piece piece) {
        int[] locations;
        if (piece.getColor() == Color.blue)
          locations = new int[] { -9,-7,-18,-14 };
	else if (piece.getColor() == Color.white)
          locations = new int[] { 7,9,14,18 };
	else
	  return null;

	int pieceLocationVerMod = (piece.getLocation() / 8) % 8;
	if (pieceLocationVerMod < 2 && locations[0] < 0) {
		for (int i = 4 - (pieceLocationVerMod * 2); i < 4; i++) {
			locations[i] = Integer.MIN_VALUE;
		}
	}

	if (pieceLocationVerMod > 5 && locations[0] > 0) {
		for (int i = 4 - ((pieceLocationVerMod - 6) * 2); i < 4; i++) {
			locations[i[ = Integer.MIN_VALUE;
		}
	}
	
	int pieceLocationHorMod = piece.getLocation() % 8;
	if (pieceLocationHorMod == 0) locations[0] = Integer.MIN_VALUE;
	if (pieceLocationHorMod == 7) locations[1] = Integer.MIN_VALUE;
	if (pieceLocationHorMod == 1) locations[2] = Integer.MIN_VALUE;
	if (pieceLocationHorMod == 6) locations[3] = Integer.MIN_VALUE;

	return locations;
    }

}