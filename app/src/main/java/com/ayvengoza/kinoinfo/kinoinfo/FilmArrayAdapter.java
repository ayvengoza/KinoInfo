package com.ayvengoza.kinoinfo.kinoinfo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ang on 03.01.17.
 */

public class FilmArrayAdapter extends BaseAdapter {
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<FilmDataObject> objects;

    public FilmArrayAdapter(Context context, ArrayList<FilmDataObject> films) {
            this.context = context;
            if(films != null){
                objects = films;
            } else  {
                objects = new ArrayList<FilmDataObject>();
            }

            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public FilmDataObject getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = layoutInflater.inflate(R.layout.rowlayout, parent, false);
        }
        FilmDataObject film = getFilm(position);

        ((TextView) view.findViewById(R.id.film_title)).setText(film.getTitle());
        ((TextView) view.findViewById(R.id.film_score)).setText(film.getVoteAverage());
        return view;
    }

    private FilmDataObject getFilm(int position){
        return (FilmDataObject)getItem(position);
    }

    public void set(ArrayList<FilmDataObject> films){
        if(films != null){
            objects = films;
        }
    }
}
