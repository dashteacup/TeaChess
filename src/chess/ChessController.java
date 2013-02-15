package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;

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
        ChessController controller = new ChessController();
    }
    
    /**
     * Handle user actions.
     */
    public void actionPerformed(ActionEvent event)
    {
        if (event.getActionCommand() == "New Game") {
            view.startNewGame();
            modelBoard = new ChessBoard();
            currentPlayerColor = ChessPieceColor.WHITE;
            pieceSelected = false;
        }
        else {
            buttonLastPicked = (JButton) event.getSource();
            String[] toolTip = buttonLastPicked.getToolTipText().split(" ");
            if (pieceSelected) {
                int newRow = Integer.valueOf(toolTip[0]);
                int newColumn = Integer.valueOf(toolTip[1]);
                view.movePieceIcon(selectedRow, selectedColumn, newRow, newColumn);
                pieceSelected = false;
            }
            else {
                selectedRow = Integer.valueOf(toolTip[0]);
                selectedColumn = Integer.valueOf(toolTip[1]);
                pieceSelected = true;
            } 
        }
    }

    /**
     * The game's Model of the chess board.
     */
    protected ChessBoard modelBoard;
    
    /**
     * The game's View.
     */
    protected ChessGameView view;
    
    /**
     * The button last selected by the user.
     */
    protected JButton buttonLastPicked;
    
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
