package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Rook;

/**
 * Tests for the Rook class
 */
public class RookTest {

    @Test
    public void testConstructorWithValidInitialPositions()
    {
        Rook lwhite = new Rook(1, 1, ChessPieceColor.WHITE);
        assertTrue(lwhite.inValidStartingPosition());
        Rook lblack = new Rook(8, 1, ChessPieceColor.BLACK);
        assertTrue(lblack.inValidStartingPosition());
        Rook rwhite = new Rook(1, 8, ChessPieceColor.WHITE);
        assertTrue(rwhite.inValidStartingPosition());
        Rook rblack = new Rook(8, 8, ChessPieceColor.BLACK);
        assertTrue(rblack.inValidStartingPosition());
        
        // Make sure these inherited functions still work correctly
        assertEquals(8, rblack.getRow());
        assertEquals(8, rblack.getColumn());
        assertEquals(ChessPieceColor.BLACK, rblack.getColor());
    }
    
    @Test
    public void testConstructorWithIncorrectInitialPositions()
    {
        Rook bad = new Rook(3, 3, ChessPieceColor.WHITE);
        assertFalse(bad.inValidStartingPosition());
        Rook invalid = new Rook(0, 1, ChessPieceColor.WHITE);
        assertFalse(invalid.inValidStartingPosition());
        Rook wrong = new Rook(7, 8, ChessPieceColor.WHITE);
        assertFalse(wrong.inValidStartingPosition());
    }
    
    @Test
    public void testConstructorWithWrongColors()
    {
        Rook black11 = new Rook(1, 1, ChessPieceColor.BLACK);
        assertFalse(black11.inValidStartingPosition());
        Rook black18 = new Rook(1, 8, ChessPieceColor.BLACK);
        assertFalse(black18.inValidStartingPosition());
        Rook white81 = new Rook(8, 1, ChessPieceColor.WHITE);
        assertFalse(white81.inValidStartingPosition());
        Rook white88 = new Rook(8, 8, ChessPieceColor.WHITE);
        assertFalse(white88.inValidStartingPosition());
    }

    @Test
    public void testMove()
    {
        Rook white = new Rook(1, 1, ChessPieceColor.WHITE);
        assertTrue(white.isValidMove(1, 3));
        assertTrue(white.isValidMove(8, 1));
        white.move(1, 5);
        assertTrue(white.move(4, 5));
        // can't move diagonally
        assertFalse(white.move(5, 6));
        assertTrue(white.move(7, 5));
        // no going out of bounds
        assertFalse(white.move(9, 6));
        
        Rook black = new Rook(3, 3, ChessPieceColor.BLACK);
        assertTrue(black.move(8, 3));
        assertFalse(black.move(3, 4));
    }
}
