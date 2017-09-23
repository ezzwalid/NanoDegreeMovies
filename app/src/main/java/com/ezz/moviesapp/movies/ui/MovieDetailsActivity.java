package com.ezz.moviesapp.movies.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ezz.moviesapp.R;
import com.ezz.moviesapp.baseListners.ActivityResultController;
import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.ui.fragments.MovieDetailsFragment;

public class MovieDetailsActivity extends AppCompatActivity implements ActivityResultController{
    //===================================================================
    public static final String FAVORITE_TRANSIT_KEY = "favoriteTransit";
    boolean favoriteTransitDone = false;
    //===================================================================
    public static final String MOVIE_KEY = "movieKey";
    Movie movie;
    //===================================================================
    Fragment detailsFragment;
    //===================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        restoreFavoriteTransitState(savedInstanceState);
        getMovie(savedInstanceState);
        addDetailsFragment(savedInstanceState);
    }
    //===================================================================
    private void getMovie(Bundle bundle){
        if (bundle != null){
            movie = bundle.getParcelable(MOVIE_KEY);
        }

        if (movie == null){
            movie = getIntent().getParcelableExtra(MOVIE_KEY);
        }
    }
    //===================================================================
    private void addDetailsFragment(Bundle bundle){
        if (bundle != null){
            detailsFragment = getSupportFragmentManager().getFragment(bundle, "details");
        }

        if (detailsFragment == null){
            detailsFragment = MovieDetailsFragment.newInstance(movie);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.details_frag_container, detailsFragment).commit();
    }
    //===================================================================
    @Override
    public void setResult(boolean result) {
        favoriteTransitDone = result;
    }
    //===================================================================
    private void restoreFavoriteTransitState(Bundle bundle){
        if (bundle != null){
            favoriteTransitDone = bundle.getBoolean(FAVORITE_TRANSIT_KEY);
        }
    }
    //===================================================================
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIE_KEY, movie);
        outState.putBoolean(FAVORITE_TRANSIT_KEY, favoriteTransitDone);
        getSupportFragmentManager().putFragment(outState, "details", detailsFragment);
    }
    //===================================================================
    @Override
    public void finish() {
        if (favoriteTransitDone)
            setResult(RESULT_OK, new Intent().putExtra(MOVIE_KEY, movie));
        super.finish();
    }
    //===================================================================
}
