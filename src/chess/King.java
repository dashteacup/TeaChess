package chess;

/**
 * Represents the King chess piece.
 */
public class King extends ChessPiece {
    /**
     * Create a new King chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 5
     * @param color black or white
     */
    public King(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }
    
    /**
     * Determine if the King is in a valid starting position.
     */
    public boolean hasValidStartingPosition()
    {
        if (column == 5) {
            boolean isWhiteKing = (color == ChessPieceColor.WHITE) && (row == 1);
            boolean isBlackKing = (color == ChessPieceColor.BLACK) && (row == 8);
            return isWhiteKing || isBlackKing;
        }
        return false;
    }
    
    /**
     * Determine if the move follows the standard movement pattern for Kings.
     * Doesn't account for other pieces. Doesn't worry about the "Check"
     * condition.
     */
    public boolean isValidMove(int row, int column)
    {
        if (!bothPlacesOnTheBoard(row, column))
            return false;
        if (sourceAndDestinationSame(row, column))
            return false;
        int deltaRow = Math.abs(row - this.row);
        int deltaColumn = Math.abs(column - this.column);
        return (deltaRow <= 1) && (deltaColumn <= 1);
    }
}
