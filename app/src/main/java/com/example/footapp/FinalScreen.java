package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FinalScreen extends AppCompatActivity implements Serializable {

    String data;
    GameData gameData;
//    TextView time;
//    TextView date;
//    TextView location;
//    TextView referee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        Intent in = getIntent();
//        data = in.getStringExtra("Game");
        data = StringConst.data;

        parseDataIntoGameObject();
        initEditTexts();
//        updateGameSettings();
    }


    private void updateGameSettings(){
//        time = findViewById(R.id.time);
//        time.setText(gameData.getTime());
//
//        location = findViewById(R.id.location);
//        location.setText(gameData.getLocation());
//
//        referee = findViewById(R.id.referee);
//        referee.setText(gameData.getReferee());
//
//        date = findViewById(R.id.date);
//        date.setText(gameData.getDate());
        return;
    }

    private void initEditTexts(){
        List<TeamData> teamsData = gameData.getTeamData();
        String playerId;
        for(TeamData teamData : teamsData) {
            for (Player player : teamData.getPlayers()) {
                playerId = player.getPlayerId();
                int id = getResources().getIdentifier(playerId, "id", getPackageName());
                EditText et = findViewById(id);
                et.setText(player.getPlayerName());
                et.setEnabled(false);
            }
        }
    }

    public void back(View view) {
        Intent EditTeam = new Intent(this, EditTeam.class);
        EditTeam.putExtra("Data", gameData);
        startActivity(EditTeam);
    }

    private void parseDataIntoGameObject(){
        final ObjectMapper mapper = new ObjectMapper();
        try {
            gameData = mapper.readValue(data, GameData.class);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FinalScreen.this,"Failed parsing the json",Toast.LENGTH_LONG).show();
        }
    }

}
