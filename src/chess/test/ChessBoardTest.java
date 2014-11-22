package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
     * Confirm that the ChessBoard has the proper size and shape.
     */
    @Test
    public void initializeFullBoardAndCheckBounds()
    {
        ChessBoard board = new ChessBoard();
        // number of rows
        assertEquals(9, board.getBoard().size());
        // number of columns
        assertEquals(9, board.getBoard().get(1).size());
        assertEquals(9, board.getBoard().get(8).size());
        // check zero row, zero row has no columns
        assertEquals(0, board.getBoard().get(0).size());
    }

    /**
     * Ensure elements that should be null are null.
     */
    @Test
    public void initializeAndCheckForNull()
    {
        ChessBoard board = new ChessBoard();
        // check zero column
        assertNull(board.getBoard().get(1).get(0));
        assertNull(board.getBoard().get(8).get(0));
        // check middle
        assertNull(board.getBoard().get(3).get(2));
        assertNull(board.getBoard().get(4).get(4));
        assertNull(board.getBoard().get(5).get(1));
        assertNull(board.getBoard().get(5).get(8));
        assertNull(board.getBoard().get(6).get(7));
    }

    /**
     * Confirm that all the white pieces are in their proper starting position.
     */
    @Test
    public void intitializeFullBoardAndCheckWhiteSide()
    {
        ChessBoard board = new ChessBoard();

        // check white side
        assertTrue(board.getBoard().get(1).get(1) instanceof Rook);
        assertTrue(board.getBoard().get(1).get(2) instanceof Knight);
        assertTrue(board.getBoard().get(1).get(3) instanceof Bishop);
        assertTrue(board.getBoard().get(1).get(4) instanceof Queen);
        assertTrue(board.getBoard().get(1).get(5) instanceof King);
        assertTrue(board.getBoard().get(1).get(6) instanceof Bishop);
        assertTrue(board.getBoard().get(1).get(7) instanceof Knight);
        assertTrue(board.getBoard().get(1).get(8) instanceof Rook);
        assertTrue(board.getBoard().get(2).get(1) instanceof Pawn);
        assertTrue(board.getBoard().get(2).get(5) instanceof Pawn);
        assertTrue(board.getBoard().get(2).get(8) instanceof Pawn);

        // check colors
        assertEquals(ChessPieceColor.WHITE, board.getBoard().get(1).get(5).getColor());
        assertEquals(ChessPieceColor.WHITE, board.getBoard().get(2).get(8).getColor());
    }

    /**
     * Confirm that all the black pieces are in their proper starting position.
     */
    @Test
    public void initializeFullBoardAndCheckBlackSide()
    {
        ChessBoard board = new ChessBoard();

        // check black side
        assertTrue(board.getBoard().get(8).get(1) instanceof Rook);
        assertTrue(board.getBoard().get(8).get(2) instanceof Knight);
        assertTrue(board.getBoard().get(8).get(3) instanceof Bishop);
        assertTrue(board.getBoard().get(8).get(4) instanceof Queen);
        assertTrue(board.getBoard().get(8).get(5) instanceof King);
        assertTrue(board.getBoard().get(8).get(6) instanceof Bishop);
        assertTrue(board.getBoard().get(8).get(7) instanceof Knight);
        assertTrue(board.getBoard().get(8).get(8) instanceof Rook);
        assertTrue(board.getBoard().get(7).get(1) instanceof Pawn);
        assertTrue(board.getBoard().get(7).get(5) instanceof Pawn);
        assertTrue(board.getBoard().get(7).get(8) instanceof Pawn);

        // check colors
        assertEquals(ChessPieceColor.BLACK, board.getBoard().get(7).get(1).getColor());
        assertEquals(ChessPieceColor.BLACK, board.getBoard().get(8).get(8).getColor());
    }

    /**
     * Confirm that the getter in row/column notation works properly with valid
     * input.
     */
    @Test
    public void getPieceRowColumnWithValidInput()
    {
        ChessBoard board = new ChessBoard();
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
        ChessBoard board = new ChessBoard();
        board.getPiece(0, 5);
    }

    /**
     * Confirm that the getter throws an exception when given a column of 9.
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceRowColumnWithColumnNine()
    {
        ChessBoard board = new ChessBoard();
        board.getPiece(8, 9);
    }

    /**
     * Confirm that the getter in file/rank notation works properly with valid
     * input.
     */
    @Test
    public void getPieceFileRankValidInput()
    {
        ChessBoard board = new ChessBoard();
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
     * Confirm the getter throws an exceptino when given a rank of 0
     */
    @Test(expected = OffTheChessBoardException.class)
    public void getPieceFileRankWithRankZero()
    {
        ChessBoard board = new ChessBoard();
        board.getPiece(File.c, 0);
    }

}
