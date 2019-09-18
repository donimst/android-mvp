package com.hokagelab.donimst.mademovie.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.hokagelab.donimst.mademovie.BuildConfig;
import com.hokagelab.donimst.mademovie.local.FavoritesRepository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.hokagelab.donimst.mademovie.local.DBContract.AUTHORITY;

public class FavoritesProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private Context context;
    private FavoritesRepository favRepository;

    static {
        uriMatcher.addURI(AUTHORITY, BuildConfig.TB_NAME, MOVIE);
        uriMatcher.addURI(AUTHORITY, BuildConfig.TB_NAME + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        context = getContext();
        favRepository = new FavoritesRepository(context);
        favRepository.open();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                cursor = favRepository.getFavoriteListProvider();
                break;
            case MOVIE_ID:
                cursor = favRepository.getFavoriteListProviderById(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null) cursor.setNotificationUri(context.getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long added_id;
        switch (uriMatcher.match(uri)) {
            case MOVIE:
                added_id = favRepository.insertByProvider(values);
                break;
            default:
                added_id = 0;
                break;
        }

        if (added_id > 0) context.getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, added_id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int deleted;
        switch (uriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = favRepository.deleteByProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        if (deleted > 0) context.getContentResolver().notifyChange(uri, null);
        return deleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
