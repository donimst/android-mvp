package com.hokagelab.donimst.myfavorites;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_ORI_TITLE;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_OVERVIEW;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_POPULARITY;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_POSTER;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_RELEASE;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_TITLE;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_VOTE;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns._ID;
import static com.hokagelab.donimst.myfavorites.DBContract.getColumnDouble;
import static com.hokagelab.donimst.myfavorites.DBContract.getColumnFloat;
import static com.hokagelab.donimst.myfavorites.DBContract.getColumnLong;
import static com.hokagelab.donimst.myfavorites.DBContract.getColumnString;

public class Movies implements Parcelable {

    private Long movId;
    private String movTitle;
    private String movPoster;
    private String movOverview;
    private String movRelease;
    private String movOriTitle;
    private Double movPopularity;
    private float movVote;

    public Movies(){ }

    public Movies(
            Long id, String title, String posterPath, String overview, String releaseDate,
            String originalTitle, Double popularity, float voteAverage) {
        this.movId = id;
        this.movTitle = title;
        this.movPoster = posterPath;
        this.movOverview = overview;
        this.movRelease = releaseDate;
        this.movOriTitle = originalTitle;
        this.movPopularity = popularity;
        this.movVote = voteAverage;
    }

    public Movies(Cursor cursor){
        this.movId = getColumnLong(cursor, _ID);
        this.movTitle = getColumnString(cursor, COL_TITLE);
        this.movPoster = getColumnString(cursor, COL_POSTER);
        this.movOverview = getColumnString(cursor, COL_OVERVIEW);
        this.movRelease = getColumnString(cursor, COL_RELEASE);
        this.movOriTitle = getColumnString(cursor, COL_ORI_TITLE);
        this.movPopularity = getColumnDouble(cursor, COL_POPULARITY);
        this.movVote = getColumnFloat(cursor, COL_VOTE);
    }

    private Movies(Parcel in) {
        movId = in.readLong();
        movTitle = in.readString();
        movPoster = in.readString();
        movOverview = in.readString();
        movRelease = in.readString();
        movOriTitle = in.readString();
        movPopularity = in.readDouble();
        movVote = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(movId);
        parcel.writeString(movTitle);
        parcel.writeString(movPoster);
        parcel.writeString(movOverview);
        parcel.writeString(movRelease);
        parcel.writeString(movOriTitle);
        parcel.writeDouble(movPopularity);
        parcel.writeFloat(movVote);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        public Movies createFromParcel(Parcel in) {
            return new Movies(in);
        }

        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };

    public Long getMovId() {
        return movId;
    }

    public void setMovId(Long movId) {
        this.movId = movId;
    }

    public String getMovTitle() {
        return movTitle;
    }

    public void setMovTitle(String movTitle) {
        this.movTitle = movTitle;
    }

    public String getMovPoster() {
        return movPoster;
    }

    public void setMovPoster(String movPoster) {
        this.movPoster = movPoster;
    }

    public String getMovOverview() {
        return movOverview;
    }

    public void setMovOverview(String movOverview) {
        this.movOverview = movOverview;
    }

    public String getMovRelease() {
        return movRelease;
    }

    public void setMovRelease(String movRelease) {
        this.movRelease = movRelease;
    }

    public String getMovOriTitle() {
        return movOriTitle;
    }

    public void setMovOriTitle(String movOriTitle) {
        this.movOriTitle = movOriTitle;
    }

    public Double getMovPopularity() {
        return movPopularity;
    }

    public void setMovPopularity(Double movPopularity) {
        this.movPopularity = movPopularity;
    }

    public float getMovVote() {
        return movVote;
    }

    public void setMovVote(float movVote) {
        this.movVote = movVote;
    }
}
