package chess.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static chess.File.*;
import static chess.ChessPieceColor.WHITE;
import static chess.ChessPieceColor.BLACK;

import org.junit.Before;
import org.junit.Test;

import chess.Bishop;
import chess.ChessBoard;
import chess.ChessPiece;
import chess.King;
import chess.Knight;
import chess.OffTheChessBoardException;
import chess.Pawn;
import chess.Queen;
import chess.Rook;

/**
 * Tests for the {@link ChessBoard} class.
 */
public class ChessBoardTest {

    /**
     * A normal chess board object with all chess pieces in their starting positions.
     */
    private ChessBoard board;

    /**
     * A chess board with no pieces.
     */
    private ChessBoard emptyBoard;

    /**
     * A chess board with only kings and rooks.
     */
    private ChessBoard kingsAndRooksBoard;

    /**
     * Create a new chess board object before every test.
     */
    @Before
    public void runBeforeTests()
    {
        board = new ChessBoard();
        emptyBoard = new ChessBoard(null);

        kingsAndRooksBoard = new ChessBoard(null);
        // White side
        kingsAndRooksBoard.addPiece(new King(e, 1, WHITE));
        kingsAndRooksBoard.addPiece(new Rook(a, 1, WHITE));
        kingsAndRooksBoard.addPiece(new Rook(h, 1, WHITE));
        // Black side
        kingsAndRooksBoard.addPiece(new King(e, 8, BLACK));
        kingsAndRooksBoard.addPiece(new Rook(a, 8, BLACK));
        kingsAndRooksBoard.addPiece(new Rook(h, 8, BLACK));
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
        assertEquals(WHITE, board.getPiece(1, 5).getColor());
        assertEquals(WHITE, board.getPiece(2, 8).getColor());
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
        assertEquals(BLACK, board.getPiece(7, 1).getColor());
        assertEquals(BLACK, board.getPiece(8, 8).getColor());
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
     * Ensure the copy constructor creates a correct deep copy of the
     * ChessBoard.
     */
    @Test
    public void copyConstructor()
    {
        ChessBoard copyBoard = new ChessBoard(board);
        // copy has a knight in the right starting place
        assertTrue(copyBoard.getPiece(b, 1) instanceof Knight);
        // move the original board's knight
        board.move(b, 1, a, 3);
        assertTrue(board.getPiece(a, 3) instanceof Knight);
        assertNull(board.getPiece(b, 1));
        // knight didn't move in the copy
        assertFalse(copyBoard.getPiece(a, 3) instanceof Knight);
        // move the copy's knight somewhere else
        copyBoard.move(b, 1, c, 3);
        assertTrue(copyBoard.getPiece(c, 3) instanceof Knight);
        // it doesn't affect the original board
        assertFalse(board.getPiece(c, 3) instanceof Knight);
    }

    /**
     * Ensure that the copy constructor creates an empty board when given null
     * as a parameter.
     */
    @Test
    public void copyConstructorEmpty()
    {
        // white king
        assertNull(emptyBoard.getPiece(e, 1));
        // black king
        assertNull(emptyBoard.getPiece(e, 8));
        // white pawn
        assertNull(emptyBoard.getPiece(h, 2));
        // black knight
        assertNull(emptyBoard.getPiece(b, 8));
        // check everything
        for (int row = 1; row <= 8; row++)
            for (int col = 1; col <= 8; col++)
                assertNull(emptyBoard.getPiece(row, col));
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
        assertEquals(BLACK, board.getPiece(7, 4).getColor());
        // white queen has right color
        assertEquals(WHITE, board.getPiece(1, 4).getColor());
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
        assertEquals(BLACK, board.getPiece(b, 8).getColor());
        // white pawn has right color
        assertEquals(WHITE, board.getPiece(f, 2).getColor());
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
     * Confirm that a ChessPiece successfully added to the board has the right
     * properties.
     */
    @Test
    public void addPiece()
    {
        Rook rook = new Rook(b, 4, WHITE);
        assertTrue(board.addPiece(rook));
        ChessPiece pieceAtLocation = board.getPiece(b, 4);
        // refer to same object?
        assertEquals(rook, pieceAtLocation);
        // check internal state
        assertEquals(4, pieceAtLocation.getRow());
        assertEquals(2, pieceAtLocation.getColumn());
        assertEquals(WHITE, pieceAtLocation.getColor());
    }

    /**
     * Ensure that a null piece cannot be added.
     */
    @Test
    public void addPieceNull()
    {
        assertFalse(board.addPiece(null));
    }

    /**
     * Ensure that pieces with locations off the board cannot be added.
     */
    @Test
    public void addPieceOffTheBoard()
    {
        Pawn pawn = new Pawn(6, 0, BLACK);
        assertFalse(board.addPiece(pawn));
    }

    /**
     * Confirm that empty/occupied spaces are recognized properly.
     */
    @Test
    public void emptySpace()
    {
        // empty
        assertTrue(board.isEmptySpace(c, 4));
        assertTrue(board.isEmptySpace(6, 8));
        // occupied
        assertFalse(board.isEmptySpace(h, 8));
        assertFalse(board.isEmptySpace(2, 1));
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

    /**
     * Return true when white makes a legal kingside (right) castle.
     */
    @Test
    public void canCastle_legalWhiteRightCastle()
    {
        // castling is a valid move
        assertTrue(kingsAndRooksBoard.canCastle(e, 1, g, 1));
    }

    /**
     * Return true when black makes a legal queenside (left) castle.
     */
    @Test
    public void canCastle_legalBlackLeftCastle()
    {
        assertTrue(kingsAndRooksBoard.canCastle(e, 8, c, 8));
    }

    @Test
    public void canCastle_kingHasAlreadyMoved()
    {
        // move up and then back down
        kingsAndRooksBoard.move(e, 1, e, 2);
        kingsAndRooksBoard.move(e, 2, e, 1);
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, c, 1));
    }

    @Test
    public void canCastle_noKingAtGivenLocation()
    {
        // move king off start
        kingsAndRooksBoard.move(e, 1, e, 2);
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, c, 1));
    }

    @Test
    public void canCastle_noRookAtGivenLocation()
    {
        // move rook off start
        kingsAndRooksBoard.move(h, 8, h, 7);
        assertFalse(kingsAndRooksBoard.canCastle(e, 8, g, 8));
    }

    @Test
    public void canCastle_wrongChessPieceAtRookStart()
    {
        // replace right white rook with queen
        kingsAndRooksBoard.addPiece(new Queen(h, 1, WHITE));
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, g, 1));
    }

    @Test
    public void canCastle_rookHasAlreadyMoved()
    {
        // move rook up and back
        kingsAndRooksBoard.move(h, 1, h, 4);
        kingsAndRooksBoard.move(h, 4, h, 1);
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, g, 1));
    }

    @Test
    public void canCastle_wrongColorRook()
    {
        kingsAndRooksBoard.addPiece(new Rook(h, 1, BLACK));
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, g, 1));
    }

    @Test
    public void canCastle_piecesInTheWay()
    {
        assertFalse(board.canCastle(e, 1, g, 1));
    }

    @Test
    public void canCastle_firstStepPutsKingInCheck()
    {
        kingsAndRooksBoard.addPiece(new Queen(d, 4, BLACK));
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, c, 1));
    }

    @Test
    public void canCastle_secondStepPutsKingInCheck()
    {
        kingsAndRooksBoard.addPiece(new Queen(c, 4, BLACK));
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, c, 1));
    }

    /**
     * Test pawn movement and capture functionality.
     */
    @Test
    public void movePawn()
    {
        // white up 2
        assertTrue(board.move(c, 2, c, 4));
        // black down 2
        assertTrue(board.move(d, 7, d, 5));
        // can white capture up-right?
        assertTrue(board.isValidMove(c, 4, d, 5));
        // black capture down-left
        assertTrue(board.move(d, 5, c, 4));
        // bad, black capture empty space down-right
        assertFalse(board.move(c, 4, d, 3));
    }

    /**
     * Run a simulated partial chess game only making valid moves.
     */
    @Test
    public void moveMultiplePieces()
    {
        // white pawn up 2
        assertTrue(board.move(h, 2, h, 4));
        // black knight down 2 left 1
        assertTrue(board.move(g, 8, f, 6));
        // white rook up 2
        assertTrue(board.move(h, 1, h, 3));
        // black pawn down 1
        assertTrue(board.move(g, 7, g, 6));
        // white rook left 3
        assertTrue(board.move(h, 3, e, 3));
        // black bishop down-right 2
        assertTrue(board.move(f, 8, h, 6));
        // white pawn up 2
        assertTrue(board.move(d, 2, d, 4));
        // black bishop capture rook
        assertTrue(board.move(h, 6, e, 3));
        // white queen up 2
        assertTrue(board.move(d, 1, d, 3));
        // black king right
        assertTrue(board.move(e, 8, f, 8));
        // white queen capture bishop
        assertTrue(board.move(d, 3, e, 3));
        // black knight down 2 right 1
        assertTrue(board.move(f, 6, g, 4));
        // white queen capture pawn
        assertTrue(board.move(e, 3, e, 7));
        // black king capture queen
        assertTrue(board.move(f, 8, e, 7));
    }

    /**
     * Confirm that you can use the forceMove method to illegally move a piece
     * to any place on the board.
     */
    @Test
    public void forceMove()
    {
        // try to move King to upper left corner from start position
        assertFalse(board.move(e, 1, a, 8));
        assertTrue(board.getPiece(e, 1) instanceof King);
        assertTrue(board.getPiece(a, 8) instanceof Rook);
        // now force move to upper left corner
        board.forceMove(e, 1, a, 8);
        assertTrue(board.isEmptySpace(e, 1));
        assertTrue(board.getPiece(a, 8) instanceof King);
    }

    /**
     * Confirm that when you attempt to force move a blank space, nothing
     * happens.
     */
    @Test
    public void forceMoveEmptySpace()
    {
        // try to move a blank space to the black king
        board.forceMove(h, 4, e, 8);
        // black king is still there
        ChessPiece blackKing = board.getPiece(e, 8);
        assertTrue(blackKing instanceof King);
        assertEquals(BLACK, blackKing.getColor());
    }

    /**
     * Confirm that attempting to force move a chess piece off the board throws
     * an exception.
     */
    @Test(expected = OffTheChessBoardException.class)
    public void forceMoveOffTheBoard()
    {
        board.forceMove(a, 8, a, 0);
    }

    /**
     * Confirm that the board can recognize when a player is in or out of check.
     */
    @Test
    public void inCheck()
    {
        // nothing should be in check at game start
        assertFalse(board.inCheck(WHITE));
        assertFalse(board.inCheck(BLACK));
        // move white pawn before queen up 1
        board.move(d, 2, d, 3);
        // move black pawn before king down 1
        board.move(e, 7, e, 6);
        // move white pawn before king up 1
        board.move(e, 2, e, 3);
        // move black bishop to put the white king in check
        board.move(f, 8, b, 4);
        assertTrue(board.inCheck(WHITE));
        // black king is still not in check
        assertFalse(board.inCheck(BLACK));
        // block with queen, making the white king safe
        board.move(d, 1, d, 2);
        assertFalse(board.inCheck(WHITE));
        // move black king down 1
        board.move(e, 8, e, 7);
        // move white queen to put the black king in check
        board.move(d, 2, b, 4);
        assertTrue(board.inCheck(BLACK));
        // move black king to safety
        board.move(e, 7, f, 6);
        assertFalse(board.inCheck(BLACK));
    }

    /**
     * Ensure that a player can't make a move that would leave his king in
     * check.
     */
    @Test
    public void checkLimitsValidMoves()
    {
        // move white pawn up 1
        board.move(f, 2, f, 3);
        // move black pawn down 1
        board.move(e, 7, e, 6);
        // move white pawn up 1
        board.move(h, 2, h, 3);
        // move black queen to put white king in check
        board.move(d, 8, h, 4);
        // king will still be in check, so you can't move the knight
        assertFalse(board.move(b, 1, c, 3));
        // king will still be in check so you can't move it up-right 1
        assertFalse(board.move(e, 1, f, 2));
        // blocks the queen, only valid move
        assertTrue(board.move(g, 2, g, 3));
    }

    /**
     * Ensure that horizontal moves fail if something blocks their way.
     */
    @Test
    public void hasClearPathHorizontal()
    {
        Rook rightRook = new Rook(g, 4, WHITE);
        assertTrue(board.addPiece(rightRook));
        // rook left all the way
        assertTrue(board.isValidMove(g, 4, a, 4));
        // put something in the way
        Rook leftRook = new Rook(c, 4, BLACK);
        assertTrue(board.addPiece(leftRook));
        // right rook can't move left over a piece
        assertFalse(board.move(g, 4, b, 4));
        // left rook can't move right over a piece
        assertFalse(board.move(c, 4, h, 4));
        // right rook CAN capture left
        assertTrue(board.move(g, 4, c, 4));
    }

    /**
     * The initial board layout should not be a checkmate or stalemate.
     */
    @Test
    public void hasValidMoves()
    {
        assertFalse(board.checkmate(WHITE));
        assertFalse(board.stalemate(WHITE));
        assertFalse(board.checkmate(BLACK));
        assertFalse(board.stalemate(BLACK));
    }

    /**
     * Recognize when black is caught in checkmate.
     */
    @Test
    public void checkmate()
    {
        emptyBoard.addPiece(new King(h, 5, BLACK));
        emptyBoard.addPiece(new Rook(h, 1, WHITE));
        emptyBoard.addPiece(new King(f, 5, WHITE));

        assertTrue(emptyBoard.checkmate(BLACK));
        assertFalse(emptyBoard.stalemate(BLACK));
    }

    /**
     * Recognize when white is caught in the fool's mate checkmate.
     */
    @Test
    public void checkmateFoolsMate()
    {
        // white pawn up 1
        board.move(f, 2, f, 3);
        // black pawn down 2
        board.move(e, 7, e, 5);
        // white pawn up 2
        board.move(g, 2, g, 4);
        // black king to checkmate
        board.move(d, 8, h, 4);

        assertTrue(board.checkmate(WHITE));
        assertFalse(board.stalemate(WHITE));
    }

    /**
     * Recognize when black is caught in a stalemate. It should have no possible
     * moves and NOT be in check.
     */
    @Test
    public void stalemate()
    {
        emptyBoard.addPiece(new King(h, 8, BLACK));
        emptyBoard.addPiece(new Queen(g, 6, WHITE));
        emptyBoard.addPiece(new King(f, 7, WHITE));

        // stalemate = no valid moves but not in check
        assertTrue(emptyBoard.stalemate(BLACK));
        assertFalse(emptyBoard.inCheck(BLACK));
        assertFalse(emptyBoard.checkmate(BLACK));
    }

    /**
     * Ensure that vertical moves fail if something blocks their way.
     */
    @Test
    public void hasClearPathVertical()
    {
        Queen queen = new Queen(e, 3, WHITE);
        assertTrue(board.addPiece(queen));
        // can't move up over a piece
        assertFalse(board.move(e, 3, e, 8));
        // can capture up
        assertTrue(board.move(e, 3, e, 7));
    }

    /**
     * Ensure that up-right diagonal moves fail if something blocks their way.
     */
    @Test
    public void hasClearPathDiagonalUpRight()
    {
        Bishop bishop = new Bishop(c, 3, WHITE);
        assertTrue(board.addPiece(bishop));
        // can't move up-right past a piece
        assertFalse(board.move(c, 3, h, 8));
        // capture up-right
        assertTrue(board.move(c, 3, g, 7));
    }

    /**
     * Ensure that up-left moves fail if something blocks their way.
     */
    @Test
    public void hasClearPathDiagonalUpLeft()
    {
        Queen queen = new Queen(g, 3, WHITE);
        assertTrue(board.addPiece(queen));
        // can't move up-left past a piece
        assertFalse(board.move(g, 3, b, 8));
        // capture up-left
        assertTrue(board.move(g, 3, c, 7));
    }

    /**
     * Ensure that down-right moves fail if something blocks their way.
     */
    @Test
    public void hasClearPathDiagonalDownRight()
    {
        Queen queen = new Queen(a, 6, BLACK);
        assertTrue(board.addPiece(queen));
        // can't move down-right past a piece
        assertFalse(board.move(a, 6, f, 1));
        // capture down-right
        assertTrue(board.move(a, 6, e, 2));
    }

    /**
     * Ensure that down-left moves fail if something blocks their way.
     */
    @Test
    public void hasClearPathDiagonalDownLeft()
    {
        Bishop bishop = new Bishop(h, 5, BLACK);
        assertTrue(board.addPiece(bishop));
        // can't move down-left past a piece
        assertFalse(board.move(h, 5, d, 1));
        // capture down-left
        assertTrue(board.move(h, 5, e, 2));
    }
}
