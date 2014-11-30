package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static chess.File.*;

import org.junit.Before;
import org.junit.Test;

import chess.Bishop;
import chess.ChessBoard;
import chess.ChessPieceColor;
import chess.King;
import chess.Knight;
import chess.OffTheChessBoardException;
import chess.Pawn;
import chess.Queen;
import chess.Rook;

/**
 * Tests for the ChessBoard class.
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
    public void getPieceRowColumnWithBadRow()
    {
        board.getPiece(0, 5);
    }

    /**
     * Confirm that the getter throws an exception when given a column of zero.
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceRowColumnWithBadColumn()
    {
        board.getPiece(8, 0);
    }


    /**
     * Confirm that the getter throws an exception when given a column of 9.
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceRowColumnWithBadColumn9()
    {
        board.getPiece(8, 9);
    }

    /**
     * Confirm that the getter in file/rank notation works properly with valid
     * input.
     */
    @Test
    public void getPieceFileRankWithValidInput()
    {
        // white left knight
        assertTrue(board.getPiece(b, 1) instanceof Knight);
        // white king
        assertTrue(board.getPiece(e, 1) instanceof King);
        // white right pawn
        assertTrue(board.getPiece(h, 2) instanceof Pawn);
        // black queen
        assertTrue(board.getPiece(d, 8) instanceof Queen);
        // black right rook
        assertTrue(board.getPiece(h, 8) instanceof Rook);
        // empty space
        assertNull(board.getPiece(a, 5));
        // black left knight has right color
        assertEquals(ChessPieceColor.BLACK, board.getPiece(b, 8).getColor());
        // white pawn has right color
        assertEquals(ChessPieceColor.WHITE, board.getPiece(f, 2).getColor());
    }

    /**
     * Confirm the getter throws an exception when given a rank of 9
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceFileRankWithBadRank()
    {
        board.getPiece(c, 9);
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
     * Ensure that elements in the middle are null.
     */
    @Test
    public void initializeAndCheckMiddle()
    {
        assertNull(board.getPiece(3, 2));
        assertNull(board.getPiece(4, 4));
        assertNull(board.getPiece(5, 1));
        assertNull(board.getPiece(5, 8));
        assertNull(board.getPiece(6, 7));
    }

    /**
     * Confirm that empty/occupied spaces are recognized properly.
     */
    @Test
    public void emptySpace()
    {
        // empty
        assertTrue(board.emptySpace(c, 4));
        assertTrue(board.emptySpace(6, 8));
        // occupied
        assertFalse(board.emptySpace(h, 8));
        assertFalse(board.emptySpace(2, 1));
    }

    /**
     * Confirm that empty spaces can't make moves.
     */
    @Test
    public void isValidMoveEmptySpace()
    {
        assertFalse(board.isValidMove(a, 5, a, 4));
        assertFalse(board.isValidMove(h, 3, a, 6));
    }

    /**
     * Confirm that moves for a black pawn are recognized as valid/invalid.
     */
    @Test
    public void isValidMoveBlackPawn()
    {
        // 1 forward
        assertTrue(board.isValidMove(c, 7, c, 6));
        // 2 forward
        assertTrue(board.isValidMove(c, 7, c, 5));
        // bad, 3 forward
        assertFalse(board.isValidMove(c, 7, c, 4));
        // bad, 1 back
        assertFalse(board.isValidMove(c, 7, c, 8));
        // bad, capture without a piece in new spot
        assertFalse(board.isValidMove(c, 7, d, 6));
        // bad, random place on board
        assertFalse(board.isValidMove(c, 7, f, 3));
    }

    /**
     * Confirm that moves for a white Knight are recognized as valid/invalid.
     */
    @Test
    public void isValidMoveWhiteKnight()
    {
        // up 2, left 1
        assertTrue(board.isValidMove(g, 1, f, 3));
        // up 2, right 1
        assertTrue(board.isValidMove(g, 1, h, 3));
        // bad, capture friendly
        assertFalse(board.isValidMove(g, 1, e, 2));
        // bad, random spot on board
        assertFalse(board.isValidMove(g, 1, d, 4));
    }
}
