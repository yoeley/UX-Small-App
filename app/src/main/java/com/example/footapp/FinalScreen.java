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

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FinalScreen extends AppCompatActivity implements Serializable {
    GameData gameData;
    TextView dateAndTime;
    TextView location;
    TextView referee;
    LocalDate calDate;
    LocalTime calTime;
    String dayOfWeek;
    String partOfDay;
    Calendar cal;


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
        location = findViewById(R.id.location);
        String gameNameTxt;
        if (gameData.getGameName().equals("")) {
            gameNameTxt = "Soccer game, location: " + gameData.getLocation();
            location.setText(gameNameTxt);
        } else {
            gameNameTxt = gameData.getGameName() + " location: " + gameData.getLocation();
            location.setText(gameNameTxt);
        }

        String timeDescription =
                gameData.getDate() + ", " + dayOfWeek + " " + partOfDay + ", " + gameData.getTime();
        dateAndTime = findViewById(R.id.dateAndTime);
        dateAndTime.setText(timeDescription);

        referee = findViewById(R.id.referee);
        referee.setText(gameData.getReferee());
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

    public void setCreativeDayDetails() {
        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
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
        Log.d("FinalScreen", "time: " + Calendar.HOUR_OF_DAY);
        if (calTime.getHour() <= 11) {
            partOfDay = "morning";
        } else if (calTime.getHour() <= 15) {
            partOfDay = "noon";
        } else partOfDay = "evening";
    }



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


    public void captureScreenshot(View view) {
        view = view.getRootView();
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        view.setDrawingCacheEnabled(false);

        File file = saveBitmap(bitmap, "game.png");
        Uri uri = Uri.fromFile(new File(file.getAbsolutePath()));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out my app.");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/*");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivity(shareIntent);
        startActivity(Intent.createChooser(shareIntent, "share via"));
        // TODO: find where the image stored, enable sending it and fix share here
    }

    private void openScreenshot(File imageFile) {
        Uri uri = Uri.fromFile(imageFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        intent.setDataAndType(uri, "image/*");
//        Log.d("FinalScreen", "displayimage");
        startActivity(intent);
    }


    public void captureScreenshot2(View view) {
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + ".jpg";
            // create bitmap screen capture
            View v1 = view.getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
//            Canvas canvas = new Canvas(bitmap);
//            view.draw(canvas);
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);

        } catch (Throwable e) {
            Log.d("FinalScreen", "error");
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    public void toShare(View view) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("C:\\Users\\Marynar\\Documents\\UX\\UX-Small-App\\app\\src\\main\\res\\drawable\\tshirt_referee.png"));
        startActivity(Intent.createChooser(share, "Share Image"));
    }
}
