/**
 * Board.java
 *
 * Version:
 *     $Id: Board.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 *
 * Revisions:
 *     $Log: Board.java,v $
 *     Revision 1.1  2002/10/22 21:12:52  se362
 *     Initial creation of case study
 *
 */
import java.util.*;
import java.awt.*;


/**
 *  This class represents the board on which checkers is being played.
 *  The board holds a collection of pieces.
 *
 *  @invariant all variables have valid values
 *
 *  @author
 */
public class Board {

   private Piece pieces[]; // the pieces that are on the board
   
   // These will probably be removed later
   public static int SINGLE = 0;
   public static int KING = 1;


   /**
    * This constructor creates a new board at the beginning of the game
    */

   public Board() {
  
	   // create a array of size 64, generate piece objects and
	   // put them in the correct location in the array
	   // Set the values of numWhites and numBlues to 12 each
	   pieces = new Piece[64];

	   // create blue pices
	   pieces[1] = new Piece( Color.blue, 1, this );
	   pieces[3] = new Piece( Color.blue, 3, this );
	   pieces[5] = new Piece( Color.blue, 5, this );
	   pieces[7] = new Piece( Color.blue, 7, this );
	   pieces[8] = new Piece( Color.blue, 8, this );
	   pieces[10] = new Piece( Color.blue, 10, this );
	   pieces[12] = new Piece( Color.blue, 12, this );
	   pieces[14] = new Piece( Color.blue, 14, this );
	   pieces[17] = new Piece( Color.blue, 17, this );
	   pieces[19] = new Piece( Color.blue, 19, this );
	   pieces[21] = new Piece( Color.blue, 21, this );
	   pieces[23] = new Piece( Color.blue, 23, this );

	   // create the white pieces
	   pieces[40] = new Piece( Color.white, 40, this );
	   pieces[42] = new Piece( Color.white, 42, this );
	   pieces[44] = new Piece( Color.white, 44, this );
	   pieces[46] = new Piece( Color.white, 46, this );
	   pieces[49] = new Piece( Color.white, 49, this );
	   pieces[51] = new Piece( Color.white, 51, this );
	   pieces[53] = new Piece( Color.white, 53, this );
	   pieces[55] = new Piece( Color.white, 55, this );
	   pieces[56] = new Piece( Color.white, 56, this );
	   pieces[58] = new Piece( Color.white, 58, this );
	   pieces[60] = new Piece( Color.white, 60, this );
	   pieces[62] = new Piece( Color.white, 62, this );

   }

   /**
    * Move the piece at the start position to the end position
    * 
    * @param start - current location of the piece
    * @param end - the position where piece is moved
    * 
    * @return -1 if there is a piece in the end position
    * @deprecated Use {@link movePiece(Move)} instead, but this might be a better format
    */
   public int movePiece(int start, int end) {


	   int returnValue = 0;	

	   // check if the end position of the piece is occupied
	   if( occupied( end ) ) {
	   	
	   	   // if it is return -1
	   	   returnValue = -1;


	   // if it is not set a start position in the array to null
	   // put a piece object at the end position in the array
	   // that was at the start positon before
	   } else {
	   	
		   pieces[end] = pieces[start];
		   pieces[start] = null;


	   }

	   return returnValue;

   }

   public int movePiece(Move move) {
        Piece p = pieces[move.getPiece().getLocation()];
    	pieces[p.getLocation()] = null;
    	pieces[move.endLocation()] = p;
    
    	p.move(move.endLocation());
    	
    	return 0; // This is only 0 so that nothing breaks while we refactor
   }

   /**
    * This method checks if the space on the board contains a piece
    * 
    * @param space - the space that needs to be checked
    * 
    * @return true or false depending on the situation
    */
   public boolean occupied(int space) {

	   boolean returnValue = true;

	   // if it's in the bounds of the array,
	   	   // return true if the space is occupied
	   	   // false if the space is empty
	   // if it's outside the bounds of the array,
	   	   // return true
           
	   if ( space >= 1 && space <= 63 && pieces[space] == null ) {
	   	   returnValue = false;
	   }
	   
	   return returnValue;
	   
   }

   
   /**
    * This method removes piece at the position space
    * 
    * @param space - the positon of the piece to be removed
    */
   public void removePiece(int space) {
   
	   // go to the space position in the array
	   // set it equal to null
	   pieces[ space ] = null;

   }
   
   
   /**
    * This method creates a king piece 
    * 
    * @param space - the position at which the king piece is created 
    */
   public void kingPiece(int space) {
	pieces[space].upgrade();
   }
   
   
   /**
    * This method returns the color of the piece at a certain space
    * 
    * @param space - the position of the piece on the board
    * 
    * @return the color of this piece
    */
   public Color colorAt(int space) {
	   
	   Color returnValue = null;
	   // go to the space position in the array
	   // check if there is a piece at that position
	   // if there is none, return null
	   // else return the color of the piece
	   
	   if( occupied( space ) ) {
		   
		   returnValue = pieces[space].getColor();
		   
	   }
   
       return returnValue;
	   
   }
   

   /**
    * This method returns the piece at the certain position
    * 
    * @param space - the space of the piece
    * 
    * @return the piece at that space
    */
   public Piece getPieceAt(int space) {

	  Piece returnValue = null; 
	   try{
	   	   // check if there is piece at space position
	   	   // if there is none, return null
	   	   // else return the piece at that position
	   
	      if( occupied(  space ) ) {
		   
	   	   	   returnValue = pieces[space];
                           
	   	   }
	   
	   } catch( ArrayIndexOutOfBoundsException e ) {
	   
                          
	   } catch( NullPointerException e ) {
	   	
           
	   }
	   
      return returnValue;
	   
   }
   
   
   
   /**
    * This method returns if there is a piece of the specified color on the board
    * 
    * @param color - the color of the piece
    * 
    * @return true if there is a piece of the specified color left on the board
    *				else return false	
    */
   public boolean hasPieceOf( Color color) {
   	

	   boolean returnValue = false;

	   // go through the whole array
	   // if there is a piece of color in the array return true
	   // else return false
	   for( int i =1; i < pieces.length; i++ ) {
		   
	   	   if( pieces[i] != null && pieces[i].getColor() == color ) {

                  
	   	   	   	   returnValue = true;
	   	   	   	   i = pieces.length;
	   	   	   }
	   }

           
      return returnValue;
	   
   }
   

   /**
    * This method returns the size of the board
    * 
    * @return the size of the board, which is always 64
    */
   public int sizeOf() {
       return 64;
   }
   
   
   
   /**
    * This method returns a vector containing all blue Pieces
    * 
    * @return blue pieces on the board
    */
   public Vector bluePieces() {
   
      Vector bluePieces = new Vector();
      
      for ( int i = 0; i < 64; i++ ) {
          if ( occupied( i ) ) {
              if ( pieces[ i ].getColor() == Color.blue ) {
                  bluePieces.addElement( pieces[ i ] );
              }
          }                 
      }
      
      return bluePieces;
      
  }
 
 
   /**
    * This method returns a vector containing all white Pieces
    * 
    * @return white pieces on the board
    */
  public Vector whitePieces() {
      
      Vector whitePieces = new Vector();
      
      for ( int i = 0; i < 64; i++ ) {
          if ( occupied( i ) ) {
              if ( pieces[ i ].getColor() == Color.white ) {
                  whitePieces.addElement( pieces[ i ] );
              }
          }                 
      }
      
      return whitePieces;
 }
 
}//Board

