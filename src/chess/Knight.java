package chess;

/**
 * Represents the Knight chess piece.
 */
public class Knight extends ChessPiece {
    /**
     * Create a new Knight chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 2 or 7
     * @param color black or white
     */
    public Knight(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
        hoppable = true;
    }
    
    /**
     * Determine if the Knight is in a valid starting position.
     */
    public boolean hasValidStartingPosition()
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
    public boolean isValidMove(int row, int column)
    {
        if (!bothPlacesOnTheBoard(row, column))
            return false;
        if (sourceAndDestinationSame(row, column))
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
