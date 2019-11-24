package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class TeamCreationForm extends AppCompatActivity {

    private ScrollView TeamCreationScrollView;
    private EditText date;
    private EditText time;
    private EditText location;
    private EditText feild1;
    private EditText field2;
    private EditText field3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        TeamCreationScrollView = findViewById(R.id.TeamCreationScrollView);
        // hide until "advanced" is clicked
        TeamCreationScrollView.setVisibility(View.GONE);

        // just trying the validation method, non of this is for real
        location = findViewById(R.id.location);
        location.addTextChangedListener(new TextValidator(location) {
            @Override public void validate(TextView textView, String text) {
                Pattern p = Pattern.compile( "[0-9]" );
                Matcher m = p.matcher(text);
                if (m.find())
                {
                    textView.setError("location may not contain numbers");
                }
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void create(View view) {
        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        EditTeam.putExtra("Index", 0);
        EditTeam.putExtra("Orig", 2);
        startActivity(EditTeam);
    }
    public void toggle_contents(View v){
        TeamCreationScrollView.setVisibility( TeamCreationScrollView.isShown()
                ? View.GONE
                : View.VISIBLE );
    }

}
