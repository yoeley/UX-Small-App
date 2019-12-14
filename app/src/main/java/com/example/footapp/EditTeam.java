package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
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
        String imageSuffix = "Image";
        if(!et.getText().toString().equals("") && !hasFocus)
        {
            String etName = et.getResources().getResourceName(et.getId());
            int resID = getResources().getIdentifier( etName + imageSuffix , "drawable", getPackageName());
            ImageView imageView = findViewById(resID);
            if(etName.contains("team1")) {
                if(etName.contains("Pos0")) {
                    imageView.setImageResource(R.drawable.tshirt_goalkeeper1);
                }
                else imageView.setImageResource(R.drawable.team_1_tshirt);
            }
            else if(etName.contains("team2")) {
                imageView.setImageResource(R.drawable.tshirt_goalkeeper1);

                if (etName.contains("Pos0")) {
                    imageView.setImageResource(R.drawable.tshirt_goalkeeper2);
                } else imageView.setImageResource(R.drawable.team_2_tshirt);
            }

        }
    }


    public void selectEditTextViaImage(ImageView iv) {

        String ivName = iv.getResources().getResourceName(iv.getId());
        String etName = ivName.substring(0, ivName.length() - 5);

        int resID = getResources().getIdentifier(etName, "edittext", getPackageName());
        EditText et = findViewById(resID);
        et.setFocusable(true);
        et.requestFocus();

        if (et.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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
