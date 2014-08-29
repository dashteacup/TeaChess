package chess;

/**
 * Represents the Bishop chess piece.
 */
public class Bishop extends ChessPiece {
    /**
     * Create a new Bishop chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 3 or 6
     * @param color black or white
     */
    public Bishop(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }
    
    /**
     * Determine if the Bishop is in a valid starting position.
     */
    public boolean inValidStartingPosition()
    {
        if ((color == ChessPieceColor.WHITE) && (row == 1)) {
            return (column == 3) || (column == 6);
        }
        else if ((color == ChessPieceColor.BLACK) && (row == 8)) {
            return (column == 3) || (column == 6);
        }
        return false;
    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Bishops. Doesn't account for other pieces.
     */
    public boolean isValidMove(int row, int column)
    {
        if (!bothPlacesOnTheBoard(row, column))
            return false;
        if (sourceAndDestinationSame(row, column))
            return false;
        int deltaRow = Math.abs(this.row - row);
        int deltaColumn = Math.abs(this.column - column);
        return (deltaRow == deltaColumn);
    }
}
