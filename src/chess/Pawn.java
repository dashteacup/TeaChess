package chess;

/**
 * Represents the Pawn chess piece.
 */
public class Pawn extends ChessPiece {
    /**
     * Create a new Pawn chess piece
     * @param row aka 'rank' valid 2 or 7
     * @param column aka 'file' valid 1-8 NOT a-h
     * @param color black or white
     */
    public Pawn(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
    }

    /**
     * Determine if the Pawn is in a valid starting position.
     */
    @Override
    public boolean inValidStartingPosition()
    {
        if (!isOnTheBoard(this.row, this.column))
            return false;
        if ((this.row == 2) && (color == ChessPieceColor.WHITE))
            return true;
        return ((this.row == 7) && (color == ChessPieceColor.BLACK));

    }

    /**
     * Determine if the move follows the standard movement pattern for Pawns.
     * Doesn't account for the presence of other pieces. You cannot capture with
     * the move command for pawns.
     */
    @Override
    public boolean isValidMove(int row, int column)
    {
        if (!super.isValidMove(row, column))
            return false;
        // no capturing
        if (this.column != column)
            return false;
        boolean isMovingForward = false;
        if (color == ChessPieceColor.WHITE) {
            isMovingForward = (row == this.row + 1);
            if (inValidStartingPosition())
                isMovingForward = isMovingForward || (row == this.row + 2);
        }
        if (color == ChessPieceColor.BLACK) {
            isMovingForward = (row == this.row - 1);
            if (inValidStartingPosition())
                isMovingForward = isMovingForward || (row == this.row - 2);
        }
        return isMovingForward;
    }

    @Override
    public boolean canCapture(int enemyRow, int enemyColumn)
    {
        if (!bothPlacesOnTheBoard(enemyRow, enemyColumn))
            return false;
        if (sourceAndDestinationSame(enemyRow, enemyColumn))
            return false;
        boolean isMovingForward = false;
        if (color == ChessPieceColor.WHITE)
            isMovingForward = this.row < enemyRow;
        if (color == ChessPieceColor.BLACK)
            isMovingForward = this.row > enemyRow;
        if (!isMovingForward)
            return false;
        int deltaRow = Math.abs(this.row - enemyRow);
        int deltaColumn = Math.abs(this.column - enemyColumn);
        return (deltaRow == 1) && (deltaColumn == 1);
    }
}
