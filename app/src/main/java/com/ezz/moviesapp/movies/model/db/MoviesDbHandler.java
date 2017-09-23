package com.ezz.moviesapp.movies.model.db;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ezz.moviesapp.movies.model.Movie;

import java.util.ArrayList;

/**
 * Created by EzzWalid on 9/22/2017.
 */

public class MoviesDbHandler {

    ContentResolver contentResolver;

    public MoviesDbHandler(Context context) {
        contentResolver = context.getContentResolver();
    }

    public void addMovie(Movie movie) {

        ContentValues values = new ContentValues();

        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID, movie.getId());
        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_NAME, movie.getOriginalTitle());
        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW, movie.getOverview());
        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_RATE, movie.getVoteAverage());
        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE, movie.getReleaseDate());
        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH, movie.getPosterPath());
        values.put(MoviesDbContract.MovieEntry.COLUMN_MOVIE_COVER_PATH, movie.getBackdropPath());

        contentResolver.insert(MoviesDbContract.CONTENT_URI, values);
    }

    public ArrayList<Movie> getMovies() {

        ArrayList<Movie> movies = new ArrayList<>();

        String[] projection = {MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_NAME,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_RATE,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_COVER_PATH,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH};


        Cursor cursor = contentResolver.query(MoviesDbContract.CONTENT_URI,
                projection, null, null,
                null);


        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setId(cursor.getInt(0));
                movie.setTitle(cursor.getString(1));
                movie.setOverview(cursor.getString(2));
                movie.setVoteAverage(cursor.getDouble(3));
                movie.setReleaseDate(cursor.getString(4));
                movie.setBackdropPath(cursor.getString(5));
                movie.setPosterPath(cursor.getString(6));
                movies.add(movie);
            }
            while (cursor.moveToNext());
        }
        cursor.close();

        return movies;
    }

    public boolean deleteMovie(Movie movie) {

        boolean result = false;

        String selection = MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID + " = \"" + movie.getId() + "\"";

        int rowsDeleted = contentResolver.delete(MoviesDbContract.CONTENT_URI,
                selection, null);

        if (rowsDeleted > 0)
            result = true;

        return result;
    }

    public boolean dbContainMovie(Movie movie){

        String[] projection = {MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_NAME,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_RATE,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_COVER_PATH,
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH};

        String selection = MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID + " = \"" + movie.getId() + "\"";

        Cursor cursor = contentResolver.query(MoviesDbContract.CONTENT_URI,
                projection, selection, null,
                null);

        boolean exist = cursor.moveToFirst();
        cursor.close();
        return exist;
    }
}
