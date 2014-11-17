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
     * Confirm that invalid moves from the black Queen's start are recognized.
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

}
