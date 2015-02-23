package chess.test;

import static org.junit.Assert.*;
import static chess.File.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Pawn;

/**
 * Tests for the {@link Pawn} Class.
 */
public class PawnTest {

    /**
     * Confirm that Pawns placed in their starting positions have the right
     * properties.
     */
    @Test
    public void initializeWithValidInitialPositions()
    {
        // Pawns go on rank 2 for white
        Pawn white = new Pawn(2, 1, ChessPieceColor.WHITE);
        assertTrue(white.inStartingPosition());
        assertEquals(a, white.getFile());
        assertEquals(2, white.getRank());
        assertEquals(ChessPieceColor.WHITE, white.getColor());

        Pawn white2 = new Pawn(2, 8, ChessPieceColor.WHITE);
        assertTrue(white2.inStartingPosition());
        assertEquals(2, white2.getRow());
        assertEquals(8, white2.getColumn());

        // Pawns go on rank 7 for black
        Pawn black = new Pawn(c, 7, ChessPieceColor.BLACK);
        assertTrue(black.inStartingPosition());
        assertEquals(7, black.getRow());
        assertEquals(3, black.getColumn());
        assertEquals(ChessPieceColor.BLACK, black.getColor());

        Pawn black2 = new Pawn(7, 8, ChessPieceColor.BLACK);
        assertTrue(black2.inStartingPosition());
        assertEquals(h, black2.getFile());
        assertEquals(7, black2.getRank());
    }

    /**
     * Confirm that Pawns placed outside their normal starting position are
     * recognized as such.
     */
    @Test
    public void initializeWithIncorrectInitialPositions()
    {
        Pawn outOfBounds = new Pawn(2, 9, ChessPieceColor.WHITE);
        assertFalse(outOfBounds.inStartingPosition());
        Pawn middleOfBoard = new Pawn(4, 5, ChessPieceColor.WHITE);
        assertFalse(middleOfBoard.inStartingPosition());
        Pawn wrongColorOnWhiteSide = new Pawn(2, 6, ChessPieceColor.BLACK);
        assertFalse(wrongColorOnWhiteSide.inStartingPosition());
        Pawn wrongColorOnBlackSide = new Pawn(7, 8, ChessPieceColor.WHITE);
        assertFalse(wrongColorOnBlackSide.inStartingPosition());
        Pawn leftSide = new Pawn(b, 3, ChessPieceColor.WHITE);
        assertFalse(leftSide.inStartingPosition());
    }

    @Test
    public void inStartingPosition_MovingToStartFromSomewhereElseFails()
    {
        Pawn piece = new Pawn(a, 1, ChessPieceColor.WHITE);
        piece.move(a, 2); // pawn start
        assertFalse(piece.inStartingPosition());
    }

    /**
     * Confirm that a white Pawn in its starting position can make all possible
     * valid moves.
     */
    @Test
    public void confirmValidStartingMovesWhite()
    {
        Pawn white = new Pawn(2, 3, ChessPieceColor.WHITE);
        assertTrue(white.inStartingPosition());
        // forward 1
        assertTrue(white.isValidMove(3, 3));
        // forward 2 spaces at start
        assertTrue(white.isValidMove(4, 3));

        // can't capture with the move command
        assertFalse(white.isValidMove(3, 2));
        // random far away places
        assertFalse(white.isValidMove(5, 1));
        assertFalse(white.isValidMove(1, 8));
        // 3 forward, bad move
        assertFalse(white.isValidMove(c, 5));
    }

    /**
     * Confirm that a black Pawn in its starting position can make all possible
     * valid moves.
     */
    @Test
    public void confirmValidStartingMovesBlack()
    {
        Pawn black = new Pawn(a, 7, ChessPieceColor.BLACK);
        assertTrue(black.inStartingPosition());
        // forward 1
        assertTrue(black.isValidMove(6, 1));
        // forward 2 at start
        assertTrue(black.isValidMove(a, 5));

        // can't capture with move
        assertFalse(black.isValidMove(6, 2));
        // 3 forward, bad move
        assertFalse(black.isValidMove(a, 4));
        // move to self
        assertFalse(black.isValidMove(a, 7));
        // random far away places
        assertFalse(black.isValidMove(f, 1));
        assertFalse(black.isValidMove(h, 5));
        assertFalse(black.isValidMove(e, 5));
        assertFalse(black.isValidMove(f, 8));
    }

