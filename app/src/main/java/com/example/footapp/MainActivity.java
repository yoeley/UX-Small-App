package com.example.footapp;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class MainActivity extends Activity {

    public Button create, load;
    public Spinner loadSpinner;
//    public String loadFilePath = Environment. + "load";
    List<String> team_names = new ArrayList<String>();
//    String saved_1, saved_2, saved_3;
    String LoadedTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String names = read_file("teamNames.txt");
        try {
            JSONObject names_json = new JSONObject(names);
            if(names_json.getBoolean("hasNames"))
            {
                JSONArray array_names_json = names_json.getJSONArray("savedTeams");
                team_names.add(array_names_json.getJSONObject(0).getString("Team_1"));
                team_names.add(array_names_json.getJSONObject(0).getString("Team_2"));
                team_names.add(array_names_json.getJSONObject(0).getString("Team_3"));
            }
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this,"Failed loading saved team names",Toast.LENGTH_LONG).show();
        }


        create = (Button) findViewById(R.id.createTeamButton);
        load = (Button) findViewById(R.id.loadTeamButton);

        loadSpinner = (Spinner) findViewById(R.id.numOfPlayersSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, team_names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loadSpinner.setAdapter(dataAdapter);
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


    public void loadTeam(View v) {

        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        int image = -1;
        if(loadSpinner.getSelectedItem().equals("Thursday night")) image = 0;
        else if(loadSpinner.getSelectedItem().equals("Sunday evening")) image = 1;
        else if(loadSpinner.getSelectedItem().equals("Saturday morning")) image = 2;
        EditTeam.putExtra("Index", image);
        EditTeam.putExtra("Orig", 1);
        startActivity(EditTeam);
        Toast.makeText(MainActivity.this, "Team " +
                String.valueOf(loadSpinner.getSelectedItem()) + "\nloaded successfully", Toast.LENGTH_SHORT).show();
    }




    public void createNewTeam(View view) {
        startActivity(new Intent(this, TeamCreationForm.class));
    }
}