package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents the result of a match, with details
public class Result implements Writable {
    private String matchDate; // date of match
    private boolean dieRoll; // true if user won die roll, false if user lost die roll
    private String result; // win, loss, or draw
    private String deckType; // type of deck the user used
    private String oppDeckType; // type of deck the user's opponent used
    private String oppName; // name of user's opponent
    private static int nextMatchID = 1; // tracks id of next match created
    private int matchID; // match id


    // EFFECTS: creates a new match result, with the user inputting:
    //          - date the match was played
    //          - result of the match
    //          - result of the die roll
    //          - type of deck the user played
    //          - type of deck the user's opponent played
    //          - the name of the user's opponent
    //          the match ID is generated with the next positive integer up from the previous match ID.
    //          the user can fill in as many or as few fields as they like - not all fields are mandatory!
    public Result(String date, boolean roll, String matchResult,
                  String deck, String oppDeck, String name) {

        matchID = nextMatchID++;
        result = matchResult;
        dieRoll = roll;
        deckType = deck;
        oppDeckType = oppDeck;
        oppName = name;
        matchDate = date;

    }

    public String getDate() {
        return matchDate;
    }

    public boolean getRoll() {
        return dieRoll;
    }

    public String getMatchResult() {
        return result;
    }

    public String getDeckType() {
        return deckType;
    }

    public String getOppDeckType() {
        return oppDeckType;
    }

    public String getOppName() {
        return oppName;
    }


    // EFFECTS: returns assigned match ID number
    public int getMatchID() {
        return matchID;
    }

    // EFFECTS: returns a string representation of match history
    @Override
    public String toString() {
        String dieRollResult = dieRoll ? "Win" : "Lose";
        return "Match ID: " + matchID
                +
                ", Date: " + matchDate
                +
                ", Result: " + result
                +
                ", Die roll: " + dieRollResult.toLowerCase()
                +
                ", Deck: " + deckType
                +
                ", Opponent's deck: " + oppDeckType
                +
                ", Opponent: " + oppName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", matchDate);
        json.put("roll", dieRoll);
        json.put("matchResult", result);
        json.put("deck", deckType);
        json.put("oppDeck", oppDeckType);
        json.put("oppName", oppName);
        return json;

    }


}


