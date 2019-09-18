package com.hokagelab.donimst.mademovie.view;

import com.hokagelab.donimst.mademovie.model.Movies;

public interface DetailView {

    void showMovieDetail(Movies movies);

    void showEmptyData();

    void showSuccessMessage(String message);

    void showErrorMessage(String message);

}
