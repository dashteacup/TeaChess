package chess;

/**
 * Simple enum to represent a chess piece's color.
 */
public enum ChessPieceColor {
    WHITE {
        @Override
        public ChessPieceColor otherColor() { return BLACK; }
    },

    BLACK {
        @Override
        public ChessPieceColor otherColor() { return WHITE; }
    },

    NONE {
        @Override
        public ChessPieceColor otherColor() { return NONE; }
    };

    /**
     * Return the opposite color of this color.
     * @return BLACK if WHITE, WHITE if BLACK, NONE otherwise
     */
    public abstract ChessPieceColor otherColor();
}