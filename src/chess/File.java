package chess;

/**
 * Constant representing a file (column) on the chessboard with letters as in
 * algebraic chess notation. This enum was made for UI convenience.
 */
public enum File {
    // These are lowercase to match algebraic chess notation.
    /**
     * The first column from the left side of the chessboard.
     */
    a (1),
    /**
     * The second column from the left side of the chessboard.
     */
    b (2),
    /**
     * The third column from the left side of the chessboard.
     */
    c (3),
    /**
     * The fourth column from the left side of the chessboard.
     */
    d (4),
    /**
     * The fifth column from the left side of the chessboard.
     */
    e (5),
    /**
     * The sixth column from the left side of the chessboard.
     */
    f (6),
    /**
     * The seventh column from the left side of the chessboard.
     */
    g (7),
    /**
     * The eighth column from the left side of the chessboard.
     */
    h (8);

    /**
     * The numerical value for a file.
     */
    private final int column;

    /**
     * @return column number of this file
     */
    public int getColumn()
    {
        return column;
    }

    /**
     * Get the corresponding file from a numerical value.
     * @param column a value from 1-8
     * @return The matching file for a given column number
     */
    public static File getFile(int column)
    {
        switch (column) {
        case 1: return File.a;
        case 2: return File.b;
        case 3: return File.c;
        case 4: return File.d;
        case 5: return File.e;
        case 6: return File.f;
        case 7: return File.g;
        case 8: return File.h;
        default:
            throw new IllegalArgumentException(
                    "Given column: " + column + " is not between 1 and 8.");
        }
    }

    private File(int column)
    {
        this.column = column;
    }
}
