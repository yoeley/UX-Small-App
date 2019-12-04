package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.PrintWriter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class TeamCreationForm extends AppCompatActivity {

    private ScrollView TeamCreationScrollView;
    private EditText gameName;
    private EditText numOfPlayers;
    private EditText date;
    private EditText time;
    private EditText location;
    private EditText referee;
    private EditText captain1;
    private EditText captain2;
    private JSONObject game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        TeamCreationScrollView = findViewById(R.id.TeamCreationScrollView);
        gameName = findViewById(R.id.gameName);
        numOfPlayers = findViewById(R.id.numOfPlayers);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        referee = findViewById(R.id.referee);
        captain1 = findViewById(R.id.captain1);
        captain2 = findViewById(R.id.captain2);

        // hide until "advanced" is clicked
        TeamCreationScrollView.setVisibility(View.GONE);

        numOfPlayers.addTextChangedListener(new TextValidator(numOfPlayers, TextValidator.numPlayersValid){});
    }

    private void createGameJSON() {
        try {
            game = new JSONObject(StringConst.newTeamJason);

            game.put("gameName", gameName.getText().toString());
            game.put("date", date.getText().toString());
            game.put("time", time.getText().toString());
            game.put("location", location.getText().toString());
            game.put("referee", referee.getText().toString());

            game.getJSONArray("teams").getJSONObject(0).put("teamName", "teamOne");
            game.getJSONArray("teams").getJSONObject(0).put("numOfPlayers", numOfPlayers.getText().toString());
            game.getJSONArray("teams").getJSONObject(0).put("captain", captain1.getText().toString());

            game.getJSONArray("teams").getJSONObject(1).put("teamName", "teamTwo");
            game.getJSONArray("teams").getJSONObject(1).put("numOfPlayers", numOfPlayers.getText().toString());
            game.getJSONArray("teams").getJSONObject(1).put("captain", captain2.getText().toString());
        }
        catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        // making JSONString into GameData class - if needed
        /*
        ObjectMapper mapper = new ObjectMapper();
        try {
            GameData gameData = mapper.readValue(game.toString(4), GameData.class);
            String gameDataString = mapper.writeValueAsString(gameData);
            System.out.println(gameDataString);
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void create(View view) {
        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        createGameJSON();
        EditTeam.putExtra("Orig", 2);
        EditTeam.putExtra("Game", game.toString());

        try {
            String gamesString = AppFileWriter.readFromFile(getApplicationContext(), "savedGames.txt");
            System.out.println("GAME STRING IS:\n");
            System.out.println(gamesString);
            JSONObject gamesJSON;
            if (gamesString == "") {
                System.out.println("IN IF\nIN IF \nIN IF\n");
                gamesJSON = new JSONObject(StringConst.savedTeamsHeader);
            }
            else {
                gamesJSON = new JSONObject(gamesString);
            }

            JSONArray gamesArr = gamesJSON.getJSONArray("savedGames");
            gamesJSON.getJSONArray("savedGames").put(2, gamesArr.getJSONObject(1));
            gamesJSON.getJSONArray("savedGames").put(1, gamesArr.getJSONObject(0));
            gamesJSON.getJSONArray("savedGames").put(0, game);

            AppFileWriter.writeToFile(gamesJSON.toString(4), "savedGames.txt", getApplicationContext());

            System.out.println(AppFileWriter.readFromFile(getApplicationContext(), "savedGames.txt"));
        }
        catch (org.json.JSONException e) {
            e.printStackTrace();
        }

        //TODO: delete this printing option before submission
        /*
        try {
            System.out.println(game.toString(4));
        }
        catch (org.json.JSONException e) {
            e.printStackTrace();
        }
        */
        startActivity(EditTeam);
    }
    public void toggle_contents(View v){
        TeamCreationScrollView.setVisibility( TeamCreationScrollView.isShown()
                ? View.GONE
                : View.VISIBLE );
    }
}
