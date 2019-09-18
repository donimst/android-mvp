package com.hokagelab.donimst.mademovie.network;

import com.hokagelab.donimst.mademovie.BuildConfig;
import com.hokagelab.donimst.mademovie.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRepository {

    private ApiService apiService;

    public ApiRepository() {
        apiService = getRetrofit().create(ApiService.class);
    }

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public void searchMovie(String keyword, final ApiRepositoryCallback<MoviesResponse> callback) {
        apiService.searchMovie(BuildConfig.API_KEY, "en-US", keyword)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onGetResponse(response.body());
                        } else {
                            callback.onGetError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onGetError();
                    }
                });
    }

    public void getNowPlaying(final ApiRepositoryCallback<MoviesResponse> callback) {
        apiService.getNowPlaying(BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onGetResponse(response.body());
                        } else {
                            callback.onGetError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onGetError();
                    }
                });
    }

    public void getUpcoming(final ApiRepositoryCallback<MoviesResponse> callback) {
        apiService.getUpcoming(BuildConfig.API_KEY, "en-US")
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            callback.onGetResponse(response.body());
                        } else {
                            callback.onGetError();
                        }
                    }

                    @Override
                    public void onFailure(Call<MoviesResponse> call, Throwable t) {
                        callback.onGetError();
                    }
                });
    }


}
