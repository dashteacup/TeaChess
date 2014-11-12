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

    /**
     * Swap the position of the white and black knights and confirm that these
     * aren't valid starting positions.
     */
    @Test
    public void initializeWithBlackAndWhiteSwapped()
    {
        Knight whiteInBlackRightSpot = new Knight(8, 7, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackRightSpot.inValidStartingPosition());

        Knight whiteInBlackLeftSpot = new Knight(b, 8, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackLeftSpot.inValidStartingPosition());

        Knight blackInWhiteRightSpot = new Knight(g, 1, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteRightSpot.inValidStartingPosition());

        Knight blackInWhiteLeftSpot = new Knight(1, 2, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteLeftSpot.inValidStartingPosition());
    }

    /**
     * Confirm that Knights set in the wrong place have invalid starting
     * positions.
     */
    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Knight whiteInWrongColumn = new Knight(1, 5, ChessPieceColor.WHITE);
        assertFalse(whiteInWrongColumn.inValidStartingPosition());

        Knight offTheBoard = new Knight(1, 9, ChessPieceColor.WHITE);
        assertFalse(offTheBoard.inValidStartingPosition());

        Knight inTheMiddle = new Knight(4, 4, ChessPieceColor.BLACK);
        assertFalse(inTheMiddle.inValidStartingPosition());

        Knight rightSideOfBoard = new Knight(h, 4, ChessPieceColor.WHITE);
        assertFalse(rightSideOfBoard.inValidStartingPosition());
    }


    /**
     * Confirm that all L-shaped moves from the right white knight's starting
     * position are recognized as valid/invalid.
     */
    @Test
    public void confirmValidMovesAtRightWhiteKnightStart()
    {
        Knight white = new Knight(1, 7, ChessPieceColor.WHITE);
        assertTrue(white.inValidStartingPosition());
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
        // down 2, right 1, off the board
        assertFalse(white.isValidMove(h, -1));
        // down 1, left 2, off the board
        assertFalse(white.isValidMove(e, 0));
        // down 1, right 2, off the board
        assertFalse(white.isValidMove(0, 9));
    }

    /**
     * Confirm that all L-shaped moves from the left black knight's starting
     * position are recognized as valid/invalid.
     */
    @Test
    public void confirmValidMovesAtLeftBlackKnightStart()
    {
        Knight black = new Knight(b, 8, ChessPieceColor.BLACK);
        assertTrue(black.inValidStartingPosition());
        // down 2, left 1
        assertTrue(black.isValidMove(a, 6));
        // down 2, right 1
        assertTrue(black.isValidMove(c, 6));
        // down 1, right 2
        assertTrue(black.isValidMove(d, 7));
        // down 1, left 2, off the board
        assertFalse(black.isValidMove(7, 0));
        // up 1, left 2, off the board
        assertFalse(black.isValidMove(9, 0));
        // up 1, right 2, off the board
        assertFalse(black.isValidMove(9, 4));
        // up 2, left 1, off the board
        assertFalse(black.isValidMove(10, 1));
        // up 2, right 1, off the board
        assertFalse(black.isValidMove(10, 3));
    }

    /**
     * Confirm that non-L-shaped moves are recognized as invalid.
     */
    @Test
    public void confirmBadMovesAtRightWhiteKnightStart()
    {
        Knight white = new Knight(g, 1, ChessPieceColor.WHITE);
        assertTrue(white.inValidStartingPosition());
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
        // move all the way up
        assertFalse(white.isValidMove(g, 8));
        // move to upper left corner
        assertFalse(white.isValidMove(a, 8));
        // move to upper right corner
        assertFalse(white.isValidMove(h, 8));
        // move to lower left corner
        assertFalse(white.isValidMove(a, 1));
        // move to middle of board
        assertFalse(white.isValidMove(4, 4));
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
