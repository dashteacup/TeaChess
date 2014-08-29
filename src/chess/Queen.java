package chess;

/**
 * Represents the Queen chess piece.
 */
public class Queen extends ChessPiece {
    /**
     * Create a new Queen chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 4
     * @param color black or white
     */
    public Queen(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }
    
    /**
     * Determine if the Queen is in a valid starting position.
     */
    public boolean inValidStartingPosition()
    {
        if (column != 4)
            return false;
        return ((color == ChessPieceColor.WHITE) && (row == 1)) ||
               ((color == ChessPieceColor.BLACK) && (row == 8));
    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Queens. Doesn't account for other pieces.
     */
    public boolean isValidMove(int row, int column)
    {
        if (!bothPlacesOnTheBoard(row, column))
            return false;
        if (sourceAndDestinationSame(row, column))
            return false;
        // up-down like rook
        if (this.row == row)
            return true;
        // left-right like rook
        if (this.column == column)
            return true;
        int deltaRow = Math.abs(this.row - row);
        int deltaColumn = Math.abs(this.column - column);
        // move diagonally like bishop
        return (deltaRow == deltaColumn);
    }
}
