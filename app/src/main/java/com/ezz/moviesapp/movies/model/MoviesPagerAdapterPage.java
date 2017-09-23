package com.ezz.moviesapp.movies.model;

import android.support.v4.app.Fragment;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class MoviesPagerAdapterPage {
    private Fragment fragment;
    private String title;

    public MoviesPagerAdapterPage(Fragment fragment, String title) {
        this.fragment = fragment;
        this.title = title;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
