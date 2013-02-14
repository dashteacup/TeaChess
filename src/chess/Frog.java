package chess;

/**
 * Represents the Frog chess piece.
 * Frogs are a custom "hopper" chess piece that moves by leaping over a 
 * <em>single</em> space up/down xor left/right. The spaces they leap over
 * can be empty or occupied, it doesn't matter. Their valid starting position
 * is (in algebraic chess notation) a3 and h3 for white, and a6 and h6 for
 * black.
 */
public class Frog extends ChessPiece {
    /**
     * Create a new Frog chess piece.
     * @param row aka 'rank' valid 3 or 6
     * @param column aka 'file' valid 1 or 8
     * @param color black or white
     */
    public Frog(int row, int column, ChessPieceColor color)
    {
        super(row, column, color);
        hoppable = true;
    }
    
    /**
     * Determine if the Frog is in a valid starting position.
     * White: a3 h3
     * Black: a6 h6
     */
    public boolean hasValidStartingPosition()
    {
        boolean isInTheRightColumn = (column == 1) || (column == 8);
        if ((row == 3) && (color == ChessPieceColor.WHITE))
            return isInTheRightColumn;
        if ((row == 6) && (color == ChessPieceColor.BLACK))
            return isInTheRightColumn;
        return false;
    }

    /**
     * Determine if the move follows the standard movement pattern for
     * Frogs. Doesn't account for other pieces. Remember, Frogs can
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
        if (deltaRow == 0)
            return (deltaColumn == 2);
        if (deltaColumn == 0)
            return (deltaRow == 2);
        return false;
    }
}
