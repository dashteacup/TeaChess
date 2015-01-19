package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 * View Class for the chess board.
 */
public class ChessGameView {
    /**
     * Default height of the game window.
     */
    private static final int WINDOW_HEIGHT = 700;

    /**
     * Default width of the game window.
     */
    private static final int WINDOW_WIDTH = 700;

    /**
     * Number of rows on a chess board.
     */
    private static final int CHESS_BOARD_ROWS = 8;

    /**
     * Number of columns on a chess board.
     */
    private static final int CHESS_BOARD_COLUMNS = 8;

    /**
     * Color of the lighter spaces on the chess board.
     */
    private static final Color LIGHT_SPACE = new Color(1.0f, 0.808f, 0.62f);

    /**
     * Color of the darker spaces on the chess board.
     */
    private static final Color DARK_SPACE = new Color(0.82f, 0.545f, 0.278f);

    /**
     * Reference to the controller that handles the game loop.
     */
    private ChessController myController;

    /**
     * Collection holding references to all the button objects representing spaces
     * on a chess board.
     */
    private ChessSpaceButton buttonCollection[][];

    /**
     * The window for this game.
     */
    private JFrame gameWindow;

    /**
     * Gives status information about the currently running game.
     */
    private JLabel statusLabel;

    /**
     * Create a new frame (top-level container) for the chess program.
     */
    public ChessGameView(ChessController gameController)
    {
        myController = gameController;
        gameWindow = new JFrame("TeaChess");
        gameWindow.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        gameWindow.setJMenuBar(createGameMenuBar());
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Set the board position to what it should be in a new game.
     */
    public void startNewGame()
    {
        JPanel totalLayoutPanel = new JPanel(new BorderLayout());
        statusLabel = new JLabel("White's turn.");
        totalLayoutPanel.add(statusLabel, BorderLayout.PAGE_START);

        // make the chess board's panel
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        boardPanel.setLayout(new GridLayout(CHESS_BOARD_ROWS, CHESS_BOARD_COLUMNS));
        buttonCollection = new ChessSpaceButton[CHESS_BOARD_ROWS][CHESS_BOARD_COLUMNS];
        addButtons(boardPanel);
        totalLayoutPanel.add(boardPanel, BorderLayout.CENTER);

        gameWindow.setContentPane(totalLayoutPanel);
        gameWindow.setVisible(true);
    }

    /**
     * Move the graphical representation of a chess piece to another space.
     */
    public void moveChessPiece(int oldRow, int oldColumn, int newRow, int newColumn)
    {
        buttonCollection[oldRow][oldColumn].movePiece(buttonCollection[newRow][newColumn]);
    }

    /**
     * @param message to display in the status area
     */
    public void setStatusLabel(String message)
    {
        statusLabel.setText(message);
    }

    /**
     * Get the chess space at the given row and column in the view.
     * @param row of the chess space (0-7)
     * @param column of the chess space (0-7)
     * @return the chess space
     */
    public ChessSpaceButton getSpace(int row, int column)
    {
        if (!( (0 <= row && row <= 7) && (0 <= column && column <= 7) )) {
            throw new OffTheChessBoardException(row, column);
        }
        return buttonCollection[row][column];
    }

    /**
     * Create the game's menu.
     * @param window to put the menu in.
     */
    private JMenuBar createGameMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGameMenuItem = new JMenuItem("New Game");
        newGameMenuItem.addActionListener(myController);
        fileMenu.add(newGameMenuItem);

        JMenuItem closeMenuItem = new JMenuItem("Close");
        closeMenuItem.addActionListener(myController);
        fileMenu.add(closeMenuItem);

        menuBar.add(fileMenu);
        return menuBar;
    }

    /**
     * Add all the buttons representing the squares and pieces on the chess board.
     * @param boardPanel
     */
    private void addButtons(JPanel boardPanel)
    {
        Color backgroundColor;
        for (int row = 0; row < CHESS_BOARD_ROWS; ++row) {
            // initial color for this row
            if (row % 2 == 0)
                backgroundColor = LIGHT_SPACE;
            else
                backgroundColor = DARK_SPACE;
            for (int column = 0; column < CHESS_BOARD_COLUMNS; ++column) {
                ChessSpaceButton b = new ChessSpaceButton(row, column, backgroundColor, myController);
                boardPanel.add(b);
                buttonCollection[row][column] = b;
                backgroundColor = nextColor(backgroundColor);
            }
        }
        setUpNewGameButtonIcons();
    }

    /**
     * Set the images to the hardcoded default positions.
     */
    private void setUpNewGameButtonIcons()
    {
        // set up pawns
        for (int i = 0; i < CHESS_BOARD_COLUMNS; ++i) {
            buttonCollection[1][i].setPiece("blackPawn.png");
            buttonCollection[6][i].setPiece("whitePawn.png");
        }
        // set up rooks
        buttonCollection[0][0].setPiece("blackRook.png");
        buttonCollection[0][7].setPiece("blackRook.png");
        buttonCollection[7][0].setPiece("whiteRook.png");
        buttonCollection[7][7].setPiece("whiteRook.png");
        // set up knights
        buttonCollection[0][1].setPiece("blackKnight.png");
        buttonCollection[0][6].setPiece("blackKnight.png");
        buttonCollection[7][1].setPiece("whiteKnight.png");
        buttonCollection[7][6].setPiece("whiteKnight.png");
        // set up bishops
        buttonCollection[0][2].setPiece("blackBishop.png");
        buttonCollection[0][5].setPiece("blackBishop.png");
        buttonCollection[7][2].setPiece("whiteBishop.png");
        buttonCollection[7][5].setPiece("whiteBishop.png");
        // set up queens
        buttonCollection[0][3].setPiece("blackQueen.png");
        buttonCollection[7][3].setPiece("whiteQueen.png");
        // set up kings
        buttonCollection[0][4].setPiece("blackKing.png");
        buttonCollection[7][4].setPiece("whiteKing.png");
    }

    /**
     * Helper function to allow swapping back and forth between black and white.
     * @param c the current color
     * @return LIGHT_SPACE if input is DARK_SPACE, DARK_SPACE if input is LIGHT_SPACE
     */
    private Color nextColor(Color c)
    {
        if (c == DARK_SPACE)
            return LIGHT_SPACE;
        else if (c == LIGHT_SPACE)
            return DARK_SPACE;
        return LIGHT_SPACE;
    }

}
