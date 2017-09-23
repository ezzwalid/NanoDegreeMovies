package com.ezz.moviesapp.movies.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.R;
import com.ezz.moviesapp.baseListners.RecyclerViewClickListener;
import com.ezz.moviesapp.helpers.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    //===================================================================
    List<Movie> movies;
    //===================================================================
    public MoviesAdapter(List<Movie> movies) {
        this.movies = movies;
    }
    //===================================================================
    private RecyclerViewClickListener clickListener;
    //===================================================================
    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, null));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindData(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }
    //===================================================================
    class MovieViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_item_image_view)
        ImageView imageView;
        @BindView(R.id.movie_item_image_view_progress)
        ProgressBar progressBar;
        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(final Movie movie){
            Utils.loadImageWithProgress(imageView, progressBar, movie.getPosterPath());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (clickListener!= null) {
                        clickListener.onRecyclerItemClicked(movie);
                    }
                }
            });
        }
    }
    //===================================================================
    public RecyclerViewClickListener getClickListener() {
        return clickListener;
    }

    public void setClickListener(RecyclerViewClickListener clickListener) {
        this.clickListener = clickListener;
    }
    //===================================================================
    public void updateData(List<Movie> movies){
        this.movies = movies;
        notifyDataSetChanged();
    }
    //===================================================================
}
