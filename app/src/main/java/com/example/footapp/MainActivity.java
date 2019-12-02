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

    public Button create, favorite1, favorite2, favorite3;
//    public Spinner loadSpinner;
//    public String loadFilePath = Environment. + "load";
    List<String> team_names = new ArrayList<String>();
//    String saved_1, saved_2, saved_3;
//    String LoadedTeam;

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
        favorite1 = findViewById(R.id.favoriteGroup1Button);
        favorite1.setText(team_names.get(0));
        favorite2 = (Button) findViewById(R.id.favoriteGroup2Button);
        favorite2.setText(team_names.get(1));
        favorite3 = (Button) findViewById(R.id.favoriteGroup3Button);
        favorite3.setText(team_names.get(2));
//        loadSpinner = (Spinner) findViewById(R.id.numOfPlayersSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, team_names);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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


    public void loadTeam(View v) {

        Button b = (Button)v;
        String id = v.getResources().getResourceEntryName(v.getId());
        String text = b.getText().toString();
        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
//        int image = -1;
//        if(loadSpinner.getSelectedItem().equals("Thursday night")) image = 0;
//        else if(loadSpinner.getSelectedItem().equals("Sunday evening")) image = 1;
//        else if(loadSpinner.getSelectedItem().equals("Saturday morning")) image = 2;
        EditTeam.putExtra("Index", id);
        EditTeam.putExtra("Orig", 1);
        startActivity(EditTeam);
        Toast.makeText(MainActivity.this, "Team \"" +
                text + "\"\nloaded successfully", Toast.LENGTH_SHORT).show();
    }



    public void createNewTeam(View view) {
        startActivity(new Intent(this, TeamCreationForm.class));
    }

    public void exitProgram(View view)
    {
        //TODO: save current configuration to json
        finish();
        System.exit(0);
    }
}