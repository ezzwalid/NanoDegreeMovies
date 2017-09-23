package com.ezz.moviesapp.movies.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ezz.moviesapp.movies.model.Movie;
import com.ezz.moviesapp.movies.model.MoviesPagerAdapterPage;
import com.ezz.moviesapp.movies.ui.MovieDetailsActivity;
import com.ezz.moviesapp.movies.ui.MoviesActivity;
import com.ezz.moviesapp.movies.ui.adapters.MoviesPagerAdapter;
import com.ezz.moviesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {
    //===================================================================
    Fragment popularFragment, topRatedFragment, favoriteFragment;
    //===================================================================
    MoviesPagerAdapter adapter;
    //===================================================================
    @BindView(R.id.movie_fragment_view_pager)
    ViewPager viewPager;
    @BindView(R.id.movie_fragment_tab_layout)
    TabLayout tabLayout;
    //===================================================================
    public MoviesFragment() {
        // Required empty public constructor
    }
    //===================================================================
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }
    //===================================================================
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewPager(savedInstanceState);
    }
    //===================================================================
    private void initViewPager(Bundle bundle){
        initAdapter(bundle);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }
    //===================================================================
    private void initAdapter(Bundle bundle){
        if (adapter == null){
            initFragmentsForPagerAdapter(bundle);
            adapter = new MoviesPagerAdapter(getChildFragmentManager());
            adapter.addPage(new MoviesPagerAdapterPage(popularFragment,"Popular"));
            adapter.addPage(new MoviesPagerAdapterPage(topRatedFragment,"Top Rated"));
            adapter.addPage(new MoviesPagerAdapterPage(favoriteFragment, "Favorite"));
        }
    }
    //===================================================================
    private void initFragmentsForPagerAdapter(Bundle bundle){
        if (bundle != null){
            topRatedFragment = getChildFragmentManager().getFragment(bundle, "topRated");
            popularFragment = getChildFragmentManager().getFragment(bundle, "popular");
            favoriteFragment = getChildFragmentManager().getFragment(bundle, "favorite");
        }
        if (topRatedFragment == null){
            topRatedFragment = MoviesPagerFragment.newInstance(MoviesPagerFragment.TOP_RATED_MOVIES);
        }
        if (popularFragment == null){
            popularFragment = MoviesPagerFragment.newInstance(MoviesPagerFragment.POPULAR_MOVIES);
        }
        if (favoriteFragment == null){
            favoriteFragment = MoviesPagerFragment.newInstance(MoviesPagerFragment.FAVORITE_MOVIES);
        }
    }
    //===================================================================
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getChildFragmentManager().putFragment(outState, "popular", popularFragment);
        getChildFragmentManager().putFragment(outState, "topRated", topRatedFragment);
        getChildFragmentManager().putFragment(outState, "favorite", favoriteFragment);
    }
    //===================================================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MoviesActivity.DETAILS_REQUEST_CODE){
            if (resultCode == RESULT_OK && favoriteFragment != null){
                ((MoviesPagerFragment)favoriteFragment).addMovieToAdapter((Movie) data.getParcelableExtra(MovieDetailsActivity.MOVIE_KEY));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    //===================================================================
}
