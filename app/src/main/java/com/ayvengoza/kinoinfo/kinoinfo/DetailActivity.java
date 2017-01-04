package com.ayvengoza.kinoinfo.kinoinfo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private ImageView backgroundImage;
    private ImageView filmImage;
    private TextView titleView;
    private TextView scoreView;
    private TextView descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FilmDataObject film = (FilmDataObject) getIntent().getParcelableExtra(FilmDataObject.class.getCanonicalName());
        backgroundImage = (ImageView)findViewById(R.id.detail_back_image);
        filmImage =(ImageView) findViewById(R.id.detail_film_image);
        titleView = (TextView)findViewById(R.id.detail_title);
        scoreView = (TextView)findViewById(R.id.detail_score);
        descriptionView = (TextView)findViewById(R.id.detail_film_description);

        new ImageDownloadTask().execute(film);
        titleView.setText(film.getTitle());
        scoreView.setText(film.getVoteAverage());
        descriptionView.setText(film.getOverview());

    }

    class ImageDownloadTask extends AsyncTask<FilmDataObject, Void, Void>{
        String addingAdress = "https://image.tmdb.org/t/p/w500";
        Bitmap imageBack = null;
        Bitmap imagePoster = null;

        @Override
        protected Void doInBackground(FilmDataObject... params) {
            if(params.length == 0) {
                return null;
            }
            FilmDataObject filmObj = params[0];
            InputStream in = null;

            try {
                in = new URL(addingAdress + filmObj.getBackgropPath()).openStream();
                imageBack = BitmapFactory.decodeStream(in);
                in = new URL(addingAdress + filmObj.getPosterPath()).openStream();
                imagePoster = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(in !=null)
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            backgroundImage.setImageBitmap(Bitmap.createScaledBitmap(imageBack, 1000, 400, false));
            filmImage.setImageBitmap(Bitmap.createScaledBitmap(imagePoster, 200, 300, false));
        }
    }
}
