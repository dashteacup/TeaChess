package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller Class for the chess game project.
 */
public class ChessController implements ActionListener {

    /**
     * The game's View.
     */
    private ChessGameView view;

    /**
     * The game's Model of the chess board.
     */
    private ChessBoard modelBoard;

    /**
     * Color of the player whose turn we are doing.
     */
    private ChessPieceColor currentPlayerColor;

    /**
     * Determine if the player has picked a piece to move.
     */
    private boolean pieceIsSelected;

    /**
     * The button corresponding with the chess piece you intend to move.
     */
    private ChessSpaceButton currentlySelectedButton;

    /**
     * Flag indicating whether or not the current game has ended.
     * Used to know when to ignore new actions from {@link ChessSpaceButton}.
     */
    private boolean gameIsOver;

    /**
     * Create a new game controller.
     */
    public ChessController()
    {
        view = new ChessGameView(this);
        setupNewChessGame();
    }

    /**
     * Handle user actions.
     */
    @Override
    public void actionPerformed(ActionEvent event)
    {
        String actionCommand = event.getActionCommand();
        if (actionCommand == ChessGameView.NEW_GAME_MENU_ITEM) {
            setupNewChessGame();
        } else if (actionCommand == ChessGameView.CLOSE_MENU_ITEM) {
            System.exit(0);
        } else if (actionCommand == ChessGameView.PRINT_BOARD_MENU_ITEM) {
            modelBoard.printBoard();
        // Clicked one of the chess spaces
        } else if (!gameIsOver) {
            buttonClickedAction((ChessSpaceButton) event.getSource());
        }
    }

    /**
     * Prepare a new chess game. This sets the model, the view, and all
     * controller variables to their default values.
     */
    private void setupNewChessGame()
    {
        view.startNewGame();
        modelBoard = new ChessBoard();
        // White player always goes first in chess.
        currentPlayerColor = ChessPieceColor.WHITE;
        pieceIsSelected = false;
        currentlySelectedButton = null;
        gameIsOver = false;
    }

    /**
     * Actions to be performed when receiving a button clicked event.
     */
    private void buttonClickedAction(ChessSpaceButton clickedButton)
    {
        assert clickedButton != null;
        if (pieceIsSelected) {
            if (isValidMove(clickedButton)) {
                moveCurrentlySelectedPiece(clickedButton);
                endTurn();
            // selecting a new piece of the same color
            } else if (clickedButton.getPieceColor() == currentPlayerColor) {
                view.clearMarkedSpaces();
                clickedButton.selectSpace();
                currentlySelectedButton = clickedButton;
                highlightValidMoves();
            }
            // do nothing if it's an invalid move
        // no piece currently selected
        } else if (!clickedButton.isEmptySpace() && clickedButton.getPieceColor() == currentPlayerColor) {
            clickedButton.selectSpace();
            currentlySelectedButton = clickedButton;
            pieceIsSelected = true;
            highlightValidMoves();
        }
    }

    /**
     * Determine if the currently selected chess piece can move to the space
     * clicked by the user.
     * @param clickedButton the chess piece will move to
     * @return true if it's a legal chess move, false otherwise
     */
    private boolean isValidMove(ChessSpaceButton clickedButton)
    {
        final int clickedRow = viewRowToModel(clickedButton.getRow());
        final int clickedColumn = viewColumnToModel(clickedButton.getColumn());
        final int selectedRow = viewRowToModel(currentlySelectedButton.getRow());
        final int selectedColumn = viewColumnToModel(currentlySelectedButton.getColumn());
        return modelBoard.isValidMove(selectedRow, selectedColumn,
                                      clickedRow,  clickedColumn);
    }

