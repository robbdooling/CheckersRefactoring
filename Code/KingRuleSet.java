/**
 * KingRuleSet.java
 */

/**
 * @author Ty
 *
 */
public class KingRuleSet extends RuleSet {

    /** 
     * Generate all valid moves for a given piece.
     * 
     * NOTE: This isn't finished, and will give you illegal moves.
     * 
     * @param piece - the piece object that moves are being generated for.
     * 
     * @return integer array of all moves; {@link Integer#MIN_VALUE} is used for
     * invalid moves, while all other values are relative to the piece's 
     * position in the board.
     */
    protected int[] moveLocations(Piece piece) {
    	int[] locations = new int[] { -9,-7,7,9,-18,-14,14,18 };
    
    	// Check vertical moves
    	int pieceLocationVerMod = (piece.getLocation() / 8);
    	if (pieceLocationVerMod < 2) {
    		for (int i = 2 * (pieceLocationVerMod * 2); i < 8; i += 4) {
    			locations[i]   = Integer.MIN_VALUE;
    			locations[i+1] = Integer.MIN_VALUE;
    		}
    	}
    	else if (pieceLocationVerMod > 5) {
    		for (int i = 2 + (2 * ((7 - pieceLocationVerMod) * 2));
    		        i < 8; i += 4) {
    			locations[i] = Integer.MIN_VALUE;
    			locations[i+1] = Integer.MIN_VALUE;
    		}
    	}
    	
    	// Check horizontal moves
    	int pieceLocationHorMod = piece.getLocation() % 8;
    	if (pieceLocationHorMod < 2) {
    	    for (int i = 2 * (pieceLocationVerMod * 2); i < 8; i += 2) {
    	        locations[i] = Integer.MIN_VALUE;
    	    }
    	}
    	else if (pieceLocationHorMod > 5) {
    	    for (int i = 1 + (2 * ((7 - pieceLocationHorMod) * 2));
                    i < 8; i += 2) {
    	        locations[i] = Integer.MIN_VALUE;
    	    }
    	}
    	
    	return locations;
    }
}
