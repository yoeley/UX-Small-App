package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class TeamCreationForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
    }



    public void back(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
    public void create(View view) {
        Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
        EditTeam.putExtra("Index", 1);
        EditTeam.putExtra("Orig", 2);
        startActivity(EditTeam);
    }
}
