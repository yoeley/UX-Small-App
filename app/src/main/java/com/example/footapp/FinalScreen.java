package com.example.footapp;

import android.content.Intent;
import android.content.Context;
import android.content.ClipData;
import android.os.Build;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import android.Manifest;
import android.content.pm.PackageManager;

public class FinalScreen extends AppCompatActivity implements Serializable {

    private final String timeDescriptionFormat = "%s, %s, %s";
    private final String gameNameRefereeFormat = "%s, referee: %s";
    private final String locationFormat = "location: %s";
    private final String screenshotFileName = "game.png";

    private GameData gameData;
    private TextView dateAndTime;
    private TextView location;
    private TextView gameNameReferee;
    private LocalDate calDate;
    private LocalTime calTime;
    private String dayOfWeek;
    private Calendar cal;
    private Bitmap bitmap;
    private File screenshotFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        Intent in = getIntent();
        gameData =(GameData) in.getSerializableExtra("Game");

        setCreativeDayDetails();
        initPlayerNames();

        setGameInfoOnScreen();
    }

    public void setCreativeDayDetails() {
        DateTimeFormatter formatterDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("kk:mm");
            calDate = LocalDate.parse(gameData.getDate(), formatterDate);
            calTime = LocalTime.parse(gameData.getTime(), formatterTime);
            cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, calDate.getYear());
            cal.set(Calendar.MONTH, calDate.getMonthValue() - 1);
            cal.set(Calendar.DAY_OF_MONTH, calDate.getDayOfMonth());
            cal.set(Calendar.HOUR_OF_DAY, calTime.getHour());
            cal.set(Calendar.MINUTE, calTime.getMinute());
            dayOfWeek = calDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        }
        else {
            String[] date = gameData.getDate().split("/");
            String[] time = gameData.getTime().split(":");
            cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(date[2]));
            cal.set(Calendar.MONTH, Integer.parseInt(date[1]) - 1);
            cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            cal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            dayOfWeek = "";
        }
        Log.d("FinalScreen", "time: " + Calendar.HOUR_OF_DAY);
    }

    private void initPlayerNames() {
        List<TeamData> teamsData = gameData.getTeams();
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

    private void setGameInfoOnScreen() {
        gameNameReferee = findViewById(R.id.gameNameReferee);
        String gameNameTxt;
        if (gameData.getReferee().equals("")) {
            gameNameReferee.setText(gameData.getGameName());
        } else {
            gameNameTxt = String.format(gameNameRefereeFormat, gameData.getGameName(), gameData.getReferee());
            gameNameReferee.setText(gameNameTxt);
        }

        String timeDescription = String.format(timeDescriptionFormat, dayOfWeek, gameData.getDate(), gameData.getTime());
        dateAndTime = findViewById(R.id.dateAndTime);
        dateAndTime.setText(timeDescription);

        String locationDescription  = String.format(locationFormat, gameData.getLocation());
        location = findViewById(R.id.location);
        location.setText(locationDescription);
    }

    public void toCalendar(View view) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, gameData.getGameName());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, gameData.getLocation());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
        startActivity(intent);
    }


    public void toMap(View view) {
        String uri = "geo:0,0?q=" + gameData.getLocation();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);
    }

    public void toShare(View view) {
        view = view.getRootView();
        view.setDrawingCacheEnabled(true);
        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        view.setDrawingCacheEnabled(false);

        saveBitmap();
        //Uri uri = Uri.fromFile(new File(screenshotFile.getAbsolutePath()));
        Uri uri = FileProvider.getUriForFile(this, this.getApplicationContext().getPackageName() + ".provider", screenshotFile);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app.");
        shareIntent.setDataAndType(uri, "image/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.setData(uri);
        shareIntent.setType("image/*");

        //TODO: these were 2 seperate tries to handle an exception the rise when trying to share picture.
        //TODO: none of them worked, but the exception doesn't prevent the app from sharing the pic, so...
        /*
        List<ResolveInfo> resolvedIntentActivities = this.getPackageManager().queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
            String packageName = resolvedIntentInfo.activityInfo.packageName;
            this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }


        if ( Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP ) {
            shareIntent.setClipData(ClipData.newRawUri( "", uri ) );
            shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION|Intent.FLAG_GRANT_READ_URI_PERMISSION );
        }
        */

        startActivity(Intent.createChooser(shareIntent, "share via"));
    }


    private void writeScreenshotFile() {
        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                screenshotFile.createNewFile();
                FileOutputStream fOut = new FileOutputStream(screenshotFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, fOut);
                fOut.flush();
                fOut.close();
            } else {
                // Request permission from the user
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBitmap() {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        Log.d("FinalScreen", "filepath: " + path);
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();

        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.d("FinalScreen", "trouble");
        }
        else {
            screenshotFile = new File(getExternalFilesDir(""), screenshotFileName);
        }
        writeScreenshotFile();
    }

    private static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    private static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                writeScreenshotFile();
        }
    }
}
