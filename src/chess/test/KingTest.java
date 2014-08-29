package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.File;
import chess.King;

/**
 * Tests for the King class.
 */
public class KingTest {

    /**
     * Check that Kings created in the correct starting positions have the right
     * properties.
     */
    @Test
    public void initializeWithValidInputs()
    {
        King whiteKing = new King(1, 5, ChessPieceColor.WHITE);
        assertTrue(whiteKing.inValidStartingPosition());
        // row/column notation
        assertEquals(1, whiteKing.getRow());
        assertEquals(5, whiteKing.getColumn());
        // file/rank notation
        assertEquals(File.e, whiteKing.getFile());
        assertEquals(1, whiteKing.getRank());
        assertEquals(ChessPieceColor.WHITE, whiteKing.getColor());
        assertFalse(whiteKing.isHoppable());

        King blackKing = new King(8, 5, ChessPieceColor.BLACK);
        assertTrue(blackKing.inValidStartingPosition());
        // row/column notation
        assertEquals(8, blackKing.getRow());
        assertEquals(5, blackKing.getColumn());
        // file/rank notation
        assertEquals(File.e, blackKing.getFile());
        assertEquals(8, blackKing.getRank());
        assertEquals(ChessPieceColor.BLACK, blackKing.getColor());
        assertFalse(blackKing.isHoppable());
    }

    /**
     * Check that Kings created in algebraic chess notation and placed in the
     * correct starting positions have the right properties.
     */
    @Test
    public void initializeInChessNotationWithValidInputs()
    {
        King whiteKing = new King(File.e, 1, ChessPieceColor.WHITE);
        assertTrue(whiteKing.inValidStartingPosition());
        // file/rank notation
        assertEquals(File.e, whiteKing.getFile());
        assertEquals(1, whiteKing.getRank());
        // row/column notation
        assertEquals(1, whiteKing.getRow());
        assertEquals(5, whiteKing.getColumn());
        assertEquals(ChessPieceColor.WHITE, whiteKing.getColor());
        assertFalse(whiteKing.isHoppable());

        King blackKing = new King(File.e, 8, ChessPieceColor.BLACK);
        assertTrue(blackKing.inValidStartingPosition());
        // file/rank notation
        assertEquals(File.e, blackKing.getFile());
        assertEquals(8, blackKing.getRank());
        // row/column notation
        assertEquals(8, blackKing.getRow());
        assertEquals(5, blackKing.getColumn());
        assertEquals(ChessPieceColor.BLACK, blackKing.getColor());
        assertFalse(blackKing.isHoppable());
    }

    /**
     * Swap the positions of the white and black king and confirm that these
     * aren't valid starting positions.
     */
    @Test
    public void initializeWithWhiteAndBlackKingSwapped()
    {
        // row/column notation
        King whiteKing = new King(8, 5, ChessPieceColor.WHITE);
        assertFalse(whiteKing.inValidStartingPosition());
        assertEquals(8, whiteKing.getRow());
        King blackKing = new King(1, 5, ChessPieceColor.BLACK);
        assertFalse(blackKing.inValidStartingPosition());
        assertEquals(1, blackKing.getRow());

        // file/rank notation
        King whiteKingAlg = new King(File.e, 8, ChessPieceColor.WHITE);
        assertEquals(8, whiteKingAlg.getRank());
        assertFalse(whiteKingAlg.inValidStartingPosition());
        King blackKingAlg = new King(File.e, 1, ChessPieceColor.BLACK);
        assertEquals(1, blackKingAlg.getRank());
        assertFalse(blackKingAlg.inValidStartingPosition());
    }

    /**
     * Confirm that Kings placed in the wrong column do not have valid starting
     * positions.
     */
    @Test
    public void initializeWithKingsInWrongColumn()
    {
        King whiteInQueenSpot = new King(1, 4, ChessPieceColor.WHITE);
        assertEquals(4, whiteInQueenSpot.getColumn());
        assertFalse(whiteInQueenSpot.inValidStartingPosition());

        King blackInQueenSpot = new King(8, 4, ChessPieceColor.BLACK);
        assertEquals(4, blackInQueenSpot.getColumn());
        assertFalse(blackInQueenSpot.inValidStartingPosition());

        King inTheMiddleOfTheBoard = new King (5, 6, ChessPieceColor.BLACK);
        assertEquals(5, inTheMiddleOfTheBoard.getRow());
        assertEquals(6, inTheMiddleOfTheBoard.getColumn());
        assertFalse(inTheMiddleOfTheBoard.inValidStartingPosition());

        King upperRightCorner = new King(8, 8, ChessPieceColor.BLACK);
        assertEquals(8, upperRightCorner.getRow());
        assertEquals(8, upperRightCorner.getColumn());
        assertFalse(upperRightCorner.inValidStartingPosition());
    }

    /**
     * Confirm that Kings placed in the wrong place with algebraic chess
     * notation do not have valid starting positions.
     */
    @Test
    public void initializeInChessNotationInWrongPosition()
    {
        King whiteInRightBishopSpot = new King(File.f, 1, ChessPieceColor.WHITE);
        assertEquals(6, whiteInRightBishopSpot.getColumn());
        assertEquals(1, whiteInRightBishopSpot.getRank());
        assertFalse(whiteInRightBishopSpot.inValidStartingPosition());

        King blackInLeftKnightSpot = new King(File.b, 8, ChessPieceColor.BLACK);
        assertEquals(File.b, blackInLeftKnightSpot.getFile());
        assertEquals(2, blackInLeftKnightSpot.getColumn());
        assertFalse(blackInLeftKnightSpot.inValidStartingPosition());

        King middleRightOfBoard = new King(File.g, 4, ChessPieceColor.WHITE);
        assertEquals(7, middleRightOfBoard.getColumn());
        assertEquals(4, middleRightOfBoard.getRow());
        assertFalse(middleRightOfBoard.inValidStartingPosition());
    }

    /**
     * Confirm the validity of all single step moves from the white king's
     * starting position.
     */
    @Test
    public void checkForValidMovesAtWhiteKingStart()
    {
        King whiteKing = new King(1, 5, ChessPieceColor.WHITE);
        assertTrue(whiteKing.inValidStartingPosition());
        // Go left
        assertTrue(whiteKing.isValidMove(1, 4));
        assertTrue(whiteKing.isValidMove(File.d, 1));
        // Go up-left
        assertTrue(whiteKing.isValidMove(2, 4));
        assertTrue(whiteKing.isValidMove(File.d, 2));
        // Go up
        assertTrue(whiteKing.isValidMove(2, 5));
        assertTrue(whiteKing.isValidMove(File.e, 2));
        // Go up-right
        assertTrue(whiteKing.isValidMove(2, 6));
        assertTrue(whiteKing.isValidMove(File.f, 2));
        // Go right
        assertTrue(whiteKing.isValidMove(1, 6));
        assertTrue(whiteKing.isValidMove(File.f, 1));
        // Go down-right
        assertFalse(whiteKing.isValidMove(0, 6));
        assertFalse(whiteKing.isValidMove(File.f, 0));
        // Go down
        assertFalse(whiteKing.isValidMove(0, 5));
        assertFalse(whiteKing.isValidMove(File.e, 0));
        // Go down-left
        assertFalse(whiteKing.isValidMove(0, 4));
        assertFalse(whiteKing.isValidMove(File.d, 0));
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
