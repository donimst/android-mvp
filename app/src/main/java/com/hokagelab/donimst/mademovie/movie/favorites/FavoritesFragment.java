package com.hokagelab.donimst.mademovie.movie.favorites;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hokagelab.donimst.mademovie.R;
import com.hokagelab.donimst.mademovie.adapter.MoviesAdapter;
import com.hokagelab.donimst.mademovie.local.FavoritesRepository;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.presenter.FavoritesPresenter;
import com.hokagelab.donimst.mademovie.view.FavoritesView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoritesFragment extends Fragment implements FavoritesView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.fav_rec_view)
    RecyclerView recyclerView;
    @BindView(R.id.fav_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.fav_tv_empty)
    TextView textEmpty;
    @BindView(R.id.fav_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private FavoritesPresenter presenter;
    private FavoritesRepository repository;
    private List<Movies> moviesList = new ArrayList<Movies>();
    private MoviesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getContext().getString(R.string.favorites));
        }
        repository = new FavoritesRepository(getContext());
        presenter = new FavoritesPresenter(this, repository);
        adapter = new MoviesAdapter(getContext(), null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
        if (savedInstanceState != null) {
            moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.fav_movie_state));
            showMoviesList(moviesList);
        } else {
            presenter.getFavoriteList();
        }
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(getString(R.string.fav_movie_state), (ArrayList<? extends Parcelable>) moviesList);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onRefresh() {
        presenter.getFavoriteList();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMoviesList(List<Movies> moviesList) {
        this.moviesList = moviesList;
        textEmpty.setVisibility(View.GONE);
        adapter.setData(moviesList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showEmptyResults() {
        textEmpty.setVisibility(View.VISIBLE);
        adapter.setData(null);
        adapter.notifyDataSetChanged();
    }

}

