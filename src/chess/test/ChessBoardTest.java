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
     * A chess board with only kings and rooks in their starting positions.
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

    @Test
    public void constructor_WhitePiecesInProperStartingPositions()
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
    }

    @Test
    public void constructor_BlackPiecesInProperStartingPositions()
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
    }

    @Test
    public void constructor_WhitePieceColorsAreWhite()
    {
        assertEquals(WHITE, board.getPiece(1, 5).getColor());
        assertEquals(WHITE, board.getPiece(2, 8).getColor());
    }

    @Test
    public void constructor_BlackPieceColorsAreBlack()
    {
        assertEquals(BLACK, board.getPiece(7, 1).getColor());
        assertEquals(BLACK, board.getPiece(8, 8).getColor());
    }

    @Test
    public void constructor_SpacesInTheMiddleAreNull()
    {
        assertNull(board.getPiece(3, 2));
        assertNull(board.getPiece(4, 4));
        assertNull(board.getPiece(5, 1));
        assertNull(board.getPiece(5, 8));
        assertNull(board.getPiece(6, 7));
    }

    @Test
    public void copyConstructor_CopiesSourceBoard()
    {
        ChessBoard copyBoard = new ChessBoard(board);
        assertTrue(copyBoard.getPiece(b, 1) instanceof Knight);
        assertTrue(copyBoard.getPiece(e, 8) instanceof King);
    }

    @Test
    public void copyConstructor_ChangeInOriginalDoesntAffectCopy()
    {
        ChessBoard copyBoard = new ChessBoard(board);
        // move the original board's knight
        board.move(b, 1, a, 3);
        // knight didn't move in the copy
        assertFalse(copyBoard.getPiece(a, 3) instanceof Knight);
    }

    @Test
    public void copyConstructor_ChangeInCopyDoesntAffectOriginal()
    {
        ChessBoard copyBoard = new ChessBoard(board);
        // move the copy's knight somewhere else
        copyBoard.move(b, 1, c, 3);
        // it doesn't affect the original board
        assertFalse(board.getPiece(c, 3) instanceof Knight);
    }

    @Test
    public void copyConstructor_WhenGivenNullCreateEmptyBoard()
    {
        ChessBoard empty = new ChessBoard(null);
        // white king
        assertNull(empty.getPiece(e, 1));
        // black knight
        assertNull(empty.getPiece(b, 8));
    }

    @Test
    public void getPiece_RowColumn_CorrectPiecesInPlace()
    {
        // white right rook
        assertTrue(board.getPiece(1, 8) instanceof Rook);
        // black king
        assertTrue(board.getPiece(8, 5) instanceof King);
    }

    @Test
    public void getPiece_RowColumn_EmptySpaceIsNull()
    {
        assertNull(board.getPiece(4, 4));
    }

    @Test
    public void getPiece_RowColumn_PiecesHaveTheRightColor()
    {
        assertEquals(BLACK, board.getPiece(7, 4).getColor());
        assertEquals(WHITE, board.getPiece(1, 4).getColor());
    }

    @Test(expected = OffTheChessBoardException.class)
    public void getPiece_RowColumn_ThrowsWhenRowIsZero()
    {
        board.getPiece(0, 5);
    }

    @Test(expected = OffTheChessBoardException.class)
    public void getPiece_RowColumn_ThrowsWhenColumnIsZero()
    {
        board.getPiece(8, 0);
    }

    @Test(expected = OffTheChessBoardException.class)
    public void getPiece_RowColumn_ThrowsWhenColumnIsNine()
    {
        board.getPiece(8, 9);
    }

    @Test
    public void getPiece_FileRank_CorrectPiecesInPlace()
    {
        // white king
        assertTrue(board.getPiece(e, 1) instanceof King);
        // black queen
        assertTrue(board.getPiece(d, 8) instanceof Queen);
    }

    @Test
    public void getPiece_FileRank_EmptySpaceIsNull()
    {
        assertNull(board.getPiece(a, 5));
    }

    @Test
    public void getPiece_FileRank_PiecesHaveTheRightColor()
    {
        // black left knight has right color
        assertEquals(BLACK, board.getPiece(b, 8).getColor());
        // white pawn has right color
        assertEquals(WHITE, board.getPiece(f, 2).getColor());
    }

    @Test(expected = OffTheChessBoardException.class)
    public void getPiece_FileRank_ThrowsWhenRankIsNine()
    {
        board.getPiece(c, 9);
    }

    @Test
    public void addPiece_WithLegalPiece()
    {
        assertTrue(board.addPiece(new Queen(c, 5, BLACK)));
    }

    @Test
    public void addPiece_HasSameReferenceAsValueRetrievedWithGetPiece()
    {
        Rook rook = new Rook(b, 4, WHITE);
        board.addPiece(rook);
        ChessPiece pieceAtLocation = board.getPiece(b, 4);
        // refer to same object?
        assertEquals(rook, pieceAtLocation);
    }

    @Test
    public void addPiece_HasCorrectInternalState()
    {
        board.addPiece(new Rook(b, 4, WHITE));
        ChessPiece piece = board.getPiece(b, 4);
        assertEquals(4, piece.getRow());
        assertEquals(2, piece.getColumn());
        assertEquals(WHITE, piece.getColor());
    }

    @Test
    public void addPiece_NullPieceFails()
    {
        assertFalse(board.addPiece(null));
    }

    @Test
    public void addPiece_OffTheBoardPieceFails()
    {
        Pawn pawn = new Pawn(6, 0, BLACK);
        assertFalse(board.addPiece(pawn));
    }

    @Test
    public void emptySpace_OnEmptySpace()
    {
        assertTrue(board.isEmptySpace(c, 4));
        assertTrue(board.isEmptySpace(6, 8));
    }

    @Test
    public void emptySpace_OccupiedSpaceFails()
    {
        assertFalse(board.isEmptySpace(h, 8));
        assertFalse(board.isEmptySpace(2, 1));
    }

    @Test
    public void isValidMove_EmptySpacesCantMakeMoves()
    {
        assertFalse(board.isValidMove(a, 5, a, 4));
        assertFalse(board.isValidMove(h, 3, a, 6));
    }

    /**
     * Confirm that moves for a black pawn are recognized as valid/invalid.
     */
    @Test
    public void isValidMove_blackPawn()
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
    public void isValidMove_whiteKnight()
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
     * Test pawn movement and capture functionality.
     */
    @Test
    public void isValidMove_capturePawn()
    {
        // white up 2
        board.move(c, 2, c, 4);
        // black down 2
        board.move(d, 7, d, 5);
        // can white capture up-right?
        assertTrue(board.isValidMove(c, 4, d, 5));
        // black capture down-left
        assertTrue(board.isValidMove(d, 5, c, 4));
        board.move(d, 5, c, 4);
        // bad, black capture empty space down-right
        assertFalse(board.isValidMove(c, 4, d, 3));
    }

    /**
     * Ensure that a player can't make a move that would leave his king in
     * check.
     */
    @Test
    public void isValidMove_checkLimitsValidMoves()
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
        assertFalse(board.isValidMove(b, 1, c, 3));
        // king will still be in check so you can't move it up-right 1
        assertFalse(board.isValidMove(e, 1, f, 2));
        // blocks the queen, only valid move
        assertTrue(board.isValidMove(g, 2, g, 3));
    }

    @Test
    public void isValidMove_CastlingIsValid()
    {
        assertTrue(kingsAndRooksBoard.isValidMove(e, 8, c, 8));
    }

    /**
     * Ensure that horizontal moves fail if something blocks their way.
     */
    @Test
    public void isValidMove_hasClearPathHorizontal()
    {
        board.addPiece(new Rook(g, 4, WHITE));
        // put something in the way
        board.addPiece(new Rook(c, 4, BLACK));
        // right rook can't move left over a piece
        assertFalse(board.isValidMove(g, 4, b, 4));
        // left rook can't move right over a piece
        assertFalse(board.isValidMove(c, 4, h, 4));
        // right rook CAN capture left
        assertTrue(board.isValidMove(g, 4, c, 4));
    }

    /**
     * Ensure that vertical moves fail if something blocks their way.
     */
    @Test
    public void isValidMove_hasClearPathVertical()
    {
        board.addPiece(new Queen(e, 3, WHITE));
        // can't move up over a piece
        assertFalse(board.isValidMove(e, 3, e, 8));
        // can capture up
        assertTrue(board.isValidMove(e, 3, e, 7));
    }

    /**
     * Ensure that up-right diagonal moves fail if something blocks their way.
     */
    @Test
    public void isValidMove_hasClearPathDiagonalUpRight()
    {
        board.addPiece(new Bishop(c, 3, WHITE));
        // can't move up-right past a piece
        assertFalse(board.isValidMove(c, 3, h, 8));
        // capture up-right
        assertTrue(board.isValidMove(c, 3, g, 7));
    }

    /**
     * Ensure that up-left moves fail if something blocks their way.
     */
    @Test
    public void isValidMove_hasClearPathDiagonalUpLeft()
    {
        board.addPiece(new Queen(g, 3, WHITE));
        // can't move up-left past a piece
        assertFalse(board.isValidMove(g, 3, b, 8));
        // capture up-left
        assertTrue(board.isValidMove(g, 3, c, 7));
    }

    /**
     * Ensure that down-right moves fail if something blocks their way.
     */
    @Test
    public void isValidMove_hasClearPathDiagonalDownRight()
    {
        board.addPiece(new Queen(a, 6, BLACK));
        // can't move down-right past a piece
        assertFalse(board.isValidMove(a, 6, f, 1));
        // capture down-right
        assertTrue(board.isValidMove(a, 6, e, 2));
    }

    /**
     * Ensure that down-left moves fail if something blocks their way.
     */
    @Test
    public void isValidMove_hasClearPathDiagonalDownLeft()
    {
        board.addPiece(new Bishop(h, 5, BLACK));
        // can't move down-left past a piece
        assertFalse(board.isValidMove(h, 5, d, 1));
        // capture down-left
        assertTrue(board.isValidMove(h, 5, e, 2));
    }

    @Test
    public void isValidMove_NoKingOnTheBoardMakesMovesFail()
    {
        emptyBoard.addPiece(new Queen(a, 3, WHITE));
        assertFalse(emptyBoard.isValidMove(a, 3, a, 6));
    }

    @Test
    public void move_MovingNullPieceDoesNothing()
    {
        board.move(f, 5, f, 8);
        assertTrue(board.getPiece(f, 8) instanceof Bishop);
    }

    /**
     * Confirm that you can use the forceMove method to illegally move a piece
     * to any place on the board.
     */
    @Test
    public void forceMove()
    {
        // try to move King to upper left corner from start position
        assertFalse(board.isValidMove(e, 1, a, 8));
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
    public void forceMove_emptySpace()
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
    public void forceMove_offTheBoard()
    {
        board.forceMove(a, 8, a, 0);
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
    public void canCastle_KingMovingToWrongColumn()
    {
        kingsAndRooksBoard.canCastle(e, 1, b, 1);
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
    public void canCastle_FailsWhenKingIsInCheck()
    {
        kingsAndRooksBoard.addPiece(new Queen(e, 4, BLACK));
        assertFalse(kingsAndRooksBoard.canCastle(e, 1, g, 1));
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

    @Test
    public void castle_blackRightCastle()
    {
        kingsAndRooksBoard.castle(e, 8, g, 8);
        // king has moved
        assertTrue(kingsAndRooksBoard.isEmptySpace(e, 8));
        assertTrue(kingsAndRooksBoard.getPiece(g, 8) instanceof King);
        // rook has moved
        assertTrue(kingsAndRooksBoard.isEmptySpace(h, 8));
        assertTrue(kingsAndRooksBoard.getPiece(f, 8) instanceof Rook);
    }

    @Test
    public void castle_whiteLeftCastle()
    {
        kingsAndRooksBoard.castle(e, 1, c, 1);
        // king has moved
        assertTrue(kingsAndRooksBoard.isEmptySpace(e, 1));
        assertTrue(kingsAndRooksBoard.getPiece(c, 1) instanceof King);
        // rook has moved
        assertTrue(kingsAndRooksBoard.isEmptySpace(a, 1));
        assertTrue(kingsAndRooksBoard.getPiece(d, 1) instanceof Rook);
    }

    @Test
    public void castle_whenCastlingIsIllegal()
    {
        // there are 2 pieces in the way
        board.castle(e, 1, g, 1);
        // pieces haven't moved
        assertTrue(board.getPiece(e, 1) instanceof King);
        assertTrue(board.getPiece(f, 1) instanceof Bishop);
        assertTrue(board.getPiece(g, 1) instanceof Knight);
        assertTrue(board.getPiece(h, 1) instanceof Rook);
    }

    @Test
    public void canPromotePawn_whitePawnOnBlackSide()
    {
        emptyBoard.addPiece(new Pawn(a, 8, WHITE));
        assertTrue(emptyBoard.canPromotePawn(a, 8));
    }

    @Test
    public void canPromotePawn_blackPawnOnWhiteSide()
    {
        emptyBoard.addPiece(new Pawn(h, 1, BLACK));
        assertTrue(emptyBoard.canPromotePawn(h, 1));
    }

    @Test
    public void canPromotePawn_noPawnAtLocation()
    {
        emptyBoard.addPiece(new Queen(c, 8, WHITE));
        assertFalse(emptyBoard.canPromotePawn(c, 8));
    }

    @Test
    public void canPromotePawn_whitePawnAtStart()
    {
        assertFalse(board.canPromotePawn(g, 2));
    }

    @Test
    public void canPromotePawn_blackPawnOnBlackSide()
    {
        emptyBoard.addPiece(new Pawn(b, 8, BLACK));
        assertFalse(emptyBoard.canPromotePawn(b, 8));
    }

    @Test
    public void hasKing_BoardIsEmpty()
    {
        assertFalse(emptyBoard.hasKing(BLACK));
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

    @Test
    public void inCheck_NoKingOnTheBoard()
    {
        assertFalse(emptyBoard.inCheck(BLACK));
    }

    @Test
    public void checkmate_notInCheckmate()
    {
        assertFalse(board.checkmate(WHITE));
        assertFalse(board.checkmate(BLACK));
    }

    @Test
    public void checkmate_blackInCheckmate()
    {
        emptyBoard.addPiece(new King(h, 5, BLACK));
        emptyBoard.addPiece(new Rook(h, 1, WHITE));
        emptyBoard.addPiece(new King(f, 5, WHITE));

        assertTrue(emptyBoard.checkmate(BLACK));
    }

    /**
     * Recognize when white is caught in the fool's mate checkmate.
     */
    @Test
    public void checkmate_whiteInFoolsMate()
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
    }

    /**
     * Recognize when black is caught in a stalemate. It should have no possible
     * moves and NOT be in check.
     */
    @Test
    public void stalemate_blackInStalemate()
    {
        emptyBoard.addPiece(new King(h, 8, BLACK));
        emptyBoard.addPiece(new Queen(g, 6, WHITE));
        emptyBoard.addPiece(new King(f, 7, WHITE));

        // stalemate = no valid moves but not in check
        assertTrue(emptyBoard.stalemate(BLACK));
        assertFalse(emptyBoard.inCheck(BLACK));
        assertFalse(emptyBoard.checkmate(BLACK));
    }

    @Test
    public void stalemate_notInStalemate()
    {
        assertFalse(board.stalemate(WHITE));
        assertFalse(board.stalemate(BLACK));
    }

    @Test
    public void stalemate_FailWhenInCheckmate()
    {
        emptyBoard.addPiece(new King(h, 5, BLACK));
        emptyBoard.addPiece(new Rook(h, 1, WHITE));
        emptyBoard.addPiece(new King(f, 5, WHITE));
        assertFalse(emptyBoard.stalemate(BLACK));
    }
}
