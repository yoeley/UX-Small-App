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

public class EditTeam extends AppCompatActivity implements Serializable {

    private String noName = "No Name";

    String data;
    GameData gameData;
    TextView dateAndTime;
    TextView gameNameTextView;
    TextView location;
    TextView referee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent in = getIntent();
        data = in.getStringExtra("Game");

//        data = StringConst.data; # Test screen without previous screen being ready.

        parseDataIntoGameObject();
        initEditTexts();
        updateGameSettings();
    }


    private void updateGameSettings(){

        gameNameTextView = findViewById(R.id.gameNameTextView);
        if(gameData.getGameName().equals("")) gameNameTextView.setText("Game on!");
        else gameNameTextView.setText(gameData.getGameName());

        dateAndTime = findViewById(R.id.dateAndTime);
        dateAndTime.setText("Date: " + gameData.getDate() + "     Time: " + gameData.getTime());

        location = findViewById(R.id.location);
        location.setText("Location: " + gameData.getLocation());

        referee = findViewById(R.id.referee);
        referee.setText("Referee: " + gameData.getReferee());
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


    private void parseDataIntoGameObject(){
        final ObjectMapper mapper
                = new ObjectMapper();
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
                if(et.getText().toString().equals("")) {
                    player.setPlayerName(noName);
                }
                else player.setPlayerName(et.getText().toString());
            }
        }

    }

    public void toFinalScreen(View view) {
        insertDataToGameObject();
        Intent FinalScreen = new Intent(getApplicationContext(), FinalScreen.class);
        FinalScreen.putExtra("Data", gameData);
        startActivity(FinalScreen);
    }
}
