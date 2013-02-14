package chess;

import java.util.ArrayList;

/**
 * Represents the chess board itself.
 * TODO: This isn't completely functional at the moment.
 */
public class ChessBoard {
    /**
     * Set to nine so I can index from 1-8 normally. The [0] element
     * will always be empty.
     */
    protected static final int boardSize = 9;
    
    /**
     * The board held in a 2 dimensional array;
     */
    protected ArrayList<ArrayList<ChessPiece>> board;
    
    /**
     * Build new standard sized chess board.
     * Currently, empty elements are set to null, I think I need to do
     * something better than that.
     */
    public ChessBoard()
    {
        board = new ArrayList<ArrayList<ChessPiece>>();
        // make rows
        for (int row = 0; row < boardSize; ++row)
            board.add(new ArrayList<ChessPiece>());
        // make columns
        for (int row = 1; row < boardSize; ++row) {
            // want to index the board from 1 like in algebraic chess notation
            board.get(row).add(null);
            switch(row) {
            case 1:
                board.get(row).add(new Rook(row, 1, ChessPieceColor.WHITE));
                board.get(row).add(new Knight(row, 2, ChessPieceColor.WHITE));
                board.get(row).add(new Bishop(row, 3, ChessPieceColor.WHITE));
                board.get(row).add(new Queen(row, 4, ChessPieceColor.WHITE));
                board.get(row).add(new King(row, 5, ChessPieceColor.WHITE));
                board.get(row).add(new Bishop(row, 6, ChessPieceColor.WHITE));
                board.get(row).add(new Knight(row, 7, ChessPieceColor.WHITE));
                board.get(row).add(new Rook(row, 8, ChessPieceColor.WHITE));
                break;
            case 2:
                for (int col = 1; col < boardSize; ++col)
                    board.get(row).add(new Pawn(row, col, ChessPieceColor.WHITE));
                break;
            case 3:
                board.get(row).add(new Frog(row, 1, ChessPieceColor.WHITE));
                for (int col = 2; col < 8; ++col) {
                    if (col == 5)
                        board.get(row).add(new Prince(row, 5, ChessPieceColor.WHITE));
                    else
                        board.get(row).add(null);
                }
                board.get(row).add(new Frog(row, 8, ChessPieceColor.WHITE));
                break;
            case 4:
            case 5:
                for (int col = 1; col < boardSize; ++col)
                    board.get(row).add(null);
                break;
            case 6:
                board.get(row).add(new Frog(row, 1, ChessPieceColor.BLACK));
                for (int col = 2; col < 8; ++col) {
                    if (col == 5)
                        board.get(row).add(new Prince(row, 5, ChessPieceColor.BLACK));
                    else
                        board.get(row).add(null);
                }
                board.get(row).add(new Frog(row, 8, ChessPieceColor.BLACK));
                break;
            case 7:
                for (int col = 1; col < boardSize; ++col)
                    board.get(row).add(new Pawn(row, col, ChessPieceColor.BLACK));
                break;
            case 8:
                board.get(row).add(new Rook(row, 1, ChessPieceColor.BLACK));
                board.get(row).add(new Knight(row, 2, ChessPieceColor.BLACK));
                board.get(row).add(new Bishop(row, 3, ChessPieceColor.BLACK));
                board.get(row).add(new Queen(row, 4, ChessPieceColor.BLACK));
                board.get(row).add(new King(row, 5, ChessPieceColor.BLACK));
                board.get(row).add(new Bishop(row, 6, ChessPieceColor.BLACK));
                board.get(row).add(new Knight(row, 7, ChessPieceColor.BLACK));
                board.get(row).add(new Rook(row, 8, ChessPieceColor.BLACK));
                break;
            }
        }       
    }

    /**
     * Get the chess board.
     */
    public ArrayList<ArrayList<ChessPiece>> getBoard() {
        return board;
    }
}
