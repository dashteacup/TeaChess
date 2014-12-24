package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller Class for the chess game project.
 */
public class ChessController implements ActionListener {

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
            view.startNewGame();
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
     * Actions to be performed when receiving a button clicked event.
     */
    public void buttonClickedAction(ChessSpaceButton buttonClicked)
    {
        final int clickedRow = buttonClicked.getRow();
        final int clickedColumn = buttonClicked.getColumn();
        if (pieceSelected) {
            final int selectedRow = currentlySelectedButton.getRow();
            final int selectedColumn = currentlySelectedButton.getColumn();
            // have to translate board positions because the model and view have different layout
            if (modelBoard.isValidMove(viewRowToModel(selectedRow), viewColumnToModel(selectedColumn),
                                       viewRowToModel(clickedRow),  viewColumnToModel(clickedColumn))) {
                view.moveChessPiece(selectedRow, selectedColumn, clickedRow, clickedColumn);
                modelBoard.move(viewRowToModel(selectedRow), viewColumnToModel(selectedColumn),
                                viewRowToModel(clickedRow),  viewColumnToModel(clickedColumn));
                changePlayers();
                currentlySelectedButton.deselectSpace();
                pieceSelected = false;
            // selecting a new piece of the same color
            } else if (buttonClicked.getPieceColor() == currentPlayerColor) {
                currentlySelectedButton.deselectSpace();
                buttonClicked.selectSpace();
                currentlySelectedButton = buttonClicked;
            }
            // do nothing if it's an invalid move
        // no piece currently selected
        } else if (!buttonClicked.isEmptySpace() && buttonClicked.getPieceColor() == currentPlayerColor) {
            buttonClicked.selectSpace();
            currentlySelectedButton = buttonClicked;
            pieceSelected = true;
        }
    }

    /**
     * Prepare the controller for a new chess game. This sets all variables except
     * the view to their default values.
     */
    private void setupNewChessGame()
    {
        modelBoard = new ChessBoard();
        // White player always goes first in chess.
        currentPlayerColor = ChessPieceColor.WHITE;
        pieceSelected = false;
        currentlySelectedButton = null;
    }

    /**
     * Change the color of the current player from black to white or white to black.
     */
    private void changePlayers()
    {
        if (currentPlayerColor == ChessPieceColor.WHITE) {
            currentPlayerColor = ChessPieceColor.BLACK;
            view.setStatusLabel("Black's turn.");
        } else { // (currentPlayerColor == ChessPieceColor.BLACK)
            currentPlayerColor = ChessPieceColor.WHITE;
            view.setStatusLabel("White's turn.");
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
    private boolean pieceSelected;

    /**
     * The button corresponding with the chess piece you intend to move.
     */
    private ChessSpaceButton currentlySelectedButton;
}
