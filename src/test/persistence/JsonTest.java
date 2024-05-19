package persistence;

import model.Result;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkResult(String matchDate, boolean dieRoll, String result, String deckType, String oppDeckType, String oppName, Result result2) {
        assertEquals(matchDate, result2.getDate());
        assertEquals(dieRoll, result2.getRoll());
        assertEquals(deckType, result2.getDeckType());
        assertEquals(oppDeckType, result2.getOppDeckType());
        assertEquals(oppName, result2.getOppName());
    }
}
