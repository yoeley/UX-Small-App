package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

public class CreateGame extends AppCompatActivity {

    final static private String fieldsMissingMsg = "Required fields are missing!";
    final static private String dateFormat = "%s/%s/%s";
    final static private String timeFormat = "%s:%s";

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

    private GameData game;
    private GamesList gamesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_game);

        Intent in = getIntent();
        game = (GameData) in.getSerializableExtra("Game");
        gamesList = (GamesList) in.getSerializableExtra("GamesList");

        TeamCreationScrollView = findViewById(R.id.TeamCreationScrollView);
        moreButton = findViewById(R.id.moreButton);
        createButton = findViewById(R.id.newGameButton);

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


    private void setDatePicker() {
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                final int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(CreateGame.this, R.style.DatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                DateTimeFormatter formatterDate = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    date.setText(LocalDate.of(year, monthOfYear, dayOfMonth).format(formatterDate));//DateTimeFormatter.ISO_LOCAL_DATE));
                                } else {
                                    date.setText(String.format(dateFormat, TimeParser.timeElementToString(dayOfMonth), TimeParser.timeElementToString(monthOfYear), TimeParser.timeElementToString(year)));
                                }
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });
    }


    private void setTimePicker() {
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                final int hour = cldr.get(Calendar.HOUR_OF_DAY);
                final int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                timePicker = new TimePickerDialog(CreateGame.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                DateTimeFormatter formatterTime = null;
                                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                    formatterTime = DateTimeFormatter.ofPattern("kk:mm");
                                    time.setText(LocalTime.of(sHour, sMinute, 0).format(formatterTime));//DateTimeFormatter.ISO_LOCAL_TIME));
                                }
                                else {
                                    time.setText(String.format(timeFormat, TimeParser.timeElementToString(sHour), TimeParser.timeElementToString(sMinute)));
                                }
                            }
                        }, hour, minutes, true);
                timePicker.show();
            }
        });
    }


    public List<String> getGamesNames() {
        return this.gamesNames;
    }


    private void extractGameNames() {
        List<GameData> gameDataList = gamesList.getGameDataList();
        for(GameData gameData : gameDataList) {
            gamesNames.add(gameData.getGameName());
        }
    }

    public void setCreateButtonActiveOrNot() {

        createButtonActive = false;
        createButton.setImageResource(R.drawable.plus);

        String gameNameS = gameName.getText().toString();
        String numOfPlayersS = numOfPlayers.getText().toString();
        String dateS = date.getText().toString();
        String timeS = time.getText().toString();
        String locationS = location.getText().toString();

        if(!gameNameS.trim().equals("") && !numOfPlayersS.trim().equals("") &&
                !dateS.trim().equals("") && !timeS.trim().equals("") && !locationS.trim().equals(""))
        {
            createButtonActive = true;
            createButton.setImageResource(R.drawable.plus);
        }
    }


    private void createGameData() {
        game = new GameData();
        game.initGameData();

        game.setGameName(gameName.getText().toString());
        game.setDate(date.getText().toString());
        game.setTime(time.getText().toString());
        game.setLocation(location.getText().toString());
        game.setReferee(referee.getText().toString());

        game.getTeams().get(0).setNumOfPlayers(Integer.parseInt(numOfPlayers.getText().toString()));
        game.getTeams().get(0).setCaptain(captain1.getText().toString());
        game.getTeams().get(0).addPlayers();

        game.getTeams().get(1).setNumOfPlayers(Integer.parseInt(numOfPlayers.getText().toString()));
        game.getTeams().get(1).setCaptain(captain1.getText().toString());
        game.getTeams().get(1).addPlayers();
    }


    public void createGame(View view) {
        if (!createButtonActive) {
            createButton.setImageResource(R.drawable.plus);
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
            moreButton.setText(R.string.less);
            toggleMore = false;
        }
        else {
            moreButton.setText(R.string.more);
            toggleMore = true;
        }

        TeamCreationScrollView.setVisibility( TeamCreationScrollView.isShown()
                ? View.GONE
                : View.VISIBLE );
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

            String gamesString = FileManager.readFromFile(getApplicationContext(), "savedGames.json");
            gamesList = GamesList.JSONToGamesList(gamesString);
        }
    }
}
