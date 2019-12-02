package com.example.footapp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameData implements Serializable {

    @JsonProperty("referee")
    private String referee;

    @JsonProperty("location")
    private String location;

    @JsonProperty("time")
    private String time;

    @JsonProperty("date")
    private String date;

    @JsonProperty("teams")
    private List<TeamData> teamData;


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

    public List<TeamData> getTeamData() {
        return teamData;
    }

    public void setTeamData(List<TeamData> teamData) {
        this.teamData = teamData;
    }
}
