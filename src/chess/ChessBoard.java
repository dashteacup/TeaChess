package chess;

/**
 * Represents the chess board itself.
 */
public class ChessBoard {
    /**
     * Set to nine so I can index from 1-8 normally. The [0] element
     * will always be empty.
     */
    private static final int BOARD_SIZE = 9;

    /**
     * The board held in a 2 dimensional array;
     */
    private ChessPiece[][] board = new ChessPiece[BOARD_SIZE][BOARD_SIZE];

    /**
     * Build new standard sized chess board with all chess pieces in their
     * starting positions. Currently, empty elements are set to null, I
     * think I need to do something better than that.
     */
    public ChessBoard()
    {
        // init white side
        int row = 1;
        int col = 1;
        board[row][col] = new Rook  (row, col++, ChessPieceColor.WHITE);
        board[row][col] = new Knight(row, col++, ChessPieceColor.WHITE);
        board[row][col] = new Bishop(row, col++, ChessPieceColor.WHITE);
        board[row][col] = new Queen (row, col++, ChessPieceColor.WHITE);
        board[row][col] = new King  (row, col++, ChessPieceColor.WHITE);
        board[row][col] = new Bishop(row, col++, ChessPieceColor.WHITE);
        board[row][col] = new Knight(row, col++, ChessPieceColor.WHITE);
        board[row][col] = new Rook  (row, col++, ChessPieceColor.WHITE);
        row = 2;
        for (col = 1; col < BOARD_SIZE; col++)
            board[row][col] = new Pawn(row, col, ChessPieceColor.WHITE);

        // init black side
        row = 7;
        for (col = 1; col < BOARD_SIZE; col++)
            board[row][col] = new Pawn(row, col, ChessPieceColor.BLACK);
        row = 8;
        col = 1;
        board[row][col] = new Rook  (row, col++, ChessPieceColor.BLACK);
        board[row][col] = new Knight(row, col++, ChessPieceColor.BLACK);
        board[row][col] = new Bishop(row, col++, ChessPieceColor.BLACK);
        board[row][col] = new Queen (row, col++, ChessPieceColor.BLACK);
        board[row][col] = new King  (row, col++, ChessPieceColor.BLACK);
        board[row][col] = new Bishop(row, col++, ChessPieceColor.BLACK);
        board[row][col] = new Knight(row, col++, ChessPieceColor.BLACK);
        board[row][col] = new Rook  (row, col++, ChessPieceColor.BLACK);
    }

    /**
     * ChessBoard copy constructor. Duplicate the current chess board layout.
     * If the sourceBoard is null, then return an empty board.
     * @param sourceBoard board to duplicate
     */
    public ChessBoard(ChessBoard sourceBoard)
    {
        if (sourceBoard == null)
            return; // all elements null by default
        for (int row = 1; row < BOARD_SIZE; row++) {
            for (int col = 1; col < BOARD_SIZE; col++) {
                ChessPiece piece = sourceBoard.getPiece(row, col);
                if (piece != null) {
                    try {
                        board[row][col] = piece.clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        System.exit(1); // this shouldn't happen
                    }
                }
            }
        }
    }

    /**
     * Get the chess piece at the given location.
     * @param row of the chess piece (1-8)
     * @param column of the chess piece (1-8)
     * @return the chess piece at the given location
     */
    public ChessPiece getPiece(int row, int column)
    {
        if (!isOnTheBoard(row, column)) {
            throw new OffTheChessBoardException(row, column);
        }
        return board[row][column];
    }

    /**
     * Get the chess piece at the location given in algebraic chess notation.
     * @param file of the chess piece (a-h)
     * @param rank of the chess piece (1-8)
     * @return the chess piece at the given location
     */
    public ChessPiece getPiece(File file, int rank)
    {
        return getPiece(rank, file.getColumn());
    }

    /**
     * Add a ChessPiece to the board. If a ChessPiece already exists at the new
     * ChessPiece's location, it will be replaced with the new piece.
     * @param piece to add to the board
     * @return true if the piece is successfully added, false otherwise
     */
    public boolean addPiece(ChessPiece piece)
    {
        if (piece == null)
            return false;
        int row = piece.getRow();
        int column = piece.getColumn();
        if (!isOnTheBoard(row, column))
            return false;
        board[row][column] = piece;
        return true;
    }

