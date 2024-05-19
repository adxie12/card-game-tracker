package persistence;

import model.Result;
import model.ResultList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ResultList rl = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyResultList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyResultList.json");
        try {
            ResultList rl = reader.read();
            assertEquals("My match results", rl.getName());
            assertEquals(0, rl.getListSize());
        } catch (IOException e) {
            fail("Couldn't read from file!");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralResultList.json");
        try {
            ResultList rl = reader.read();
            assertEquals("My results list", rl.getName());
            List<Result> results = rl.getResults();
            assertEquals(1, results.size());
            checkResult("March 1, 2024", true, "lose", "Dark Magician", "Fire", "Bob", results.get(0));
        } catch(IOException e) {
            fail("Couldn't read from file");
        }
    }
}
