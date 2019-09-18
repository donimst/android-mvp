package com.hokagelab.donimst.mademovie;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hokagelab.donimst.mademovie.movie.favorites.FavoritesFragment;
import com.hokagelab.donimst.mademovie.movie.nowplaying.NowPlayingFragment;
import com.hokagelab.donimst.mademovie.movie.upcoming.UpcomingFragment;
import com.hokagelab.donimst.mademovie.settings.SettingsFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_menu)
    BottomNavigationView menuView;
    private SearchView searchView;

    private Fragment nowPlayingFragment = new NowPlayingFragment();
    private Fragment upcomingFragment = new UpcomingFragment();
    private Fragment favoriteFragment = new FavoritesFragment();
    private Fragment settingsFragment = new SettingsFragment();
    private FragmentManager fragmentManager = getSupportFragmentManager();

    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction().replace(R.id.main_content, fragment, fragment.getClass().getSimpleName()).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        menuView.setOnNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            menuView.setSelectedItemId(R.id.now_playing);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);
        MenuItem searchMenu = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenu.getActionView();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_search:
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        Intent intent = new Intent(MainActivity.this, SearchingActivity.class);
                        intent.putExtra(getString(R.string.search_query), s);
                        startActivity(intent);
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
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.now_playing:
                loadFragment(nowPlayingFragment);
                return true;
            case R.id.up_coming:
                loadFragment(upcomingFragment);
                return true;
            case R.id.favorite_movie:
                loadFragment(favoriteFragment);
                return true;
            case R.id.app_settings:
                loadFragment(settingsFragment);
                return true;
        }
        return false;
    }

}
