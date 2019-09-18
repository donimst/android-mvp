package com.hokagelab.donimst.myfavorites;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_POSTER;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns.COL_TITLE;
import static com.hokagelab.donimst.myfavorites.DBContract.FavoritesColumns._ID;
import static com.hokagelab.donimst.myfavorites.DBContract.URI_FAVMOVIE;
import static com.hokagelab.donimst.myfavorites.DBContract.getColumnLong;
import static com.hokagelab.donimst.myfavorites.DBContract.getColumnString;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {

    private Cursor cursor;
    private Context context;

    public FavoritesAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView) AppCompatImageView imageView;
        @BindView(R.id.textView_title) AppCompatTextView tvTitle;
        @BindView(R.id.fav_action) AppCompatImageButton favoriteAction;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(Context ctx, Cursor c) {
            long movieID = getColumnLong(c, _ID);
            RequestOptions reqOpt = new RequestOptions();
            reqOpt.transforms(new CenterCrop());
            reqOpt.placeholder(R.mipmap.ic_launcher);
            reqOpt.error(R.mipmap.ic_launcher);
            Glide.with(ctx)
                    .load(BuildConfig.IMG_URL + getColumnString(c, COL_POSTER))
                    .apply(reqOpt)
                    .into(imageView);
            tvTitle.setText(getColumnString(c, COL_TITLE));
            favoriteAction.setOnClickListener( v -> showConfirmDialog(ctx, movieID) );
        }

        public void showConfirmDialog(Context ctx, Long movieID) {
            AlertDialog.Builder confirmDialog = new AlertDialog.Builder(ctx);
            confirmDialog.setTitle(ctx.getString(R.string.confirm));
            confirmDialog.setMessage(ctx.getString(R.string.remove));
            confirmDialog.setNegativeButton(ctx.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;
                }
            });
            confirmDialog.setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ctx.getContentResolver().delete(
                            Uri.parse(URI_FAVMOVIE + "/" + movieID), null, null);
                }
            });
            confirmDialog.show();
        }
    }

    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fav_movie_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoritesAdapter.ViewHolder holder, int position) {
        cursor.moveToPosition(position);
        holder.bindView(context, cursor);

    }

    @Override
    public int getItemCount() {
        return (cursor != null) ? cursor.getCount() : 0;
    }
}
