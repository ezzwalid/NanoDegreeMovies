package com.ezz.moviesapp.movies.model.cloud;

import com.ezz.moviesapp.base.datamanager.BaseCloudDataManager;
import com.ezz.moviesapp.base.exception.UiException;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class MovieCloudDataManger extends BaseCloudDataManager {
    //===================================================================
    public MoviesResponse getPopularMovies(String apiKey) throws UiException {
        return performWebCall(create(MovieCloudServices.class).requestPopularMovies(apiKey));
    }
    //===================================================================
    public MoviesResponse getTopRatedMovies(String apiKey) throws UiException {
        return performWebCall(create(MovieCloudServices.class).requestTopRatedMovies(apiKey));
    }
    //===================================================================
    public TrailersResponse getMovieTrailers(int movieId, String apiKey) throws UiException {
        return performWebCall(create(MovieCloudServices.class).requestMovieTrailers(movieId, apiKey));
    }
    //===================================================================
    public ReviewsResponse getMovieReviews(int movieId, String apiKey) throws UiException {
        return performWebCall(create(MovieCloudServices.class).requestMovieReviews(movieId, apiKey));
    }
    //===================================================================
}
