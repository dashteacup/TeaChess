package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Frog;

/**
 * Tests for the Frog custom chess piece class.
 * Remember, white frogs start at a3 h3, black frogs start at a6 h6.
 */
public class FrogTest {

    @Test
    public void initializeWithValidInputs()
    {
        Frog leftWhiteFrog = new Frog(3, 1, ChessPieceColor.WHITE);
        assertTrue(leftWhiteFrog.hasValidStartingPosition());
        Frog rightWhiteFrog = new Frog(3, 8, ChessPieceColor.WHITE);
        assertTrue(rightWhiteFrog.hasValidStartingPosition());
        Frog leftBlackFrog = new Frog(6, 1, ChessPieceColor.BLACK);
        assertTrue(leftBlackFrog.hasValidStartingPosition());
        Frog rightBlackFrog = new Frog(6, 8, ChessPieceColor.BLACK);
        assertTrue(rightBlackFrog.hasValidStartingPosition());
        assertTrue(rightBlackFrog.isHoppable());
    }
    
    @Test
    public void initializeWithBlackAndWhiteSwapped()
    {
        Frog whiteInLeftBlackSpot = new Frog(6, 1, ChessPieceColor.WHITE);
        assertFalse(whiteInLeftBlackSpot.hasValidStartingPosition());
        Frog blackInRightWhiteSpot = new Frog(3, 8, ChessPieceColor.BLACK);
        assertFalse(blackInRightWhiteSpot.hasValidStartingPosition());
    }
    
    @Test
    public void initializeWithCompletelyWrongLocation()
    {
        Frog wrongColumnWhite = new Frog(3, 6, ChessPieceColor.WHITE);
        assertFalse(wrongColumnWhite.hasValidStartingPosition());
        Frog middleOfBoard = new Frog(4, 4, ChessPieceColor.BLACK);
        assertFalse(middleOfBoard.hasValidStartingPosition());
        Frog wrongRowBlack = new Frog(8, 1, ChessPieceColor.BLACK);
        assertFalse(wrongRowBlack.hasValidStartingPosition());
    }
    
    @Test
    public void checkValidMovesAtLeftWhiteFrogStart()
    {
        Frog white = new Frog(3, 1, ChessPieceColor.WHITE);
        // hop up
        assertTrue(white.isValidMove(5, 1));
        // hop right
        assertTrue(white.isValidMove(3, 3));
        // hop off left edge
        assertFalse(white.isValidMove(3, -1));
        // hop down
        assertTrue(white.isValidMove(1, 1));
        // hop up-right, not allowed
        assertFalse(white.isValidMove(5, 3));
        // hop up too far, fail
        assertFalse(white.isValidMove(6, 1));
        // hop right too far, fail
        assertFalse(white.isValidMove(3, 5));
        // no hopping to your own space
        assertFalse(white.isValidMove(3, 1));
    }

}
