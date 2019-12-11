package com.example.footapp;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TeamData implements Serializable {

    private static final String idFormat = "team%dPos%d";

    @JsonProperty("numOfPlayers")
    private int numOfPlayers;

    @JsonProperty("players")
    private ArrayList<Player> players;

    @JsonProperty("teamNumber")
    private int teamNumber;

    @JsonProperty("captain")
    private String captain;

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    public String getCaptain() {
        return captain;
    }

    public void setCaptain(String captain) {
        this.captain = captain;
    }

    public void addPlayers() {
        for (int i = 0; i < numOfPlayers; ++i) {
            Player player = new Player();
            player.initPlayer();
            player.setPlayerId(String.format(idFormat, teamNumber, i));
            players.add(player);
        }
    }

    public void initTeamData(int teamNumber) {
        if (players == null) {
            players = new ArrayList<Player>();
            this.teamNumber = teamNumber;
        }

        captain = "";
    }
}
