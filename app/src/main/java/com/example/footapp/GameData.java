package com.example.footapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameData implements Serializable {

    private static final int numTeams = 2;

    @JsonProperty("gameName")
    private String gameName;

    @JsonProperty("referee")
    private String referee;

    @JsonProperty("location")
    private String location;

    @JsonProperty("time")
    private String time;

    @JsonProperty("date")
    private String date;

    @JsonProperty("teams")
    private ArrayList<TeamData> teams;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public ArrayList<TeamData> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<TeamData> teams) {
        this.teams = teams;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void initGameData() {
        if (teams == null) {
            teams = new ArrayList<TeamData>(numTeams);
            for (int i = 1; i <= numTeams; ++i) {
                TeamData team = new TeamData();
                team.initTeamData(i);
                teams.add(team);
            }
        }
        gameName = "";
        referee = "";
        location = "";
        time = "";
        date = "";
    }
}
