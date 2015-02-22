package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * View Class for the chess board.
 */
public class ChessGameView {

    /**
     * The text for the menu item that starts a new game.
     */
    public static final String NEW_GAME_MENU_ITEM = "New Game";

    /**
     * The text for the menu item that closes the app.
     */
    public static final String CLOSE_MENU_ITEM = "Close";

    /**
     * The text for the menu item that prints the current board state to the
     * console.
     */
    public static final String PRINT_BOARD_MENU_ITEM = "Print Board";

    /**
     * Default height of the game window.
     */
    private static final int WINDOW_HEIGHT = 600;

    /**
     * Default width of the game window.
     */
    private static final int WINDOW_WIDTH = 600;

    /**
     * Number of rows on a chess board.
     */
    private static final int CHESS_BOARD_ROWS = ChessBoard.BOARD_SIZE;

    /**
     * Number of columns on a chess board.
     */
    private static final int CHESS_BOARD_COLUMNS = ChessBoard.BOARD_SIZE;

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
     * A label that shows who can make the next move.
     */
    private JLabel currentPlayerLabel;

    /**
     * A label that shows if a player's king is in check.
     */
    private JLabel checkConditionLabel;

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
        // layout for the entire frame
        JPanel totalLayoutPanel = new JPanel(new BorderLayout());

