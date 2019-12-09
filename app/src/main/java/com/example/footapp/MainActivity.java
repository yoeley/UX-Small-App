package com.example.footapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends Activity {

    public static final int SECOND_FAVORITE = 1;
    public static final int THIRD_FAVORITE = 2;
    public static final int FIRST_FAVORITE = 0;
    public Button create, favorite1, favorite2, favorite3;
    public TextView favorite1text, favorite2text, favorite3text;
    String[] jsonString = new String[3];
    JSONObject gamesJson;
    JSONArray gamesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String games = read_file("savedTeams.txt");
//        String games;
//        try {
//
//            games = AppFileManager.readFromFile(getApplicationContext(), "savedGames.txt");
//
//        }
//        catch (Exception e)
//        {
//            AppFileManager.writeToFile("", "savedGames.txt", getApplicationContext());
//            games = AppFileManager.readFromFile(getApplicationContext(), "savedGames.txt");
//        }
//        if (games.equals("")) {
//            games = StringConst.savedTeamsHeader;
//        }
        favorite1 = findViewById(R.id.favoriteGroup1Button);
        favorite1text = findViewById(R.id.favorite1);
        favorite2 = findViewById(R.id.favoriteGroup2Button);
        favorite2text = findViewById(R.id.favorite2);
        favorite3 = findViewById(R.id.favoriteGroup3Button);
        favorite3text = findViewById(R.id.favorite3);
        try {
            gamesJson = new JSONObject(games);
            if(gamesJson.getBoolean("hasNames"))
            {
                //might need to check for json errors if parts of the array are empty
                gamesArray = gamesJson.getJSONArray("savedGames");
                if(gamesArray.getJSONObject(FIRST_FAVORITE).getString("gameName").equals(""))
                {
                    killButton(favorite1, 1);
                }
                else
                {
                    jsonString[MainActivity.FIRST_FAVORITE] = gamesArray.getJSONObject(FIRST_FAVORITE).toString();
                    favorite1text.setText(gamesArray.getJSONObject(FIRST_FAVORITE).getString("gameName"));
                    // add the whole game string to pass on
                }
                if(gamesArray.getJSONObject(SECOND_FAVORITE).getString("gameName").equals(""))
                {
                    killButton(favorite2, 2);
                }
                else
                {
//                    gameList.add(gamesArray.getJSONObject(1).toString());
                    jsonString[SECOND_FAVORITE] = gamesArray.getJSONObject(SECOND_FAVORITE).toString();
                    favorite2text.setText(gamesArray.getJSONObject(SECOND_FAVORITE).getString("gameName"));
                }
                if(gamesArray.getJSONObject(THIRD_FAVORITE).getString("gameName").equals(""))
                {
//                    gameList.add("");
                    killButton(favorite3, 3);
                }
                else
                {
//                    gameList.add(gamesArray.getJSONObject(2).toString());
                    jsonString[MainActivity.THIRD_FAVORITE] = gamesArray.getJSONObject(MainActivity.THIRD_FAVORITE).toString();
                    favorite3text.setText(gamesArray.getJSONObject(MainActivity.THIRD_FAVORITE).getString("gameName"));
                }
//                gameList.add(gamesArray.getJSONObject(0).getString("Team_1"));
//                gameList.add(array_names_json.getJSONObject(0).getString("Team_2"));
//                gameList.add(array_names_json.getJSONObject(0).getString("Team_3"));
            }
            else
            {
                killButton(favorite1, 1);
                killButton(favorite2, 2);
                killButton(favorite3, 3);
            }
        } catch (JSONException e) {
            e.printStackTrace(); //for debug
            Toast.makeText(MainActivity.this,"Failed loading saved teams",Toast.LENGTH_LONG).show();
        }


        create = findViewById(R.id.createTeamButton);

//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_spinner_item, gameList);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        loadSpinner.setAdapter(dataAdapter);
    }


    public String read_file(String filename) {
        try {
            InputStream is = getAssets().open(filename);
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
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
        intent.putExtra("Game",jsonString[idx]);
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
        startActivity(new Intent(this, TeamCreationForm.class));
    }

}