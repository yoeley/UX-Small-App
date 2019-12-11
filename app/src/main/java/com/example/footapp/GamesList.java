package com.example.footapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GamesList implements Serializable {

    public static final int maxNumGames = 3;

    public GamesList() {

    }

    @JsonProperty("numGames")
    private int numGames;

    @JsonProperty("gameDataList")
    private ArrayList<GameData> gameDataList;


    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public ArrayList<GameData> getGameDataList() {
        return gameDataList;
    }

    public void setGameDataList(ArrayList<GameData> gameDataList) {
        this.gameDataList = gameDataList;
    }

    public void initGamesList() {
        if (gameDataList== null) {
            gameDataList = new ArrayList<GameData>();
            for (int i = 0; i < maxNumGames; ++i) {
                GameData gameData = new GameData();
                gameData.initGameData();
                gameDataList.add(gameData);
            }
        }
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
