package chess.test;

import static org.junit.Assert.*;
import static chess.File.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Queen;

/**
 * Tests for the Queen class.
 */
public class QueenTest {

    /**
     * Confirm that Queens placed in their starting positions have the right
     * properties.
     */
    @Test
    public void initializeWithValidInputs()
    {
        Queen white = new Queen(1, 4, ChessPieceColor.WHITE);
        assertTrue(white.inValidStartingPosition());
        assertEquals(d, white.getFile());
        assertEquals(1, white.getRank());
        assertEquals(ChessPieceColor.WHITE, white.getColor());

        Queen black = new Queen(8, 4, ChessPieceColor.BLACK);
        assertTrue(black.inValidStartingPosition());
        assertEquals(8, black.getRow());;
        assertEquals(4, black.getColumn());
        assertEquals(ChessPieceColor.BLACK, black.getColor());
    }

    /**
     * Confirm that if the white and black Queens are swapped, they are not in
     * their starting positions.
     */
    @Test
    public void initializeWithBlackAndWhiteSwapped()
    {
        Queen whiteInBlackSpot = new Queen(8, 4, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackSpot.inValidStartingPosition());
        Queen blackInWhiteSpot = new Queen(1, 4, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteSpot.inValidStartingPosition());
    }

    /**
     * Confirm that Queens placed randomly on the board are not in valid
     * starting positions.
     */
    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Queen whiteInWrongColumn = new Queen(1, 7, ChessPieceColor.WHITE);
        assertFalse(whiteInWrongColumn.inValidStartingPosition());
        Queen blackInWrongRow = new Queen(7, 4, ChessPieceColor.BLACK);
        assertFalse(blackInWrongRow.inValidStartingPosition());
        Queen middleOfBoard = new Queen(4, 4, ChessPieceColor.BLACK);
        assertFalse(middleOfBoard.inValidStartingPosition());
        Queen offTheBoard = new Queen(3, 9, ChessPieceColor.WHITE);
        assertFalse(offTheBoard.inValidStartingPosition());
        Queen lowerRightCorner = new Queen(h, 1, ChessPieceColor.WHITE);
        assertFalse(lowerRightCorner.inValidStartingPosition());
    }

    /**
     * Confirm that all valid moves from the black Queen's start are recognized
     * properly.
     */
    @Test
    public void checkValidMovesAtBlackQueenStart()
    {
        Queen black = new Queen(8, 4, ChessPieceColor.BLACK);
        // go left all the way
        assertTrue(black.isValidMove(8, 1));
        // go left 1
        assertTrue(black.isValidMove(c, 8));
        // go right all the way
        assertTrue(black.isValidMove(8, 8));
        // go right 3
        assertTrue(black.isValidMove(g, 8));
        // go down all the way
        assertTrue(black.isValidMove(1, 4));
        // go down 5
        assertTrue(black.isValidMove(d, 3));
        // go down-right all the way
        assertTrue(black.isValidMove(4, 8));
        // go down-right 3
        assertTrue(black.isValidMove(g, 5));
        // go down-left all the way
        assertTrue(black.isValidMove(5, 1));
        // go down-left 1
        assertTrue(black.isValidMove(c, 7));
    }

    /**
     * Confirm that invalid moves from the black Queen's start are recognized
     * properly.
     */
    @Test
    public void checkWrongMovesAtBlackQueenStart()
    {
        Queen black = new Queen(d, 8, ChessPieceColor.BLACK);
        // try to go up, but fail
        assertFalse(black.isValidMove(9, 4));
        // try to go up-right, but fail
        assertFalse(black.isValidMove(10, 6));
        // try to move to self, fail
        assertFalse(black.isValidMove(8, 4));
        // jump to bottom right corner
        assertFalse(black.isValidMove(h, 1));
        // totally wrong move
        assertFalse(black.isValidMove(5, 6));
        // move like a knight
        assertFalse(black.isValidMove(e, 6));
    }

    /**
     * Confirm that valid moves made by a white Queen in the middle of the board
     * are recognized properly.
     */
    @Test
    public void checkValidMovesInCenter()
    {
        Queen white = new Queen(f, 4, ChessPieceColor.WHITE);
        // go up
        assertTrue(white.isValidMove(f, 8));
        // go up 3
        assertTrue(white.isValidMove(f, 7));
        // go up-right
        assertTrue(white.isValidMove(h, 6));
        // go up-right 1
        assertTrue(white.isValidMove(g, 5));
        // go right
        assertTrue(white.isValidMove(h, 4));
        // go right 1
        assertTrue(white.isValidMove(g, 4));
        // go down-right
        assertTrue(white.isValidMove(h, 2));
        // go down-right 1
        assertTrue(white.isValidMove(g, 3));
        // go down
        assertTrue(white.isValidMove(f, 1));
        // go down 2
        assertTrue(white.isValidMove(f, 2));
        // go down-left
        assertTrue(white.isValidMove(c, 1));
        // go left
        assertTrue(white.isValidMove(a, 4));
        // go left 4
        assertTrue(white.isValidMove(b, 4));
        // go up-left
        assertTrue(white.isValidMove(b, 8));
        // go up-left 2
        assertTrue(white.isValidMove(d, 6));
    }

    /**
     * Confirm that invalid moves made by a white Queen in the middle of the
     * board are recognized as invalid.
     */
    @Test
    public void checkWrongMovesInCenter()
    {
        Queen wrong = new Queen(f, 4, ChessPieceColor.WHITE);
        // move like a Knight
        assertFalse(wrong.isValidMove(d, 5));
        // jump to lower left corner
        assertFalse(wrong.isValidMove(a, 1));
        // up 1, left all the way
        assertFalse(wrong.isValidMove(a, 5));
        // off right edge
        assertFalse(wrong.isValidMove(4, 9));
        // jump to upper right corner
        assertFalse(wrong.isValidMove(h, 8));
        // move to self
        assertFalse(wrong.isValidMove(4, 6));
    }

    /**
     * Walk a white Queen around the board from its starting position,
     * confirming that valid moves succeed and invalid ones fail.
     */
    @Test
    public void walkWhiteQueenFromStart()
    {
        Queen walker = new Queen(d, 1, ChessPieceColor.WHITE);
        assertTrue(walker.inValidStartingPosition());
        // up 3
        assertTrue(walker.move(d, 4));
        // down-left 2
        assertTrue(walker.move(f, 2));
        // up-right 2
        assertTrue(walker.move(h, 4));
        // bad, jump to upper left corner
        assertFalse(walker.move(a, 8));
        // capture left 6
        assertTrue(walker.capture(b, 4));
        // up 2
        assertTrue(walker.move(b, 6));
        // bad, knight move
        assertFalse(walker.capture(d, 5));
        // up-right 1
        assertTrue(walker.move(c, 7));
        // down-right 5
        assertTrue(walker.capture(h, 2));
    }
}
