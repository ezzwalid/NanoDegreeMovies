package com.ezz.moviesapp.movies.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.model.cloud.MoviesResponse;
import com.ezz.moviesapp.movies.ui.MovieDetailsActivity;
import com.ezz.moviesapp.movies.ui.MoviesActivity;
import com.ezz.moviesapp.movies.ui.adapters.MoviesAdapter;
import com.ezz.moviesapp.movies.ui.presenters.MoviesPagerFragmentPresenter;
import com.ezz.moviesapp.movies.ui.presenters.impl.MoviesPagerFragmentPresenterImpl;
import com.ezz.moviesapp.movies.ui.views.MoviesPagerFragmentView;
import com.ezz.moviesapp.R;
import com.ezz.moviesapp.base.BaseFragment;
import com.ezz.moviesapp.baseListners.RecyclerViewClickListener;
import com.ezz.moviesapp.helpers.Contacts;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesPagerFragment extends BaseFragment<MoviesPagerFragmentPresenter> implements MoviesPagerFragmentView, RecyclerViewClickListener{
    //===================================================================
    public static final int POPULAR_MOVIES = 1;
    public static final int TOP_RATED_MOVIES = 2;
    public static final int FAVORITE_MOVIES = 3;
    //===================================================================
    private final int MOVIES_REQUEST_CODE = 0;
    //===================================================================
    private final String MOVIES_RESPONSE_KEY = "moviesResponseKey";
    MoviesResponse moviesResponse;
    //===================================================================
    MoviesAdapter moviesAdapter;
    //===================================================================
    public static final String TYPE_KEY = "typeKey";
    int type = 10;
    //===================================================================
    public static Fragment newInstance(int type){
        MoviesPagerFragment fragment = new MoviesPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_KEY, type);
        fragment.setArguments(bundle);
        return fragment;
    }
    //===================================================================
    @BindView(R.id.movies_pager_fragment_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.movies_pager_fragment_recycler_progress)
    ProgressBar progressBar;
    //===================================================================
    public MoviesPagerFragment() {
        // Required empty public constructor
    }
    //===================================================================
    @Override
    protected MoviesPagerFragmentPresenter createPresenter() {
        return new MoviesPagerFragmentPresenterImpl();
    }
    //===================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_pager, container, false);
    }
    //===================================================================
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        getType(savedInstanceState);
        initRecycler(savedInstanceState);
        requestMoviesData();
    }
    //===================================================================
    private void getType(Bundle bundle){
        if (bundle != null)
            type = bundle.getInt(TYPE_KEY);

        if (type == 10){
            type = getArguments().getInt(TYPE_KEY);
        }
    }
    //===================================================================
    private void initRecycler(Bundle bundle){
        initAdapter(bundle);
        recyclerView.setAdapter(moviesAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
    //===================================================================
    private void initAdapter(Bundle bundle){
        if (moviesAdapter == null){
            getMoviesResponseInstanceState(bundle);
            if (moviesResponse != null) {
                moviesAdapter = new MoviesAdapter(moviesResponse.getMovies());
            }
            else {
                moviesAdapter = new MoviesAdapter(new ArrayList<Movie>());
            }
        }
        moviesAdapter.setClickListener(this);
    }
    //===================================================================
    private void getMoviesResponseInstanceState(Bundle bundle){
        if (bundle != null){
            moviesResponse = bundle.getParcelable(MOVIES_RESPONSE_KEY);
        }
    }
    //===================================================================
    private void requestMoviesData(){
        if (moviesResponse == null){
            switch (type){
                case POPULAR_MOVIES:
                    getPresenter().getPopularMovies(MOVIES_REQUEST_CODE, Contacts.API_KEY);
                    break;
                case TOP_RATED_MOVIES:
                    getPresenter().getTopRatedMovies(MOVIES_REQUEST_CODE, Contacts.API_KEY);
                    break;
                case FAVORITE_MOVIES:
                    getPresenter().getFavoriteMovies(MOVIES_REQUEST_CODE);
                    break;
            }
        }
    }
    //===================================================================
    @Override
    public void populateMovies(MoviesResponse moviesResponse) {
        this.moviesResponse = moviesResponse;
        moviesAdapter.updateData(moviesResponse.getMovies());
    }
    //===================================================================
    @Override
    public void onRecyclerItemClicked(Movie movie) {
        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra(MovieDetailsActivity.MOVIE_KEY, movie);
        getActivity().startActivityForResult(intent, MoviesActivity.DETAILS_REQUEST_CODE);
    }
    //===================================================================
    @Override
    public void showLoading(int requestCode) {
        if (requestCode == MOVIES_REQUEST_CODE){
            progressBar.setVisibility(View.VISIBLE);
        }
    }
    //===================================================================
    @Override
    public void hideLoading(int requestCode) {
        if (requestCode == MOVIES_REQUEST_CODE){
            progressBar.setVisibility(View.GONE);
        }
    }
    //===================================================================
    public void addMovieToAdapter(Movie movie){
        if (moviesResponse != null) {
            moviesResponse.getMovies().add(movie);
            if (moviesAdapter != null){
                moviesAdapter.updateData(moviesResponse.getMovies());
            }
        }

    }
    //===================================================================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(MOVIES_RESPONSE_KEY, moviesResponse);
        outState.putInt(TYPE_KEY, type);
    }
    //===================================================================
}