    /**
     * Determine if there is a ChessPiece at the given location.
     * @param row (1-8)
     * @param column (1-8)
     * @return true if the space is empty, false otherwise
     */
    public boolean isEmptySpace(int row, int column)
    {
        return getPiece(row, column) == null;
    }

    /**
     * Determine if there is a ChessPiece at the given location.
     * @param file in algebraic chess notation
     * @param rank in algebraic chess notation (1-8)
     * @return true if the space is empty, false otherwise
     */
    public boolean isEmptySpace(File file, int rank)
    {
        return isEmptySpace(rank, file.getColumn());
    }

    /**
     * Determine if the indicated move is a valid chess move.
     * @param oldRow of the piece to move (1-8)
     * @param oldColumn of the piece to move (1-8)
     * @param newRow to move to (1-8)
     * @param newColumn to move to (1-8)
     * @return true if it's a valid move, false otherwise
     */
    public boolean isValidMove(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        // There must be a piece to move
        if (isEmptySpace(oldRow, oldColumn))
            return false;
        ChessPiece piece = getPiece(oldRow, oldColumn);
        // moving to an empty space
        if (isEmptySpace(newRow, newColumn)) {
            // castling is a special case that requires knowledge of ChessBoard's state
            if (canCastle(oldRow, oldColumn, newRow, newColumn))
                return true;
            if (!piece.isValidMove(newRow, newColumn))
                return false;
        // the space is occupied
        } else {
            if ( (!piece.canCapture(newRow, newColumn))
                 || // can't capture your own color
                 (piece.getColor() == getPiece(newRow, newColumn).getColor()) ) {
                return false;
            }
        }
        // piece must be hoppable or have a clear path to its destination
        if (! (piece.isHoppable() || hasClearPath(oldRow, oldColumn, newRow, newColumn)) )
            return false;
        // Will this move put the king in check?
        ChessBoard potential = new ChessBoard(this);
        potential.forceMove(oldRow, oldColumn, newRow, newColumn);
        return !potential.inCheck(piece.getColor());
    }

