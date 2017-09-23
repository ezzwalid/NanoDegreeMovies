package com.ezz.moviesapp.movies.ui.presenters;

import com.ezz.moviesapp.base.BasePresenter;
import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.ui.views.MoviesPagerFragmentView;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public abstract class MoviesPagerFragmentPresenter extends BasePresenter<MoviesPagerFragmentView> {
    public abstract void getPopularMovies(int requestCode, String apiKey);
    public abstract void getTopRatedMovies(int requestCode, String apiKey);
    public abstract void getFavoriteMovies(int requestCode);
}
