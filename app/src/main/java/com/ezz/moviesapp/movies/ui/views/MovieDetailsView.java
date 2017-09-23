package com.ezz.moviesapp.movies.ui.views;

import com.ezz.moviesapp.base.view.BaseView;
import com.ezz.moviesapp.movies.model.cloud.ReviewsResponse;
import com.ezz.moviesapp.movies.model.cloud.TrailersResponse;

/**
 * Created by EzzWalid on 9/23/2017.
 */

public interface MovieDetailsView extends BaseView {
    void populateTrailers(TrailersResponse trailersResponse);
    void populateReviews(ReviewsResponse reviewsResponse);
    void populateMovieFavoriteStatus(boolean favorite);
}
