package chess;

import java.util.ArrayList;

/**
 * Represents the chess board itself.
 */
public class ChessBoard {
    /**
     * Build new standard sized chess board.
     * Currently, empty elements are set to null, I think I need to do
     * something better than that.
     */
    public ChessBoard()
    {
        board = new ArrayList<ArrayList<ChessPiece>>();
        // make rows
        for (int row = 0; row < BOARD_SIZE; ++row)
            board.add(new ArrayList<ChessPiece>());
        // make columns, ignore the 0th row
        for (int row = 1; row < BOARD_SIZE; ++row) {
            // Want to index the board from 1 like in algebraic chess notation.
            // 0th column on every row is null.
            board.get(row).add(null);
            switch(row) {
            case 1:
                board.get(row).add(new Rook  (row,  1, ChessPieceColor.WHITE));
                board.get(row).add(new Knight(row,  2, ChessPieceColor.WHITE));
                board.get(row).add(new Bishop(row,  3, ChessPieceColor.WHITE));
                board.get(row).add(new Queen (row,  4, ChessPieceColor.WHITE));
                board.get(row).add(new King  (row,  5, ChessPieceColor.WHITE));
                board.get(row).add(new Bishop(row,  6, ChessPieceColor.WHITE));
                board.get(row).add(new Knight(row,  7, ChessPieceColor.WHITE));
                board.get(row).add(new Rook  (row,  8, ChessPieceColor.WHITE));
                break;
            case 2:
                for (int col = 1; col < BOARD_SIZE; ++col)
                    board.get(row).add(new Pawn(row, col, ChessPieceColor.WHITE));
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                for (int col = 1; col < BOARD_SIZE; ++col)
                    board.get(row).add(null);
                break;
            case 7:
                for (int col = 1; col < BOARD_SIZE; ++col)
                    board.get(row).add(new Pawn(row, col, ChessPieceColor.BLACK));
                break;
            case 8:
                board.get(row).add(new Rook  (row, 1, ChessPieceColor.BLACK));
                board.get(row).add(new Knight(row, 2, ChessPieceColor.BLACK));
                board.get(row).add(new Bishop(row, 3, ChessPieceColor.BLACK));
                board.get(row).add(new Queen (row, 4, ChessPieceColor.BLACK));
                board.get(row).add(new King  (row, 5, ChessPieceColor.BLACK));
                board.get(row).add(new Bishop(row, 6, ChessPieceColor.BLACK));
                board.get(row).add(new Knight(row, 7, ChessPieceColor.BLACK));
                board.get(row).add(new Rook  (row, 8, ChessPieceColor.BLACK));
                break;
            }
        }
    }

    /**
     * Determine if the indicated move is a valid chess move.
     * @param oldRow of the piece to move (1-8)
     * @param oldColumn of the piece to move (1-8)
     * @param newRow to move to (1-8)
     * @param newColumn to move to (1-8)
     * @return true if it's a valid move, false otherwise
     */
    public boolean isValidMove(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        // There must be a piece to move
        if (emptySpace(oldRow, oldColumn))
            return false;
        ChessPiece piece = getPiece(oldRow, oldColumn);
        // moving to an empty space
        if (emptySpace(newRow, newColumn)) {
            if (!piece.isValidMove(newRow, newColumn))
                return false;
        // the space is occupied
        } else {
            if ( (!piece.canCapture(newRow, newColumn))
                 || // can't capture your own color
                 (piece.getColor() == getPiece(newRow, newColumn).getColor()) ) {
                return false;
            }
        }
        // if the piece can hop, then intervening pieces don't matter
        if (piece.isHoppable())
            return true;
        // otherwise, make sure there's nothing between the two positions
        return hasClearPath(oldRow, oldColumn, newRow, newColumn);
    }

