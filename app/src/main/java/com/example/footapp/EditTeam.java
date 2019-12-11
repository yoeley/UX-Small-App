package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class EditTeam extends AppCompatActivity implements Serializable {

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
        for(TeamData teamData : teamsData) {
            for (Player player : teamData.getPlayers()) {
                playerId = player.getPlayerId();
                int id = getResources().getIdentifier(playerId, "id", getPackageName());
                EditText et = findViewById(id);
                et.setText(player.getPlayerName());
            }
        }
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
        List<GameData> gameDataList = gamesList.getGameDataList();
        gameDataList.set(2, gameDataList.get(1));
        gameDataList.set(1, gameDataList.get(0));
        gameDataList.set(0, game);

        if (gamesList.getNumGames() < GamesList.maxNumGames) {
            gamesList.setNumGames(gamesList.getNumGames() + 1);
        }

        String gamesString = GamesList.GamesListToJSON(gamesList);
        AppFileManager.writeToFile(gamesString, "savedGames.json", getApplicationContext());

        System.out.println(AppFileManager.readFromFile(getApplicationContext(), "savedGames.json"));
    }

    public void toFinalScreen(View view) {
        writeJSONToFile();
        insertDataToGameObject();
        Intent FinalScreen = new Intent(getApplicationContext(), FinalScreen.class);
        FinalScreen.putExtra("Game", game);
        startActivity(FinalScreen);
    }
}
