package com.hokagelab.donimst.mademovie.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hokagelab.donimst.mademovie.BuildConfig;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, BuildConfig.DB_NAME, null, BuildConfig.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DBContract.CREATE_TB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DBContract.DROP_TB);
    }
}
