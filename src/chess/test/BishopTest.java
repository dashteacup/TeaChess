package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.Bishop;
import chess.ChessPieceColor;

/**
 * Tests for the Bishop class.
 */
public class BishopTest {
    
    @Test
    public void initializeWithValidInputs()
    {
        Bishop whiteLeft = new Bishop(1, 3, ChessPieceColor.WHITE);
        assertTrue(whiteLeft.inValidStartingPosition());
        Bishop whiteRight = new Bishop(1, 6, ChessPieceColor.WHITE);
        assertTrue(whiteRight.inValidStartingPosition());
        Bishop blackLeft = new Bishop(8, 3, ChessPieceColor.BLACK);
        assertTrue(blackLeft.inValidStartingPosition());
        Bishop blackRight = new Bishop(8, 6, ChessPieceColor.BLACK);
        assertTrue(blackRight.inValidStartingPosition());
    }
    
    @Test
    public void initializeWithWhiteAndBlackBishopSwapped()
    {
        Bishop whiteInBlackSpot = new Bishop(8, 3, ChessPieceColor.WHITE);
        assertFalse(whiteInBlackSpot.inValidStartingPosition());
        Bishop blackInWhiteSpot = new Bishop(1, 6, ChessPieceColor.BLACK);
        assertFalse(blackInWhiteSpot.inValidStartingPosition());
    }
    
    @Test
    public void initializeWithRightRowWrongColumn()
    {
        Bishop wrongColumnWhite = new Bishop(1, 1, ChessPieceColor.WHITE);
        assertFalse(wrongColumnWhite.inValidStartingPosition());
        Bishop wrongColumnBlack = new Bishop(8, 8, ChessPieceColor.BLACK);
        assertFalse(wrongColumnBlack.inValidStartingPosition());
    }
    
    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Bishop offTheBoard = new Bishop(4, 9, ChessPieceColor.WHITE);
        assertFalse(offTheBoard.inValidStartingPosition());
        Bishop middleOfTheBoard = new Bishop(6, 7, ChessPieceColor.BLACK);
        assertFalse(middleOfTheBoard.inValidStartingPosition());
    }

    @Test
    public void checkForValidMovesAtLeftWhiteBishopStart()
    {
        Bishop leftWhite = new Bishop(1, 3, ChessPieceColor.WHITE);
        // Go up-right one
        assertTrue(leftWhite.isValidMove(2, 4));
        // Go up-right all the way
        assertTrue(leftWhite.isValidMove(6, 8));
        // Go up-left all the way
        assertTrue(leftWhite.isValidMove(3, 1));
        // Go off the edge
        assertFalse(leftWhite.isValidMove(-1, 5));
        // Move to self
        assertFalse(leftWhite.isValidMove(1, 3));
    }
    
    @Test
    public void checkForValidMovesInMiddleOfBoard()
    {
        Bishop middle = new Bishop(5, 4, ChessPieceColor.BLACK);
        // go up-left all the way
        assertTrue(middle.isValidMove(8, 1));
        // go up-right all the way
        assertTrue(middle.isValidMove(8, 7));
        // go down-left all the way
        assertTrue(middle.isValidMove(2, 1));
        // go down-right all the way
        assertTrue(middle.isValidMove(1, 8));
        // go up-right too far
        assertFalse(middle.isValidMove(9, 8));
    }
    
    @Test
    public void checkCompletelyWrongMovement()
    {
        Bishop wrong = new Bishop(3, 3, ChessPieceColor.BLACK);
        // move like rook right
        assertFalse(wrong.isValidMove(3, 6));
        // move like rook up
        assertFalse(wrong.isValidMove(6, 3));
        // move like pawn down
        assertFalse(wrong.isValidMove(2, 3));
        // totally random
        assertFalse(wrong.isValidMove(4, 8));
    }
}
