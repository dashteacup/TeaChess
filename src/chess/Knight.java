package chess;

/**
 * Represents the Knight chess piece.
 */
public class Knight extends ChessPiece {
    /**
     * Create a new Knight chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 2 or 7
     * @param color BLACK or WHITE
     */
    public Knight(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
        hoppable = true;
    }

    /**
     * Create a new Knight chess piece in algebraic chess notation.
     * @param file letter for the column in algebraic chess notation (a-h)
     * @param rank number indicating the row in algebraic chess notation (1-8)
     * @param color BLACK or WHITE
     */
    public Knight(File file, int rank, ChessPieceColor color)
    {
        super(file, rank, color);
        hoppable = true;
    }

    /**
     * Determine if the Knight is in a valid starting position.
     */
    @Override
    public boolean inValidStartingPosition()
    {
        boolean isCorrectColumn = (column == 2) || (column == 7);
        if ((color == ChessPieceColor.WHITE) && (row == 1))
            return isCorrectColumn;
        if ((color == ChessPieceColor.BLACK) && (row == 8))
            return isCorrectColumn;
        return false;

    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Knights. Doesn't account for other pieces. Remember, knights can
     * hop over other pieces.
     */
    @Override
    public boolean isValidMove(int row, int column)
    {
        if (!super.isValidMove(row, column))
            return false;
        int deltaRow = Math.abs(this.row - row);
        int deltaColumn = Math.abs(this.column - column);
        if ((deltaRow == 2) && (deltaColumn == 1))
            return true;
        if ((deltaRow == 1) && (deltaColumn == 2))
            return true;
        return false;
    }
}
