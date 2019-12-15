package com.example.footapp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        launchChooseOrCreate();
    }

    public void onResume() {
        super.onResume();
        launchChooseOrCreate();
    }

    private void launchChooseOrCreate() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent ChooseOrCreate = new Intent(getApplicationContext(), ChooseOrCreate.class);
                startActivity(ChooseOrCreate);
            }
        }, 800);
    }
}