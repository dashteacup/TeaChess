package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Knight;

/**
 * Tests for the Knight class.
 */
public class KnightTest {

    @Test
    public void initializeWithValidInputs()
    {
        Knight whiteLeft = new Knight(1, 2, ChessPieceColor.WHITE);
        assertTrue(whiteLeft.hasValidStartingPosition());
        Knight whiteRight = new Knight(1, 7, ChessPieceColor.WHITE);
        assertTrue(whiteRight.hasValidStartingPosition());
        Knight blackLeft = new Knight(8, 2, ChessPieceColor.BLACK);
        assertTrue(blackLeft.hasValidStartingPosition());
        Knight blackRight = new Knight(8, 7, ChessPieceColor.BLACK);
        assertTrue(blackRight.hasValidStartingPosition());
        assertTrue(blackRight.isHoppable());
    }
    
    @Test
    public void initializeWithBlackAndWhiteSwapped()
    {
        Knight whiteInBlackRightSpot = new Knight(8, 7, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackRightSpot.hasValidStartingPosition());
        Knight blackInWhiteLeftSpot = new Knight(1, 2, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteLeftSpot.hasValidStartingPosition());
    }
    
    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Knight whiteInWrongColumn = new Knight(1, 5, ChessPieceColor.WHITE);
        assertFalse(whiteInWrongColumn.hasValidStartingPosition());
        Knight offTheBoard = new Knight(1, 9, ChessPieceColor.WHITE);
        assertFalse(offTheBoard.hasValidStartingPosition());
        Knight inTheMiddle = new Knight(4, 4, ChessPieceColor.BLACK);
        assertFalse(inTheMiddle.hasValidStartingPosition());
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
