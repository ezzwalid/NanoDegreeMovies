package com.ezz.moviesapp.movies.ui.presenters;

import com.ezz.moviesapp.base.BasePresenter;
import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.ui.views.MovieDetailsView;

/**
 * Created by EzzWalid on 9/23/2017.
 */

public abstract class MovieDetailsPresenter extends BasePresenter<MovieDetailsView> {
    public abstract void getTrailers(int MovieId, String apiKet, int requestCode);
    public abstract void getReviews(int MovieId, String apiKet, int requestCode);
    public abstract void isFavoriteMovie(Movie movie);
    public abstract void favoriteMovie(Movie movie);
    public abstract void unFavoriteMovie(Movie movie);
}
