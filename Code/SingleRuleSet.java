/**
 * SingleRuleSet.java
 */
// TODO: Check to see that the validation logic is correct.

import java.awt.Color;

/**
 * @author Ty
 * 
 */
public class SingleRuleSet extends RuleSet {

    /**
     * Get all valid moves for a given piece.
     * 
     * Valid moves are determined by the Piece's color and its stored location.
     * 
     * @param piece - the piece object that moves are being generated for.
     * @return integer array of all moves; {@link Integer#MIN_VALUE} is used for
     * invalid moves, while all other values are relative to the piece's 
     * position in the board.
     */
    protected int[] moveLocations(Piece piece) {
        int[] locations;
        if (piece.getColor() == Color.blue)
            locations = new int[] { -9, -7, -18, -14 };
        else if (piece.getColor() == Color.white)
            locations = new int[] { 7, 9, 14, 18 };
        else
            return null;

        // Check vertical move validity
        int pieceLocationVerMod = (piece.getLocation() / 8);
        if (piece.getColor() == Color.blue && pieceLocationVerMod < 2) {
            for (int i = pieceLocationVerMod * 2; i < 4; i++) {
                locations[i] = Integer.MIN_VALUE;
            }
        } else if (piece.getColor() == Color.white && pieceLocationVerMod > 5) {
            for (int i = (7 - pieceLocationVerMod) * 2; i < 4; i++) {
                locations[i] = Integer.MIN_VALUE;
            }
        }

        // Check horizontal move validity
        int pieceLocationHorMod = piece.getLocation() % 8;
        if (pieceLocationHorMod == 0)
            locations[0] = Integer.MIN_VALUE;
        if (pieceLocationHorMod == 7)
            locations[1] = Integer.MIN_VALUE;
        if (pieceLocationHorMod == 1)
            locations[2] = Integer.MIN_VALUE;
        if (pieceLocationHorMod == 6)
            locations[3] = Integer.MIN_VALUE;

        return locations;
    }
}
