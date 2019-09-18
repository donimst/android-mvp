package com.hokagelab.donimst.mademovie.movie.upcoming;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hokagelab.donimst.mademovie.R;
import com.hokagelab.donimst.mademovie.adapter.MoviesAdapter;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.model.MoviesResponse;
import com.hokagelab.donimst.mademovie.network.ApiRepository;
import com.hokagelab.donimst.mademovie.presenter.MoviePresenter;
import com.hokagelab.donimst.mademovie.view.MovieView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingFragment extends Fragment implements MovieView, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.up_rec_view)
    RecyclerView recyclerView;
    @BindView(R.id.up_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.up_tv_empty)
    TextView textEmpty;
    @BindView(R.id.up_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private MoviePresenter presenter;
    private ApiRepository apiRepository = new ApiRepository();
    private List<Movies> moviesList = new ArrayList<Movies>();
    private MoviesAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getContext().getString(R.string.up_coming));
        }
        presenter = new MoviePresenter(this, apiRepository);
        adapter = new MoviesAdapter(getContext(), null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(adapter);
        if (savedInstanceState != null) {
            moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.upcome_movie_state));
            showMoviesList(moviesList);
        } else {
            presenter.getUpcoming();
        }
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(getString(R.string.upcome_movie_state), (ArrayList<? extends Parcelable>) moviesList);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public void onRefresh() {
        presenter.getUpcoming();
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

    @Override
    public void onGetResponse(MoviesResponse response) {
        moviesList = response.getResults();
        showMoviesList(moviesList);
    }

    @Override
    public void onGetError() {
        showEmptyResults();
    }

}

