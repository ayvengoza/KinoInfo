package com.ayvengoza.kinoinfo.kinoinfo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ang on 03.01.17.
 */

public class FilmDataObject implements Parcelable {
    static String POSTER_PATH = "poster_path";
    static String OVERVIEW = "overview";
    static String RELEASE_DATE = "release_date";
    static String ORIGINAL_TITLE = "original_title";
    static String TITLE = "title";
    static String BACKDROP_PATH = "backdrop_path";
    static String POPULARITY = "popularity";
    static String VOTE_COUNT = "vote_count";
    static String VOTE_AVERAGE = "vote_average";

    private String posterPath;
    private String overview;
    private String releaseDate;
    private String originTitle;
    private String title;
    private String backgropPath;
    private String popularyty;
    private String voteCount;
    private String voteAverage;

    protected FilmDataObject() {}

    protected FilmDataObject(Parcel in) {
        posterPath = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        originTitle = in.readString();
        title = in.readString();
        backgropPath = in.readString();
        popularyty = in.readString();
        voteCount = in.readString();
        voteAverage = in.readString();
    }

    public static final Creator<FilmDataObject> CREATOR = new Creator<FilmDataObject>() {
        @Override
        public FilmDataObject createFromParcel(Parcel in) {
            return new FilmDataObject(in);
        }

        @Override
        public FilmDataObject[] newArray(int size) {
            return new FilmDataObject[size];
        }
    };

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setOriginTitle(String originTitle) {
        this.originTitle = originTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBackgropPath(String backgropPath) {
        this.backgropPath = backgropPath;
    }

    public void setPopularyty(String popularyty) {
        this.popularyty = popularyty;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginTitle() {
        return originTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getBackgropPath() {
        return backgropPath;
    }

    public String getPopularyty() {
        return popularyty;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(originTitle);
        dest.writeString(title);
        dest.writeString(backgropPath);
        dest.writeString(popularyty);
        dest.writeString(voteCount);
        dest.writeString(voteAverage);
    }
}