    /**
     * Alternate function for testing valid moves at a black Pawn's starting
     * position. Does everything in Row-Column notation.
     */
    @Test
    public void confirmValidStartingMovesBlackInRowColumnNotation()
    {
        Pawn black = new Pawn(7, 3, ChessPieceColor.BLACK);
        assertTrue(black.isValidMove(6, 3));
        // forward 2 spaces at start
        assertTrue(black.isValidMove(5, 3));
        // capture another piece
        assertTrue(black.canCapture(6, 4));
        assertTrue(black.canCapture(6, 2));
        // stay in place
        assertFalse(black.isValidMove(7, 3));
        // move backwards
        assertFalse(black.isValidMove(8, 3));
        // no capturing backwards
        assertFalse(black.canCapture(8, 4));
    }

    /**
     * Confirm that Pawn moving off the edge of the board is an invalid move.
     */
    @Test
    public void checkOffTheEdgeOfTheBoard()
    {
        Pawn offTop = new Pawn(8, 1, ChessPieceColor.WHITE);
        assertFalse(offTop.isValidMove(9, 1));
        Pawn offBottom = new Pawn(d, 1, ChessPieceColor.BLACK);
        assertFalse(offBottom.isValidMove(d, 0));
    }

    /**
     * Test a black Pawn's capture functionality.
     */
    @Test
    public void testBlackCapture()
    {
        Pawn black = new Pawn(7, 1, ChessPieceColor.BLACK);
        // can capture right
        assertTrue(black.canCapture(6, 2));
        // can't capture off left edge
        assertFalse(black.canCapture(6, 0));
        // can't capture self
        assertFalse(black.canCapture(7, 1));
        // can't capture forward
        assertFalse(black.canCapture(6, 1));
        // can't capture backwards
        assertFalse(black.canCapture(a, 8));
        // can't capture back-right
        assertFalse(black.canCapture(b, 8));
    }

    /**
     * Test a white Pawn's capture functionality.
     */
    @Test
    public void testWhiteCapture()
    {
        Pawn white = new Pawn(2, 8, ChessPieceColor.WHITE);
        // capture left
        assertTrue(white.canCapture(3, 7));
        // can't capture backwards
        assertFalse(white.canCapture(1, 7));
        // can't capture like bishop
        assertFalse(white.canCapture(4, 6));
    }

    /**
     * Confirm that valid/invalid moves for a white Pawn in the middle of the
     * board are recognized properly.
     */
    @Test
    public void confirmValidMiddleMovesWhite()
    {
        Pawn inTheMiddle = new Pawn(4, 4, ChessPieceColor.WHITE);
        assertFalse(inTheMiddle.inStartingPosition());
        // one forward
        assertTrue(inTheMiddle.isValidMove(5, 4));
        // capture left
        assertTrue(inTheMiddle.canCapture(5, 3));
        // capture right
        assertTrue(inTheMiddle.canCapture(5, 5));

        // no moving 2 spaces when not at start
        assertFalse(inTheMiddle.isValidMove(6, 4));
        // no capture back-right
        assertFalse(inTheMiddle.canCapture(e, 3));
        // no capture back-left
        assertFalse(inTheMiddle.canCapture(c, 3));
        // no going backwards
        assertFalse(inTheMiddle.isValidMove(d, 3));
        // no moving sideways
        assertFalse(inTheMiddle.isValidMove(4, 5));
    }

    /**
     * Confirm that valid/invalid moves for a black Pawn in the middle of the
     * board are recognized properly.
     */
    @Test
    public void confirmValidMiddleMovesBlack()
    {
        Pawn middle = new Pawn(5, 5, ChessPieceColor.BLACK);
        assertFalse(middle.inStartingPosition());
        // forward
        assertTrue(middle.isValidMove(4, 5));
        // capture right
        assertTrue(middle.canCapture(f, 4));
        // capture left
        assertTrue(middle.canCapture(d, 4));

        // no moving 2 spaces when not at start
        assertFalse(middle.isValidMove(e, 3));
        // no capture back-right
        assertFalse(middle.canCapture(f, 6));
        // no capture back-left
        assertFalse(middle.canCapture(d, 6));
        // no going backwards
        assertFalse(middle.isValidMove(e, 6));
        // no moving sideways
        assertFalse(middle.isValidMove(d, 5));
    }
}
