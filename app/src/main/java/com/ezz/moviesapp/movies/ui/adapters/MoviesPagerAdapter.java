package com.ezz.moviesapp.movies.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ezz.moviesapp.movies.model.MoviesPagerAdapterPage;

import java.util.ArrayList;

/**
 * Created by EzzWalid on 9/21/2017.
 */

public class MoviesPagerAdapter extends FragmentPagerAdapter {
    //===================================================================
    ArrayList<MoviesPagerAdapterPage> pages = new ArrayList<>();
    //===================================================================
    public MoviesPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    //===================================================================
    @Override
    public Fragment getItem(int position) {
        return pages.get(position).getFragment();
    }
    //===================================================================
    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }
    //===================================================================
    @Override
    public int getCount() {
        return pages.size();
    }
    //===================================================================
    public void addPage(MoviesPagerAdapterPage page){
        pages.add(page);
        notifyDataSetChanged();
    }
    //===================================================================
}
