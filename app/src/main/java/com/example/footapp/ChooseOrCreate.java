package com.example.footapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.core.content.ContextCompat;

import java.util.List;

public class ChooseOrCreate extends Activity {

    public static final String savedTeamsHeader = "{\n" +
            "    \"numGames\": 0,\n" +
            "    \"gameDataList\": null}";

    public Button newGameButton, loadGameButton;

    private GamesList gamesList;
    private List<GameData> gameDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_or_create);

        newGameButton = findViewById(R.id.newGameButton);
        loadGameButton = findViewById(R.id.loadGameButton);

        setLoadGameButton();
    }

    @Override
    public void onResume() {
        super.onResume();
        setLoadGameButton();
    }

    public void setLoadGameButton() {
        String gamesString;
        gamesString = FileManager.readFromFile(getApplicationContext(), "savedGames.json");
        if (gamesString.equals("")) {
            gamesString = savedTeamsHeader;
            FileManager.writeToFile(gamesString, "savedGames.json", getApplicationContext());
        }

        gamesList = GamesList.JSONToGamesList(gamesString);
        gamesList.initGamesList();

        if(gamesList.getNumGames() == 0) {
            loadGameButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.small2));
            loadGameButton.setEnabled(false);
        }
    }


    public void toChooseGameActivity(View view)
    {
        Intent ChooseGame = new Intent(getApplicationContext(), ChooseGame.class);
        ChooseGame.putExtra("GamesList",gamesList);
        startActivity(ChooseGame);
    }

    public void toCreateGameActivity(View view) {
        Intent CreateTeam = new Intent(getApplicationContext(), CreateGame.class);
        CreateTeam.putExtra("GamesList", gamesList);
        startActivity(CreateTeam);
    }

}