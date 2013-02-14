package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Prince;

/**
 * Tests for the Prince custom test piece class.
 * Remember white princes start at e3, black princes start at e6.
 */
public class PrinceTest {

    @Test
    public void initializeWithValidInputs()
    {
        Prince white = new Prince(3, 5, ChessPieceColor.WHITE);
        assertTrue(white.hasValidStartingPosition());
        Prince black = new Prince(6, 5, ChessPieceColor.BLACK);
        assertTrue(black.hasValidStartingPosition());
    }

    @Test
    public void initializeWithWhiteAndBlackSwapped()
    {
        Prince whiteOnBlackSpot = new Prince(6, 5, ChessPieceColor.WHITE);
        assertFalse(whiteOnBlackSpot.hasValidStartingPosition());
        Prince blackOnWhiteSpot = new Prince(3, 5, ChessPieceColor.BLACK);
        assertFalse(blackOnWhiteSpot.hasValidStartingPosition());
    }
    
    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Prince wrongColumnWhite = new Prince(3, 6, ChessPieceColor.WHITE);
        assertFalse(wrongColumnWhite.hasValidStartingPosition());
        Prince offTheBoard = new Prince(8, 9, ChessPieceColor.BLACK);
        assertFalse(offTheBoard.hasValidStartingPosition());
        Prince inTheMiddle = new Prince(4, 4, ChessPieceColor.WHITE);
        assertFalse(inTheMiddle.hasValidStartingPosition());
    }
    
    @Test
    public void checkValidMovesAtBlackPrinceStart()
    {
        Prince black = new Prince(6, 5, ChessPieceColor.BLACK);
        // go down
        assertTrue(black.isValidMove(5, 5));
        // go down-right
        assertTrue(black.isValidMove(5, 6));
        // go right
        assertTrue(black.isValidMove(6, 6));
        // go up-right
        assertTrue(black.isValidMove(7, 6));
        // go up
        assertTrue(black.isValidMove(7, 5));
        // go up-left
        assertTrue(black.isValidMove(7, 4));
        // go left
        assertTrue(black.isValidMove(6, 4));
        // go down-left
        assertTrue(black.isValidMove(5, 4));
    }
    
    @Test
    public void checkBadMoves()
    {
        Prince upperLeft = new Prince(8, 1, ChessPieceColor.BLACK);
        // can't move to self
        assertFalse(upperLeft.isValidMove(8, 1));
        // go off upper edge
        assertFalse(upperLeft.isValidMove(9, 1));
        // can't move 2 spaces
        assertFalse(upperLeft.isValidMove(8, 3));
        // can't jump all over the board
        assertFalse(upperLeft.isValidMove(3, 8));
    }
}
