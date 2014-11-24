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
        modelBoard = new ChessBoard();
        view = new ChessGameView(this);
        // White player always goes first in chess.
        currentPlayerColor = ChessPieceColor.WHITE;
        pieceSelected = false;
    }
    
    /**
     * Run the main game loop.
     */
    public static void main(String[] args) 
    {
        new ChessController();
    }
    
    /**
     * Handle user actions.
     */
    public void actionPerformed(ActionEvent event)
    {
        // Clicked the new game menu item.
        if (event.getActionCommand() == "New Game") {
            view.startNewGame();
            modelBoard = new ChessBoard();
            currentPlayerColor = ChessPieceColor.WHITE;
            pieceSelected = false;
        }
        else {
            buttonLastPicked = (ChessSpaceButton) event.getSource();
            buttonClickedAction();
        }
                    
    }
    
    /**
     * Actions to be performed when receiving a button clicked event.
     */
    public void buttonClickedAction()
    {
        int newRow = buttonLastPicked.getRow();
        int newColumn = buttonLastPicked.getColumn();
        if (pieceSelected) {
            // No moving to your own place, just deselect current piece.
            if (!(newRow == selectedRow && newColumn == selectedColumn)) {
                // have to translate board positions.. I should rewrite chessboard to be consistant
                if (modelBoard.checkValidMove(8 - selectedRow, 1 + selectedColumn, 8 - newRow, 1 + newColumn)) {
                    view.moveChessPiece(selectedRow, selectedColumn, newRow, newColumn);
                    modelBoard.forceMove(8 - selectedRow, 1 + selectedColumn, 8 - newRow, 1 + newColumn);
                    changePlayers();
                }
            }
            pieceSelected = false;
        }
        else {
            // Can't move around empty spaces
            if (!buttonLastPicked.isEmptySpace() && buttonLastPicked.getPieceColor() == currentPlayerColor) {
                selectedRow = newRow;
                selectedColumn = newColumn;
                pieceSelected = true;
            }
        } 
    }

    /**
     * Change the color of the current player from black to white or white to black. 
     */
    private void changePlayers()
    {
        if (currentPlayerColor == ChessPieceColor.WHITE) {
            currentPlayerColor = ChessPieceColor.BLACK;
            view.setStatusLabel("Black's turn.");
        }
        else {
            currentPlayerColor = ChessPieceColor.WHITE;
            view.setStatusLabel("White's turn.");
        }
    }

    /**
     * The game's Model of the chess board.
     */
    private ChessBoard modelBoard;
    
    /**
     * The game's View.
     */
    private ChessGameView view;
    
    /**
     * The button last selected by the user.
     */
    private ChessSpaceButton buttonLastPicked;
    
    /**
     * Color of the player whose turn we are doing.
     */
    private ChessPieceColor currentPlayerColor;
    
    /**
     * Determine if the player has picked a piece to move.
     */
    private boolean pieceSelected;
    
    /**
     * selected row
     */
    private int selectedRow;
    
    /**
     * selected column
     */
    private int selectedColumn;
}
