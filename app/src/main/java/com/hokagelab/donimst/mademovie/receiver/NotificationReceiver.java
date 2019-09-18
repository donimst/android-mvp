package com.hokagelab.donimst.mademovie.receiver;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import com.hokagelab.donimst.mademovie.DetailActivity;
import com.hokagelab.donimst.mademovie.MainActivity;
import com.hokagelab.donimst.mademovie.R;
import com.hokagelab.donimst.mademovie.model.Movies;
import com.hokagelab.donimst.mademovie.model.MoviesResponse;
import com.hokagelab.donimst.mademovie.network.ApiRepository;
import com.hokagelab.donimst.mademovie.network.ApiRepositoryCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


public class NotificationReceiver extends BroadcastReceiver {

    private Context ctx;
    public static final String NOTIF_TYPE = "NOTIF_TYPE";
    public static final String DAILY = "DAILY";
    public static final String RELEASE = "RELEASE";
    public static final String CHANNEL = "CHANNEL_01";
    public static final int ID_DAILY = 101;
    public static final int ID_RELEASE = 102;

    public NotificationReceiver() {
    }

    public NotificationReceiver(Context context) {
        this.ctx = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getStringExtra(NOTIF_TYPE)) {
            case DAILY:
                makeNotification(context, DAILY, null, null);
                break;
            case RELEASE:
                getReleasedMovie(context);
                //makeNotification(context, RELEASE, intent.getIntExtra("notificationID", 0), intent.getParcelableExtra("movie"));
                break;
        }
    }

    private Calendar getReminderTime(String type) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, type.equals(DAILY) ? 7 : 8); //pengaturan jam, jika daily jam 7, dan jika release reminder jam 8
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        if (calendar.getTimeInMillis() < System.currentTimeMillis()) calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar;
    }

    private Intent getReminderIntent(String type) {
        Intent intent = new Intent(ctx, NotificationReceiver.class);
        intent.putExtra(NOTIF_TYPE, type);
        return intent;
    }

    public void setReminderDaily(String mode) {
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(ctx, ID_DAILY, getReminderIntent(DAILY), PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (mode.equals("active")) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    getReminderTime(DAILY).getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

//    public void setReminderRelease(String mode, @Nullable List<Movies> moviesList) {
//        if (moviesList != null) {
//            int notificationID = 200;
//            for (Movies movies : moviesList) {
//                Intent intent = getReminderIntent(RELEASE);
//                intent.putExtra("movie", movies);
//                intent.putExtra("notificationID", notificationID);
//                PendingIntent pendingIntent = PendingIntent
//                        .getBroadcast(ctx, ID_RELEASE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//                AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
//                alarmManager.cancel(pendingIntent);
//                if (mode.equals("active")) {
//                    alarmManager.setInexactRepeating(
//                            AlarmManager.RTC_WAKEUP,
//                            getReminderTime(RELEASE).getTimeInMillis(),
//                            AlarmManager.INTERVAL_DAY,
//                            pendingIntent);
//                }
//                notificationID += 1;
//            }
//        }
//    }

    public void setReminderRelease(String mode) {
        PendingIntent pendingIntent = PendingIntent
                .getBroadcast(ctx, ID_RELEASE, getReminderIntent(RELEASE), PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        if (mode.equals("active")) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    getReminderTime(RELEASE).getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }

    private void makeNotification(Context context, String type, @Nullable Integer id, @Nullable Movies movies) {
        String notifTitle = type.equals(DAILY) ? context.getString(R.string.daily_reminder) : movies.getMovTitle() + " " + context.getString(R.string.released_now);
        int ID = type.equals(DAILY) ? ID_DAILY : id;
        Intent intent = type.equals(DAILY) ? new Intent(context, MainActivity.class) : new Intent(context, DetailActivity.class);
        if(type.equals((RELEASE))) intent.putExtra(context.getString(R.string.detail_state), movies);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notifManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, CHANNEL)
                .setSmallIcon(R.drawable.ic_notifications_black_24)
                .setLargeIcon(BitmapFactory
                        .decodeResource(context.getResources(), R.drawable.ic_notifications_black_24))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(notifTitle)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifChannel = new NotificationChannel(CHANNEL, "Movie Channel", NotificationManager.IMPORTANCE_DEFAULT);
            notifChannel.enableLights(true);
            notifChannel.setLightColor(Color.BLUE);
            notifBuilder.setChannelId(CHANNEL);
        }

        if (type.equals(DAILY)) {
            notifManager.notify(ID_DAILY, notifBuilder.build());
        } else {
            notifManager.notify(ID_RELEASE, notifBuilder.build());
        }
    }

    private void getReleasedMovie(Context context) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String now = dateFormat.format(new Date());
        ApiRepository apiRepository = new ApiRepository();
        apiRepository.getUpcoming(new ApiRepositoryCallback<MoviesResponse>() {
            @Override
            public void onGetResponse(MoviesResponse response) {
                int notificationID = 200;
                for (Movies movies : response.getResults()) {
                    if (movies.getMovRelease().equals(now)) {
                        makeNotification(context, RELEASE, notificationID, movies);
                        notificationID += 1;
                    }
                }
            }

            @Override
            public void onGetError() {
            }
        });

    }
}
