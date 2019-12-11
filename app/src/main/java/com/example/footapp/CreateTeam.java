package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateTeam extends AppCompatActivity {

    final static private String fieldsMissingMsg = "Required fields are missing!";

    private ScrollView TeamCreationScrollView;

    private Button moreButton;
    private ImageButton createButton;
    private EditText gameName;
    private EditText numOfPlayers;
    private EditText date;
    private EditText time;
    private EditText location;
    private EditText referee;
    private EditText captain1;
    private EditText captain2;

    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    private GameData game;

    private List<String> gamesNames;
    private Boolean createButtonActive;
    private Boolean isReturnVisit = false;
    private Boolean toggleMore = true;

    private String gameNameS;
    private String numOfPlayersS;
    private String dateS;
    private String timeS;
    private String locationS;
    private String refereeS;
    private String captain1S;
    private String captain2S;

    private GamesList gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        Intent in = getIntent();
        gamesList = (GamesList) in.getSerializableExtra("Game");

        TeamCreationScrollView = findViewById(R.id.TeamCreationScrollView);
        moreButton = findViewById(R.id.moreButton);
        createButton = findViewById(R.id.createTeamButton);

        gameName = findViewById(R.id.gameName);
        numOfPlayers = findViewById(R.id.numOfPlayers);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        referee = findViewById(R.id.referee);
        captain1 = findViewById(R.id.captain1);
        captain2 = findViewById(R.id.captain2);
        gamesNames = new ArrayList<String>();
        createButtonActive = false;

        setDatePicker();
        setTimePicker();
        extractGameNames();

        // hide until "advanced" is clicked
        TeamCreationScrollView.setVisibility(View.GONE);

        numOfPlayers.addTextChangedListener(new TextValidator(numOfPlayers, TextValidator.numPlayersValid, this){});
        gameName.addTextChangedListener(new TextValidator(gameName, TextValidator.gameNameValid, this){});
        date.addTextChangedListener(new TextValidator(date, TextValidator.emptyChecker, this){});
        time.addTextChangedListener(new TextValidator(time, TextValidator.emptyChecker, this){});
        location.addTextChangedListener(new TextValidator(location, TextValidator.emptyChecker, this){});
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isReturnVisit) {
            gameName.setText(gameNameS);
            numOfPlayers.setText(numOfPlayersS);
            date.setText(dateS);
            time.setText(timeS);
            location.setText(locationS);
            referee.setText(refereeS);
            captain1.setText(captain1S);
            captain2.setText(captain2S);
        }
    }

    public List<String> getGamesNames() {
        return this.gamesNames;
    }

    private void extractGameNames() {
        List<GameData> gameDataList = gamesList.getGameDataList();
        gamesNames.add(gameDataList.get(0).getGameName());
        gamesNames.add(gameDataList.get(1).getGameName());
        gamesNames.add(gameDataList.get(2).getGameName());
    }
    private void setDatePicker() {
        date.setInputType(InputType.TYPE_DATETIME_VARIATION_DATE);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(CreateTeam.this, R.style.DatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
    }

    private void setTimePicker() {
        time.setInputType(InputType.TYPE_DATETIME_VARIATION_TIME);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(CreateTeam.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                time.setText(sHour + ":" + sMinute);
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });
    }

    public void setCreateButtonActiveOrNot() {

        createButtonActive = false;
        createButton.setImageResource(R.drawable.add_pitch_disabled);

        String gameNameS = gameName.getText().toString();
        String numOfPlayersS = numOfPlayers.getText().toString();
        String dateS = date.getText().toString();
        String timeS = time.getText().toString();
        String locationS = location.getText().toString();

        if(!gameNameS.trim().equals("") && !numOfPlayersS.trim().equals("") &&
                !dateS.trim().equals("") && !timeS.trim().equals("") && !locationS.trim().equals(""))
        {
            createButtonActive = true;
            createButton.setImageResource(R.drawable.add_pitch2);
        }
    }

    private void createGameData() {
        game = new GameData();

        game.setGameName(gameName.getText().toString());
        game.setDate(date.getText().toString());
        game.setTime(time.getText().toString());
        game.setLocation(location.getText().toString());
        game.setReferee(referee.getText().toString());

        game.getTeams().get(0).setTeamName("teamOne");
        game.getTeams().get(0).setNumOfPlayers(Integer.parseInt(numOfPlayers.getText().toString()));
        game.getTeams().get(0).setCaptain(captain1.getText().toString());

        game.getTeams().get(1).setTeamName("teamTwo");
        game.getTeams().get(1).setNumOfPlayers(Integer.parseInt(numOfPlayers.getText().toString()));
        game.getTeams().get(1).setCaptain(captain1.getText().toString());
    }


    private void saveFields() {
        gameNameS = gameName.getText().toString();
        numOfPlayersS = numOfPlayers.getText().toString();
        dateS = date.getText().toString();
        timeS = time.getText().toString();
        locationS = location.getText().toString();
        refereeS = referee.getText().toString();
        captain1S = captain1.getText().toString();
        captain2S = captain2.getText().toString();
    }


    public void create(View view) {
        if (!createButtonActive) {
            createButton.setImageResource(R.drawable.add_pitch_disabled);
            Toast.makeText(this, fieldsMissingMsg, Toast.LENGTH_SHORT).show();
        }
        else {
            Intent EditTeam = new Intent(getApplicationContext(), EditTeam.class);
            createGameData();
            saveFields();
            isReturnVisit = true;

            EditTeam.putExtra("Orig", 2);
            EditTeam.putExtra("Game", game);
            EditTeam.putExtra("GamesList",gamesList);

            startActivity(EditTeam);
        }
    }

    public void toggle_contents(View v){
        if (toggleMore) {
            moreButton.setText("less...");
            toggleMore = false;
        }
        else {
            moreButton.setText("more...");
            toggleMore = true;
        }

        TeamCreationScrollView.setVisibility( TeamCreationScrollView.isShown()
                ? View.GONE
                : View.VISIBLE );
    }
}
