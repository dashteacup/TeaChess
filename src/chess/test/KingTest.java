package chess.test;

import static chess.File.a;
import static chess.File.b;
import static chess.File.c;
import static chess.File.d;
import static chess.File.e;
import static chess.File.f;
import static chess.File.g;
import static chess.File.h;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.King;

/**
 * Tests for the {@link King} class.
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
        assertTrue(whiteKing.inStartingPosition());
        // row/column notation
        assertEquals(1, whiteKing.getRow());
        assertEquals(5, whiteKing.getColumn());
        // file/rank notation
        assertEquals(e, whiteKing.getFile());
        assertEquals(1, whiteKing.getRank());
        assertEquals(ChessPieceColor.WHITE, whiteKing.getColor());
        assertFalse(whiteKing.isHoppable());

        King blackKing = new King(8, 5, ChessPieceColor.BLACK);
        assertTrue(blackKing.inStartingPosition());
        // row/column notation
        assertEquals(8, blackKing.getRow());
        assertEquals(5, blackKing.getColumn());
        // file/rank notation
        assertEquals(e, blackKing.getFile());
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
        King whiteKing = new King(e, 1, ChessPieceColor.WHITE);
        assertTrue(whiteKing.inStartingPosition());
        // file/rank notation
        assertEquals(e, whiteKing.getFile());
        assertEquals(1, whiteKing.getRank());
        // row/column notation
        assertEquals(1, whiteKing.getRow());
        assertEquals(5, whiteKing.getColumn());
        assertEquals(ChessPieceColor.WHITE, whiteKing.getColor());
        assertFalse(whiteKing.isHoppable());

        King blackKing = new King(e, 8, ChessPieceColor.BLACK);
        assertTrue(blackKing.inStartingPosition());
        // file/rank notation
        assertEquals(e, blackKing.getFile());
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
        assertFalse(whiteKing.inStartingPosition());
        assertEquals(8, whiteKing.getRow());
        King blackKing = new King(1, 5, ChessPieceColor.BLACK);
        assertFalse(blackKing.inStartingPosition());
        assertEquals(1, blackKing.getRow());

        // file/rank notation
        King whiteKingAlg = new King(e, 8, ChessPieceColor.WHITE);
        assertEquals(8, whiteKingAlg.getRank());
        assertFalse(whiteKingAlg.inStartingPosition());
        King blackKingAlg = new King(e, 1, ChessPieceColor.BLACK);
        assertEquals(1, blackKingAlg.getRank());
        assertFalse(blackKingAlg.inStartingPosition());
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
        assertFalse(whiteInQueenSpot.inStartingPosition());

        King blackInQueenSpot = new King(8, 4, ChessPieceColor.BLACK);
        assertEquals(4, blackInQueenSpot.getColumn());
        assertFalse(blackInQueenSpot.inStartingPosition());

        King inTheMiddleOfTheBoard = new King (5, 6, ChessPieceColor.BLACK);
        assertEquals(5, inTheMiddleOfTheBoard.getRow());
        assertEquals(6, inTheMiddleOfTheBoard.getColumn());
        assertFalse(inTheMiddleOfTheBoard.inStartingPosition());

        King upperRightCorner = new King(8, 8, ChessPieceColor.BLACK);
        assertEquals(8, upperRightCorner.getRow());
        assertEquals(8, upperRightCorner.getColumn());
        assertFalse(upperRightCorner.inStartingPosition());
    }

    /**
     * Confirm that Kings placed in the wrong place with algebraic chess
     * notation do not have valid starting positions.
     */
    @Test
    public void initializeInChessNotationInWrongPosition()
    {
        King whiteInRightBishopSpot = new King(f, 1, ChessPieceColor.WHITE);
        assertEquals(6, whiteInRightBishopSpot.getColumn());
        assertEquals(1, whiteInRightBishopSpot.getRank());
        assertFalse(whiteInRightBishopSpot.inStartingPosition());

        King blackInLeftKnightSpot = new King(b, 8, ChessPieceColor.BLACK);
        assertEquals(b, blackInLeftKnightSpot.getFile());
        assertEquals(2, blackInLeftKnightSpot.getColumn());
        assertFalse(blackInLeftKnightSpot.inStartingPosition());

        King middleRightOfBoard = new King(g, 4, ChessPieceColor.WHITE);
        assertEquals(7, middleRightOfBoard.getColumn());
        assertEquals(4, middleRightOfBoard.getRow());
        assertFalse(middleRightOfBoard.inStartingPosition());
    }

    @Test
    public void inStartingPosition_MovingToStartFromSomewhereElseFails()
    {
        King king = new King(f, 1, ChessPieceColor.WHITE);
        king.move(e, 1); // white king start
        assertFalse(king.inStartingPosition());
    }

    /**
     * Confirm that single step moves from the white king's starting position
     * are properly recognized as valid/invalid.
     */
    @Test
    public void confirmValidMovesAtWhiteKingStart()
    {
        King whiteKing = new King(1, 5, ChessPieceColor.WHITE);
        assertTrue(whiteKing.inStartingPosition());
        // Move to self
        assertFalse(whiteKing.isValidMove(e, 1));
        // Go left
        assertTrue(whiteKing.isValidMove(1, 4));
        assertTrue(whiteKing.isValidMove(d, 1));
        // Go up-left
        assertTrue(whiteKing.isValidMove(2, 4));
        assertTrue(whiteKing.isValidMove(d, 2));
        // Go up
        assertTrue(whiteKing.isValidMove(2, 5));
        assertTrue(whiteKing.isValidMove(e, 2));
        // Go up-right
        assertTrue(whiteKing.isValidMove(2, 6));
        assertTrue(whiteKing.isValidMove(f, 2));
        // Go right
        assertTrue(whiteKing.isValidMove(1, 6));
        assertTrue(whiteKing.isValidMove(f, 1));
        // Go down-right
        assertFalse(whiteKing.isValidMove(0, 6));
        assertFalse(whiteKing.isValidMove(f, 0));
        // Go down
        assertFalse(whiteKing.isValidMove(0, 5));
        assertFalse(whiteKing.isValidMove(e, 0));
        // Go down-left
        assertFalse(whiteKing.isValidMove(0, 4));
        assertFalse(whiteKing.isValidMove(d, 0));
    }

    /**
     * Confirm that single step moves from the black king's starting position
     * are properly recognized as valid/invalid.
     */
    @Test
    public void confirmValidMovesAtBlackKingStart()
    {
        King blackKing = new King(e, 8, ChessPieceColor.BLACK);
        assertTrue(blackKing.inStartingPosition());
        // Move to self
        assertFalse(blackKing.isValidMove(8, 5));
        // Go left
        assertTrue(blackKing.isValidMove(d, 8));
        assertTrue(blackKing.isValidMove(8, 4));
        // Go up-left
        assertFalse(blackKing.isValidMove(d, 9));
        assertFalse(blackKing.isValidMove(9, 4));
        // Go up
        assertFalse(blackKing.isValidMove(e, 9));
        assertFalse(blackKing.isValidMove(9, 5));
        // Go up-right
        assertFalse(blackKing.isValidMove(f, 9));
        assertFalse(blackKing.isValidMove(9, 6));
        // Go right
        assertTrue(blackKing.isValidMove(f, 8));
        assertTrue(blackKing.isValidMove(8, 6));
        // Go down-right
        assertTrue(blackKing.isValidMove(f, 7));
        assertTrue(blackKing.isValidMove(7, 6));
        // Go down
        assertTrue(blackKing.isValidMove(e, 7));
        assertTrue(blackKing.isValidMove(7, 5));
        // Go down-left
        assertTrue(blackKing.isValidMove(d, 7));
        assertTrue(blackKing.isValidMove(7, 4));
    }

    /**
     * Confirm that single step moves from a king in the upper right corner are
     * properly recognized as valid/invalid.
     */
    @Test
    public void confirmValidMovesAtUpperRightCorner()
    {
        King upperRightKing = new King(8, 8, ChessPieceColor.BLACK);
        assertFalse(upperRightKing.inStartingPosition());
        // Go left
        assertTrue(upperRightKing.isValidMove(8, 7));
        assertTrue(upperRightKing.isValidMove(g, 8));
        // Go up-left
        assertFalse(upperRightKing.isValidMove(9, 7));
        assertFalse(upperRightKing.isValidMove(g, 9));
        // Go up
        assertFalse(upperRightKing.isValidMove(9, 8));
        assertFalse(upperRightKing.isValidMove(h, 9));
        // Go up-right
        assertFalse(upperRightKing.isValidMove(9, 9));
        // Note that because of the enum, the file MUST be a-h. There is no way
        // to give it an out of bounds value.
        // Go right
        assertFalse(upperRightKing.isValidMove(8, 9));
        // Go down-right
        assertFalse(upperRightKing.isValidMove(7, 9));
        // Go down
        assertTrue(upperRightKing.isValidMove(7, 8));
        assertTrue(upperRightKing.isValidMove(h, 7));
        // Go down-left
        assertTrue(upperRightKing.isValidMove(7, 7));
        assertTrue(upperRightKing.isValidMove(g, 7));
    }

    /**
     * Confirm that all single step moves from a king in the center of the board
     * are recognized as valid.
     */
    @Test
    public void confirmAllMovesValidInCenter()
    {
        King centeredKing = new King(c, 3, ChessPieceColor.WHITE);
        assertFalse(centeredKing.inStartingPosition());
        // go left
        assertTrue(centeredKing.isValidMove(b, 3));
        // go up-left
        assertTrue(centeredKing.isValidMove(b, 4));
        // go up
        assertTrue(centeredKing.isValidMove(c, 4));
        // go up-right
        assertTrue(centeredKing.isValidMove(d, 4));
        // go right
        assertTrue(centeredKing.isValidMove(d, 3));
        // go down-right
        assertTrue(centeredKing.isValidMove(d, 2));
        // go down
        assertTrue(centeredKing.isValidMove(c, 2));
        // go down-left
        assertTrue(centeredKing.isValidMove(b, 2));
    }

    /**
     * Walk a black king around the board. Confirm that valid moves succeed,
     * invalid moves fail, and the state is updated correctly.
     */
    @Test
    public void moveFromBlackStart()
    {
        King blackWanderer = new King(8, 5, ChessPieceColor.BLACK);
        assertTrue(blackWanderer.inStartingPosition());
        // Try to go up, but fail
        assertFalse(blackWanderer.move(9, 5));
        // Go down
        assertTrue(blackWanderer.move(7, 5));
        assertEquals(e, blackWanderer.getFile());
        assertEquals(7, blackWanderer.getRank());
        // Go down-left
        assertTrue(blackWanderer.move(6, 4));
        assertEquals(d, blackWanderer.getFile());
        assertEquals(6, blackWanderer.getRank());
        // Go up
        assertTrue(blackWanderer.move(7, 4));
        assertEquals(d, blackWanderer.getFile());
        assertEquals(7, blackWanderer.getRank());
        // Go left
        assertTrue(blackWanderer.move(7, 3));
        assertEquals(c, blackWanderer.getFile());
        assertEquals(7, blackWanderer.getRank());
        // Go left
        assertTrue(blackWanderer.move(7, 2));
        assertEquals(b, blackWanderer.getFile());
        assertEquals(7, blackWanderer.getRank());
        // Go left
        assertTrue(blackWanderer.move(7, 1));
        assertEquals(a, blackWanderer.getFile());
        assertEquals(7, blackWanderer.getRank());
        // Try to go left, but fail
        assertFalse(blackWanderer.move(7, 0));
        // Try to go down-left, but fail
        assertFalse(blackWanderer.move(6, 0));
        // go down
        assertTrue(blackWanderer.move(6, 1));
        assertEquals(a, blackWanderer.getFile());
        assertEquals(6, blackWanderer.getRank());
        // go down-right
        assertTrue(blackWanderer.move(b, 5));
        // go down
        assertTrue(blackWanderer.move(b, 4));
        assertEquals(4, blackWanderer.getRow());
        assertEquals(2, blackWanderer.getColumn());
    }

    /**
     * Walk a white king around the board, starting from the lower left corner.
     * Confirm that valid moves succeed, invalid moves fail, and the state is
     * updated correctly.
     */
    @Test
    public void moveFromLowerLeftCorner()
    {
        King whiteTraveler = new King(1, 1, ChessPieceColor.WHITE);
        assertFalse(whiteTraveler.inStartingPosition());
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
        assertEquals(d, whiteTraveler.getFile());
        assertEquals(1, whiteTraveler.getRank());
        // Go up
        assertTrue(whiteTraveler.move(2, 4));
        assertEquals(d, whiteTraveler.getFile());
        assertEquals(2, whiteTraveler.getRank());
        // Go up-right
        assertTrue(whiteTraveler.move(3, 5));
        assertEquals(e, whiteTraveler.getFile());
        assertEquals(3, whiteTraveler.getRank());
        // go up-right
        assertTrue(whiteTraveler.move(f, 4));
        assertEquals(f, whiteTraveler.getFile());
        assertEquals(4, whiteTraveler.getRank());
        // go right
        assertTrue(whiteTraveler.move(g, 4));
        // go up-right
        assertTrue(whiteTraveler.move(h, 5));
        assertEquals(5, whiteTraveler.getRow());
        assertEquals(8, whiteTraveler.getColumn());
        // go right, but fail
        assertFalse(whiteTraveler.move(5, 9));
        // go up
        assertTrue(whiteTraveler.move(h, 6));
        assertEquals(h, whiteTraveler.getFile());
        assertEquals(6, whiteTraveler.getRank());
    }

    /**
     * Test that a king behaves correctly when given invalid but in-bounds moves.
     */
    @Test
    public void moveCrazyWrong()
    {
        King confusedKing = new King(4, 4, ChessPieceColor.WHITE);
        // Can't cover two spaces in one move
        assertFalse(confusedKing.move(4, 6));
        // Go right
        assertTrue(confusedKing.move(4, 5));
        // Can't jump to random places
        assertFalse(confusedKing.move(1, 1));
        assertFalse(confusedKing.move(b, 8));
        assertFalse(confusedKing.move(8, 8));
        assertFalse(confusedKing.move(h, 6));
        // Go up-right (still at (4, 5))
        assertTrue(confusedKing.move(5, 6));
        // Go right
        assertTrue(confusedKing.move(5, 7));
        // Go down-right
        assertTrue(confusedKing.move(4, 8));
        // Can't skip 3 spaces
        assertFalse(confusedKing.move(7, 8));
        // Try to go right, but fail
        assertFalse(confusedKing.move(4, 9));
        // Can't move to yourself
        assertFalse(confusedKing.move(4, 8));
        // Can't move like a bishop
        assertFalse(confusedKing.move(d, 8));
        // Go up-left
        assertTrue(confusedKing.move(g, 5));
        // Can't move like a knight
        assertFalse(confusedKing.move(e, 4));
        // Go up
        assertTrue(confusedKing.move(g, 6));
        // Can't move like a rook
        assertFalse(confusedKing.move(a, 6));
        // Make sure we are in the right place
        assertEquals(6, confusedKing.getRow());
        assertEquals(7, confusedKing.getColumn());
    }

}
