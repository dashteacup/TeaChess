package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static chess.File.*;

import org.junit.Test;

import chess.ChessPieceColor;
import chess.Rook;

/**
 * Tests for the {@link Rook} class
 */
public class RookTest {

    /**
     * Confirm that Rooks initialized in their proper starting points have the
     * right properties.
     */
    @Test
    public void initializeWithValidInitialPositions()
    {
        Rook lwhite = new Rook(1, 1, ChessPieceColor.WHITE);
        assertTrue(lwhite.inStartingPosition());
        assertEquals(a, lwhite.getFile());
        assertEquals(1, lwhite.getRank());
        assertEquals(ChessPieceColor.WHITE, lwhite.getColor());

        Rook rwhite = new Rook(1, 8, ChessPieceColor.WHITE);
        assertTrue(rwhite.inStartingPosition());
        assertEquals(h, rwhite.getFile());
        assertEquals(1, rwhite.getRank());
        assertEquals(ChessPieceColor.WHITE, rwhite.getColor());

        Rook lblack = new Rook(8, 1, ChessPieceColor.BLACK);
        assertTrue(lblack.inStartingPosition());
        assertEquals(8, lblack.getRow());
        assertEquals(1, lblack.getColumn());
        assertEquals(ChessPieceColor.BLACK, lblack.getColor());

        Rook rblack = new Rook(8, 8, ChessPieceColor.BLACK);
        assertTrue(rblack.inStartingPosition());
        assertEquals(8, rblack.getRow());
        assertEquals(8, rblack.getColumn());
        assertEquals(ChessPieceColor.BLACK, rblack.getColor());
    }

    /**
     * Confirm that Rooks placed randomly on the board are recognized as outside
     * their starting positions.
     */
    @Test
    public void initializeInWrongPosition()
    {
        // not on back row
        Rook bad = new Rook(3, 3, ChessPieceColor.WHITE);
        assertFalse(bad.inStartingPosition());
        // off the board
        Rook invalid = new Rook(0, 1, ChessPieceColor.WHITE);
        assertFalse(invalid.inStartingPosition());
        // 1 row off
        Rook wrong = new Rook(7, 8, ChessPieceColor.BLACK);
        assertFalse(wrong.inStartingPosition());
        // 1 column off
        Rook offColumn = new Rook(g, 1, ChessPieceColor.WHITE);
        assertFalse(offColumn.inStartingPosition());
    }

    /**
     * Confirm that Rooks with the wrong color, but the right place, are not in
     * valid starting positions.
     */
    @Test
    public void initializeWithWrongColors()
    {
        // black on white side
        Rook black11 = new Rook(1, 1, ChessPieceColor.BLACK);
        assertFalse(black11.inStartingPosition());
        Rook black18 = new Rook(1, 8, ChessPieceColor.BLACK);
        assertFalse(black18.inStartingPosition());
        // white on black side
        Rook white81 = new Rook(8, 1, ChessPieceColor.WHITE);
        assertFalse(white81.inStartingPosition());
        Rook white88 = new Rook(8, 8, ChessPieceColor.WHITE);
        assertFalse(white88.inStartingPosition());
    }

    @Test
    public void inStartingPosition_MovingToStartFromSomewhereElseFails()
    {
        Rook piece = new Rook(a, 4, ChessPieceColor.WHITE);
        piece.move(a, 1);
        assertFalse(piece.inStartingPosition());
    }

    @Test
    public void inStartingPosition_BlackRookOnWrongColumnFails()
    {
        Rook piece = new Rook(d, 8, ChessPieceColor.BLACK);
        assertFalse(piece.inStartingPosition());
    }

    /**
     * Confirm that valid moves are recognized as such.
     */
    @Test
    public void checkValidMovesFromCenter()
    {
        Rook good = new Rook(c, 5, ChessPieceColor.BLACK);
        // all the way up
        assertTrue(good.isValidMove(c, 8));
        // all the way right
        assertTrue(good.isValidMove(h, 5));
        // all the way down
        assertTrue(good.isValidMove(c, 1));
        // all the way left
        assertTrue(good.isValidMove(a, 5));

        // up 2
        assertTrue(good.isValidMove(c, 7));
        // right 4
        assertTrue(good.isValidMove(g, 5));
        // down 3
        assertTrue(good.isValidMove(c, 2));
        // left 1
        assertTrue(good.isValidMove(b, 5));
    }

    /**
     * Confirm that wrong moves are recognized as such.
     */
    @Test
    public void checkWrongMovesFromCenter()
    {
        Rook wrong = new Rook(c, 5, ChessPieceColor.BLACK);
        // up off the board
        assertFalse(wrong.isValidMove(c, 9));
        // right off the board
        assertFalse(wrong.isValidMove(5, 9));
        // down off the board
        assertFalse(wrong.isValidMove(c, 0));
        // left off the board
        assertFalse(wrong.isValidMove(5, 0));
        // down-right like bishop
        assertFalse(wrong.isValidMove(f, 2));
        // up-right like knight
        assertFalse(wrong.isValidMove(d, 7));
        // jump to upper left corner
        assertFalse(wrong.isValidMove(a, 8));
    }
}
