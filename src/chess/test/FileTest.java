package chess.test;

import static org.junit.Assert.*;

import org.junit.Test;

import chess.File;

/**
 * Tests for the {@link chess.File} enum.
 */
public class FileTest {

    /**
     * Ensure that getColumn matches the right files with the right columns.
     */
    @Test
    public void getColumn()
    {
        assertEquals(1, File.a.getColumn());
        assertEquals(4, File.d.getColumn());
        assertEquals(8, File.h.getColumn());
    }

    /**
     * Ensure that getFile matches the columns with the correct files.
     */
    @Test
    public void getFile()
    {
        assertEquals(File.c, File.getFile(3));
        assertEquals(File.e, File.getFile(5));
        assertEquals(File.g, File.getFile(7));
    }

    /**
     * Throw an exception when given a column number below the valid range.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getFileTooLow()
    {
        File.getFile(0);
    }

    /**
     * Throw an exception when given a column number above the valid range.
     */
    @Test(expected = IllegalArgumentException.class)
    public void getFileTooHigh()
    {
        File.getFile(9);
    }

}
