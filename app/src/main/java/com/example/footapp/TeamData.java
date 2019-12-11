package com.example.footapp;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TeamData implements Serializable {

    private static final int initialNumPlayers = 0;
    private static final String idFormat = "team%dPos%d";

    public TeamData(int teamNumber) {
        players = new ArrayList<Player>(initialNumPlayers);
        this.teamNumber = teamNumber;
    }

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
        players.ensureCapacity(numOfPlayers);
        for (int i = 0; i < numOfPlayers; ++i) {
            players.add(new Player(String.format(idFormat, teamNumber, i)));
        }
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
}
