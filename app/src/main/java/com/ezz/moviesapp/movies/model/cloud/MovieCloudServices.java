package com.ezz.moviesapp.movies.model.cloud;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public interface MovieCloudServices {
    //===================================================================
    @GET("/3/movie/popular")
    Call<MoviesResponse> requestPopularMovies(@Query("api_key") String apiKey);
    //===================================================================
    @GET("/3/movie/top_rated")
    Call<MoviesResponse> requestTopRatedMovies(@Query("api_key") String apiKey);
    //===================================================================
    @GET("/3/movie/{movie_id}/videos")
    Call<TrailersResponse> requestMovieTrailers(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
    //===================================================================
    @GET("/3/movie/{movie_id}/reviews")
    Call<ReviewsResponse> requestMovieReviews(@Path("movie_id") int movieId, @Query("api_key") String apiKey);
    //===================================================================
}
