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
        for (int row = 0; row < boardSize; ++row)
            board.add(new ArrayList<ChessPiece>());
        // make columns, ignore the 0th row
        for (int row = 1; row < boardSize; ++row) {
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
                for (int col = 1; col < boardSize; ++col)
                    board.get(row).add(new Pawn(row, col, ChessPieceColor.WHITE));
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                for (int col = 1; col < boardSize; ++col)
                    board.get(row).add(null);
                break;
            case 7:
                for (int col = 1; col < boardSize; ++col)
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
     */
    public boolean isValidMove(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        ChessPiece piece = getPiece(oldRow, oldColumn);
        // moving to an empty space
        if (emptySpace(newRow, newColumn)) {
            if (!piece.isValidMove(newRow, newColumn))
                return false;
        // the space is occupied
        } else {
            if (!piece.canCapture(newRow, newColumn))
                return false;
        }
        // if the piece can hop, then intervening pieces don't matter
        if (piece.isHoppable()) {
            return true;
        // otherwise, make sure there's nothing between the two positions
        } else {
            return hasClearPath(oldRow, oldColumn, newRow, newColumn);
        }
    }

    /**
     * Force a move. Doesn't do error checking or account for ChessPiece type.
     */
    public void forceMove(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        ChessPiece movingPiece = getPiece(oldRow, oldColumn);
        board.get(newRow).set(newColumn, movingPiece);
        board.get(oldRow).set(oldColumn, null);
    }

    /**
     * Get the chess piece at the given location.
     */
    public ChessPiece getPiece(int row, int column)
    {
        if (!( (1 <= row && row <= 8) && (1 <= column && column <= 8) )) {
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
        int deltaRow = Math.abs(oldRow - newRow);
        int deltaColumn = Math.abs(oldColumn - newColumn);
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
        } else if (deltaRow == deltaColumn) {
            // beneath target
            if (oldRow < newRow) {
                // heading up-right
                if (oldColumn < newColumn) {
                    for (int row = oldRow + 1, col = oldColumn + 1;
                         row < newRow && col < newColumn;
                         row++, col++) {
                        if (!emptySpace(row, col))
                            return false;
                    }
                    return true;
                // heading up-left
                } else {
                    for (int row = oldRow + 1, col = oldColumn - 1;
                         row < newRow && col > newColumn;
                         ++row, --col) {
                        if (!emptySpace(row, col))
                            return false;
                    }
                    return true;
                }
            // above target
            } else {
                // heading down-right
                if (oldColumn < newColumn) {
                    for (int row = oldRow - 1, col = oldColumn + 1;
                         row > newRow && col < newColumn;
                         --row, ++col) {
                        if (!emptySpace(row, col))
                            return false;
                    }
                    return true;
                // heading down-left
                } else {
                    for (int row = oldRow - 1, col = oldColumn - 1;
                         row > newRow && col > newColumn;
                         --row, --col) {
                        if (!emptySpace(row, col))
                            return false;
                    }
                    return true;
                }
            }
        }
        // All non-hoppable pieces move either vertically, horizontally, or diagonally
        return false;
    }

    /**
     * Set to nine so I can index from 1-8 normally. The [0] element
     * will always be empty.
     */
    private static final int boardSize = 9;

    /**
     * The board held in a 2 dimensional array;
     */
    private ArrayList<ArrayList<ChessPiece>> board;
}
