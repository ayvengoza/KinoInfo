package com.ayvengoza.kinoinfo.kinoinfo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class MainFragment extends Fragment {
    private String DEBUG_TAG = "Main Fragment";

    String[] data = {};
    ArrayList<FilmDataObject> films;
    private ArrayAdapter<String> adapter;
    FilmArrayAdapter filmArrayAdapter;
    String  wedAdrdess =
            "https://api.themoviedb.org/3/discover/movie?api_key=b7094a341146a208cb2af9ae6bf6439f&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.refresh_main_fragment:
                DownloadFilmData downloadFilmTask = new DownloadFilmData();
                downloadFilmTask.execute(wedAdrdess);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(DEBUG_TAG, "Start onCreate View");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ListView lvMain = (ListView) rootView.findViewById(R.id.main_list_view);
        //filmArrayAdapter = new FilmArrayAdapter(getActivity(), films);
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new ArrayList<String>(Arrays.asList(data)));
        /*DownloadFilmData downloadFilmTask = new DownloadFilmData();
        downloadFilmTask.execute(wedAdrdess);*/
        lvMain.setAdapter(adapter);
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FilmDataObject sendFilm = films.get(position);
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(FilmDataObject.class.getCanonicalName(), sendFilm);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private class DownloadFilmData extends AsyncTask<String, Void, ArrayList<FilmDataObject>>{

        @Override
        protected ArrayList<FilmDataObject> doInBackground(String... params) {
            if(params.length == 0)
                return null;
            try {
                return getDataFromServer(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.d(DEBUG_TAG, "Something went wrong");
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<FilmDataObject> filmDataObjects) {
            if(filmDataObjects != null){
                adapter.clear();
                films = filmDataObjects;
                for(FilmDataObject filmObject : filmDataObjects){
                    adapter.add(filmObject.toString());
                }
            }
        }

        private ArrayList<FilmDataObject> getDataFromServer(String myurl) throws IOException {
            int READ_TIMEOUT = 10000;
            int CONNECT_TIMEOUT = 15000;
            InputStream is = null;
            try {
                URL url = new URL(myurl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECT_TIMEOUT);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                connection.connect();
                int response = connection.getResponseCode();
                Log.d(DEBUG_TAG, "The response is: " + response);
                is = connection.getInputStream();
                Scanner sc = new Scanner(is);
                StringBuilder resultString = new StringBuilder();
                while(sc.hasNext())
                    resultString.append(sc.nextLine());
                Log.d(DEBUG_TAG, "Message: " + resultString.toString());
                return parseData(resultString.toString());

            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null)
                    is.close();
            }
            Log.d(DEBUG_TAG, "Do not connect");
            return null;
        }

        private ArrayList<FilmDataObject> parseData(String jsonString){
            ArrayList<FilmDataObject> filmsList = new ArrayList<>();
            try {
                JSONObject mainJson = new JSONObject(jsonString);
                JSONArray resultArray = mainJson.getJSONArray("results");
                for(int i=0; i<resultArray.length(); i++){
                    filmsList.add(parseFilmData(resultArray.getJSONObject(i)));
                }

                return filmsList;

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        private FilmDataObject parseFilmData(JSONObject json){
            FilmDataObject fdo = new FilmDataObject();
            try {
                fdo.setPosterPath(json.getString(FilmDataObject.POSTER_PATH));
                fdo.setOverview(json.getString(FilmDataObject.OVERVIEW));
                fdo.setReleaseDate(json.getString(FilmDataObject.RELEASE_DATE));
                fdo.setOriginTitle(json.getString(FilmDataObject.ORIGINAL_TITLE));
                fdo.setTitle(json.getString(FilmDataObject.TITLE));
                fdo.setBackgropPath(json.getString(FilmDataObject.BACKDROP_PATH));
                fdo.setPopularyty(json.getString(FilmDataObject.POPULARITY));
                fdo.setVoteCount(json.getString(FilmDataObject.VOTE_COUNT));
                fdo.setVoteAverage(json.getString(FilmDataObject.VOTE_AVERAGE));
                return fdo;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
