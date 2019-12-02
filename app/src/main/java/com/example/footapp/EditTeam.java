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
import java.util.ArrayList;
import java.util.List;

public class EditTeam extends AppCompatActivity implements Serializable {

    int image = -1;
    int orig = 2;
    String data;
    GameData gameData;
    TextView time;
    TextView date;
    TextView location;
    TextView referee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent in = getIntent();
        image = in.getIntExtra("Index", -1);
        orig = in.getIntExtra("Orig", -1);
//        data = in.getStringExtra("Game");
        data = StringConst.data;

        parseDataIntoGameObject();
        initEditTexts();
        updateGameSettings();
    }


    private void updateGameSettings(){
        time = findViewById(R.id.time);
        time.setText(gameData.getTime());

        location = findViewById(R.id.location);
        location.setText(gameData.getLocation());

        referee = findViewById(R.id.referee);
        referee.setText(gameData.getReferee());

        date = findViewById(R.id.date);
        date.setText(gameData.getDate());
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
            }
        }
    }


    public void back(View view) {
        if(orig == 1) startActivity(new Intent(this, MainActivity.class));
        else startActivity(new Intent(this, TeamCreationForm.class));
    }


    private void parseDataIntoGameObject(){
        final ObjectMapper mapper = new ObjectMapper();
        try {
            gameData = mapper.readValue(data, GameData.class);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(EditTeam.this,"Failed parsing the json",Toast.LENGTH_LONG).show();
        }
    }


    private void insertDataToGameObject(){
        List<TeamData> teamsData = gameData.getTeamData();
        String playerId;
        for(TeamData teamData : teamsData) {
            for (Player player : teamData.getPlayers()) {
                playerId = player.getPlayerId();
                int id = getResources().getIdentifier(playerId, "id", getPackageName());
                EditText et = findViewById(id);
                player.setPlayerName(et.getText().toString());
            }
        }

    }

    public void toFinalScreen(View view) {
        insertDataToGameObject();
        Intent FinalScreen = new Intent(getApplicationContext(), FinalScreen.class);
        FinalScreen.putExtra("Index", image);
        FinalScreen.putExtra("Orig", 1);
        FinalScreen.putExtra("Data", gameData);
        startActivity(FinalScreen);
    }
}
