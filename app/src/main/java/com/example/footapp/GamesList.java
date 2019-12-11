package com.example.footapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GamesList implements Serializable {

    @JsonProperty("numGames")
    private int numGames;

    @JsonProperty("games")
    private List<GameData> gameDataList;


    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public List<GameData> getGameDataList() {
        return gameDataList;
    }

    public void setTeamData(List<GameData> gameDataList) {
        this.gameDataList = gameDataList;
    }


    public static GamesList JSONToGamesList(String gamesListString){
        final ObjectMapper mapper = new ObjectMapper();
        GamesList gamesList = new GamesList();
        try {
            gamesList = mapper.readValue(gamesListString, GamesList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gamesList;
    }


    public static String GamesListToJSON(GamesList gamesList){
        final ObjectMapper mapper = new ObjectMapper();
        String gamesListString = "";
        try {
            gamesListString = mapper.writeValueAsString(gamesList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return gamesListString;
    }
}