    /**
     * Determine if the indicated move in algebraic chess notation is a valid
     * chess move.
     * @param oldFile of the piece to move (a-h)
     * @param oldRank of the piece to move (1-8)
     * @param newFile to move to (a-h)
     * @param newRank to move to (1-8)
     * @return true if it's a valid move, false otherwise
     */
    public boolean isValidMove(File oldFile, int oldRank, File newFile, int newRank)
    {
        return isValidMove(oldRank, oldFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Move a ChessPiece from one position to another in row/column notation.
     * @param oldRow of the piece to move (1-8)
     * @param oldColumn of the piece to move (1-8)
     * @param newRow to move to (1-8)
     * @param newColumn to move to (1-8)
     * @return true if the move succeeds, false otherwise
     */
    public boolean move(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        if (isValidMove(oldRow, oldColumn, newRow, newColumn)) {
            ChessPiece movingPiece = getPiece(oldRow, oldColumn);
            if (movingPiece.canCapture(newRow, newColumn))
                movingPiece.capture(newRow, newColumn);
            else
                movingPiece.move(newRow, newColumn);
            board[newRow][newColumn] = movingPiece;
            board[oldRow][oldColumn] = null;
            return true;
        }
        return false;
    }

    /**
     * Move a ChessPiece from one position to another in algebraic chess notation.
     * @param oldFile of the piece to move (a-h)
     * @param oldRank of the piece to move (1-8)
     * @param newFile to move to (a-h)
     * @param newRank to move to (1-8)
     * @return true if the move succeeds, false otherwise
     */
    public boolean move(File oldFile, int oldRank, File newFile, int newRank)
    {
        return move(oldRank, oldFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Move a ChessPiece from one position to another without confirming that
     * it's a valid chess move. Does nothing if there is no piece to move.
     * @param oldRow of the piece to move (1-8)
     * @param oldColumn of the piece to move (1-8)
     * @param newRow to move to (1-8)
     * @param newColumn to move to (1-8)
     */
    public void forceMove(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        ChessPiece movingPiece = getPiece(oldRow, oldColumn);
        if (movingPiece != null) {
            movingPiece.forceMove(newRow, newColumn);
            board[newRow][newColumn] = movingPiece;
            board[oldRow][oldColumn] = null;
        }
    }

    /**
     * Move a ChessPiece from one position to another in algebraic chess
     * notation without confirming that it's a valid chess move. Does nothing if
     * there is no piece to move.
     * @param oldFile of the piece to move (a-h)
     * @param oldRank of the piece to move (1-8)
     * @param newFile to move to (a-h)
     * @param newRank to move to (1-8)
     */
    public void forceMove(File oldFile, int oldRank, File newFile, int newRank)
    {
        forceMove(oldRank, oldFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Determine if a king can castle to the given location.
     * @param kingRow of the king (1-8)
     * @param kingColumn of the king (1-8)
     * @param newRow the king will castle to (1-8)
     * @param newColumn the king will castle to (1-8)
     * @return true if the king can castle, false otherwise
     * @see <a href="http://en.wikipedia.org/w/index.php?title=Castling&oldid=641949496#Requirements">Castling Requirements</a> (Wikipedia)
     */
    public boolean canCastle(int kingRow, int kingColumn, int newRow, int newColumn)
    {
        ChessPiece king = getPiece(kingRow, kingColumn);
        // must have a king there
        if (!(king instanceof King))
            return false;
        // King can't have moved from its starting position
        if (!king.inValidStartingPosition() || king.hasMoved())
            return false;
        // king and rook must be on same row
        if (kingRow != newRow)
            return false;
        ChessPiece rook;
        // have to move to column 3 or 7
        if (newColumn == 3) {
            rook = getPiece(kingRow, 1); // left (queenside) rook
        } else if (newColumn == 7) {
            rook = getPiece(kingRow, 8); // right (kingside) rook
        } else { // king isn't moving to the right column for castling
            return false;
        }
        // have to have a rook that hasn't moved from its starting position
        if (!(rook instanceof Rook) || rook.hasMoved())
            return false;
        // no other pieces between king and rook
        if (!hasClearPath(kingRow, kingColumn, rook.getRow(), rook.getColumn()))
            return false;
        // King can't be in check
        if (inCheck(king.getColor()))
            return false;
        // King can't move through a square that would put it in check,
        // so look ahead two spaces.
        int stepDirection = (newColumn == 3) ? -1 : 1; // -1 = left, 1 = right
        ChessBoard oneStep = new ChessBoard(this);
        oneStep.forceMove(kingRow, kingColumn, newRow, kingColumn + stepDirection);
        ChessBoard twoSteps = new ChessBoard(oneStep);
        twoSteps.forceMove(kingRow, kingColumn + stepDirection, newRow, newColumn);
        return !( oneStep.inCheck(king.getColor()) || twoSteps.inCheck(king.getColor()) );
    }

    /**
     * Determine if a king can castle to a new location, using algebraic chess
     * notation.
     * @param kingFile of the king (a-h)
     * @param kingRank of the king (1-8)
     * @param newFile of the king (a-h)
     * @param newRank of the king (1-8)
     * @return true if the king can castle, false otherwise
     * @see <a href="http://en.wikipedia.org/w/index.php?title=Castling&oldid=641949496#Requirements">Castling Requirements</a> (Wikipedia)
     */
    public boolean canCastle(File kingFile, int kingRank, File newFile, int newRank)
    {
        return canCastle(kingRank, kingFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Castle the king to the given location also moving the rook to
     * its proper place. If this isn't a valid castle, then do nothing.
     * @param kingRow of the king (1-8)
     * @param kingColumn of the king (1-8)
     * @param newRow of the king (1-8)
     * @param newColumn of the king (1-8)
     */
    public void castle(int kingRow, int kingColumn, int newRow, int newColumn)
    {
        if (!canCastle(kingRow, kingColumn, newRow, newColumn))
            return; // do nothing
        // forced because castling isn't handled in King.isValidMove()
        forceMove(kingRow, kingColumn, newRow, newColumn); // move king
        if (newColumn == 7) // right castle
            forceMove(newRow, 8, newRow, 6);
        else // left castle
            forceMove(newRow, 1, newRow, 4);
    }

    /**
     * Castle the king to the location given in algebraic chess notation, also
     * moving the rook to its proper place. If this isn't a valid castle, then
     * do nothing.
     * @param kingFile of the king (a-h)
     * @param kingRank of the king (1-8)
     * @param newFile of the king (a-h)
     * @param newRank of the king (1-8)
     */
    public void castle(File kingFile, int kingRank, File newFile, int newRank)
    {
        castle(kingRank, kingFile.getColumn(), newRank, newFile.getColumn());
    }

    /**
     * Determine if a pawn at the given location can be promoted. Promotion
     * happens when a pawn reaches the opposite side of the board.
     * @param row of the pawn (1-8)
     * @param column of the pawn. (1-8)
     * @return true if there is a pawn that can be promoted, false otherwise
     */
    public boolean canPromotePawn(int row, int column)
    {
        ChessPiece piece = getPiece(row, column);
        if (piece instanceof Pawn) {
            if ( (piece.getColor() == ChessPieceColor.WHITE) && (piece.getRow() == 8) )
                return true;
            if ( (piece.getColor() == ChessPieceColor.BLACK) && (piece.getRow() == 1) )
                return true;
        }
        return false;
    }

    /**
     * Determine if a pawn at the location given in algebraic chess notation can
     * be promoted. Promotion happens when a pawn reaches the opposite side of
     * the board.
     * @param file of the pawn (a-h)
     * @param rank of the pawn (1-8)
     * @return true if there is a pawn that can be promoted, false otherwise
     */
    public boolean canPromotePawn(File file, int rank)
    {
        return canPromotePawn(rank, file.getColumn());
    }


    /**
     * Determine if the current player is in check (i.e. their king is exposed
     * to attack).
     * @param currentPlayer to determine if checked
     * @return true if in check, false otherwise
     */
    public boolean inCheck(ChessPieceColor currentPlayer)
    {
        ChessPiece myKing = getKing(currentPlayer);
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece attackingPiece = getPiece(row, col);
                // has to be an enemy piece
                if (attackingPiece != null && attackingPiece.getColor() != currentPlayer) {
                    // can this peace capture the king?
                    if (isValidMove(row, col, myKing.getRow(), myKing.getColumn()))
                        return true;
                }
            }
        }
        return false;
    }

    /**
     * Determine if the given player has been checkmated. The game reaches
     * checkmate when the current player is in check and has no valid moves
     * to make.
     * @param currentPlayer whose turn it is
     * @return true if the player has been checkmated, false otherwise.
     */
    public boolean checkmate(ChessPieceColor currentPlayer)
    {
        return hasNoValidMoves(currentPlayer) && inCheck(currentPlayer);
    }

    /**
     * Determine if the given player has forced a stalemate. The game is a
     * stalemate if the current player has no valid moves and is NOT in check.
     * @param currentPlayer whose turn it is
     * @return true if the player is in stalemate, false otherwise.
     */
    public boolean stalemate(ChessPieceColor currentPlayer)
    {
        return hasNoValidMoves(currentPlayer) && !inCheck(currentPlayer);
    }

    /**
     * Determine if the given player has no valid moves. This means there is
     * either a checkmate or a stalemate.
     * @param currentPlayer to check for valid moves
     * @return true if the player can make any moves, false otherwise
     */
    private boolean hasNoValidMoves(ChessPieceColor currentPlayer)
    {
        // check every space for my pieces
        for (int row = 1; row < BOARD_SIZE; row++) {
            for (int col = 1; col < BOARD_SIZE; col++) {
                ChessPiece pieceToCheck = getPiece(row, col);
                // if it is one of my chess pieces
                if (pieceToCheck != null && pieceToCheck.getColor() == currentPlayer) {
                    // see if it has any valid moves
                    if (hasValidMoves(pieceToCheck))
                        return false;
                }
            }
        }
        return true;
    }

    /**
     * Determine if a ChessPiece has any valid moves to make
     * @param piece to check
     * @return true if the piece can make any moves, false otherwise
     */
    private boolean hasValidMoves(ChessPiece piece)
    {
        assert piece != null;
        for (int row = 1; row < BOARD_SIZE; row++) {
            for (int col = 1; col < BOARD_SIZE; col++) {
                if (isValidMove(piece.getRow(), piece.getColumn(), row, col))
                    return true;
            }
        }
        return false;
    }

    /**
     * Return a reference to the king of the given player. Assumes that there is
     * exactly one king for each player.
     * @param player the King's color
     * @return the king of the player
     */
    private ChessPiece getKing(ChessPieceColor player)
    {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPiece piece = getPiece(row, col);
                if ((piece instanceof King) && (piece.getColor() == player))
                    return piece;
            }
        }
        assert false; // the board has to have a king
        return null;
    }

    /**
     * Determine if there is a clear (unobstructed) path between the two positions on the
     * chess board. Do not use this function on hoppable chess pieces.
     * @param oldRow of the piece to move
     * @param oldColumn of the piece to move
     * @param newRow where the piece will be moved
     * @param newColumn where the piece will be moved
     * @return true if there is a clear path between the old position and the
     * new one, false otherwise
     */
    private boolean hasClearPath(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        assert !isEmptySpace(oldRow, oldColumn);
        assert !(oldRow == newRow && oldColumn == newColumn);
        assert !getPiece(oldRow, oldColumn).isHoppable();
        int deltaRow = newRow - oldRow;
        int deltaColumn = newColumn - oldColumn;
        // horizontal movement
        if (deltaRow == 0 && deltaColumn != 0) {
            return hasClearHorizontal(oldRow, oldColumn, newColumn);
        // vertical movement
        } else if (deltaRow != 0 && deltaColumn == 0) {
            return hasClearVertical(oldColumn, oldRow, newRow);
        // diagonal movement
        } else if (Math.abs(deltaRow) == Math.abs(deltaColumn)) {
            return hasClearDiagonal(oldRow,
                                    oldColumn,
                                    Math.abs(deltaRow),
                                    Integer.signum(deltaRow),
                                    Integer.signum(deltaColumn));
        }
        // All non-hoppable pieces move either vertically, horizontally, or diagonally.
        assert false; // This line should never actually execute.
        return false;
    }

    /**
     * Determine if there are any chess pieces between two columns on the chess
     * board.
     * @param row of the chess piece
     * @param startColumn of the chess piece
     * @param endColumn of the chess piece
     * @return true if there are no pieces between the start and end, false
     * otherwise
     */
    private boolean hasClearHorizontal(int row, int startColumn, int endColumn)
    {
        assert isOnTheBoard(row, startColumn) : startColumn;
        assert isOnTheBoard(row, endColumn) : endColumn;
        int lower = Math.min(startColumn, endColumn);
        int upper = Math.max(startColumn, endColumn);
        // check every space between the two columns
        for (int column = lower + 1; column < upper; ++column) {
            if (!isEmptySpace(row, column))
                return false;
        }
        return true;
    }

    /**
     * Determine if there are any chess pieces between two rows on the chess
     * board.
     * @param column of the chess piece
     * @param startRow of the chess piece
     * @param endRow of the chess piece
     * @return true if there are no pieces between the start and end, false
     * otherwise
     */
    private boolean hasClearVertical(int column, int startRow, int endRow)
    {
        assert isOnTheBoard(startRow, column) : startRow;
        assert isOnTheBoard(endRow, column) : endRow;
        int lower = Math.min(startRow, endRow);
        int upper = Math.max(startRow, endRow);
        // check every space between the two rows
        for (int row = lower + 1; row < upper; ++row) {
            if (!isEmptySpace(row, column))
                return false;
        }
        return true;
    }

    /**
     * Determine if any chess pieces block the diagonal movement of a chess piece.
     * @param row of the piece to move
     * @param column of the piece to move
     * @param spacesApart the number of steps between the piece and its destination
     * @param rowStep the size of a single step between rows (-1 or 1)
     * @param columnStep the size of a single step between columns (-1 or 1)
     * @return true if no pieces block the diagonal moving piece, false otherwise
     */
    private boolean hasClearDiagonal(int row, int column, int spacesApart, int rowStep, int columnStep)
    {
        assert isOnTheBoard(row, column);
        assert isOnTheBoard(row + spacesApart * rowStep, column + spacesApart * columnStep);
        assert rowStep == 1 || rowStep == -1 : rowStep;
        assert columnStep == 1 || columnStep == -1 : columnStep;
        for (int offset = 1; offset < spacesApart; ++offset) {
            if (!isEmptySpace(row + (offset * rowStep), column + (offset * columnStep)))
                return false;
        }
        return true;
    }

    /**
     * Determine if a given position is on the ChessBoard.
     * @param row to inspect (1-8 is valid)
     * @param column to inspect (1-8 is valid)
     * @return true if the position is on the board, false otherwise.
     */
    private boolean isOnTheBoard(int row, int column)
    {
        boolean validRow = (1 <= row && row <= 8);
        boolean validColumn = (1 <= column && column <= 8);
        return validRow && validColumn;
    }
}
