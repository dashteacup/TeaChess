package chess;

/**
 * Represents the Rook chess piece.
 */
public class Rook extends ChessPiece {
    /**
     * Create a new rook chess piece
     * @param row aka 'rank' valid 1 or 8
     * @param column aka 'file' valid 1 or 8 NOT a or h
     * @param color black or white
     */
    public Rook(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }

    /**
     * Determine if the Rook is in a valid starting position.
     */
    @Override
    public boolean inValidStartingPosition()
    {
        if (!isOnTheBoard(this.row, this.column))
            return false;
        boolean onWhiteSide = (this.row == 1 && this.column == 1) ||
                              (this.row == 1 && this.column == 8);
        boolean onBlackSide = (this.row == 8 && this.column == 1) ||
                              (this.row == 8 && this.column == 8);
        return (onWhiteSide && (color == ChessPieceColor.WHITE)) ||
               (onBlackSide && (color == ChessPieceColor.BLACK));
    }

    /**
     * Determine if a move follows the standard movement pattern for Rooks.
     * Doesn't account for the presence of other pieces.
     */
    @Override
    public boolean isValidMove(int newRow, int newColumn)
    {
        if (!super.isValidMove(newRow, newColumn))
            return false;
        boolean rowMatch = (newRow == this.row);
        boolean columnMatch = (newColumn == this.column);
        return (rowMatch || columnMatch);
    }
}
