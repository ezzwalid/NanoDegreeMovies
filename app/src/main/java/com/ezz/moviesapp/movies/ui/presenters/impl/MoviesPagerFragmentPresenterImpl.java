package com.ezz.moviesapp.movies.ui.presenters.impl;

import com.ezz.moviesapp.movies.business.MovieBusiness;
import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.model.cloud.MoviesResponse;
import com.ezz.moviesapp.movies.ui.presenters.MoviesPagerFragmentPresenter;
import com.ezz.moviesapp.base.datamanager.interfaces.AsyncAction;
import com.ezz.moviesapp.base.datamanager.interfaces.OnRequestCompletedListener;
import com.ezz.moviesapp.base.exception.UiException;
import com.ezz.moviesapp.baseListners.Request_fail_dialog_action_listener;
import com.ezz.moviesapp.movies.ui.views.MoviesPagerFragmentView;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class MoviesPagerFragmentPresenterImpl extends MoviesPagerFragmentPresenter {
    //===================================================================
    MovieBusiness business;
    //===================================================================
    @Override
    public void getPopularMovies(final int requestCode, final String apiKey) {
        getView().showLoading(requestCode);

        AsyncAction asyncAction = new AsyncAction() {
            @Override
            public Object run() throws UiException {
                return business.getPopularMovies(apiKey);
            }
        };

        OnRequestCompletedListener onRequestCompletedListener = new OnRequestCompletedListener() {
            @Override
            public void onSuccess(Object response) {
                getView().hideLoading(requestCode);
                getView().populateMovies((MoviesResponse) response);
            }

            @Override
            public void onFail(Exception ex) {
                getView().hideLoading(requestCode);
                getView().showError("Network failure error");
                getView().handleConnectionError(new Request_fail_dialog_action_listener() {
                    @Override
                    public void positiveAction() {
                        getPopularMovies(requestCode, apiKey);
                    }
                });
            }
        };

        performAsyncOperation(asyncAction, onRequestCompletedListener);

    }
    //===================================================================
    @Override
    public void getTopRatedMovies(final int requestCode, final String apiKey) {
        getView().showLoading(requestCode);

        AsyncAction asyncAction = new AsyncAction() {
            @Override
            public Object run() throws UiException {
                return business.getTopRatedMovies(apiKey);
            }
        };

        OnRequestCompletedListener onRequestCompletedListener = new OnRequestCompletedListener() {
            @Override
            public void onSuccess(Object response) {
                getView().hideLoading(requestCode);
                getView().populateMovies((MoviesResponse) response);
            }

            @Override
            public void onFail(Exception ex) {
                getView().hideLoading(requestCode);
                getView().showError("Network failure error");
                getView().handleConnectionError(new Request_fail_dialog_action_listener() {
                    @Override
                    public void positiveAction() {
                        getPopularMovies(requestCode, apiKey);
                    }
                });
            }
        };

        performAsyncOperation(asyncAction, onRequestCompletedListener);
    }
    //===================================================================
    @Override
    public void getFavoriteMovies(int requestCode) {
        getView().populateMovies(business.getFavoriteMovies());
    }
    //===================================================================
    @Override
    public void onAttach(MoviesPagerFragmentView view) {
        super.onAttach(view);
        business = new MovieBusiness(view.getRunningActivity());
    }
    //===================================================================
}
