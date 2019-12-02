package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.PrintWriter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class TeamCreationForm extends AppCompatActivity {

    private ScrollView TeamCreationScrollView;
    private EditText numOfPlayers;
    private EditText date;
    private EditText time;
    private EditText location;
    private EditText captain;
    private EditText judge;
    private JSONObject game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        TeamCreationScrollView = findViewById(R.id.TeamCreationScrollView);
        numOfPlayers = findViewById(R.id.numOfPlayers);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        captain = findViewById(R.id.captain);
        judge = findViewById(R.id.judge);

        // hide until "advanced" is clicked
        TeamCreationScrollView.setVisibility(View.GONE);

        numOfPlayers.addTextChangedListener(new TextValidator(numOfPlayers, TextValidator.numPlayersValid){});
    }

    private void createGameJSON() {
        game = new JSONObject();
        try {
            game.put("date", date.getText().toString());
            game.put("time", time.getText().toString());
            game.put("location", location.getText().toString());
            game.put("judge", judge.getText().toString());

            int numPlayers;
            try {
                numPlayers = Integer.parseInt(numOfPlayers.getText().toString());
            }
            catch (Exception e) {
                e.printStackTrace();
                numPlayers = 0;
            }

            JSONArray players1 = new JSONArray();
            for (int i = 0; i < numPlayers; ++i)
            {
                JSONObject player = new JSONObject("{\"playerName\": \"\", \"playerId\": null, \"playerPos\": \"\"}");
                players1.put(player);
            }
            JSONArray players2 = new JSONArray();
            for (int i = 0; i < numPlayers; ++i)
            {
                JSONObject player = new JSONObject("{\"playerName\": \"\", \"playerId\": null, \"playerPos\": \"\"}");
                players2.put(player);
            }


            JSONArray teams = new JSONArray();

            JSONObject team1 = new JSONObject();
            team1.put("teamName", "teamOne");
            team1.put("numOfPlayers", numOfPlayers.getText().toString());
            team1.put("captain", captain.getText().toString());
            team1.put("players", players1);

            JSONObject team2 = new JSONObject();
            team2.put("teamName", "teamTwo");
            team2.put("numOfPlayers", numOfPlayers.getText().toString());
            team2.put("captain", captain.getText().toString());
            team2.put("players", players2);

            teams.put(team1);
            teams.put(team2);

            game.put("teams", teams);


            /*
            {
                "date": "12.12.2019",
                "time": "18:00",
                "location": "Jerusalem",
                "judge": "Sam",
                "teams": [
                            {
                            "teamName": "teamOne",
                            "numOfPlayers": "6",
                            "captain": "Ain?",
                            "players":
                                [
                                {"playerName": "Yoel", "playerId": 0, "playerPos": "GK"},
                                {"playerName": "Dan", "playerId": 1, "playerPos": "MID"}
                                ]
                            },
                            {
                            "teamName": "teamTwo",
                            "numOfPlayers": "6",
                            "captain": "Capt",
                            "players":
                                [
                                {"playerName": "Avi", "playerId":2, "playerPos":"GK"},
                                {"playerName": "Mar", "playerId":3, "playerPos":"MID"},
                                {"playerName": "Noa", "playerId":4, "playerPos":"LF"}
                                ]
                            }
                ]
}
             */
        }
        catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void create(View view) {
        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        createGameJSON();
        EditTeam.putExtra("Index", 0);
        EditTeam.putExtra("Orig", 2);
        EditTeam.putExtra("Game", game.toString());

        //TODO: delete this printing option before submission

        try {
            System.out.println(game.toString(4));
        }
        catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        startActivity(EditTeam);
    }
    public void toggle_contents(View v){
        TeamCreationScrollView.setVisibility( TeamCreationScrollView.isShown()
                ? View.GONE
                : View.VISIBLE );
    }

}
