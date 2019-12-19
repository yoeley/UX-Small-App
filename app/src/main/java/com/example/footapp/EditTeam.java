package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EditTeam extends AppCompatActivity implements Serializable, View.OnFocusChangeListener {

    private final String noName = "No Name";
    private final String dummyTextViewString = "com.example.footapp:id/dummyTextView";

    private GameData game;
    private GamesList gamesList;
    private TextView dummyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent in = getIntent();
        game = (GameData) in.getSerializableExtra("Game");
        gamesList = (GamesList) in.getSerializableExtra("GamesList");

        initEditTexts();
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
        int resID = getResources().getIdentifier( dummyTextViewString, "drawable", getPackageName());
        dummyTextView = findViewById(resID);
        dummyTextView.setFocusable(true);
        dummyTextView.requestFocus();
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
                else imageView.setImageResource(R.drawable.add_player_icon_team1);
            }
            else if(etName.contains("team2")) {
                if(!et.getText().toString().equals("")) {
                    if (etName.contains("Pos0")) {
                        imageView.setImageResource(R.drawable.tshirt_goalkeeper2);
                    } else imageView.setImageResource(R.drawable.team_2_tshirt);
                }
                else imageView.setImageResource(R.drawable.add_player_icon_team2);
            }
        }
        removeTextViewFocuse();
    }

    private ImageView getImageViewViaEditText(String etName){
        String imageSuffix = "Image";
        int resID = getResources().getIdentifier( etName + imageSuffix , "drawable", getPackageName());
        return findViewById(resID);
    }

    private void removeTextViewFocuse(){
        dummyTextView.requestFocus();
    }

    public void selectEditTextViaImage(ImageView iv) {

        String ivName = iv.getResources().getResourceName(iv.getId());
        String etName = ivName.substring(0, ivName.length() - 5);

        EditText et = getEditTextViaName(etName);
        et.setFocusable(true);
        et.requestFocus();

        if (et.requestFocus()) {
            showKeyboardForET(et);
        }
    }

    private void showKeyboardForET(EditText et){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
    private void toggleShowKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
    private void toggleCloseKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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
