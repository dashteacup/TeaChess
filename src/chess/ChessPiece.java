package chess;

/**
 * Represents a generic chess piece.
 */
public abstract class ChessPiece implements Cloneable {

    /**
     * Equivalent to chess term 'rank'. Valid values are 1-8.
     * Rows begin numbering from bottom to top.
     */
    private int row;

    /**
     * Equivalent to chess term 'file'. Valid values are 1-8.
     * Note: I didn't use the letters typically used in chess notation.
     * Columns begin numbering from left to right.
     */
    private int column;

    /**
     * Color of this particular chess piece.
     */
    private ChessPieceColor color;

    /**
     * Flag indicating whether or not the piece has moved. This will be set to
     * true after the piece uses the move method. Knowing whether or not a piece
     * has moved is useful for castling, which requires that both the King and
     * Rook haven't moved.
     */
    private boolean hasMoved = false;

    /**
     * Create a new chess piece
     * @param row aka 'rank' valid 1-8
     * @param column aka 'file' valid 1-8 NOT a-h
     * @param color BLACK or WHITE
     */
    public ChessPiece(int row, int column, ChessPieceColor color)
    {
        this.row = row;
        this.column = column;
        this.color = color;
    }

    /**
     * Create a new chess piece in algebraic chess notation.
     * @param file letter for the column in algebraic chess notation (a-h)
     * @param rank number for the row in algebraic chess notation (1-8)
     * @param color BLACK or WHITE
     */
    public ChessPiece(File file, int rank, ChessPieceColor color)
    {
        this(rank, file.getColumn(), color);
    }

    /**
     * Chess pieces only have primitives for fields, so shallow copies via clone
     * work fine.
     */
    @Override
    public ChessPiece clone() throws CloneNotSupportedException
    {
        return (ChessPiece) super.clone();
    }

    /**
     * Determine if a position is on the board and not the same as the chess
     * piece's current position. Subclasses should call the superclass's
     * isValidMove to check for those conditions. Does not take into account the
     * placement of other pieces.
     * @param newRow row you want to move to (1-8)
     * @param newColumn column you want move to (1-8)
     * @return true if this is a valid move for this chess piece
     */
    public boolean isValidMove(int newRow, int newColumn)
    {
        return bothPlacesOnTheBoard(newRow, newColumn) &&
               (!sourceAndDestinationSame(newRow, newColumn));
    }

    /**
     * Determine if the chess piece can move to a new position on the
     * board. The positions are specified in algebraic chess notation. Does not
     * take into account the placement of other pieces.
     * @param file letter for the column you want to move to (a-h)
     * @param rank number for the row you want to move to (1-8)
     * @return true if this is a valid move for this chess piece
     */
    public boolean isValidMove(File file, int rank)
    {
        return isValidMove(rank, file.getColumn());
    }

    /**
     * Move the chess piece to a new place on the board. If the new place is not
     * a valid way to move, then do not move the piece.
     * @return true if the piece moves to the new place, false otherwise.
     */
    public boolean move(int newRow, int newColumn)
    {
        if (isValidMove(newRow, newColumn)) {
            forceMove(newRow, newColumn);
            return true;
        }
        return false;
    }

    /**
     * Move the chess piece to a new place on the board. If the new place is not
     * a valid way to move, then do not move the piece.
     * @return true if the piece moves to the new place, false otherwise.
     */
    public boolean move(File file, int rank)
    {
        return move(rank, file.getColumn());
    }

    /**
     * Move the chess piece to a new place on the board without confirming that
     * it's a valid move.
     * @param row to move to (1-8)
     * @param column to move to (1-8)
     */
    public void forceMove(int row, int column)
    {
        if (!isOnTheBoard(row, column))
            throw new OffTheChessBoardException(row, column);
        hasMoved = true;
        this.row = row;
        this.column = column;
    }

    /**
     * Determine if the chess piece has moved earlier in the game.
     * @return true if the piece has moved before, false otherwise
     */
    public boolean hasMoved()
    {
        return hasMoved;
    }

    /**
     * Determine if the piece is in a valid starting position.
     * @return true if the piece is where it could be at the beginning of a
     * chess game
     */
    public abstract boolean atValidStartingPosition();

