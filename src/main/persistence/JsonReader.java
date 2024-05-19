package persistence;


import model.Result;
import model.ResultList;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads ResultList from JSON data stored in file

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ResultsList from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ResultList read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseResultList(jsonObject);

    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ResultList from JSON object and returns it
    private ResultList parseResultList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ResultList rl = new ResultList(name);
        addResults(rl, jsonObject);
        return rl;
    }

    // MODIFIES: rl
    // EFFECTS: parses Results from JSON object and adds them to ResultList
    private void addResults(ResultList rl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("matchResults");
        for (Object json : jsonArray) {
            JSONObject nextResult = (JSONObject) json;
            addResult(rl, nextResult);
        }
    }

    // MODIFIES: rl
    // EFFECTS: parses Result from JSON object and adds it to ResultList
    private void addResult(ResultList rl, JSONObject jsonObject) {
        String matchDate = jsonObject.getString("date");
        boolean dieRoll = jsonObject.getBoolean("roll");
        String result = jsonObject.getString("matchResult");
        String deckType = jsonObject.getString("deck");
        String oppDeckType = jsonObject.getString("oppDeck");
        String oppName = jsonObject.getString("oppName");
        Result result2 = new Result(matchDate, dieRoll, result, deckType, oppDeckType, oppName);
        rl.addResult(result2);



    }
}
