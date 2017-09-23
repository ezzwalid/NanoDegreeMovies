package com.ezz.moviesapp.movies.business;

import android.content.Context;

import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.model.cloud.MovieCloudDataManger;
import com.ezz.moviesapp.movies.model.cloud.MoviesResponse;
import com.ezz.moviesapp.base.exception.UiException;
import com.ezz.moviesapp.movies.model.cloud.ReviewsResponse;
import com.ezz.moviesapp.movies.model.cloud.TrailersResponse;
import com.ezz.moviesapp.movies.model.db.MoviesDbHandler;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class MovieBusiness {
    //===================================================================
    public MovieBusiness(Context context) {
        dbHandler = new MoviesDbHandler(context);
    }
    //===================================================================
    MoviesDbHandler dbHandler;
    //===================================================================
    MovieCloudDataManger cloudDataManger = new MovieCloudDataManger();
    //===================================================================
    public MoviesResponse getPopularMovies(String apiKey) throws UiException {
        return cloudDataManger.getPopularMovies(apiKey);
    }
    //===================================================================
    public MoviesResponse getTopRatedMovies(String apiKey) throws UiException {
        return cloudDataManger.getTopRatedMovies(apiKey);
    }
    //===================================================================
    public TrailersResponse getMovieTrailers(int movieId, String apiKey) throws UiException {
        return cloudDataManger.getMovieTrailers(movieId, apiKey);
    }
    //===================================================================
    public ReviewsResponse getMovieReviews(int movieId, String apiKey) throws UiException {
        return cloudDataManger.getMovieReviews(movieId, apiKey);
    }
    //===================================================================
    public boolean isFavoriteMovie(Movie movie){
        return dbHandler.dbContainMovie(movie);
    }
    //===================================================================
    public MoviesResponse getFavoriteMovies(){
        MoviesResponse moviesResponse = new MoviesResponse();
        moviesResponse.setMovies(dbHandler.getMovies());
        return moviesResponse;
    }
    //===================================================================
    public void addMovieToFavorite(Movie movie){
        dbHandler.addMovie(movie);
    }
    //===================================================================
    public boolean removeMovieFromFavorite(Movie movie){
        return dbHandler.deleteMovie(movie);
    }
    //===================================================================
}
