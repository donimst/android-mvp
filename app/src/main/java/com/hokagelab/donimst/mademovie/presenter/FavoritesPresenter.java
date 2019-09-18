package com.hokagelab.donimst.mademovie.presenter;

import com.hokagelab.donimst.mademovie.local.FavoritesRepository;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.view.FavoritesView;

import java.util.List;

public class FavoritesPresenter {

    private FavoritesRepository favoritesRepository;
    private FavoritesView view;

    public FavoritesPresenter(FavoritesView view, FavoritesRepository favoritesRepository) {
        this.view = view;
        this.favoritesRepository = favoritesRepository;
    }

    public void getFavoriteList() {
        view.showProgress();
        favoritesRepository.open();
        favoritesRepository.begin();
        List<Movies> favList = favoritesRepository.getFavoritesList();
        favoritesRepository.setSuccessful();
        favoritesRepository.end();
        favoritesRepository.close();
        view.hideProgress();
        if (!favList.isEmpty()) {
            view.showMoviesList(favList);
        } else {
            view.showEmptyResults();
        }
    }
}
