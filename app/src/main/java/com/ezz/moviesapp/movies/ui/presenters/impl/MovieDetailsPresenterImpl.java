package com.ezz.moviesapp.movies.ui.presenters.impl;

import com.ezz.moviesapp.base.datamanager.interfaces.AsyncAction;
import com.ezz.moviesapp.base.datamanager.interfaces.OnRequestCompletedListener;
import com.ezz.moviesapp.base.exception.UiException;
import com.ezz.moviesapp.baseListners.Request_fail_dialog_action_listener;
import com.ezz.moviesapp.movies.business.MovieBusiness;
import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.model.cloud.ReviewsResponse;
import com.ezz.moviesapp.movies.model.cloud.TrailersResponse;
import com.ezz.moviesapp.movies.ui.presenters.MovieDetailsPresenter;
import com.ezz.moviesapp.movies.ui.views.MovieDetailsView;

/**
 * Created by EzzWalid on 9/23/2017.
 */

public class MovieDetailsPresenterImpl extends MovieDetailsPresenter {
    //===================================================================
    MovieBusiness movieBusiness;
    //===================================================================
    @Override
    public void getTrailers(final int movieId, final String apiKet, final int requestCode) {
        getView().showLoading(requestCode);

        AsyncAction asyncAction = new AsyncAction() {
            @Override
            public Object run() throws UiException {
                return movieBusiness.getMovieTrailers(movieId, apiKet);
            }
        };

        OnRequestCompletedListener onRequestCompletedListener = new OnRequestCompletedListener() {
            @Override
            public void onSuccess(Object response) {
                getView().hideLoading(requestCode);
                getView().populateTrailers((TrailersResponse) response);
            }

            @Override
            public void onFail(Exception ex) {
                getView().hideLoading(requestCode);
                getView().handleConnectionError(new Request_fail_dialog_action_listener() {
                    @Override
                    public void positiveAction() {
                        getTrailers(movieId, apiKet, requestCode);
                    }
                });
            }
        };

        performAsyncOperation(asyncAction, onRequestCompletedListener);
    }
    //===================================================================
    @Override
    public void getReviews(final int movieId, final String apiKet, final int requestCode) {
        getView().showLoading(requestCode);

        AsyncAction asyncAction = new AsyncAction() {
            @Override
            public Object run() throws UiException {
                return movieBusiness.getMovieReviews(movieId, apiKet);
            }
        };

        OnRequestCompletedListener onRequestCompletedListener = new OnRequestCompletedListener() {
            @Override
            public void onSuccess(Object response) {
                getView().hideLoading(requestCode);
                getView().populateReviews((ReviewsResponse) response);
            }

            @Override
            public void onFail(Exception ex) {
                getView().hideLoading(requestCode);
                getView().handleConnectionError(new Request_fail_dialog_action_listener() {
                    @Override
                    public void positiveAction() {
                        getTrailers(movieId, apiKet, requestCode);
                    }
                });
            }
        };

        performAsyncOperation(asyncAction, onRequestCompletedListener);
    }
    //===================================================================
    @Override
    public void isFavoriteMovie(Movie movie) {
        getView().populateMovieFavoriteStatus(movieBusiness.isFavoriteMovie(movie));
    }
    //===================================================================
    @Override
    public void favoriteMovie(Movie movie) {
        movieBusiness.addMovieToFavorite(movie);
    }
    //===================================================================
    @Override
    public void unFavoriteMovie(Movie movie) {
        movieBusiness.removeMovieFromFavorite(movie);
    }
    //===================================================================
    @Override
    public void onAttach(MovieDetailsView view) {
        super.onAttach(view);
        movieBusiness = new MovieBusiness(view.getRunningActivity());
    }

    //===================================================================
}
