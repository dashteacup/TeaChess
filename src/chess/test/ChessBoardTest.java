package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import chess.Bishop;
import chess.ChessBoard;
import chess.ChessPieceColor;
import chess.King;
import chess.Knight;
import chess.Pawn;
import chess.Queen;
import chess.Rook;

/**
 * Tests for the ChessBoard class.
 * TODO: I need to actually finish writing tests here.
 */
public class ChessBoardTest {
    /**
     * Confirm that the ChessBoard has the proper size and shape.
     */
    @Test
    public void initializeFullBoardAndCheckBounds()
    {
        ChessBoard b = new ChessBoard();
        // number of rows
        assertEquals(9, b.getBoard().size());
        // number of columns
        assertEquals(9, b.getBoard().get(1).size());
        assertEquals(9, b.getBoard().get(8).size());
        // check zero row, zero row has no columns
        assertEquals(0, b.getBoard().get(0).size());
    }

    /**
     * Ensure elements that should be null are null.
     */
    @Test
    public void initializeAndCheckForNull()
    {
        ChessBoard b = new ChessBoard();
        // check zero column
        assertNull(b.getBoard().get(1).get(0));
        assertNull(b.getBoard().get(8).get(0));
        // check middle
        assertNull(b.getBoard().get(3).get(2));
        assertNull(b.getBoard().get(4).get(4));
        assertNull(b.getBoard().get(5).get(1));
        assertNull(b.getBoard().get(5).get(8));
        assertNull(b.getBoard().get(6).get(7));
    }

    /**
     * Confirm that all the white pieces are in their proper starting position.
     */
    @Test
    public void intitializeFullBoardAndCheckWhiteSide()
    {
        ChessBoard b = new ChessBoard();

        // check white side
        assertTrue(b.getBoard().get(1).get(1) instanceof Rook);
        assertTrue(b.getBoard().get(1).get(2) instanceof Knight);
        assertTrue(b.getBoard().get(1).get(3) instanceof Bishop);
        assertTrue(b.getBoard().get(1).get(4) instanceof Queen);
        assertTrue(b.getBoard().get(1).get(5) instanceof King);
        assertTrue(b.getBoard().get(1).get(6) instanceof Bishop);
        assertTrue(b.getBoard().get(1).get(7) instanceof Knight);
        assertTrue(b.getBoard().get(1).get(8) instanceof Rook);
        assertTrue(b.getBoard().get(2).get(1) instanceof Pawn);
        assertTrue(b.getBoard().get(2).get(5) instanceof Pawn);
        assertTrue(b.getBoard().get(2).get(8) instanceof Pawn);

        // check colors
        assertEquals(ChessPieceColor.WHITE, b.getBoard().get(1).get(5).getColor());
        assertEquals(ChessPieceColor.WHITE, b.getBoard().get(2).get(8).getColor());
    }

    /**
     * Confirm that all the black pieces are in their proper starting position.
     */
    @Test
    public void initializeFullBoardAndCheckBlackSide()
    {
        ChessBoard b = new ChessBoard();

        // check black side
        assertTrue(b.getBoard().get(8).get(1) instanceof Rook);
        assertTrue(b.getBoard().get(8).get(2) instanceof Knight);
        assertTrue(b.getBoard().get(8).get(3) instanceof Bishop);
        assertTrue(b.getBoard().get(8).get(4) instanceof Queen);
        assertTrue(b.getBoard().get(8).get(5) instanceof King);
        assertTrue(b.getBoard().get(8).get(6) instanceof Bishop);
        assertTrue(b.getBoard().get(8).get(7) instanceof Knight);
        assertTrue(b.getBoard().get(8).get(8) instanceof Rook);
        assertTrue(b.getBoard().get(7).get(1) instanceof Pawn);
        assertTrue(b.getBoard().get(7).get(5) instanceof Pawn);
        assertTrue(b.getBoard().get(7).get(8) instanceof Pawn);

        // check colors
        assertEquals(ChessPieceColor.BLACK, b.getBoard().get(7).get(1).getColor());
        assertEquals(ChessPieceColor.BLACK, b.getBoard().get(8).get(8).getColor());
    }

}
