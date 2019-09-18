package com.hokagelab.donimst.mademovie.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.hokagelab.donimst.mademovie.model.Movies;

import java.util.ArrayList;
import java.util.List;

import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_ORI_TITLE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_OVERVIEW;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_POPULARITY;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_POSTER;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_RELEASE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_TITLE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_VOTE;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns._ID;
import static com.hokagelab.donimst.mademovie.local.DBContract.TB_NAME;

public class FavoritesRepository {

    private Context context;
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public FavoritesRepository(Context context) {
        this.context = context;
    }

    public FavoritesRepository open() throws SQLException {
        this.dbHelper = new DBHelper(this.context);
        this.database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void begin() {
        database.beginTransaction();
    }

    public void end() {
        database.endTransaction();
    }

    public void setSuccessful() {
        database.setTransactionSuccessful();
    }

    public long insert(Movies movies) {
        ContentValues values = new ContentValues();
        values.put(_ID, movies.getMovId());
        values.put(COL_TITLE, movies.getMovTitle());
        values.put(COL_POSTER, movies.getMovPoster());
        values.put(COL_OVERVIEW, movies.getMovOverview());
        values.put(COL_RELEASE, movies.getMovRelease());
        values.put(COL_ORI_TITLE, movies.getMovOriTitle());
        values.put(COL_POPULARITY, movies.getMovPopularity());
        values.put(COL_VOTE, movies.getMovVote());
        return database.insert(TB_NAME, null, values);
    }

    public int delete(long movieID) {
        return database.delete(TB_NAME, _ID + " = '" + movieID + "'", null);
    }

    public boolean isFavorite(long movieID) {
        Cursor c = database.rawQuery("SELECT * FROM " + TB_NAME + " WHERE " + _ID + " = '" + movieID + "'", null);
        c.moveToFirst();
        return c.getCount() > 0;
    }

    public List<Movies> getFavoritesList() {
        List<Movies> moviesList = new ArrayList<Movies>();
        Cursor c = database.rawQuery("SELECT * FROM " + TB_NAME, null);
        c.moveToFirst();
        Movies movies;
        if (c.getCount() > 0) {
            do {
                movies = new Movies(c.getLong(c.getColumnIndexOrThrow(_ID)),
                        c.getString(c.getColumnIndexOrThrow(COL_TITLE)),
                        c.getString(c.getColumnIndexOrThrow(COL_POSTER)),
                        c.getString(c.getColumnIndexOrThrow(COL_OVERVIEW)),
                        c.getString(c.getColumnIndexOrThrow(COL_RELEASE)),
                        c.getString(c.getColumnIndexOrThrow(COL_ORI_TITLE)),
                        c.getDouble(c.getColumnIndexOrThrow(COL_POPULARITY)),
                        c.getFloat(c.getColumnIndexOrThrow(COL_VOTE)));
                moviesList.add(movies);
                c.moveToNext();
            } while (!c.isAfterLast());
        }
        c.close();
        return moviesList;
    }

    public Cursor getFavoriteListProviderById(String movieID) {
        return database.query(TB_NAME, null, _ID + " = ? ", new String[]{movieID}, null, null, null);
    }

    public Cursor getFavoriteListProvider() {
        return database.query(TB_NAME, null, null, null, null, null, null);
    }

    public int deleteByProvider(String movieID) {
        return database.delete(TB_NAME, _ID + " = ? ", new String[]{movieID});
    }

    public long insertByProvider(ContentValues values) {
        Movies movies = new Movies(
                values.getAsLong(_ID),
                values.getAsString(COL_TITLE),
                values.getAsString(COL_POSTER),
                values.getAsString(COL_OVERVIEW),
                values.getAsString(COL_RELEASE),
                values.getAsString(COL_ORI_TITLE),
                values.getAsDouble(COL_POPULARITY),
                values.getAsFloat(COL_VOTE)
        );
        return this.insert(movies);
    }
}
