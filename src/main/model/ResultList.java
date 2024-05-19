package model;

import model.exceptions.MatchNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import java.util.ArrayList;


// Represents a list where results of matches go
public class ResultList implements Writable {
    private String name;
    private ArrayList<Result> matchResults;
    private Result result;
    private Scanner input;

    public ResultList(String name) {
        this.name = name;
        matchResults = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds a match result to the list of match results
    public void addResult(Result result) {
        matchResults.add(result);
        EventLog.getInstance().logEvent(new Event("Match result from " + result.getDate()
                + " added to match log."));
    }

    public void removeChartResult(Result result) {
        matchResults.remove(result);
        EventLog.getInstance().logEvent(new Event("Match result from " + result.getDate()
                + " removed from match log."));
    }

    public void saveLog() {
        EventLog.getInstance().logEvent(new Event("Match results saved."));
    }

    public void loadLog() {
        EventLog.getInstance().logEvent(new Event("Match results loaded."));
    }

    // EFFECTS: shows all the match results inputted so far
    public void viewMatchResults() {
        EventLog.getInstance().logEvent(new Event("Logged match results viewed."));

//        System.out.println("Match results:");
//        for (Result newResult : matchResults) {
//            System.out.println(newResult.toString());
//        }
//        System.out.println("Dice roll win %: " + getDiceRollWinRate() * 100 + "%");
//        System.out.println("Wins: " + getWins());
//        System.out.println("Losses: " + getLosses());
//        System.out.println("Draws: " + getDraws());
//        System.out.println("Press Enter to return to main menu");
//
//

    }


    // EFFECTS: returns number of won dice rolls in list of match results
    public double getRollResults() {
        int wonRolls = 0;
        for (Result newResult : matchResults) {
            if (newResult.getRoll()) {
                wonRolls++;
            }
        }
        return wonRolls;
    }

    // MODIFIES: this
    // EFFECTS: removes result from list of results
    public void removeResult(int resultID) throws MatchNotFoundException {
        if (resultID < 0 || resultID > matchResults.size()) {
            throw new MatchNotFoundException();
        }
        matchResults.remove(resultID);
    }

    public double getListSize() {
        return matchResults.size();
    }

    // EFFECTS: calculates the win rate of dice rolls
    public double getDiceRollWinRate() {
        return getRollResults() / getListSize();
    }

    // EFFECTS: goes through list of results and returns number of wins
    public int getWins() {
        int wins = 0;
        for (Result newResult : matchResults) {
            if (newResult.getMatchResult().equals("win")) {
                wins++;
            }
        }
        return wins;

    }

    // EFFECTS: goes through list of results and returns number of losses
    public int getLosses() {
        int losses = 0;
        for (Result newResult : matchResults) {
            if (newResult.getMatchResult().equals("loss")) {
                losses++;
            }
        }
        return losses;

    }

    // MODIFIES: this
    // EFFECTS: goes through list of results and returns number of draws
    public int getDraws() {
        int draws = 0;
        for (Result newResult : matchResults) {
            if (newResult.getMatchResult().equals("draw")) {
                draws++;
            }
        }
        return draws;


    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("matchResults", matchResultsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray matchResultsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Result r : matchResults) {
            jsonArray.put(r.toJson());
        }

        return jsonArray;
    }

    public List<Result> getResults() {
        return Collections.unmodifiableList(matchResults);
    }




}