        // layout for the content (chess board and status labels)
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.PAGE_AXIS));
        contentPanel.add(createStatusBarPanel());
        contentPanel.add(createChessBoardPanel());

        totalLayoutPanel.add(contentPanel, BorderLayout.CENTER);
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
     * Set the current player label to the appropriate text and color.
     * @param playerColor of the current player
     */
    public void setCurrentPlayer(ChessPieceColor playerColor)
    {
        switch(playerColor) {
        case WHITE:
            setCurrentPlayerLabel(playerColor, "White's turn.");
            break;
        case BLACK:
            setCurrentPlayerLabel(playerColor, "Black's turn.");
            break;
        case NONE:
            break;
        }
    }

    /**
     * Set the label that shows if a player's king is in check.
     * @param playerColor of the player in check
     */
    public void setCheckCondition(ChessPieceColor playerColor)
    {
        checkConditionLabel.setOpaque(true);
        switch (playerColor) {
        case WHITE:
            checkConditionLabel.setText("White King in check.");
            break;
        case BLACK:
            checkConditionLabel.setText("Black King in check.");
            break;
        case NONE:
            checkConditionLabel.setText("");
            checkConditionLabel.setOpaque(false); // hide background
            break;
        }
    }

    /**
     * Set the status bar labels to indicate the winner of the game.
     * @param player who won the game, or NONE if it was a draw
     */
    public void setWinner(ChessPieceColor player)
    {
        checkConditionLabel.setOpaque(true);
        checkConditionLabel.setBackground(Color.RED);
        switch (player) {
        case WHITE:
            checkConditionLabel.setText("Checkmate!");
            setCurrentPlayerLabel(player, "White wins!");
            break;
        case BLACK:
            checkConditionLabel.setText("Checkmate!");
            setCurrentPlayerLabel(player, "Black wins!");
            break;
        case NONE:
            checkConditionLabel.setText("Stalemate!");
            setCurrentPlayerLabel(player, "A draw!");
            break;
        }
    }

    /**
     * Display a dialog box asking the user which chess piece they will replace
     * their pawn with.
     * @param row of the chess piece in the view
     * @param column of the chess piece in the view
     * @return "Queen", "Knight", "Rook", or "Bishop" depending on user selection
     */
    public String chooseChessPieceToReplacePawn(int row, int column)
    {
        // TODO: I should replace this with a real custom dialog later.
        ChessSpaceButton space = getSpace(row, column);
        Object[] choices = { "Queen", "Knight", "Rook", "Bishop" };
        String message = "Choose which chess piece will replace your pawn:";
        String choice;
        do {
            choice = (String) JOptionPane.showInputDialog(gameWindow,
                                                          message,
                                                          "Promote Pawn",
                                                          JOptionPane.QUESTION_MESSAGE,
                                                          space.getIcon(),
                                                          choices,
                                                          "Queen");
        } while (choice == null); // user hit cancel, have to promote pawn to something
        String color = space.getPieceColor().toString().toLowerCase();
        space.setPiece(color  + choice + ".png");
        return choice;
    }

    /**
     * Set the currentPlayerLabel's text with an appropriate color scheme.
     * @param playerColor whose turn it is
     * @param text to show on the label
     */
    private void setCurrentPlayerLabel(ChessPieceColor playerColor, String text)
    {
        currentPlayerLabel.setText(text);
        switch (playerColor) {
        case WHITE:
            currentPlayerLabel.setForeground(Color.BLACK);
            currentPlayerLabel.setBackground(Color.WHITE);
            break;
        case BLACK:
            currentPlayerLabel.setForeground(Color.WHITE);
            currentPlayerLabel.setBackground(Color.BLACK);
            break;
        case NONE:
            currentPlayerLabel.setOpaque(false);
            currentPlayerLabel.setForeground(Color.BLACK);
            break;
        }
    }

    /**
     * Create the game's menu bar.
     * @return a new JMenuBar
     */
    private JMenuBar createGameMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem newGameMenuItem = new JMenuItem(NEW_GAME_MENU_ITEM);
        newGameMenuItem.addActionListener(myController);
        fileMenu.add(newGameMenuItem);

        JMenuItem closeMenuItem = new JMenuItem(CLOSE_MENU_ITEM);
        closeMenuItem.addActionListener(myController);
        fileMenu.add(closeMenuItem);

        menuBar.add(fileMenu);

        JMenu printMenu = new JMenu("Debug");
        JMenuItem printMenuItem = new JMenuItem(PRINT_BOARD_MENU_ITEM);
        printMenuItem.addActionListener(myController);
        printMenu.add(printMenuItem);
        menuBar.add(printMenu);

        return menuBar;
    }

    /**
     * Create a status bar panel for displaying information about the current
     * game's state.
     * @return a new status bar panel
     */
    private JPanel createStatusBarPanel()
    {
        JPanel statusBarPanel = new JPanel();
        statusBarPanel.setLayout(new BoxLayout(statusBarPanel, BoxLayout.LINE_AXIS));

        currentPlayerLabel = new JLabel();
        setCurrentPlayer(ChessPieceColor.WHITE);
        currentPlayerLabel.setOpaque(true); // show bg color
        int horizontalBorder = 20;
        int verticalBorder = horizontalBorder / 2;
        currentPlayerLabel.setBorder(BorderFactory.createEmptyBorder(verticalBorder, horizontalBorder, verticalBorder, horizontalBorder));
        statusBarPanel.add(currentPlayerLabel);

        statusBarPanel.add(Box.createHorizontalGlue());

        checkConditionLabel = new JLabel();
        checkConditionLabel.setBackground(Color.PINK);
        checkConditionLabel.setOpaque(false); // hide bg color initially
        checkConditionLabel.setBorder(BorderFactory.createEmptyBorder(verticalBorder, horizontalBorder, verticalBorder, horizontalBorder));
        statusBarPanel.add(checkConditionLabel);
        return statusBarPanel;
    }

    /**
     * Create the panel that holds the whole chess board.
     * @return a new chess board panel
     */
    private JPanel createChessBoardPanel()
    {
        JPanel boardPanel = new JPanel();
        boardPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        boardPanel.setLayout(new GridLayout(CHESS_BOARD_ROWS, CHESS_BOARD_COLUMNS));
        buttonCollection = new ChessSpaceButton[CHESS_BOARD_ROWS][CHESS_BOARD_COLUMNS];
        addButtons(boardPanel);
        return boardPanel;
    }

    /**
     * Add all the buttons representing the squares and pieces on the chess board.
     * @param boardPanel to add the buttons to
     */
    private void addButtons(JPanel boardPanel)
    {
        assert boardPanel != null;
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
        assert buttonCollection != null;
        assert buttonCollection.length == CHESS_BOARD_ROWS : buttonCollection.length;
        assert buttonCollection[7].length == CHESS_BOARD_COLUMNS : buttonCollection[7].length;
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
