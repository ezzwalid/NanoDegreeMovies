package com.ezz.moviesapp.movies.ui.fragments;


import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ezz.moviesapp.R;
import com.ezz.moviesapp.base.BaseFragment;
import com.ezz.moviesapp.baseListners.ActivityResultController;
import com.ezz.moviesapp.helpers.ColorUtils;
import com.ezz.moviesapp.helpers.Contacts;
import com.ezz.moviesapp.helpers.Utils;
import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.model.Review;
import com.ezz.moviesapp.movies.model.Trailer;
import com.ezz.moviesapp.movies.model.cloud.ReviewsResponse;
import com.ezz.moviesapp.movies.model.cloud.TrailersResponse;
import com.ezz.moviesapp.movies.ui.adapters.ReviewAdapter;
import com.ezz.moviesapp.movies.ui.adapters.TrailerAdapter;
import com.ezz.moviesapp.movies.ui.presenters.MovieDetailsPresenter;
import com.ezz.moviesapp.movies.ui.presenters.impl.MovieDetailsPresenterImpl;
import com.ezz.moviesapp.movies.ui.views.MovieDetailsView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailsFragment extends BaseFragment<MovieDetailsPresenter> implements MovieDetailsView {
    //===================================================================
    public static final int REVIEWS_REQUEST_CODE = 1;
    public static final int TRAILERS_REQUEST_CODE = 2;
    //===================================================================
    public static final String TRAILERS_RESPONSE_KEY = "trailersResponse";
    TrailerAdapter trailerAdapter;
    TrailersResponse trailersResponse;
    //===================================================================
    public static final String REVIEWS_RESPONSE_KEY = "reviewsResponse";
    ReviewsResponse reviewsResponse;
    ReviewAdapter reviewAdapter;
    //===================================================================
    @BindView(R.id.review_recycler)
    RecyclerView reviewsRecycler;
    @BindView(R.id.review_recycler_progress)
    ProgressBar reviewsProgress;
    @BindView(R.id.details_fragment_trailer_recycler)
    RecyclerView trailersRecycler;
    @BindView(R.id.details_fragment_trailer_recycler_progress)
    ProgressBar trailersProgress;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapse)
    CollapsingToolbarLayout toolbar;
    @BindView(R.id.details_fragment_cover_image)
    ImageView cover_imge;
    @BindView(R.id.details_fragment_cover_image_progress)
    ProgressBar coverProgress;
    @BindView(R.id.details_fragment_poster_image)
    ImageView poster_Image;
    @BindView(R.id.details_fragment_poster_image_progress)
    ProgressBar posterProgress;
    @BindView(R.id.details_fragment_title)
    TextView title;
    @BindView(R.id.details_fragment_date)
    TextView date;
    @BindView(R.id.mainDetailFragment_info_container)
    RelativeLayout info_container;
    @BindView(R.id.details_fragment_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.favorite_fab)
    FloatingActionButton fab;
    @BindView(R.id.share_fab)
    FloatingActionButton share_fab;
    @BindView(R.id.detail_fragment_description)
    TextView description;
    @BindView(R.id.detail_fragment_info_date)
    TextView releaseDate;
    @BindView(R.id.detail_fragment_rate)
    RatingBar ratingBar;
    //===================================================================
    public static final String MOVIE_KEY = "movieKey";
    Movie movie;
    //===================================================================
    public static Fragment newInstance(Movie movie){
        MovieDetailsFragment fragment = new MovieDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MOVIE_KEY, movie);
        fragment.setArguments(bundle);
        return fragment;
    }
    //===================================================================
    public MovieDetailsFragment() {
        // Required empty public constructor
    }
    //===================================================================
    @Override
    protected MovieDetailsPresenter createPresenter() {
        return new MovieDetailsPresenterImpl();
    }
    //===================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }
    //===================================================================
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getMovie(savedInstanceState);
        initReviewsRecycler(savedInstanceState);
        initTrailersRecycler(savedInstanceState);
        initCollapseMagic();
        bindData();
        getPresenter().isFavoriteMovie(movie);
        setFabOnclick();
        requestMovieCloudData();
    }
    //===================================================================
    private void getMovie(Bundle bundle){
        if (bundle != null)
            movie = bundle.getParcelable(MOVIE_KEY);
        if (movie == null){
            movie = getArguments().getParcelable(MOVIE_KEY);
        }
    }
    //===================================================================
    private void initTrailersRecycler(Bundle bundle){
        initTrailerAdapter(bundle);
        trailersRecycler.setAdapter(trailerAdapter);
        trailersRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    }
    //===================================================================
    private void initTrailerAdapter(Bundle bundle){
        if (bundle != null) {
            trailersResponse = bundle.getParcelable(TRAILERS_RESPONSE_KEY);
        }

        if (trailerAdapter == null) {
            if (trailersResponse != null){
                trailerAdapter = new TrailerAdapter(trailersResponse.getTrailers(), getContext());
            }
            else {
                trailerAdapter = new TrailerAdapter(new ArrayList<Trailer>(), getContext());
            }
        }
    }
    //===================================================================
    private void initReviewsRecycler(Bundle bundle){
        initReviewsAdapter(bundle);
        reviewsRecycler.setAdapter(reviewAdapter);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    //===================================================================
    private void initReviewsAdapter(Bundle bundle){
        if (bundle != null){
            reviewsResponse = bundle.getParcelable(REVIEWS_RESPONSE_KEY);
        }
        if (reviewAdapter == null){
            if (reviewsResponse != null){
                reviewAdapter = new ReviewAdapter(reviewsResponse.getReviews());
            }
            else {
                reviewAdapter = new ReviewAdapter(new ArrayList<Review>());
            }
        }
    }
    //===================================================================
    private void setFabOnclick(){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().favoriteMovie(movie);
                ((ActivityResultController)getActivity()).setResult(true);
                fab.hide();
            }
        });

        share_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trailersResponse != null && trailersResponse.getTrailers().size() > 0){
                    Utils.share("https://www.youtube.com/watch?v=" + trailersResponse.getTrailers().get(0).getKey(), movie.getTitle(), getContext());
                }
            }
        });
    }
    //===================================================================
    private void requestMovieCloudData(){
        if (trailersResponse == null){
            getPresenter().getTrailers(movie.getId(), Contacts.API_KEY, TRAILERS_REQUEST_CODE);
        }
        if (reviewsResponse == null){
            getPresenter().getReviews(movie.getId(), Contacts.API_KEY, REVIEWS_REQUEST_CODE);
        }
    }
    //===================================================================
    private void bindData()
    {
        Utils.loadImageWithProgress(cover_imge, coverProgress, movie.getBackdropPath());
        title.setText(movie.getTitle());
        date.setText(Utils.parseDate(movie.getReleaseDate(),"MMMM dd,yyyy"));
        description.setText(movie.getOverview());
        releaseDate.setText(Utils.parseDate(movie.getReleaseDate(),"MMMM dd,yyyy"));
        ratingBar.setRating((float) (movie.getVoteAverage()/2));
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                posterProgress.setVisibility(View.GONE);
                poster_Image.setImageBitmap(bitmap);
                Palette palette = Palette.from(bitmap).generate();
                int dominantColor = palette.getDarkMutedColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                int complimentColor = ColorUtils.getComplimentColor(dominantColor);
                info_container.setBackgroundColor(dominantColor);
                title.setTextColor(complimentColor);
                date.setTextColor(complimentColor);
                toolbar.setContentScrimColor(dominantColor);
                changeStatusBarColor(dominantColor);
                tabLayout.setBackgroundColor(palette.getDarkVibrantColor(ContextCompat.getColor(getContext(), R.color.colorAccent)));
                fab.setBackgroundTintList(ColorStateList.valueOf(palette.getMutedColor(ContextCompat.getColor(getContext(), R.color.colorAccent))));
                share_fab.setBackgroundTintList(ColorStateList.valueOf(palette.getMutedColor(ContextCompat.getColor(getContext(), R.color.colorAccent))));
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        Picasso.with(getContext()).load(Contacts.IMAGE_HOST + movie.getPosterPath()).into(target);
    }
    //===================================================================
    private void changeStatusBarColor(int color)
    {
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }
    }
    //===================================================================
    @Override
    public void showLoading(int requestCode) {
        switch (requestCode){
            case REVIEWS_REQUEST_CODE :
                reviewsProgress.setVisibility(View.VISIBLE);
                break;
            case TRAILERS_REQUEST_CODE :
                trailersProgress.setVisibility(View.VISIBLE);
                break;
        }
    }
    //===================================================================
    @Override
    public void hideLoading(int requestCode) {
        switch (requestCode){
            case REVIEWS_REQUEST_CODE :
                reviewsProgress.setVisibility(View.GONE);
                break;
            case TRAILERS_REQUEST_CODE :
                trailersProgress.setVisibility(View.GONE);
                break;
        }
    }
    //===================================================================
    @Override
    public void populateTrailers(TrailersResponse trailersResponse) {
        this.trailersResponse = trailersResponse;
        trailerAdapter.updateAdapterList(trailersResponse.getTrailers());
    }
    //===================================================================
    @Override
    public void populateReviews(ReviewsResponse reviewsResponse) {
        this.reviewsResponse = reviewsResponse;
        reviewAdapter.updateList(reviewsResponse.getReviews());
    }
    //===================================================================
    @Override
    public void populateMovieFavoriteStatus(boolean favorite) {
        if (favorite)
            fab.hide();
    }
    //===================================================================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_KEY, movie);
        outState.putParcelable(TRAILERS_RESPONSE_KEY, trailersResponse);
        outState.putParcelable(REVIEWS_RESPONSE_KEY, reviewsResponse);
    }
    //===================================================================
    private void initCollapseMagic(){
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setTitle(movie.getTitle());
                    isShow = true;
                } else if(isShow) {
                    toolbar.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
    }
    //===================================================================
}
