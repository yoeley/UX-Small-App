package com.example.footapp;

import android.widget.EditText;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

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
}
