package chess;

/**
 * Represents the Pawn chess piece.
 * TODO: implement en passant capture
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
     * Create a new Pawn chess piece
     * @param file letter in algebraic chess notation
     * @param rank number indicating the row in algebraic chess notation
     * @param color BLACK or WHITE
     */
    public Pawn(File file, int rank, ChessPieceColor color)
    {
        super(file, rank, color);
    }

    /**
     * Determine if the Pawn is in a valid starting position.
     */
    @Override
    public boolean inValidStartingPosition()
    {
        if (!isOnTheBoard(getRow(), getColumn()))
            return false;
        if ((getRow() == 2) && (getColor() == ChessPieceColor.WHITE))
            return true;
        return ((getRow() == 7) && (getColor() == ChessPieceColor.BLACK));

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
        if (getColumn() != column)
            return false;
        boolean isMovingForward = false;
        if (getColor() == ChessPieceColor.WHITE) {
            isMovingForward = (row == getRow() + 1);
            if (inValidStartingPosition())
                isMovingForward = isMovingForward || (row == getRow() + 2);
        }
        if (getColor() == ChessPieceColor.BLACK) {
            isMovingForward = (row == getRow() - 1);
            if (inValidStartingPosition())
                isMovingForward = isMovingForward || (row == getRow() - 2);
        }
        return isMovingForward;
    }

    /**
     * Determine if the Pawn can capture a piece at another location. Doesn't
     * check to see if there is a chess piece at the indicated location.
     * Doesn't support en passant.
     * @param enemyRow row of the piece to capture
     * @param enemyColumn column of the piece to capture
     * @return true if the Pawn can capture the piece at the indicated location,
     * false otherwise
     */
    @Override
    public boolean canCapture(int enemyRow, int enemyColumn)
    {
        if (!bothPlacesOnTheBoard(enemyRow, enemyColumn))
            return false;
        if (sourceAndDestinationSame(enemyRow, enemyColumn))
            return false;
        boolean isMovingForward = false;
        if (getColor() == ChessPieceColor.WHITE)
            isMovingForward = getRow() < enemyRow;
        if (getColor() == ChessPieceColor.BLACK)
            isMovingForward = getRow() > enemyRow;
        if (!isMovingForward)
            return false;
        int deltaRow = Math.abs(getRow() - enemyRow);
        int deltaColumn = Math.abs(getColumn() - enemyColumn);
        return (deltaRow == 1) && (deltaColumn == 1);
    }
}
