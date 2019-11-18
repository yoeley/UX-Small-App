package com.example.footapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class FinalScreen extends AppCompatActivity {

    int image = -1;
    int orig = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_screen);

        Button GoogleSearch = (Button) findViewById(R.id.GoogleSearchBtn);
        GoogleSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String googleAddress = "http://www.google.com";
                Uri webAdrr = Uri.parse(googleAddress);

                Intent googleIntent = new Intent(Intent.ACTION_VIEW, webAdrr);
                if(googleIntent.resolveActivity(getPackageManager()) != null)
                {
                    startActivity(googleIntent);
                }
            }
        });

        Intent in = getIntent();
        image = in.getIntExtra("Index", -1);
        orig = in.getIntExtra("Orig", -1);

        if(image > -1)
        {
            int pic = getImg(image);
            ImageView img = (ImageView) findViewById(R.id.pitch_imageView);
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
        Intent EditTeam = new Intent(this, EditTeam.class);
        EditTeam.putExtra("Index", image);
        EditTeam.putExtra("Orig", orig);
        startActivity(EditTeam);
    }

    public void share(View view) {
        //do something
        return;
    }
}
