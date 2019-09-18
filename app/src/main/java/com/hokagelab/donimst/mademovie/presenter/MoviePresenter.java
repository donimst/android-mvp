package com.hokagelab.donimst.mademovie.presenter;

import com.hokagelab.donimst.mademovie.model.MoviesResponse;
import com.hokagelab.donimst.mademovie.network.ApiRepository;
import com.hokagelab.donimst.mademovie.network.ApiRepositoryCallback;
import com.hokagelab.donimst.mademovie.view.MovieView;

public class MoviePresenter {

    private ApiRepository apiRepository;
    private MovieView view;

    public MoviePresenter(MovieView view, ApiRepository apiRepository) {
        this.view = view;
        this.apiRepository = apiRepository;
    }

    public void searchMovie(String keyword) {
        view.showProgress();
        apiRepository.searchMovie(keyword, new ApiRepositoryCallback<MoviesResponse>() {
            @Override
            public void onGetResponse(MoviesResponse response) {
                view.onGetResponse(response);
                view.hideProgress();
            }

            @Override
            public void onGetError() {
                view.onGetError();
                view.hideProgress();
            }
        });
    }

    public void getNowPlaying() {
        view.showProgress();
        apiRepository.getNowPlaying(new ApiRepositoryCallback<MoviesResponse>() {
            @Override
            public void onGetResponse(MoviesResponse response) {
                view.onGetResponse(response);
                view.hideProgress();
            }

            @Override
            public void onGetError() {
                view.onGetError();
                view.hideProgress();
            }
        });
    }

    public void getUpcoming() {
        view.showProgress();
        apiRepository.getUpcoming(new ApiRepositoryCallback<MoviesResponse>() {
            @Override
            public void onGetResponse(MoviesResponse response) {
                view.onGetResponse(response);
                view.hideProgress();
            }

            @Override
            public void onGetError() {
                view.onGetError();
                view.hideProgress();
            }
        });
    }
}
