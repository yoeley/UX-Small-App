package com.example.footapp;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player implements Serializable {

    @JsonProperty("playerName")
    private String playerName;

    @JsonProperty("playerId")
    private String playerId;

    @JsonProperty("playerPos")
    private String playerPosition;


    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPlayerPosition() {
        return playerPosition;
    }

    public void setPlayerPosition(String playerPosition) {
        this.playerPosition = playerPosition;
    }

    public void initPlayer() {
        if (playerName == null) playerName = "";
        if (playerId == null) playerId = "";
        if (playerPosition == null) playerPosition = "";
    }
}
