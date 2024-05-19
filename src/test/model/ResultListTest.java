package model;

import model.exceptions.MatchNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


import static org.junit.jupiter.api.Assertions.*;

public class ResultListTest {
    private ResultList testList;
    private ResultList testList2;
    private Result testResult;
    private Result testResult2;
    private Result testResult3;
    private ByteArrayOutputStream output;


    @BeforeEach
    void runBefore() {
        testList = new ResultList("Burnaby Regionals");
        testList2 = new ResultList("2023 Nats");
        testResult = new Result("02/10/24", true, "win",
                "Drytron", "Swordsoul", "Will");
        testResult2 = new Result("02/11/24", false, "loss",
                "ABC", "Branded", "Ben");
        testResult3 = new Result("07/06/23", false, "draw",
                "Kashtira", " Dark World", "Patrick");
        testList.addResult(testResult);



    }

    @Test
    void testResultList() {
        assertEquals(1, testList.getListSize());
    }

    @Test
    void testAddResult() {
        assertEquals(1, testList.getListSize());
        testList.addResult(testResult2);
        assertEquals(2, testList.getListSize());

    }

    @Test
    void testViewMatchResults() {
        testList2.addResult(testResult);
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
        testList2.viewMatchResults();
        String expectedOutput = "Match results:\r\n" +
                "Match ID: 1, Date: 02/10/24, Result: win, Die roll: win, " +
                "Deck: Drytron, Opponent's deck: Swordsoul, Opponent: Will\r\n" +
                "Dice roll win %: 100.0%\r\n" +
                "Wins: 1\r\n" +
                "Losses: 0\r\n" +
                "Draws: 0\r\n" +
                "Press Enter to return to main menu\r\n";

        assertEquals(expectedOutput, output.toString());


    }

    @Test
    void testGetRollResults() {
        assertEquals(1, testList.getRollResults());
        testList.addResult(testResult2);
        assertEquals(1, testList.getRollResults());

    }

    @Test
    void testRemoveResult() {
        try {
            testList.removeResult(0);
        } catch (MatchNotFoundException e) {
            fail("Not supposed to be thrown!");
        }
        assertEquals(0, testList.getListSize());
    }

    @Test
    void testRemoveWrongResult() {
        try {
            testList.removeResult(42);
            fail("Exception not thrown!");
        } catch (MatchNotFoundException e) {
            assertEquals(1, testList.getListSize());
        }
    }

    @Test
    void testRemoveWrongResult2() {
        try {
            testList.removeResult(-10);
            fail("Exception not thrown!");
        } catch (MatchNotFoundException e) {
            assertEquals(1, testList.getListSize());
        }
    }


    @Test
    void testGetListSize() {
        assertEquals(1, testList.getListSize());
    }

    @Test
    void testGetDiceRollWinRate() {
        assertEquals(1.0, testList.getDiceRollWinRate());
    }

    @Test
    void testGetWins() {
        assertEquals(1, testList.getWins());
        testList.addResult(testResult2);
        assertEquals(1, testList.getWins());

    }

    @Test
    void testGetLosses() {
        assertEquals(0, testList.getLosses());
        testList.addResult(testResult2);
        assertEquals(1, testList.getLosses());

    }

    @Test
    void testGetDraws() {
        assertEquals(0, testList.getDraws());
        testList.addResult(testResult3);
        assertEquals(1, testList.getDraws());

    }

}
