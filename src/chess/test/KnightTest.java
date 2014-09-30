package chess.test;

import static org.junit.Assert.*;
import static chess.File.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Knight;

/**
 * Tests for the Knight class.
 */
public class KnightTest {

    /**
     * Confirm that Knights placed in their starting positions have the right
     * properties.
     */
    @Test
    public void initializeWithValidInputs()
    {
        Knight whiteLeft = new Knight(1, 2, ChessPieceColor.WHITE);
        assertTrue(whiteLeft.inValidStartingPosition());
        assertEquals(b, whiteLeft.getFile());
        assertEquals(1, whiteLeft.getRank());

        Knight whiteRight = new Knight(1, 7, ChessPieceColor.WHITE);
        assertTrue(whiteRight.inValidStartingPosition());
        assertEquals(g, whiteRight.getFile());
        assertEquals(1, whiteRight.getRank());

        Knight blackLeft = new Knight(8, 2, ChessPieceColor.BLACK);
        assertTrue(blackLeft.inValidStartingPosition());
        assertEquals(b, blackLeft.getFile());
        assertEquals(8, blackLeft.getRank());

        Knight blackRight = new Knight(8, 7, ChessPieceColor.BLACK);
        assertTrue(blackRight.inValidStartingPosition());
        assertTrue(blackRight.isHoppable());
        assertEquals(g, blackRight.getFile());
        assertEquals(8, blackRight.getRank());
    }

    @Test
    public void initializeWithBlackAndWhiteSwapped()
    {
        Knight whiteInBlackRightSpot = new Knight(8, 7, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackRightSpot.inValidStartingPosition());
        Knight blackInWhiteLeftSpot = new Knight(1, 2, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteLeftSpot.inValidStartingPosition());
    }

    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Knight whiteInWrongColumn = new Knight(1, 5, ChessPieceColor.WHITE);
        assertFalse(whiteInWrongColumn.inValidStartingPosition());
        Knight offTheBoard = new Knight(1, 9, ChessPieceColor.WHITE);
        assertFalse(offTheBoard.inValidStartingPosition());
        Knight inTheMiddle = new Knight(4, 4, ChessPieceColor.BLACK);
        assertFalse(inTheMiddle.inValidStartingPosition());
    }

    @Test
    public void checkValidMovesAtRightWhiteKnightStart()
    {
        Knight white = new Knight(1, 7, ChessPieceColor.WHITE);
        // up 2, left 1
        assertTrue(white.isValidMove(3, 6));
        // up 1, left 2
        assertTrue(white.isValidMove(2, 5));
        // up 2, right 1
        assertTrue(white.isValidMove(3, 8));
        // up 1, right 2, off the board
        assertFalse(white.isValidMove(2, 9));
        // down 2, left 1, off the board
        assertFalse(white.isValidMove(-1, 6));
        // up-left 2, like bishop, fail
        assertFalse(white.isValidMove(3, 5));
        // up 2, like rook, fail
        assertFalse(white.isValidMove(3, 7));
        // right 1, like rook, fail
        assertFalse(white.isValidMove(1, 8));
        // illegal move
        assertFalse(white.isValidMove(2, 4));
        // move to self, fail
        assertFalse(white.isValidMove(1, 7));
    }

    @Test
    public void checkValidMovesInMiddle()
    {
        Knight black = new Knight(4, 4, ChessPieceColor.BLACK);
        // up 2, left 1
        assertTrue(black.isValidMove(6, 3));
        // up 1, left 2
        assertTrue(black.isValidMove(5, 2));
        // up 2, right 1
        assertTrue(black.isValidMove(6, 5));
        // up 1, right 2
        assertTrue(black.isValidMove(5, 6));
        // down 2, left 1
        assertTrue(black.isValidMove(2, 3));
        // down 1, left 2
        assertTrue(black.isValidMove(3, 2));
        // down 2, right 1
        assertTrue(black.isValidMove(2, 5));
        // down 1, right 2
        assertTrue(black.isValidMove(3, 6));

    }

    @Test
    public void checkCapture()
    {
        Knight white = new Knight(4, 4, ChessPieceColor.WHITE);
        // up 2, left 1
        assertTrue(white.capture(6, 3));
    }
}
