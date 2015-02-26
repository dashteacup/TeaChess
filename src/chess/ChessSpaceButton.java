package chess;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * Custom button type made so I can store additional information on a JButton.
 */
@SuppressWarnings("serial")
public class ChessSpaceButton extends JButton {
    /**
     * Path to the directory where the chess piece image files are stored.
     */
    // TODO: Make this work on systems that don't use forward slash as path
    // separator (Windows).
    private static final String IMAGE_DIRECTORY = "src/chess/images/";

    /**
     * Flag marking whether or not this space contains a chess piece.
     */
    private boolean emptySpace;

    /**
     * Color of the chess piece in this space.
     */
    private ChessPieceColor pieceColor;

    /**
     * The border style of an unselected space on the chess board.
     */
    private Border unselectedBorder;

    /**
     * The border style of a selected space on the chess board.
     */
    private Border selectedBorder;

    /**
     * The border style of a highlighted space on the chess board.
     */
    private Border highlightedBorder;

    /**
     * Row in the grid layout of buttons. Valid values 0 - 7.
     */
    private int row;

    /**
     * Column in the grid layout of buttons. Valid values 0 - 7.
     */
    private int column;

    /**
     * Create a new empty chess space button
     * @param row in the grid layout of buttons
     * @param column in the grid layout of buttons
     * @param backgroundColor the background color of this space.
     * @param listener the ActionListener that will receive this space's events.
     */
    public ChessSpaceButton(int row, int column, Color backgroundColor, ActionListener listener)
    {
        emptySpace = true;
        pieceColor = ChessPieceColor.NONE;
        this.row = row;
        this.column = column;

        unselectedBorder = BorderFactory.createLineBorder(Color.WHITE);
        selectedBorder = BorderFactory.createLineBorder(Color.BLACK, 5);
        Border highlighting = BorderFactory.createLineBorder(Color.GREEN, 3);
        highlightedBorder = BorderFactory.createCompoundBorder(unselectedBorder, highlighting);

        setBackground(backgroundColor);
        setOpaque(true);
        setBorder(unselectedBorder);
        addActionListener(listener);
    }

    /**
     * Make this space have a chess piece based on the given image.
     * @param iconSource file name of the chess piece's image
     */
    public void setPiece(String iconSource)
    {
        setIcon(new ImageIcon(IMAGE_DIRECTORY + iconSource));
        emptySpace = false;
        if (iconSource.startsWith("white"))
            pieceColor = ChessPieceColor.WHITE;
        else if (iconSource.startsWith("black"))
            pieceColor = ChessPieceColor.BLACK;
    }

    /**
     * Move one space's button properties to another space
     * @param newSpace the button that this space will move to.
     */
    public void movePiece(ChessSpaceButton newSpace)
    {
        newSpace.setIcon(getIcon());
        newSpace.setPieceColor(getPieceColor());
        newSpace.setSpaceOccupied();
        setEmptySpace();
    }

    /**
     * Mark this space as selected.
     */
    public void selectSpace()
    {
        setBorder(selectedBorder);
    }

    /**
     * Mark this space as unselected.
     */
    public void unselectSpace()
    {
        setBorder(unselectedBorder);
    }

    /**
     * Highlight this space. This is used to indicate valid moves.
     */
    public void highlightSpace()
    {
        setBorder(highlightedBorder);
    }

    /**
     * Determine if this chess space is empty.
     */
    public boolean isEmptySpace()
    {
        return emptySpace;
    }

    /**
     * Make this space empty.
     */
    public void setEmptySpace()
    {
        emptySpace = true;
        pieceColor = ChessPieceColor.NONE;
        super.setIcon(null);
    }

    /**
     * Mark this space as not empty.
     */
    public void setSpaceOccupied()
    {
        emptySpace = false;
    }

    /**
     * @return the color of the chess piece.
     */
    public ChessPieceColor getPieceColor()
    {
        return pieceColor;
    }

    /**
     * @param pieceColor the pieceColor to set
     */
    public void setPieceColor(ChessPieceColor pieceColor)
    {
        this.pieceColor = pieceColor;
    }

    /**
     * @return the row of this chess space in the grid layout of buttons.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @return the column of this chess space in the grid layout of buttons.
     */
    public int getColumn()
    {
        return column;
    }
}
