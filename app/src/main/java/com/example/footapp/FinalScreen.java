package com.example.footapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

public class FinalScreen extends AppCompatActivity implements Serializable {

    String data;
    GameData gameData;
    TextView dateAndTime;
    TextView location;
    TextView referee;
    Bitmap screenshot;

    private static File saveBitmap(Bitmap bm, String fileName) {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        Log.d("FinalScreen", "filepath: " + path);
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        Intent in = getIntent();
//        data = in.getStringExtra("Game");
        data = StringConst.data; // Test screen without previous screen being ready.
        parseDataIntoGameObject();
        initPlayerNames();
        setGameInfoOnScreen();
        savetoJSOMfile();
    }

    private void setGameInfoOnScreen() {
        location = findViewById(R.id.location);
        if (gameData.getGameName().equals("")) {
            location.setText("Soccer game, location: " + gameData.getLocation());
        } else {
            location.setText(gameData.getGameName() + " location: " + gameData.getLocation());
        }

        dateAndTime = findViewById(R.id.dateAndTime);
        dateAndTime.setText(gameData.getDate() + ", " + "sunday " + "evening, " + gameData.getTime());

        referee = findViewById(R.id.referee);
        referee.setText(gameData.getReferee());
    }

    private void initPlayerNames() {
        List<TeamData> teamsData = gameData.getTeamData();
        String playerId;
        TextView et;
        for(TeamData teamData : teamsData) {
            for (Player player : teamData.getPlayers()) {
                playerId = player.getPlayerId();
                int id = getResources().getIdentifier(playerId, "id", getPackageName());
                et = findViewById(id);
                et.setText(player.getPlayerName());
                et.setEnabled(false);
            }
        }
    }

    private void parseDataIntoGameObject(){
        final ObjectMapper mapper = new ObjectMapper();
        try {
            gameData = mapper.readValue(data, GameData.class);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(FinalScreen.this, "oops! something's wrong!", Toast.LENGTH_LONG).show();
        }
    }

    public void toCalendar(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, gameData.getGameName());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, gameData.getLocation());

        LocalDate calDate;
        LocalTime calTime;
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("kk:mm");
        calDate = LocalDate.parse(gameData.getDate(), formatterDate);
        calTime = LocalTime.parse(gameData.getTime(), formatterTime);
        Log.d("FinalScreen", "calDate " + calDate.toString());
        Log.d("FinalScreen", "calTime " + calTime.toString());


        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, calDate.getYear()); //TODO something's wrong
        cal.set(Calendar.MONTH, calDate.getMonthValue()); // TODO here too
        cal.set(Calendar.DAY_OF_MONTH, calDate.getDayOfMonth());
        cal.set(Calendar.HOUR_OF_DAY, calTime.getHour());
        cal.set(Calendar.MINUTE, calTime.getMinute());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());

        startActivity(intent);
    }

    public void toMap(View view) {
        String uri = "geo:0,0?q=" + gameData.getLocation();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    private Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void toShare(View view) {
        view = getWindow().getDecorView().getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bm = screenShot(view);
        view.setDrawingCacheEnabled(false);
        File file = saveBitmap(bm, "game.png");
        Log.d("FinalScreen", "filepath: " + file.getAbsolutePath());
        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app.");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(Intent.createChooser(shareIntent, "share via"));
        // TODO: find where the image stored, enable sending it and fix share here
    }

    public void savetoJSOMfile() { //TODO: AVIAD?
        // save the GameData gameData obj
    }
}
