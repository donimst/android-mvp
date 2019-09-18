package com.hokagelab.donimst.mademovie.settings;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;

import com.hokagelab.donimst.mademovie.R;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.model.MoviesResponse;
import com.hokagelab.donimst.mademovie.network.ApiRepository;
import com.hokagelab.donimst.mademovie.network.ApiRepositoryCallback;
import com.hokagelab.donimst.mademovie.receiver.NotificationReceiver;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragmentCompat implements OnPreferenceChangeListener {

    private SwitchPreference dailyReminder, releasedReminder;
    private Preference languagePreference;
    private NotificationReceiver notifReceiver;

    public SettingsFragment() {

    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_settings);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setBackground(getContext().getResources().getDrawable(R.drawable.custom_card));
        ViewGroup.MarginLayoutParams margin = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        margin.setMargins(16, 16, 16, 16);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(getContext().getString(R.string.settings));
        }
        notifReceiver = new NotificationReceiver(getContext());

        dailyReminder = (SwitchPreference) findPreference(getString(R.string.daily_key));
        releasedReminder = (SwitchPreference) findPreference(getString(R.string.release_key));
        languagePreference = (Preference) findPreference(getString(R.string.lang_key));

        dailyReminder.setOnPreferenceChangeListener(this::onPreferenceChange);
        releasedReminder.setOnPreferenceChangeListener(this::onPreferenceChange);
        languagePreference.setOnPreferenceClickListener(preference -> {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        });
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.daily_key))) {
            if ((boolean) newValue) {
                notifReceiver.setReminderDaily("active");
            } else {
                notifReceiver.setReminderDaily("inactive");
            }
        } else if (key.equals(getString(R.string.release_key))) {
            if ((boolean) newValue) {
                notifReceiver.setReminderRelease("active");
            } else {
                notifReceiver.setReminderRelease("inactive");
            }
        }
        return true;
    }

    private void setReleaseAlarm() {
        List<Movies> moviesList = new ArrayList<Movies>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String now = dateFormat.format(new Date());
        ApiRepository apiRepository = new ApiRepository();
        apiRepository.getUpcoming(new ApiRepositoryCallback<MoviesResponse>() {
            @Override
            public void onGetResponse(MoviesResponse response) {
                for (Movies movies : response.getResults()) {
                    if (movies.getMovRelease().equals(now)) {
                        moviesList.add(movies);
                    }
                }

            }

            @Override
            public void onGetError() {
            }
        });

    }
}
