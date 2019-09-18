package com.hokagelab.donimst.mademovie.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.hokagelab.donimst.mademovie.BuildConfig;
import com.hokagelab.donimst.mademovie.DetailActivity;
import com.hokagelab.donimst.mademovie.R;
import com.hokagelab.donimst.mademovie.model.Movies;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private List<Movies> moviesList = new ArrayList<Movies>();
    private Context context;

    public MoviesAdapter(Context context, List<Movies> moviesList) {
        this.moviesList = moviesList;
        this.context = context;
    }

    public void setData(List<Movies> data) {
        this.moviesList = data;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MovieViewHolder(
                LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.movie_view, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int i) {
        RequestOptions reqOpt = new RequestOptions();
        reqOpt.transforms(new CenterCrop());
        reqOpt.placeholder(R.mipmap.ic_launcher);
        reqOpt.error(R.mipmap.ic_launcher);

        Glide.with(context)
                .load(BuildConfig.IMG_URL + "/w185/" + moviesList.get(i).getMovPoster())
                .apply(reqOpt)
                .into(holder.moviePoster);
        holder.movieTitle.setText(moviesList.get(i).getMovTitle());
        holder.movieRating.setText(String.valueOf(moviesList.get(i).getMovVote()));
        holder.cardView.setOnClickListener(view -> sendIntent(moviesList.get(i)));
    }

    @Override
    public int getItemCount() {
        if (moviesList != null && !moviesList.isEmpty()) {
            return moviesList.size();
        } else {
            return 0;
        }
    }

    private void sendIntent(Movies movies) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(context.getString(R.string.detail_state), movies);
        context.startActivity(intent);
    }


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageView)
        ImageView moviePoster;
        @BindView(R.id.card_view)
        CardView cardView;
        @BindView(R.id.textView_title)
        TextView movieTitle;
        @BindView(R.id.textView_rating)
        TextView movieRating;

        public MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}