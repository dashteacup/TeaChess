package chess;

/**
 * Represents the Prince chess piece.
 * Princes are a custom chess piece that moves a single space in any
 * direction, like a king. Unlike a king, princes are expendable.
 * Their valid starting position is (in algebraic chess notation) 
 * e3 for white, and e6 for black.
 */
public class Prince extends ChessPiece {
    /**
     * Create a new Prince chess piece.
     * @param row aka 'rank' valid 3 or 6
     * @param column aka 'file' valid 5
     * @param color black or white
     */
    public Prince(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }
    
    /**
     * Determine if the Prince is in a valid starting position.
     * White: e3
     * Black: e6
     */
    public boolean hasValidStartingPosition()
    {
        if (column != 5)
            return false;
        return ((color == ChessPieceColor.WHITE) && (row == 3)) ||
               ((color == ChessPieceColor.BLACK) && (row == 6)); 
    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Princes. Doesn't account for other pieces. Remember, Princes can
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
        return (deltaRow <= 1) && (deltaColumn <= 1);
    }
}
