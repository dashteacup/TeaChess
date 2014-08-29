package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.King;

/**
 * Tests for the King class.
 */
public class KingTest {

    @Test
    public void initializeWithValidInputs()
    {
        King whiteKing = new King(1, 5, ChessPieceColor.WHITE);
        assertTrue(whiteKing.inValidStartingPosition());
        King blackKing = new King(8, 5, ChessPieceColor.BLACK);
        assertTrue(blackKing.inValidStartingPosition());
        assertFalse(blackKing.isHoppable());
    }
    
    @Test
    public void initializeWithWhiteAndBlackKingSwapped()
    {
        King whiteKing = new King(8, 5, ChessPieceColor.WHITE);
        assertFalse(whiteKing.inValidStartingPosition());
        King blackKing = new King(1, 5, ChessPieceColor.BLACK);
        assertFalse(blackKing.inValidStartingPosition());
    }
    
    @Test
    public void initializeWithKingsInWrongColumn()
    {
        King whiteInQueenSpot = new King(1, 4, ChessPieceColor.WHITE);
        assertFalse(whiteInQueenSpot.inValidStartingPosition());
        King blackInQueenSpot = new King(8, 4, ChessPieceColor.BLACK);
        assertFalse(blackInQueenSpot.inValidStartingPosition());
        King inTheMiddleOfTheBoard = new King (5, 6, ChessPieceColor.BLACK);
        assertFalse(inTheMiddleOfTheBoard.inValidStartingPosition());
    }
    
    @Test
    public void checkForValidMovesAtWhiteKingStart()
    {
        King whiteKing = new King(1, 5, ChessPieceColor.WHITE);
        // Go right
        assertTrue(whiteKing.isValidMove(1, 6));
        // Go left
        assertTrue(whiteKing.isValidMove(1, 4));
        // Go up
        assertTrue(whiteKing.isValidMove(2, 5));
        // Go up-left
        assertTrue(whiteKing.isValidMove(2, 4));
        // Go down
        assertFalse(whiteKing.isValidMove(0, 5));
        // Go down-right
        assertFalse(whiteKing.isValidMove(0, 6));
    }
    
    @Test
    public void checkForValidMovesAtBlackKingStart()
    {
        King blackKing = new King(8, 5, ChessPieceColor.BLACK);
        // Go up-right
        assertFalse(blackKing.isValidMove(9, 6));
        // Go down-left
        assertTrue(blackKing.isValidMove(7, 4));
    }
    
    @Test
    public void checkForValidMovesAtUpperRightCorner()
    {
        King blackKing = new King(8, 8, ChessPieceColor.BLACK);
        // Go up
        assertFalse(blackKing.isValidMove(9, 8));
        // Go up-right
        assertFalse(blackKing.isValidMove(9, 9));
        // Go down-right
        assertFalse(blackKing.isValidMove(7, 9));
        // Go down
        assertTrue(blackKing.isValidMove(7, 8));
    }
    
    @Test
    public void moveFromBlackStart()
    {
        King blackWanderer = new King(8, 5, ChessPieceColor.BLACK);
        // Try to go up, but fail
        assertFalse(blackWanderer.move(9, 5));
        // Go down
        assertTrue(blackWanderer.move(7, 5));
        // Go down-left
        assertTrue(blackWanderer.move(6, 4));
        // Go up
        assertTrue(blackWanderer.move(7,  4));
        // Go left
        assertTrue(blackWanderer.move(7, 3));
        // Go left
        assertTrue(blackWanderer.move(7, 2));
        // Go left
        assertTrue(blackWanderer.move(7, 1));
        // Try to go left, but fail
        assertFalse(blackWanderer.move(7, 0));
    }
    
    @Test
    public void moveFromLowerLeftCorner()
    {
        King whiteTraveler = new King(1, 1, ChessPieceColor.WHITE);
        assertFalse(whiteTraveler.inValidStartingPosition());
        // Try to go down-left, but fail
        assertFalse(whiteTraveler.move(0, 0));
        // Try to go left, but fail
        assertFalse(whiteTraveler.move(1, 0));
        // Go right
        assertTrue(whiteTraveler.move(1, 2));
        // Go right
        assertTrue(whiteTraveler.move(1, 3));
        // Go right
        assertTrue(whiteTraveler.move(1, 4));
        // Go up
        assertTrue(whiteTraveler.move(2, 4));
        // Go up-right
        assertTrue(whiteTraveler.move(3, 5));
    }
    
    @Test
    public void moveCrazyWrong()
    {
        King confused = new King(4, 4, ChessPieceColor.WHITE);
        // Can't cover two spaces in one move
        assertFalse(confused.move(4, 6));
        // Go right
        confused.move(4, 5);
        assertFalse(confused.move(1,1));
        // Go up-right
        confused.move(5, 6);
        // Go right
        confused.move(5, 7);
        // Go down-right
        assertTrue(confused.move(4, 8));
        // Skipping 3 spaces this time
        assertFalse(confused.move(7, 8));
        // Try to go right, but fail
        assertFalse(confused.move(4, 9));
        // Can't move to yourself
        assertFalse(confused.move(4, 8));
    }

}
