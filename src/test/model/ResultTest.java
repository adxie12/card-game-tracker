package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class ResultTest {
    private Result testResult;
    private Result testResult2;
    private ResultList testList;

    @BeforeEach
    void runBefore() {
        testResult = new Result("2/10/24", true, "win",
                "Drytron", "Swordsoul", "Will");
        testResult2 = new Result("2/12/24", false, "win",
                "VS", "Purrely", "Robbie");

    }

    @Test
    void testConstructor() {
        assertEquals(testResult.getDate(), "2/10/24");
        assertTrue(testResult.getRoll());
        assertEquals(testResult.getMatchResult(), "win");
        assertEquals(testResult.getDeckType(), "Drytron");
        assertEquals(testResult.getOppDeckType(), "Swordsoul");
        assertEquals(testResult.getOppName(), "Will");

    }
    @Test
    void testToStringDieRollTrue() {
        assertTrue(testResult.toString().contains("Match ID: 1, Date: 2/10/24, Result: win, " +
                "Die roll: win, Deck: Drytron, Opponent's deck: Swordsoul, Opponent: Will"));
    }

    @Test
    void testToStringDieRollFalse() {
        assertTrue(testResult2.toString().contains("Match ID: 2, Date: 2/12/24, Result: win, " +
                "Die roll: lose, Deck: VS, Opponent's deck: Purrely, Opponent: Robbie"));
    }

    @Test
    void testGetMatchID() {
        assertEquals(1, testResult.getMatchID());
    }
}