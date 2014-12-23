package chess;

import javax.swing.UIManager;

/**
 * Wrapper for the chess program's main method.
 */
public final class TeaChess {

    /**
     * Run the main game loop.
     */
    public static void main(String[] args)
    {
        setupSystem();
        new ChessController();
    }

    /**
     * Set up the system-wide settings for the TeaChess app.
     */
    public static void setupSystem()
    {
        // Use the OS's native widgets
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
        if (isMacOS()) {
            // setup mac menu bar
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
    }

    /**
     * Determine if the operating system is Mac OS X.
     * @return true if you're on Mac OS X, false otherwise
     */
    public static boolean isMacOS()
    {
        return System.getProperty("os.name").contains("OS X");
    }

}
