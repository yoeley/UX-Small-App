package com.example.footapp;

public class StringConst {
    // Data Json example.
    public static final String data = "{"+
            "\"gameName\": \"Game On!\","+
            "\"date\": \"09.12.2019\"," +
            "\"time\": \"18:00\","+
            "\"location\": \"Jerusalem, Jaffa 37\"," +
            "\"referee\": \"Sam\","+
            "\"teams\": ["+
    "{"+
        "\"teamNumber\": 1,"+
            "\"numOfPlayers\": \"6\","+
            "\"captain\": \"Ain?\","+
            "\"players\":"+
            "["+
        "{\"playerName\": \"\", \"playerId\": \"team1Pos0\", \"playerPos\": \"GK\"},"+
        "{\"playerName\": \"\", \"playerId\": \"team1Pos1\", \"playerPos\": \"MID\"},"+
        "{\"playerName\": \"\", \"playerId\": \"team1Pos2\", \"playerPos\": \"LB\"},"+
        "{\"playerName\": \"\", \"playerId\": \"team1Pos3\", \"playerPos\": \"RB\"},"+
        "{\"playerName\": \"\", \"playerId\": \"team1Pos4\", \"playerPos\": \"LF\"},"+
        "{\"playerName\": \"\", \"playerId\": \"team1Pos5\", \"playerPos\": \"RF\"}"+
            "]"+
    "},"+
    "{"+
        "\"teamNumber\": 2,"+
            "\"numOfPlayers\": \"6\","+
            "\"captain\": \"Capt\","+
            "\"players\":"+
            "["+
        "{\"playerName\": \"\", \"playerId\":\"team2Pos0\", \"playerPos\":\"GK\"},"+
        "{\"playerName\": \"\", \"playerId\":\"team2Pos1\", \"playerPos\":\"MID\"},"+
        "{\"playerName\": \"\", \"playerId\":\"team2Pos2\", \"playerPos\":\"LB\"},"+
        "{\"playerName\": \"\", \"playerId\":\"team2Pos3\", \"playerPos\":\"RB\"},"+
        "{\"playerName\": \"\", \"playerId\":\"team2Pos4\", \"playerPos\":\"LF\"},"+
        "{\"playerName\": \"\", \"playerId\":\"team2Pos5\", \"playerPos\":\"LR\"}"+
            "]"+
    "}"+
        "]"+
"}";

    public static final String newTeamJSON = "{"+
            "\"gameName\": \"\","+
            "\"date\": \"\","+
            "\"time\": \"\","+
            "\"location\": \"\","+
            "\"referee\": \"\","+
            "\"teams\": ["+
            "{"+
            "\"teamNumber\": 1,"+
            "\"numOfPlayers\": \"6\","+
            "\"captain\": \"\","+
            "\"players\":"+
            "["+
            "{\"playerName\": \"\", \"playerId\": \"team1Pos0\", \"playerPos\": \"GK\"},"+
            "{\"playerName\": \"\", \"playerId\": \"team1Pos1\", \"playerPos\": \"MID\"},"+
            "{\"playerName\": \"\", \"playerId\": \"team1Pos2\", \"playerPos\": \"LB\"},"+
            "{\"playerName\": \"\", \"playerId\": \"team1Pos3\", \"playerPos\": \"RB\"},"+
            "{\"playerName\": \"\", \"playerId\": \"team1Pos4\", \"playerPos\": \"LF\"},"+
            "{\"playerName\": \"\", \"playerId\": \"team1Pos5\", \"playerPos\": \"RF\"}"+
            "]"+
            "},"+
            "{"+
            "\"teamNumber\": 2,"+
            "\"numOfPlayers\": \"6\","+
            "\"captain\": \"Capt\","+
            "\"players\":"+
            "["+
            "{\"playerName\": \"\", \"playerId\":\"team2Pos0\", \"playerPos\":\"GK\"},"+
            "{\"playerName\": \"\", \"playerId\":\"team2Pos1\", \"playerPos\":\"MID\"},"+
            "{\"playerName\": \"\", \"playerId\":\"team2Pos2\", \"playerPos\":\"LB\"},"+
            "{\"playerName\": \"\", \"playerId\":\"team2Pos3\", \"playerPos\":\"RB\"},"+
            "{\"playerName\": \"\", \"playerId\":\"team2Pos4\", \"playerPos\":\"LF\"},"+
            "{\"playerName\": \"\", \"playerId\":\"team2Pos5\", \"playerPos\":\"LR\"}"+
            "]"+
            "}"+
            "]"+
            "}";

    public static final String savedTeamsHeader = "{\n" +
            "    \"numGames\": 0,\n" +
            "    \"gameDataList\": null}";//[{\"gameName\":\"\"}, {\"gameName\":\"\"}, {\"gameName\":\"\"}]\n" +
            //"}";
}
