package com.example.footapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    public static final int SECOND_FAVORITE = 1;
    public static final int THIRD_FAVORITE = 2;
    public static final int FIRST_FAVORITE = 0;
    public Button create, favorite1, favorite2, favorite3;
    public TextView favorite1text, favorite2text, favorite3text;

    private GamesList gamesList;
    private List<GameData> gameDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String gamesString;

        gamesString = AppFileManager.readFromFile(getApplicationContext(), "savedGames.json");
        if (gamesString.equals("")) {
            gamesString = StringConst.savedTeamsHeader;
            AppFileManager.writeToFile(gamesString, "savedGames.json", getApplicationContext());
        }

        gamesList = GamesList.JSONToGamesList(gamesString);

        favorite1 = findViewById(R.id.favoriteGroup1Button);
        favorite1text = findViewById(R.id.favorite1);
        favorite2 = findViewById(R.id.favoriteGroup2Button);
        favorite2text = findViewById(R.id.favorite2);
        favorite3 = findViewById(R.id.favoriteGroup3Button);
        favorite3text = findViewById(R.id.favorite3);


        if(gamesList.getNumGames() != 0)
        {
            //might need to check for json errors if parts of the array are empty
            gameDataList = gamesList.getGameDataList();
            if(gameDataList.get(0).getGameName().equals(""))
            {
                killButton(favorite1, 1);
            }
            else
            {
                // add the whole game string to pass on
                //jsonString[MainActivity.FIRST_FAVORITE] = gamesArray.getJSONObject(FIRST_FAVORITE).toString();
                favorite1text.setText(gameDataList.get(0).getGameName());
            }
            if(gameDataList.get(1).getGameName().equals(""))
            {
                killButton(favorite2, 2);
            }
            else
            {
                // add the whole game string to pass on
                //jsonString[MainActivity.FIRST_FAVORITE] = gamesArray.getJSONObject(FIRST_FAVORITE).toString();
                favorite2text.setText(gameDataList.get(1).getGameName());
            }
            if(gameDataList.get(2).getGameName().equals(""))
            {
                killButton(favorite3, 3);
            }
            else
            {
                // add the whole game string to pass on
                //jsonString[MainActivity.FIRST_FAVORITE] = gamesArray.getJSONObject(FIRST_FAVORITE).toString();
                favorite3text.setText(gameDataList.get(2).getGameName());
            }
        }
        else
        {
            killButton(favorite1, 1);
            killButton(favorite2, 2);
            killButton(favorite3, 3);
        }



        create = findViewById(R.id.createTeamButton);

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, gameList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        loadSpinner.setAdapter(dataAdapter);
    }

    public void killButton(Button button, int id)
    {
        TextView text;
        switch (id)
        {
            case 1:
                text = favorite1text;
                break;
            case 2:
                text = favorite2text;
                break;
            case 3:
                text = favorite3text;
                break;
            default:
                text = favorite1text;
                break;
        }
        text.setText("No Favorite");
        text.setTextColor(getResources().getColor(R.color.greydOut));
        text.setShadowLayer(5,0,0,getResources().getColor(R.color.white));
        button.setBackground(getResources().getDrawable(R.drawable.small2));
        button.setEnabled(false);
    }

    public void goToNextScreen(Intent intent, int idx)
    {
        intent.putExtra("Game",gameDataList.get(idx));
        startActivity(intent);
    }

    public void loadTeam1(View v) {

        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        EditTeam.putExtra("Orig", 1);
        goToNextScreen(EditTeam, FIRST_FAVORITE);
    }

    public void loadTeam2(View v) {

        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        EditTeam.putExtra("Orig", 1);
        goToNextScreen(EditTeam, SECOND_FAVORITE);
    }

    public void loadTeam3(View v) {

        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        EditTeam.putExtra("Orig", 1);
        goToNextScreen(EditTeam, THIRD_FAVORITE);
    }


    public void createNewTeam(View view) {
        Intent CreateTeam = new Intent(getApplicationContext(), com.example.footapp.CreateTeam.class);
        CreateTeam.putExtra("GamesList",gamesList);
        startActivity(CreateTeam);
    }

}