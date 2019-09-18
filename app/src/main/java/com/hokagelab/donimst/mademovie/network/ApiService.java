package com.hokagelab.donimst.mademovie.network;

import com.hokagelab.donimst.mademovie.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("search/movie")
    Call<MoviesResponse> searchMovie(@Query("api_key") String api_key, @Query("language") String lang, @Query("query") String keyword);

    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlaying(@Query("api_key") String api_key, @Query("language") String lang);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcoming(@Query("api_key") String api_key, @Query("language") String lang);

}