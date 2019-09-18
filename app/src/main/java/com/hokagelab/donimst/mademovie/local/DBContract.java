package com.hokagelab.donimst.mademovie.local;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import com.hokagelab.donimst.mademovie.BuildConfig;

import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_ORI_TITLE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_OVERVIEW;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_POPULARITY;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_POSTER;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_RELEASE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_TITLE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_VOTE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns._ID;

public class DBContract {

    public static final String TB_NAME = BuildConfig.TB_NAME;

    public static final class FavoritesColumns implements BaseColumns {
        public static final String COL_TITLE = "TITLE";
        public static final String COL_POSTER = "POSTER_PATH";
        public static final String COL_OVERVIEW = "OVERVIEW";
        public static final String COL_RELEASE = "RELEASE_DATE";
        public static final String COL_ORI_TITLE = "ORI_TITLE";
        public static final String COL_POPULARITY = "POPULARITY";
        public static final String COL_VOTE = "VOTE_AVERAGE";
    }

    public static final String AUTHORITY = BuildConfig.AUTHORITY;

    public static final Uri URI_FAVMOVIE = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(BuildConfig.TB_NAME)
            .build();

    public static final String DROP_TB = "DROP TABLE IF EXISTS " + TB_NAME;

    public static final String CREATE_TB = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s TEXT NOT NULL,"
                    + " %s REAL NOT NULL,"
                    + " %s REAL NOT NULL)",
            TB_NAME, _ID, COL_TITLE, COL_POSTER, COL_OVERVIEW, COL_RELEASE, COL_ORI_TITLE, COL_POPULARITY, COL_VOTE);

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }
}
