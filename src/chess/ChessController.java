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
        if (event.getActionCommand() == "New Game") {
            view.startNewGame();
            modelBoard = new ChessBoard();
            currentPlayerColor = ChessPieceColor.WHITE;
        }
        else {
            buttonLastPicked = (JButton) event.getSource();
            JOptionPane.showMessageDialog(null,
                                          "I was clicked by "+ buttonLastPicked.getToolTipText(),
                                          "Square Clicked", 
                                          JOptionPane.INFORMATION_MESSAGE);
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
    

}
