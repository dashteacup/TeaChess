package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Pawn;

/**
 * Tests for the Pawn Class.
 */
public class PawnTest {

    @Test
    public void testConstructorWithValidInitialPositions()
    {
        // Pawns go on rank 2 for white
        Pawn white = new Pawn(2, 1, ChessPieceColor.WHITE);
        assertTrue(white.hasValidStartingPosition());
        Pawn white2 = new Pawn(2, 8, ChessPieceColor.WHITE);
        assertTrue(white2.hasValidStartingPosition());
        // Pawns go on rank 7 for black
        Pawn black = new Pawn(7, 3, ChessPieceColor.BLACK);
        assertTrue(black.hasValidStartingPosition());
        Pawn black2 = new Pawn(7, 8, ChessPieceColor.BLACK);
        assertTrue(black2.hasValidStartingPosition());
    }
    
    @Test
    public void testConstructorWithIncorrectInitialPositions()
    {
        Pawn outOfBounds = new Pawn(2, 9, ChessPieceColor.WHITE);
        assertFalse(outOfBounds.hasValidStartingPosition());
        Pawn middleOfBoard = new Pawn(4, 5, ChessPieceColor.WHITE);
        assertFalse(middleOfBoard.hasValidStartingPosition());
        Pawn wrongColorOnWhiteSide = new Pawn(2, 6, ChessPieceColor.BLACK);
        assertFalse(wrongColorOnWhiteSide.hasValidStartingPosition());
        Pawn wrongColorOnBlackSide = new Pawn(7, 8, ChessPieceColor.WHITE);
        assertFalse(wrongColorOnBlackSide.hasValidStartingPosition());
    }

    @Test
    public void testIsValidStartingMoveWhite()
    {
        Pawn white = new Pawn(2, 1, ChessPieceColor.WHITE);
        assertTrue(white.isValidMove(3, 1));
        // forward 2 spaces at start
        assertTrue(white.isValidMove(4, 1));
        // can't capture with the move command
        assertFalse(white.isValidMove(3, 2));
        // random far away places
        assertFalse(white.isValidMove(5, 1));
        assertFalse(white.isValidMove(1, 8));
    }
    
    @Test
    public void testOffTheEdgeOfTheBoard()
    {
        Pawn off = new Pawn(8, 1, ChessPieceColor.WHITE);
        assertFalse(off.isValidMove(9, 1));
    }
    
    @Test
    public void testIsValidMiddleMoveWhite()
    {
        Pawn inTheMiddle = new Pawn(4, 4, ChessPieceColor.WHITE);
        assertTrue(inTheMiddle.isValidMove(5, 4));
        // capture left
        assertTrue(inTheMiddle.canCapture(5, 3));
        // capture right
        assertTrue(inTheMiddle.canCapture(5, 5));
        // no moving 2 spaces when not at start
        assertFalse(inTheMiddle.isValidMove(6, 4));
    }
    
    @Test
    public void testIsValidStartingMoveBlack()
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
    
    @Test
    public void testMiddleMoveBlack()
    {
        Pawn middle = new Pawn(5, 5, ChessPieceColor.BLACK);
        // forward
        assertTrue(middle.isValidMove(4, 5));
    }
    
    @Test
    public void testBlackCapture()
    {
        Pawn black = new Pawn(7, 1, ChessPieceColor.BLACK);
        // capture right
        assertTrue(black.canCapture(6, 2));
        // capture off left edge
        assertFalse(black.canCapture(6, 0));
        // can't capture self
        assertFalse(black.canCapture(7, 1));
        // can't capture forward
        assertFalse(black.canCapture(6, 1));
    }
    
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
        
        // let's actually capture something (left)
        assertTrue(white.capture(3, 7));
        // capture right
        assertTrue(white.capture(4, 8));
        // no capture forwards
        assertFalse(white.capture(5, 8));
    }
}
