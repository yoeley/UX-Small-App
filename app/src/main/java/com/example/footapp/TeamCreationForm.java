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
            game.put("numOfPlayers", numOfPlayers.getText().toString());
            game.put("date", date.getText().toString());
            game.put("time", time.getText().toString());
            game.put("location", location.getText().toString());
            game.put("captain", captain.getText().toString());
            game.put("judge", judge.getText().toString());
            game.put("players", new JSONArray());
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
        /*
        try {
            System.out.println(game.toString(4));
        }
        catch (org.json.JSONException e) {
            e.printStackTrace();
        }*/
        startActivity(EditTeam);
    }
    public void toggle_contents(View v){
        TeamCreationScrollView.setVisibility( TeamCreationScrollView.isShown()
                ? View.GONE
                : View.VISIBLE );
    }

}
