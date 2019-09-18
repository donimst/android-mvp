package com.hokagelab.donimst.mademovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.request.RequestOptions;
import com.hokagelab.donimst.mademovie.local.FavoritesRepository;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.presenter.DetailPresenter;
import com.hokagelab.donimst.mademovie.view.DetailView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailView {

    @BindView(R.id.movie_toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_empty_detail)
    TextView emptyTextView;
    @BindView(R.id.tv_title)
    TextView mvTitle;
    @BindView(R.id.tv_ori_title)
    TextView mvOriTitle;
    @BindView(R.id.tv_rate)
    TextView mvRate;
    @BindView(R.id.tv_popularity)
    TextView mvPopularity;
    @BindView(R.id.tv_released)
    TextView mvReleased;
    @BindView(R.id.tv_overview)
    TextView mvOverview;
    @BindView(R.id.iv_poster)
    ImageView mvPoster;

    private DetailPresenter presenter;
    private FavoritesRepository repository;
    private Movies movies;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        movies = intent.getParcelableExtra(getString(R.string.detail_state));
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(movies.getMovTitle());
        }

        repository = new FavoritesRepository(this);
        presenter = new DetailPresenter(this, repository);
        isFavorite = presenter.isFavorite(movies);
        invalidateOptionsMenu();
        presenter.loadContentData(movies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem fav = menu.findItem(R.id.action_fav);
        MenuItem unfav = menu.findItem(R.id.action_unfav);
        fav.setVisible(!isFavorite);
        unfav.setVisible(isFavorite);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_fav:
                presenter.addFavorite(movies);
                isFavorite = !isFavorite;
                invalidateOptionsMenu();
                return true;
            case R.id.action_unfav:
                presenter.removeFavorite(movies);
                isFavorite = !isFavorite;
                invalidateOptionsMenu();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @Override
    public void showMovieDetail(Movies movies) {
        emptyTextView.setVisibility(View.GONE);

        RequestOptions reqOpt = new RequestOptions();
        reqOpt.transforms(new FitCenter());
        reqOpt.placeholder(R.mipmap.ic_launcher);
        reqOpt.error(R.mipmap.ic_launcher);

        Glide.with(this)
                .load(BuildConfig.IMG_URL + "/w185/" + movies.getMovPoster())
                .apply(reqOpt)
                .into(mvPoster);
        mvTitle.setText(movies.getMovTitle());
        mvOriTitle.setText(movies.getMovOriTitle());
        mvPopularity.setText(String.valueOf(movies.getMovPopularity()));
        mvRate.setText(String.valueOf(movies.getMovVote()));
        mvReleased.setText(movies.getMovRelease());
        mvOverview.setText(movies.getMovOverview());
    }

    @Override
    public void showEmptyData() {
        emptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSuccessMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
