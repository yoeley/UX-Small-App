package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditTeam extends AppCompatActivity implements Serializable, View.OnFocusChangeListener {

    private final String noName = "No Name";

    private GameData game;
    private GamesList gamesList;
    private TextView dateAndTime;
    private TextView gameNameTextView;
    private TextView location;
    private TextView referee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent in = getIntent();
        game = (GameData) in.getSerializableExtra("Game");
        gamesList = (GamesList) in.getSerializableExtra("GamesList");

        initEditTexts();
        updateGameSettings();
    }


    private void updateGameSettings(){

        gameNameTextView = findViewById(R.id.gameNameTextView);
        if(game.getGameName().equals("")) gameNameTextView.setText("Game on!");
        else gameNameTextView.setText(game.getGameName());

        dateAndTime = findViewById(R.id.dateAndTime);
        dateAndTime.setText("Date: " + game.getDate() + "     Time: " + game.getTime());

        location = findViewById(R.id.location);
        location.setText("Location: " + game.getLocation());

        referee = findViewById(R.id.referee);
        referee.setText("Referee: " + game.getReferee());
    }


    private void initEditTexts(){
        List<TeamData> teamsData = game.getTeams();
        String playerId;
        String imageSuffix = "Image";
        for(TeamData teamData : teamsData) {
            for (Player player : teamData.getPlayers()) {
                playerId = player.getPlayerId();
                int id = getResources().getIdentifier(playerId, "id", getPackageName());
                EditText et = findViewById(id);
                et.setText(player.getPlayerName());
                et.setOnFocusChangeListener(this);

                String etName = et.getResources().getResourceName(et.getId());
                int resID = getResources().getIdentifier( etName + imageSuffix , "drawable", getPackageName());
                ImageView imageView = (ImageView) findViewById(resID);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ImageView iv = (ImageView) view;
                        selectEditTextViaImage(iv);
                    }
                });
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        EditText et = (EditText)v;
        String etName = et.getResources().getResourceName(et.getId());
        if(!hasFocus)
        {
            ImageView imageView = getImageViewViaEditText(etName);
            if(etName.contains("team1")) {
                if(!et.getText().toString().equals("")) {
                    if (etName.contains("Pos0")) {
                        imageView.setImageResource(R.drawable.tshirt_goalkeeper1);
                    } else imageView.setImageResource(R.drawable.team_1_tshirt);
                }
                else imageView.setImageResource(R.drawable.add_player_icon1);
            }
            else if(etName.contains("team2")) {
                if(!et.getText().toString().equals("")) {
                    if (etName.contains("Pos0")) {
                        imageView.setImageResource(R.drawable.tshirt_goalkeeper2);
                    } else imageView.setImageResource(R.drawable.team_2_tshirt);
                }
                else imageView.setImageResource(R.drawable.add_player_icon2);
            }

        }
    }

    private ImageView getImageViewViaEditText(String etName){
        String imageSuffix = "Image";
        int resID = getResources().getIdentifier( etName + imageSuffix , "drawable", getPackageName());
        return findViewById(resID);
    }

    public void selectEditTextViaImage(ImageView iv) {

        String ivName = iv.getResources().getResourceName(iv.getId());
        String etName = ivName.substring(0, ivName.length() - 5);

        EditText et = getEditTextViaName(etName);
        et.setFocusable(true);
        et.requestFocus();

        if (et.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private EditText getEditTextViaName(String etName){
        int resID = getResources().getIdentifier(etName, "edittext", getPackageName());
        return findViewById(resID);
    }

    private void insertDataToGameObject(){
        List<TeamData> teamsData = game.getTeams();
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


    private void writeJSONToFile() {
        ArrayList<GameData> gameDataList = gamesList.getGameDataList();

        Boolean uniqueName = true;
        for (GameData gameData : gameDataList) {
            if (gameData.getGameName().equals(game.getGameName())) {
                uniqueName = false;
            }
        }

        if (uniqueName) {
            gameDataList.add(0, game);
            gamesList.setNumGames(gamesList.getNumGames() + 1);
        }

        String gamesString = GamesList.GamesListToJSON(gamesList);
        FileManager.writeToFile(gamesString, "savedGames.json", getApplicationContext());
    }

    public void toFinalScreen(View view) {
        writeJSONToFile();
        insertDataToGameObject();
        Intent FinalScreen = new Intent(getApplicationContext(), FinalScreen.class);
        FinalScreen.putExtra("Game", game);
        startActivity(FinalScreen);
    }
}
