package com.ezz.moviesapp.movies.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ezz.moviesapp.R;
import com.ezz.moviesapp.movies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ezz on 11/27/2016.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    List<Trailer> movieTrailers;
    Context context;

    public TrailerAdapter(List<Trailer> movieTrailers, Context context) {
        this.movieTrailers = movieTrailers;
        this.context = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,null);
        TrailerViewHolder viewHolder = new TrailerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.bindViews(movieTrailers.get(position));
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.trailer_img)
        ImageView imageView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
        public void bindViews(final Trailer movieTrailer)
        {
            Picasso.with(context).load("https://img.youtube.com/vi/"+movieTrailer.getKey()+"/mqdefault.jpg").placeholder(R.color.white).error(R.drawable.ic_star_white_18dp).into(imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startVideo(movieTrailer.getKey());
                }
            });

        }
    }

    public void updateAdapterList(List<Trailer> movieTrailers)
    {
        this.movieTrailers = movieTrailers;
        notifyDataSetChanged();
    }

    private void startVideo(String videoID) {
        // default youtube app
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + videoID));
        context.startActivity(i);
    }

}
