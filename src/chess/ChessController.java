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
        // Clicked the new game menu item.
        if (event.getActionCommand() == "New Game") {
            setupNewChessGame();
        // Clicked the close menu item.
        } else if (event.getActionCommand() == "Close") {
            System.exit(0);
        // Clicked one of the chess spaces
        } else {
            buttonClickedAction((ChessSpaceButton) event.getSource());
        }
    }

    /**
     * Prepare a new chess game. This sets the model, the view, and all
     * controller variables to their default values.
     */
    private void setupNewChessGame()
    {
        assert view != null;
        view.startNewGame();
        modelBoard = new ChessBoard();
        // White player always goes first in chess.
        currentPlayerColor = ChessPieceColor.WHITE;
        pieceIsSelected = false;
        currentlySelectedButton = null;
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
                changePlayers();
            // selecting a new piece of the same color
            } else if (clickedButton.getPieceColor() == currentPlayerColor) {
                clearMarkedSpaces();
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
        assert clickedButton != null;
        assert currentlySelectedButton != null;
        assert modelBoard != null;
        final int clickedRow = clickedButton.getRow();
        final int clickedColumn = clickedButton.getColumn();
        final int selectedRow = currentlySelectedButton.getRow();
        final int selectedColumn = currentlySelectedButton.getColumn();
        // have to translate board positions because the model and view have different layout
        return modelBoard.isValidMove(viewRowToModel(selectedRow), viewColumnToModel(selectedColumn),
                                      viewRowToModel(clickedRow),  viewColumnToModel(clickedColumn));
    }

    /**
     * Move the currently selected piece to the chess space clicked by the user.
     * @param clickedButton the chess piece will move to
     */
    private void moveCurrentlySelectedPiece(ChessSpaceButton clickedButton)
    {
        assert clickedButton != null;
        assert pieceIsSelected;
        assert currentlySelectedButton != null;
        assert view != null;
        assert modelBoard != null;
        final int clickedRow = clickedButton.getRow();
        final int clickedColumn = clickedButton.getColumn();
        final int selectedRow = currentlySelectedButton.getRow();
        final int selectedColumn = currentlySelectedButton.getColumn();

        // have to translate board positions because the model and view have different layout
        view.moveChessPiece(selectedRow, selectedColumn, clickedRow, clickedColumn);
        modelBoard.move(viewRowToModel(selectedRow), viewColumnToModel(selectedColumn),
                        viewRowToModel(clickedRow),  viewColumnToModel(clickedColumn));
        final ChessPieceColor otherPlayer = currentPlayerColor.otherColor();
        if (modelBoard.checkmate(otherPlayer)) {
            view.setWinner(currentPlayerColor);
        } else if (modelBoard.stalemate(otherPlayer)) {
            view.setWinner(ChessPieceColor.NONE);
        } else if (modelBoard.inCheck(otherPlayer)) {
            view.setCheckCondition(otherPlayer);
        } else {
            view.setCheckCondition(ChessPieceColor.NONE);
        }
        pieceIsSelected = false;
        clearMarkedSpaces();
    }

    /**
     * Change the color of the current player from black to white or white to black.
     */
    private void changePlayers()
    {
        assert view != null;
        switch(currentPlayerColor) {
        case WHITE:
            currentPlayerColor = ChessPieceColor.BLACK;
            view.setCurrentPlayer(ChessPieceColor.BLACK);
            break;
        case BLACK:
            currentPlayerColor = ChessPieceColor.WHITE;
            view.setCurrentPlayer(ChessPieceColor.WHITE);
            break;
        case NONE:
            assert false;
            break;
        }
    }

    /**
     * Translate a row in the chess board's view to its row in the model.
     * @param viewRow the row in the view
     * @return the corresponding row in the model
     */
    private int viewRowToModel(int viewRow)
    {
        return 8 - viewRow;
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
        assert currentlySelectedButton != null;
        assert modelBoard != null;
        assert view != null;
        int selectedRow = currentlySelectedButton.getRow();
        int selectedColumn = currentlySelectedButton.getColumn();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (modelBoard.isValidMove(viewRowToModel(selectedRow), viewColumnToModel(selectedColumn),
                                           viewRowToModel(row),         viewColumnToModel(col))) {
                    view.getSpace(row, col).highlightSpace();
                }
            }
        }
    }

    /**
     * Clear all selected and highlighted spaces.
     */
    private void clearMarkedSpaces()
    {
        assert view != null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                view.getSpace(row, col).deselectSpace();
            }
        }
    }
}