    /**
     * Determine if the indicated move in algebraic chess notation is a valid
     * chess move.
     * @param oldFile of the piece to move (a-h)
     * @param oldRank of the piece to move (1-8)
     * @param newFile to move to (a-h)
     * @param newRank to move to (1-8)
     * @return true if it's a valid move, false otherwise
     */
    public boolean isValidMove(File oldFile, int oldRank, File newFile, int newRank)
    {
        return isValidMove(oldRank, oldFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Move a ChessPiece from one position to another in row/column notation.
     * @param oldRow of the piece to move (1-8)
     * @param oldColumn of the piece to move (1-8)
     * @param newRow to move to (1-8)
     * @param newColumn to move to (1-8)
     * @return true if the move succeeds, false otherwise
     */
    public boolean move(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        if (isValidMove(oldRow, oldColumn, newRow, newColumn)) {
            ChessPiece movingPiece = getPiece(oldRow, oldColumn);
            if (movingPiece.canCapture(newRow, newColumn))
                movingPiece.capture(newRow, newColumn);
            else
                movingPiece.move(newRow, newColumn);
            board.get(newRow).set(newColumn, movingPiece);
            board.get(oldRow).set(oldColumn, null);
            return true;
        }
        return false;
    }

    /**
     * Move a ChessPiece from one position to another in algebraic chess notation.
     * @param oldFile of the piece to move (a-h)
     * @param oldRank of the piece to move (1-8)
     * @param newFile to move to (a-h)
     * @param newRank to move to (1-8)
     * @return true if the move succeeds, false otherwise
     */
    public boolean move(File oldFile, int oldRank, File newFile, int newRank)
    {
        return move(oldRank, oldFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Get the chess piece at the given location.
     */
    public ChessPiece getPiece(int row, int column)
    {
        if (!isOnTheBoard(row, column)) {
            throw new OffTheChessBoardException("row: " + row +  " column: " + column);
        }
        return board.get(row).get(column);
    }

    /**
     * Get the chess piece at the location given in algebraic chess notation.
     */
    public ChessPiece getPiece(File file, int rank)
    {
        return getPiece(rank, file.getColumn());
    }


    /**
     * Add a ChessPiece to the board. If a ChessPiece already exists at the new
     * ChessPiece's location, it will be replaced with the new piece.
     * @param piece to add to the board
     * @return true if the piece is successfully added, false otherwise
     */
    public boolean addPiece(ChessPiece piece)
    {
        if (piece == null)
            return false;
        int row = piece.getRow();
        int column = piece.getColumn();
        if (!isOnTheBoard(row, column))
            return false;
        board.get(row).set(column, piece);
        return true;
    }

    /**
     * Determine if there is a ChessPiece at the given location.
     * @param row (1-8)
     * @param column (1-8)
     * @return true if the space is empty, false otherwise
     */
    public boolean emptySpace(int row, int column)
    {
        return getPiece(row, column) == null;
    }

    /**
     * Determine if there is a ChessPiece at the given location.
     * @param file in algebraic chess notation
     * @param rank in algebraic chess notation (1-8)
     * @return true if the space is empty, false otherwise
     */
    public boolean emptySpace(File file, int rank)
    {
        return emptySpace(rank, file.getColumn());
    }

    /**
     * Determine if there is a clear (unobstructed) path between the two positions on the
     * chess board. Do not use this function on hoppable chess pieces.
     */
    private boolean hasClearPath(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        int deltaRow = newRow - oldRow;
        int deltaColumn = newColumn - oldColumn;
        // horizontal movement
        if (deltaRow == 0 && deltaColumn != 0) {
            int lower = Math.min(oldColumn, newColumn);
            int upper = Math.max(oldColumn, newColumn);
            // check every space between the two columns
            for (int column = lower + 1; column < upper; ++column) {
                if (!emptySpace(oldRow, column))
                    return false;
            }
            return true;
        // vertical movement
        } else if (deltaRow != 0 && deltaColumn == 0) {
            int lower = Math.min(oldRow, newRow);
            int upper = Math.max(oldRow, newRow);
            // check every space between the two rows
            for (int row = lower + 1; row < upper; ++row) {
                if (!emptySpace(row, oldColumn))
                    return false;
            }
            return true;
        // diagonal movement
        } else if (Math.abs(deltaRow) == Math.abs(deltaColumn)) {
            int spacesApart = Math.abs(deltaRow);
            // up-right
            if (deltaRow > 0 && deltaColumn > 0) {
                for (int offset = 1; offset < spacesApart; ++offset) {
                    if (!emptySpace(oldRow + offset, oldColumn + offset))
                        return false;
                }
            // up-left
            } else if (deltaRow > 0 && deltaColumn < 0) {
                for (int offset = 1; offset < spacesApart; ++offset) {
                    if (!emptySpace(oldRow + offset, oldColumn - offset))
                        return false;
                }
            // down-right
            } else if (deltaRow < 0 && deltaColumn > 0) {
                for (int offset = 1; offset < spacesApart; ++offset) {
                    if (!emptySpace(oldRow - offset, oldColumn + offset))
                        return false;
                }
            // down-left
            } else { // (deltaRow < 0 && deltaColumn < 0)
                for (int offset = 1; offset < spacesApart; ++offset) {
                    if (!emptySpace(oldRow - offset, oldColumn - offset))
                        return false;
                }
            }
            return true; // found no occupied spaces across the diagonal
        }
        // All non-hoppable pieces move either vertically, horizontally, or diagonally.
        return false; // This line should never actually execute.
    }

    /**
     * Determine if a given position is on the ChessBoard.
     * @param row to inspect (1-8 is valid)
     * @param column to inspect (1-8 is valid)
     * @return true if the position is on the board, false otherwise.
     */
    private boolean isOnTheBoard(int row, int column)
    {
        boolean validRow = (1 <= row && row <= 8);
        boolean validColumn = (1 <= column && column <= 8);
        return validRow && validColumn;
    }

    /**
     * Set to nine so I can index from 1-8 normally. The [0] element
     * will always be empty.
     */
    private static final int BOARD_SIZE = 9;

    /**
     * The board held in a 2 dimensional array;
     */
    private ArrayList<ArrayList<ChessPiece>> board;
}