    /**
     * Move the currently selected piece to the chess space clicked by the user.
     * @param clickedButton the chess piece will move to
     */
    private void moveCurrentlySelectedPiece(ChessSpaceButton clickedButton)
    {
        assert pieceIsSelected;
        final int selectedRow = currentlySelectedButton.getRow();
        final int selectedColumn = currentlySelectedButton.getColumn();
        final int clickedRow = clickedButton.getRow();
        final int clickedColumn = clickedButton.getColumn();
        // have to translate board positions because the model and view have different layout
        final int modelOldRow = viewRowToModel(selectedRow);
        final int modelOldColumn = viewColumnToModel(selectedColumn);
        final int modelNewRow = viewRowToModel(clickedRow);
        final int modelNewColumn = viewColumnToModel(clickedColumn);

        view.moveChessPiece(selectedRow, selectedColumn, clickedRow, clickedColumn);
        if (modelBoard.canCastle(modelOldRow, modelOldColumn, modelNewRow, modelNewColumn)) {
            modelBoard.castle(modelOldRow, modelOldColumn, modelNewRow, modelNewColumn);
            if (modelNewColumn > modelOldColumn)
                view.moveChessPiece(clickedRow, 7, clickedRow, 5); // castle right
            else
                view.moveChessPiece(clickedRow, 0, clickedRow, 3); // castle left
        } else if (modelBoard.canEnPassant(modelOldRow, modelOldColumn, modelNewRow, modelNewColumn)) {
            modelBoard.enPassant(modelOldRow, modelOldColumn, modelNewRow, modelNewColumn);
            view.emptySpace(selectedRow, clickedColumn);
        } else {
            modelBoard.move(modelOldRow, modelOldColumn, modelNewRow, modelNewColumn);
        }

        if (modelBoard.canPromotePawn(modelNewRow, modelNewColumn)) {
            String choice = view.chooseChessPieceToReplacePawn(clickedRow, clickedColumn);
            ChessPiece replacement;
            switch (choice) {
            case "Knight":
                replacement = new Knight(modelNewRow, modelNewColumn, currentPlayerColor);
                break;
            case "Rook":
                replacement = new Rook(modelNewRow, modelNewColumn, currentPlayerColor);
                break;
            case "Bishop":
                replacement = new Bishop(modelNewRow, modelNewColumn, currentPlayerColor);
                break;
            case "Queen":
            default:
                replacement = new Queen(modelNewRow, modelNewColumn, currentPlayerColor);
                break;
            }
            modelBoard.addPiece(replacement);
        }
    }

    /**
     * Update the status bar and change players.
     */
    private void endTurn()
    {
        final ChessPieceColor otherPlayer = currentPlayerColor.otherColor();
        // do this first, so setWinner can override it if necessary
        view.setCurrentPlayer(otherPlayer);
        if (modelBoard.checkmate(otherPlayer)) {
            view.setWinner(currentPlayerColor);
            gameIsOver = true;
        } else if (modelBoard.stalemate(otherPlayer)) {
            view.setWinner(ChessPieceColor.NONE);
            gameIsOver = true;
        } else if (modelBoard.inCheck(otherPlayer)) {
            view.setCheckCondition(otherPlayer);
        } else {
            view.setCheckCondition(ChessPieceColor.NONE);
        }
        pieceIsSelected = false;
        view.clearMarkedSpaces();
        currentPlayerColor = otherPlayer;
    }

    /**
     * Translate a row in the chess board's view to its row in the model.
     * @param viewRow the row in the view
     * @return the corresponding row in the model
     */
    private int viewRowToModel(int viewRow)
    {
        return ChessBoard.BOARD_SIZE - viewRow;
    }

    /**
     * Translate a column in the chess board's view to its column in the model
     * @param viewColumn the column in the view
     * @return the corresponding column in the model
     */
    private int viewColumnToModel(int viewColumn)
    {
        return 1 + viewColumn;
    }

    /**
     * Highlight all valid moves for the currently selected piece.
     */
    private void highlightValidMoves()
    {
        int selectedRow = currentlySelectedButton.getRow();
        int selectedColumn = currentlySelectedButton.getColumn();
        for (int row = 0; row < ChessBoard.BOARD_SIZE; row++) {
            for (int col = 0; col < ChessBoard.BOARD_SIZE; col++) {
                if (modelBoard.isValidMove(viewRowToModel(selectedRow), viewColumnToModel(selectedColumn),
                                           viewRowToModel(row),         viewColumnToModel(col))) {
                    view.highlightSpace(row, col);
                }
            }
        }
    }
}
