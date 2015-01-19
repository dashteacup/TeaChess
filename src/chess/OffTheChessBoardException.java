package chess;

/**
 * Thrown to indicate the user tried to access a location off the Chess board.
 */
public class OffTheChessBoardException extends IndexOutOfBoundsException {

    /**
     * Compiler generated serialization id.
     */
    private static final long serialVersionUID = -2541299160035666071L;

    /**
     * Construct an OffTheChessBoardException with no detail message.
     */
    public OffTheChessBoardException()
    {
    }

    /**
     * Construct an OffTheChessBoardException with a detail message indicating
     * the position accessed.
     * @param row of location accessed
     * @param column of location accessed
     */
    public OffTheChessBoardException(int row, int column)
    {
        super("row: " + row + " column: " + column);
    }

    /**
     * Construct an OffTheChessBoardException with the specified detail message.
     * @param s the detail message
     */
    public OffTheChessBoardException(String s)
    {
        super(s);
    }

}
