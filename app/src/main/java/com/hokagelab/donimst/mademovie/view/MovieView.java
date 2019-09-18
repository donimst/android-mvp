package com.hokagelab.donimst.mademovie.view;

import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.model.MoviesResponse;
import com.hokagelab.donimst.mademovie.network.ApiRepositoryCallback;

import java.util.List;

public interface MovieView extends ApiRepositoryCallback<MoviesResponse> {

    void showProgress();

    void hideProgress();

    void showMoviesList(List<Movies> moviesList);

    void showEmptyResults();

}
