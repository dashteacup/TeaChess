package chess;

/**
 * Represents the King chess piece. Since castling requires information about
 * the state of the board, it is implemented in {@link ChessBoard}.
 */
public class King extends ChessPiece {
    /**
     * Create a new King chess piece.
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 5
     * @param color BLACK or WHITE
     */
    public King(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }

    /**
     * Create a new King chess piece in algebraic chess notation.
     * @param file letter for the column in algebraic chess notation (a-h)
     * @param rank number indicating the row in algebraic chess notation (1-8)
     * @param color BLACK or WHITE
     */
    public King(File file, int rank, ChessPieceColor color)
    {
        super(file, rank, color);
    }

    /**
     * Determine if the King is in a valid starting position.
     */
    @Override
    public boolean inValidStartingPosition()
    {
        if (getColumn() == 5) {
            boolean isWhiteKing = (getColor() == ChessPieceColor.WHITE) && (getRow() == 1);
            boolean isBlackKing = (getColor() == ChessPieceColor.BLACK) && (getRow() == 8);
            return isWhiteKing || isBlackKing;
        }
        return false;
    }

    /**
     * Determine if the move follows the standard movement pattern for Kings.
     * Doesn't account for other pieces, so it doesn't support castling. Doesn't
     * worry about the "Check" condition.
     */
    @Override
    public boolean isValidMove(int row, int column)
    {
        if (!super.isValidMove(row, column))
            return false;
        int deltaRow = Math.abs(row - getRow());
        int deltaColumn = Math.abs(column - getColumn());
        return (deltaRow <= 1) && (deltaColumn <= 1);
    }
}
