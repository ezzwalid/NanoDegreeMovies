package com.ezz.moviesapp.movies.ui.views;

import com.ezz.moviesapp.base.view.BaseView;
import com.ezz.moviesapp.movies.model.cloud.MoviesResponse;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public interface MoviesPagerFragmentView extends BaseView {
    void populateMovies(MoviesResponse moviesResponse);
}
