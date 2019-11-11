package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;

public class EditTeam extends AppCompatActivity {

    int image = -1;
    int orig = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_team);

        Intent in = getIntent();
        image = in.getIntExtra("Index", -1);
        orig = in.getIntExtra("Orig", -1);

        if(image > -1)
        {
            int pic = getImg(image);
            ImageView img = (ImageView) findViewById(R.id.imageView2);
            scaleImg(img, pic);
        }
    }

    private int getImg(int index)
    {
        switch(index)
        {
            case 0:
                return R.drawable.blue_team;
            case 1:
                return R.drawable.yellow_team;
            case 2:
                return R.drawable.orange_team;
            default:
                return -1;
        }
    }
    private void scaleImg(ImageView img, int pic)
    {
        Display screen = getWindowManager().getDefaultDisplay();
        BitmapFactory.Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), pic, options);

        int imgWidth = options.outWidth;
        int screenWidth = screen.getWidth();

        if(imgWidth > screenWidth)
        {
            options.inSampleSize = Math.round((float)imgWidth / (float)screenWidth );
        }
        options.inJustDecodeBounds = false;
        Bitmap scaledImage = BitmapFactory.decodeResource(getResources(), pic, options);
        img.setImageBitmap(scaledImage);
    }

    public void back(View view) {
        if(orig == 1) startActivity(new Intent(this, MainActivity.class));
        else startActivity(new Intent(this, TeamCreationForm.class));
    }

    public void addPlayer(View view) {
        //do something
        return;
    }

    public void toFinalScreen(View view) {
        Intent FinalScreen = new Intent(getApplicationContext(), FinalScreen.class);
        FinalScreen.putExtra("Index", image);
        FinalScreen.putExtra("Orig", 1);
        startActivity(FinalScreen);
    }
}
