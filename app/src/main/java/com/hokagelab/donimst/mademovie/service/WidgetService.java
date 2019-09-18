package com.hokagelab.donimst.mademovie.service;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.hokagelab.donimst.mademovie.BuildConfig;
import com.hokagelab.donimst.mademovie.R;

import java.util.concurrent.ExecutionException;

import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_POSTER;
import static com.hokagelab.donimst.mademovie.local.DBContract.FavoritesColumns.COL_TITLE;
import static com.hokagelab.donimst.mademovie.local.DBContract.URI_FAVMOVIE;
import static com.hokagelab.donimst.mademovie.local.DBContract.getColumnString;

public class WidgetService extends RemoteViewsService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteView(this.getApplicationContext(), intent);
    }


    class StackRemoteView implements RemoteViewsService.RemoteViewsFactory {

        private Context ctx;
        private Cursor cursor;

        public StackRemoteView(Context context, Intent intent) {
            ctx = context;
        }

        @Override
        public void onCreate() {
            cursor = ctx.getContentResolver()
                    .query(URI_FAVMOVIE, null, null, null, null);
        }

        @Override
        public void onDataSetChanged() {
            if (cursor != null) cursor.close();

            final long token = Binder.clearCallingIdentity();
            cursor = ctx.getContentResolver()
                    .query(URI_FAVMOVIE, null, null, null, null);
            Binder.restoreCallingIdentity(token);
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return cursor.getCount();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (!cursor.moveToPosition(position))
                throw new IllegalStateException("Invalid Position");

            RemoteViews rv = new RemoteViews(ctx.getPackageName(), R.layout.widget_item);

            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(ctx)
                        .asBitmap()
                        .load(BuildConfig.IMG_URL + "/w185/" + getColumnString(cursor, COL_POSTER))
                        .apply(new RequestOptions().fitCenter()
                        )
                        .submit()
                        .get();

                rv.setImageViewBitmap(R.id.stack_imageView, bitmap);
                rv.setTextViewText(R.id.stack_textView, getColumnString(cursor, COL_TITLE));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
