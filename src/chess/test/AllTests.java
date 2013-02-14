package chess.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite for running all of my tests in the chess project.
 */
@RunWith(Suite.class)
@SuiteClasses({ RookTest.class, PawnTest.class, 
                KingTest.class, BishopTest.class, QueenTest.class,
                KnightTest.class, FrogTest.class, PrinceTest.class,
                ChessBoardTest.class })
public class AllTests {

}
