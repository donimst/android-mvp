package com.hokagelab.donimst.myfavorites;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

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

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static Double getColumnDouble(Cursor cursor, String columnName) {
        return cursor.getDouble( cursor.getColumnIndex(columnName) );
    }

    public static float getColumnFloat(Cursor cursor, String columnName) {
        return cursor.getFloat( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
