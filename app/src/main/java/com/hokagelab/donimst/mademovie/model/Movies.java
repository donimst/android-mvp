package com.hokagelab.donimst.mademovie.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Movies implements Parcelable {

    @SerializedName("id")
    private Long movId;
    @SerializedName("title")
    private String movTitle;
    @SerializedName("poster_path")
    private String movPoster;
    @SerializedName("overview")
    private String movOverview;
    @SerializedName("release_date")
    private String movRelease;
    @SerializedName("original_title")
    private String movOriTitle;
    @SerializedName("popularity")
    private Double movPopularity;
    @SerializedName("vote_average")
    private float movVote;


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
