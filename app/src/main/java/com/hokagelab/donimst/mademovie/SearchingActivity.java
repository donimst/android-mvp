package com.hokagelab.donimst.mademovie;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hokagelab.donimst.mademovie.adapter.MoviesAdapter;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.model.MoviesResponse;
import com.hokagelab.donimst.mademovie.network.ApiRepository;
import com.hokagelab.donimst.mademovie.presenter.MoviePresenter;
import com.hokagelab.donimst.mademovie.view.MovieView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchingActivity extends AppCompatActivity implements MovieView {

    @BindView(R.id.setting_toolbar)
    Toolbar toolbar;
    @BindView(R.id.rec_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_empty)
    TextView textEmpty;
    private SearchView searchView;
    private String searchQuery;
    private MoviePresenter presenter;
    private ApiRepository apiRepository = new ApiRepository();
    private List<Movies> moviesList = new ArrayList<Movies>();
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        presenter = new MoviePresenter(this, apiRepository);
        adapter = new MoviesAdapter(this, null);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            moviesList = savedInstanceState.getParcelableArrayList(getString(R.string.movie_state));
            showMoviesList(moviesList);
        } else {
            if (getIntent() != null) {
                searchQuery = getIntent().getStringExtra(getString(R.string.search_query));
                presenter.searchMovie(searchQuery);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenu.getActionView();
        searchView.requestFocus();
        searchView.setQuery(this.searchQuery, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_search:
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        presenter.searchMovie(s);
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelableArrayList(getString(R.string.movie_state), (ArrayList<? extends Parcelable>) moviesList);
        super.onSaveInstanceState(bundle);
    }
}
