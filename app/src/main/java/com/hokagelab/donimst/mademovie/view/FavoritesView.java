package com.hokagelab.donimst.mademovie.view;

import com.hokagelab.donimst.mademovie.model.Movies;

import java.util.List;

public interface FavoritesView {

    void showProgress();

    void hideProgress();

    void showMoviesList(List<Movies> moviesList);

    void showEmptyResults();

}
