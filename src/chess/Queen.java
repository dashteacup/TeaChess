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
    @Override
    public boolean inValidStartingPosition()
    {
        if (getColumn() != 4)
            return false;
        return ((getColor() == ChessPieceColor.WHITE) && (getRow() == 1)) ||
               ((getColor() == ChessPieceColor.BLACK) && (getRow() == 8));
    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Queens. Doesn't account for other pieces.
     */
    @Override
    public boolean isValidMove(int row, int column)
    {
        if (!super.isValidMove(row, column))
            return false;
        // up-down like rook
        if (getRow() == row)
            return true;
        // left-right like rook
        if (getColumn() == column)
            return true;
        int deltaRow = Math.abs(getRow() - row);
        int deltaColumn = Math.abs(getColumn() - column);
        // move diagonally like bishop
        return (deltaRow == deltaColumn);
    }
}
