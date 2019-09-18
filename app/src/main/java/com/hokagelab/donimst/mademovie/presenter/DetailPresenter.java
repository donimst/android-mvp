package com.hokagelab.donimst.mademovie.presenter;

import com.hokagelab.donimst.mademovie.R;
import com.hokagelab.donimst.mademovie.core.App;
import com.hokagelab.donimst.mademovie.local.FavoritesRepository;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.view.DetailView;

public class DetailPresenter {

    private FavoritesRepository favoritesRepository;
    private DetailView view;

    public DetailPresenter(DetailView view, FavoritesRepository repository) {
        this.view = view;
        this.favoritesRepository = repository;
    }

    public void loadContentData(Movies movies) {
        if (movies != null) view.showMovieDetail(movies);
        else view.showEmptyData();
    }

    public boolean isFavorite(Movies data) {
        favoritesRepository.open();
        boolean isFav = favoritesRepository.isFavorite(data.getMovId());
        favoritesRepository.close();
        return isFav;
    }

    public void addFavorite(Movies data) {
        favoritesRepository.open();
        favoritesRepository.begin();
        long insert = favoritesRepository.insert(data);
        favoritesRepository.setSuccessful();
        favoritesRepository.end();
        favoritesRepository.close();
        if (insert > 0)
            view.showSuccessMessage(App.getInstance().getApplicationContext().getString(R.string.added));
        else
            view.showErrorMessage(App.getInstance().getApplicationContext().getString(R.string.added_error));
    }

    public void removeFavorite(Movies data) {
        favoritesRepository.open();
        favoritesRepository.begin();
        int deleted = favoritesRepository.delete(data.getMovId());
        favoritesRepository.setSuccessful();
        favoritesRepository.end();
        favoritesRepository.close();
        if (deleted > 0)
            view.showSuccessMessage(App.getInstance().getApplicationContext().getString(R.string.removed));
        else
            view.showErrorMessage(App.getInstance().getApplicationContext().getString(R.string.removed_error));
    }
}
