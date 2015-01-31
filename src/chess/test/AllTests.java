package chess.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for running all of my tests in the chess project.
 */
@RunWith(Suite.class)
@SuiteClasses({
    BishopTest.class,
    ChessBoardTest.class,
    ChessPieceColorTest.class,
    KingTest.class,
    KnightTest.class,
    PawnTest.class,
    QueenTest.class,
    RookTest.class
})
public class AllTests {

}
