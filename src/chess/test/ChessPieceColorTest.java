package chess.test;

import static org.junit.Assert.assertEquals;

import static chess.ChessPieceColor.BLACK;
import static chess.ChessPieceColor.NONE;
import static chess.ChessPieceColor.WHITE;

import org.junit.Test;

import chess.ChessPieceColor;

/**
 * Tests for the {@link ChessPieceColor} enum.
 */
public class ChessPieceColorTest {

    /**
     * Ensure that the otherColor method returns the right values in all cases.
     */
    @Test
    public void otherColor()
    {
        assertEquals(WHITE, BLACK.otherColor());
        assertEquals(BLACK, WHITE.otherColor());
        assertEquals(NONE, NONE.otherColor());
    }

}