    /**
     * Determine if the chess piece can capture a piece at another place on the
     * board. Doesn't actually check to see if there is a chess piece at the
     * indicated location.
     * @param enemyRow row of the piece to capture
     * @param enemyColumn column of the piece to capture
     * @return true if the piece can capture an enemy at the indicated location,
     * false otherwise.
     */
    public boolean canCapture(int enemyRow, int enemyColumn)
    {
        // Most pieces capture in the same way that they move.
        return isValidMove(enemyRow, enemyColumn);
    }

    /**
     * Determine if the chess piece can capture a piece at another place on the
     * board. Doesn't actually check to see if there is a chess piece at the
     * indicated location. The positions are specified in algebraic chess notation.
     * @param enemyFile letter for the column you want to move to (a-h)
     * @param enemyRank number for the row you want to move to (1-8)
     * @return true if the piece can capture an enemy at the indicated location,
     * false otherwise.
     */
    public boolean canCapture(File enemyFile, int enemyRank)
    {
        return canCapture(enemyRank, enemyFile.getColumn());
    }

    /**
     * "Capture" an enemy piece at the enemy row and column. Doesn't actually
     * handle the destruction of a piece and it's removal from the board--only
     * moves this piece to the other's place. Also doesn't check to see if there
     * is actually a chess piece at the indicated position. If this is not a
     * valid way to capture, the piece will not be moved.
     * @param enemyRow row of the piece to capture
     * @param enemyColumn column of the piece to capture
     * @return true if the capture succeeds, false if it cannot be made.
     */
    public boolean capture(int enemyRow, int enemyColumn)
    {
        if (canCapture(enemyRow, enemyColumn)) {
            this.row = enemyRow;
            this.column = enemyColumn;
            hasMoved = true;
            return true;
        }
        return false;
    }

    /**
     * "Capture" an enemy piece at the enemy file and rank. Doesn't actually
     * handle the destruction of a piece and it's removal from the board--only
     * moves this piece to the other's place. Also doesn't check to see if there
     * is actually a chess piece at the indicated position. If this is not a
     * valid way to capture, the piece will not be moved.
     * @param enemyFile file of the piece to capture
     * @param enemyRank rank of the piece to capture
     * @return true if the capture succeeds, false if it cannot be made.
     */
    public boolean capture(File enemyFile, int enemyRank)
    {
        return capture(enemyRank, enemyFile.getColumn());
    }

    /**
     * Get the chess piece's row aka 'rank' on the board.
     * Rows begin numbering from bottom to top.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Get the chess piece's column aka 'file' on the board.
     * Using 1-8 instead of traditional chess notation a-h.
     * Columns begin numbering from left to right.
     */
    public int getColumn()
    {
        return column;
    }

    /**
     * @return the chess piece's rank, a number from 1-8
     */
    public int getRank()
    {
        return getRow();
    }

    /**
     * @return the chess piece's file, a letter from a-h
     */
    public File getFile()
    {
        return File.getFile(getColumn());
    }

    /**
     * Get the chess piece's color.
     */
    public ChessPieceColor getColor()
    {
        return color;
    }

    /**
     * Determine if the chess piece can hop over other chess pieces like a Knight.
     * Generic chess pieces are not hoppable.
     */
    public boolean isHoppable()
    {
        return false;
    }

    /**
     * Determine if the position at row and column lies within the board.
     * Assumes an 8x8 chess board.
     */
    protected boolean isOnTheBoard(int row, int column)
    {
        return ChessBoard.isOnTheBoard(row, column);
    }

    /**
     * Determine if the piece's current position and it's desired
     * destination both reside within the chess board.
     */
    protected boolean bothPlacesOnTheBoard(int newRow, int newColumn)
    {
        return isOnTheBoard(newRow, newColumn) &&
               isOnTheBoard(getRow(), getColumn());
    }

    /**
     * Determine if the piece's destination and source are the same.
     * Chess pieces can't make no-op moves.
     */
    protected boolean sourceAndDestinationSame(int newRow, int newColumn)
    {
        return (getRow() == newRow) && (getColumn() == newColumn);
    }
};
