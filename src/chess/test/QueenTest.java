package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Queen;

/**
 * Tests for the Queen class.
 */
public class QueenTest {
    
    @Test
    public void initializeWithValidInputs()
    {
        Queen white = new Queen(1, 4, ChessPieceColor.WHITE);
        assertTrue(white.inValidStartingPosition());
        Queen black = new Queen(8, 4, ChessPieceColor.BLACK);
        assertTrue(black.inValidStartingPosition());        
    }
    
    @Test
    public void initializeWithBlackAndWhiteSwapped()
    {
        Queen whiteInBlackSpot = new Queen(8, 4, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackSpot.inValidStartingPosition());
        Queen blackInWhiteSpot = new Queen(1, 4, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteSpot.inValidStartingPosition());
    }
    
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
    
    @Test
    public void checkValidMovesAtBlackQueenStart()
    {
        Queen black = new Queen(8, 4, ChessPieceColor.BLACK);
        // go left all the way
        assertTrue(black.isValidMove(8, 1));
        // go right all the way
        assertTrue(black.isValidMove(8, 8));
        // go down all the way
        assertTrue(black.isValidMove(1, 4));
        // try to go up, but fail
        assertFalse(black.isValidMove(9, 4));
        // go down-right all the way
        assertTrue(black.isValidMove(4, 8));
        // go down-left all the way
        assertTrue(black.isValidMove(5, 1));
        // try to go up-right, but fail
        assertFalse(black.isValidMove(10, 6));
        // try to move to self, fail
        assertFalse(black.isValidMove(8, 4));
        // totally wrong move
        assertFalse(black.isValidMove(5, 6));
    }

}
