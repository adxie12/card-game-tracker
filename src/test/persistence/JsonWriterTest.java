package persistence;

import model.Result;
import model.ResultList;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ResultList rl = new ResultList("My results list");
            JsonWriter writer = new JsonWriter(".data/budingisverycool.json");
            writer.open();
            fail("Exception was supposed to be thrown!");
        } catch (IOException e) {
            //
        }
    }

    @Test
    void testWriterEmptyResultList() {
        try {
            ResultList rl = new ResultList("My results list");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyResultList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyResultList.json");
            rl = reader.read();
            assertEquals("My results list", rl.getName());
            assertEquals(0, rl.getListSize());
        } catch (IOException e) {
            fail("No exception should be thrown!");
        }
    }

    @Test
    void testWriterGeneralResultList() {
        try {
            ResultList rl = new ResultList("My results list");
            rl.addResult(new Result("March 7, 2024", true, "win", "ABC", "HERO", "Chris"));
            rl.addResult(new Result("March 6, 2024", false, "lose", "Exosister", "Floo", "Josh"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralResultList.json");
            writer.open();
            writer.write(rl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralResultList.json");
            rl = reader.read();
            assertEquals("My results list", rl.getName());
            List<Result> results = rl.getResults();
            assertEquals(2, results.size());
            checkResult("March 7, 2024", true, "win", "ABC", "HERO", "Chris", results.get(0));
            checkResult("March 6, 2024", false, "lose", "Exosister", "Floo", "Josh", results.get(1));

        } catch (IOException e) {
            fail("No exception should be thrown!");
        }

    }
}
