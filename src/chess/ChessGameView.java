package chess;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * View Class for the chess board.
 */
public class ChessGameView {
    /**
     * Create a new gui for the chess program.
     */
    public ChessGameView(ChessController gameController)
    {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception e) {
            //silently ignore
        }
        myController = gameController;
        gameWindow = new JFrame("Chess Program");
        gameWindow.setSize(windowWidth, windowHeight);
        startNewGame();
        setUpMenu(gameWindow);
        gameWindow.setVisible(true);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Set the board position to what it should be in a new game.
     */
    public void startNewGame()
    {
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(windowWidth, windowHeight));
        boardPanel.setLayout(new GridLayout(chessBoardRows, chessBoardColumns));
        buttonCollection = new JButton[chessBoardRows][chessBoardColumns]; 
        addButtons(boardPanel);
        gameWindow.setContentPane(boardPanel);
        gameWindow.setVisible(true);
    }
  
    /**
     * Add all the buttons representing the squares and pieces on the chess board.
     * @param boardPanel
     */
    public void addButtons(JPanel boardPanel)
    {
        Color backgroundColor;
        for (int row = 0; row < chessBoardRows; ++row) {
            if (row % 2 == 0)
                backgroundColor = lightSpace;
            else backgroundColor = darkSpace;
            for (int column = 0; column < chessBoardColumns; ++column) {
                JButton b = new JButton();
                b.setBackground(backgroundColor);
                b.addActionListener(myController);
                b.setToolTipText("row: " + row + " col: " + column);
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
        for (int i = 0; i < chessBoardColumns; ++i) {
            buttonCollection[1][i].setIcon(new ImageIcon(imageDirectory + "blackPawn.png"));
            buttonCollection[6][i].setIcon(new ImageIcon(imageDirectory + "whitePawn.png"));
        }
        // set up rooks
        buttonCollection[0][0].setIcon(new ImageIcon(imageDirectory + "blackRook.png"));
        buttonCollection[0][7].setIcon(new ImageIcon(imageDirectory + "blackRook.png"));
        buttonCollection[7][0].setIcon(new ImageIcon(imageDirectory + "whiteRook.png"));
        buttonCollection[7][7].setIcon(new ImageIcon(imageDirectory + "whiteRook.png"));
        // set up knights
        buttonCollection[0][1].setIcon(new ImageIcon(imageDirectory + "blackKnight.png"));
        buttonCollection[0][6].setIcon(new ImageIcon(imageDirectory + "blackKnight.png"));
        buttonCollection[7][1].setIcon(new ImageIcon(imageDirectory + "whiteKnight.png"));
        buttonCollection[7][6].setIcon(new ImageIcon(imageDirectory + "whiteKnight.png"));
        // set up bishops
        buttonCollection[0][2].setIcon(new ImageIcon(imageDirectory + "blackBishop.png"));
        buttonCollection[0][5].setIcon(new ImageIcon(imageDirectory + "blackBishop.png"));
        buttonCollection[7][2].setIcon(new ImageIcon(imageDirectory + "whiteBishop.png"));
        buttonCollection[7][5].setIcon(new ImageIcon(imageDirectory + "whiteBishop.png"));
        // set up queens
        buttonCollection[0][3].setIcon(new ImageIcon(imageDirectory + "blackQueen.png"));
        buttonCollection[7][3].setIcon(new ImageIcon(imageDirectory + "whiteQueen.png"));
        // set up kings
        buttonCollection[0][4].setIcon(new ImageIcon(imageDirectory + "blackKing.png"));
        buttonCollection[7][4].setIcon(new ImageIcon(imageDirectory + "whiteKing.png"));
        // set up frogs
        buttonCollection[2][0].setIcon(new ImageIcon(imageDirectory + "blackFrog.png"));
        buttonCollection[2][7].setIcon(new ImageIcon(imageDirectory + "blackFrog.png"));
        buttonCollection[5][0].setIcon(new ImageIcon(imageDirectory + "whiteFrog.png"));
        buttonCollection[5][7].setIcon(new ImageIcon(imageDirectory + "whiteFrog.png"));
        // set up princes
        buttonCollection[2][4].setIcon(new ImageIcon(imageDirectory + "blackPrince.png"));
        buttonCollection[5][4].setIcon(new ImageIcon(imageDirectory + "whitePrince.png"));
    }
    
    /**
     * Create the game's menu.
     * @param window to put the menu in.
     */
    private void setUpMenu(JFrame window)
    {
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem newGame = new JMenuItem("New Game");
        newGame.addActionListener(myController);
        file.add(newGame); 
        menubar.add(file);
        window.setJMenuBar(menubar);
    }
    
    /**
     * Helper function to allow swapping back and forth between black and white.
     * @param c the current color
     * @return WHITE if input is BLACK, BLACK if input is WHITE 
     */
    private Color nextColor(Color c)
    {
        if (c == darkSpace)
            return lightSpace;
        else if (c == lightSpace)
            return darkSpace;
        return lightSpace;
    }
    
    /**
     * Reference to the controller that handles the game loop.
     */
    protected ChessController myController;

    /**
     * Default height of the game window.
     */
    private final static int windowHeight = 700;
    
    /**
     * Default width of the game window.
     */
    private final static int windowWidth = 700;
    
    /**
     * Number of rows on a chess board.
     */
    private final static int chessBoardRows = 8;
    
    /**
     * Number of columns on a chess board.
     */
    private final static int chessBoardColumns = 8;
    
    /**
     * Color of the lighter spaces on the chess board.
     */
    private final static Color lightSpace = new Color(1.0f, 0.808f, 0.62f);
    
    /**
     * Color of the darker spaces on the chess board.
     */
    private final static Color darkSpace = new Color(0.82f, 0.545f, 0.278f);
    
    /**
     * Path to the directory where the image files are stored. 
     */
    private final static String imageDirectory = "src/chess/images/";
    
    /**
     * Collection holding references to all the button objects representing spaces
     * on a chess board.
     */
    private JButton buttonCollection[][];
    
    /**
     * The window for this game.
     */
    private JFrame gameWindow;

}
