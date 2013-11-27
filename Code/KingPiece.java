/**
 * KingPiece.java
 *
 * Version:
 *   $Id: KingPiece.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *   $Log: KingPiece.java,v $
 *   Revision 1.1  2002/10/22 21:12:52  se362
 *   Initial creation of case study
 *
 */
import java.awt.*;

/**
 * This is a class representing a king piece (a piece that has been kinged)
 *
 * @author
 *
 * @deprecated Use an upgraded {@link Piece} object instead.
 */
public class KingPiece extends Piece {

   private static int KING = 1; // the king type
   private int type; // the type of his object
  
   /**
    * This constructor creates a king piece object
    * 
    * @param c - the color of this king piece
    * @param pc - the possible captures of this king piece
    * @param pm - the possible moves of this king piece
    * 
    * @deprecated Use an upgraded {@link Piece} object instead.
    */
   public KingPiece( Color c ) {
   
	   super( c, -3, null ); 
	   type = KING;
   }
   
   /**
    * This method returns the type of piece that this object is 
    * 
    * @return 1 for the king piece representation
    * 
    * @deprecated This class is deprecated; use {@link Piece#getType()} instead.
    */
   public int getType() {
  
	   return type;
   }
   
}//KingPiece
