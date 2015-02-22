package chess;

/**
 * Represents the Bishop chess piece.
 */
public class Bishop extends ChessPiece {
    /**
     * Create a new Bishop chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 3 or 6
     * @param color BLACK or WHITE
     */
    public Bishop(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }

    /**
     * Create a new Bishop chess piece in algebraic chess notation.
     * @param file letter for the column in algebraic chess notation (a-h)
     * @param rank number indicating the row in algebraic chess notation (1-8)
     * @param color BLACK or WHITE
     */
    public Bishop(File file, int rank, ChessPieceColor color)
    {
        super(file, rank, color);
    }

    /**
     * Determine if the Bishop is in a valid starting position.
     */
    @Override
    public boolean atValidStartingPosition()
    {
        if ((getColor() == ChessPieceColor.WHITE) && (getRow() == 1)) {
            return (getColumn() == 3) || (getColumn() == 6);
        }
        else if ((getColor() == ChessPieceColor.BLACK) && (getRow() == 8)) {
            return (getColumn() == 3) || (getColumn() == 6);
        }
        return false;
    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Bishops. Doesn't account for other pieces.
     */
    @Override
    public boolean isValidMove(int row, int column)
    {
        if (isOffTheBoardOrToSelf(row, column))
            return false;
        int deltaRow = Math.abs(getRow() - row);
        int deltaColumn = Math.abs(getColumn() - column);
        return (deltaRow == deltaColumn);
    }
}
