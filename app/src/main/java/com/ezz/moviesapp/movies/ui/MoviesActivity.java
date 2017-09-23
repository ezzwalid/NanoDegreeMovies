package com.ezz.moviesapp.movies.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ezz.moviesapp.movies.ui.fragments.MoviesFragment;
import com.ezz.moviesapp.R;

public class MoviesActivity extends AppCompatActivity {
    //===================================================================
    public static final int DETAILS_REQUEST_CODE = 10;
    //===================================================================
    Fragment fragment;
    //===================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (savedInstanceState == null){
            fragment =  new MoviesFragment();
            addMoviesFragment();
        }
        else {
            fragment = getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
            addMoviesFragment();
        }
    }
    //===================================================================
    private void addMoviesFragment(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frag_container,fragment)
                .commit();
    }
    //===================================================================
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "myFragment", fragment);
    }
    //===================================================================
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DETAILS_REQUEST_CODE){
            if (resultCode == RESULT_OK && fragment != null){
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //===================================================================
}
