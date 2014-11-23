package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import chess.Bishop;
import chess.ChessBoard;
import chess.ChessPieceColor;
import chess.File;
import chess.King;
import chess.Knight;
import chess.OffTheChessBoardException;
import chess.Pawn;
import chess.Queen;
import chess.Rook;

/**
 * Tests for the ChessBoard class.
 * TODO: I need to actually finish writing tests here.
 */
public class ChessBoardTest {

    /**
     * A chess board object to test.
     */
    private ChessBoard board;

    /**
     * Create a new chess board object before every test.
     */
    @Before
    public void runBeforeTests()
    {
        board = new ChessBoard();
    }

    /**
     * Confirm that the getter in row/column notation works properly with valid
     * input.
     */
    @Test
    public void getPieceRowColumnWithValidInput()
    {
        // white right rook
        assertTrue(board.getPiece(1, 8) instanceof Rook);
        // white left bishop
        assertTrue(board.getPiece(1, 3) instanceof Bishop);
        // black left rook
        assertTrue(board.getPiece(8, 1) instanceof Rook);
        // black king
        assertTrue(board.getPiece(8, 5) instanceof King);
        // empty space
        assertNull(board.getPiece(4, 4));
        // black pawn has right color
        assertEquals(ChessPieceColor.BLACK, board.getPiece(7, 4).getColor());
        // white queen has right color
        assertEquals(ChessPieceColor.WHITE, board.getPiece(1, 4).getColor());
    }

    /**
     * Confirm that the getter throws an exception when given a row of zero.
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceRowColumnWithRowZero()
    {
        board.getPiece(0, 5);
    }

    /**
     * Confirm that the getter throws an exception when given a column of 9.
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceRowColumnWithColumnNine()
    {
        board.getPiece(8, 9);
    }

    /**
     * Confirm that the getter in file/rank notation works properly with valid
     * input.
     */
    @Test
    public void getPieceFileRankValidInput()
    {
        // white left knight
        assertTrue(board.getPiece(File.b, 1) instanceof Knight);
        // white king
        assertTrue(board.getPiece(File.e, 1) instanceof King);
        // white right pawn
        assertTrue(board.getPiece(File.h, 2) instanceof Pawn);
        // black queen
        assertTrue(board.getPiece(File.d, 8) instanceof Queen);
        // black right rook
        assertTrue(board.getPiece(File.h, 8) instanceof Rook);
        // empty space
        assertNull(board.getPiece(File.a, 5));
        // black left knight has right color
        assertEquals(ChessPieceColor.BLACK, board.getPiece(File.b, 8).getColor());
        // white pawn has right color
        assertEquals(ChessPieceColor.WHITE, board.getPiece(File.f, 2).getColor());
    }

    /**
     * Confirm the getter throws an exception when given a rank of 0
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceFileRankWithRankZero()
    {
        board.getPiece(File.c, 0);
    }

    /**
     * Confirm that all the white pieces are in their proper starting position.
     */
    @Test
    public void initializeAndCheckWhiteSide()
    {
        // check white side
        assertTrue(board.getPiece(1, 1) instanceof Rook);
        assertTrue(board.getPiece(1, 2) instanceof Knight);
        assertTrue(board.getPiece(1, 3) instanceof Bishop);
        assertTrue(board.getPiece(1, 4) instanceof Queen);
        assertTrue(board.getPiece(1, 5) instanceof King);
        assertTrue(board.getPiece(1, 6) instanceof Bishop);
        assertTrue(board.getPiece(1, 7) instanceof Knight);
        assertTrue(board.getPiece(1, 8) instanceof Rook);
        assertTrue(board.getPiece(2, 1) instanceof Pawn);
        assertTrue(board.getPiece(2, 5) instanceof Pawn);
        assertTrue(board.getPiece(2, 8) instanceof Pawn);

        // check colors
        assertEquals(ChessPieceColor.WHITE, board.getPiece(1, 5).getColor());
        assertEquals(ChessPieceColor.WHITE, board.getPiece(2, 8).getColor());
    }

    /**
     * Confirm that all the black pieces are in their proper starting position.
     */
    @Test
    public void initializeAndCheckBlackSide()
    {
        // check black side
        assertTrue(board.getPiece(8, 1) instanceof Rook);
        assertTrue(board.getPiece(8, 2) instanceof Knight);
        assertTrue(board.getPiece(8, 3) instanceof Bishop);
        assertTrue(board.getPiece(8, 4) instanceof Queen);
        assertTrue(board.getPiece(8, 5) instanceof King);
        assertTrue(board.getPiece(8, 6) instanceof Bishop);
        assertTrue(board.getPiece(8, 7) instanceof Knight);
        assertTrue(board.getPiece(8, 8) instanceof Rook);
        assertTrue(board.getPiece(7, 1) instanceof Pawn);
        assertTrue(board.getPiece(7, 5) instanceof Pawn);
        assertTrue(board.getPiece(7, 8) instanceof Pawn);

        // check colors
        assertEquals(ChessPieceColor.BLACK, board.getPiece(7, 1).getColor());
        assertEquals(ChessPieceColor.BLACK, board.getPiece(8, 8).getColor());
    }

    /**
     * Ensure elements that should be null are null.
     */
    @Test
    public void initializeAndCheckForNull()
    {
        // check middle
        assertNull(board.getPiece(3, 2));
        assertNull(board.getPiece(4, 4));
        assertNull(board.getPiece(5, 1));
        assertNull(board.getPiece(5, 8));
        assertNull(board.getPiece(6, 7));
    }

}
