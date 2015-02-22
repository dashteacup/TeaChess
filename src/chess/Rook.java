package chess;

/**
 * Represents the Rook chess piece. Since castling requires information about
 * the state of the board, it is implemented in {@link ChessBoard}.
 */
public class Rook extends ChessPiece {
    /**
     * Create a new Rook chess piece
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 1 or 8 NOT a or h
     * @param color black or white
     */
    public Rook(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }

    /**
     * Create a new Rook chess piece
     * @param file letter in algebraic chess notation
     * @param rank number indicating the row in algebraic chess notation
     * @param color BLACK or WHITE
     */
    public Rook(File file, int rank, ChessPieceColor color)
    {
        super(file, rank, color);
    }

    /**
     * Determine if the Rook is in a valid starting position.
     */
    @Override
    public boolean atValidStartingPosition()
    {
        if (!isOnTheBoard(getRow(), getColumn()))
            return false;
        boolean onWhiteSide = (getRow() == 1 && getColumn() == 1) ||
                              (getRow() == 1 && getColumn() == 8);
        boolean onBlackSide = (getRow() == 8 && getColumn() == 1) ||
                              (getRow() == 8 && getColumn() == 8);
        return (onWhiteSide && (getColor() == ChessPieceColor.WHITE)) ||
               (onBlackSide && (getColor() == ChessPieceColor.BLACK));
    }

    /**
     * Determine if a move follows the standard movement pattern for Rooks.
     * Doesn't account for the presence of other pieces, so it doesn't support
     * castling.
     */
    @Override
    public boolean isValidMove(int newRow, int newColumn)
    {
        if (isOffTheBoardOrToSelf(newRow, newColumn))
            return false;
        boolean rowMatch = (newRow == getRow());
        boolean columnMatch = (newColumn == getColumn());
        return (rowMatch || columnMatch);
    }
}
