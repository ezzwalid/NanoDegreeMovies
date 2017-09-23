package com.ezz.moviesapp.movies.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by EzzWalid on 9/22/2017.
 */

public class MoviesDbHelper extends SQLiteOpenHelper{
    //===================================================================
    public static final String dbName = "movies.db";
    //===================================================================
    public MoviesDbHelper(Context context) {
        super(context, dbName, null, 1);
    }
    //===================================================================
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " + MoviesDbContract.MovieEntry.TABLE_NAME + " (" +
                MoviesDbContract.MovieEntry._ID + " INTEGER PRIMARY KEY," +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID + " INTEGER NOT NULL, " +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_NAME + " TEXT NOT NULL, " +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_COVER_PATH + " TEXT NOT NULL, " +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_RELEASE_DATE + " TEXT NOT NULL, " +
                MoviesDbContract.MovieEntry.COLUMN_MOVIE_RATE + " REAL NOT NULL" +
        ");";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIES_TABLE);

    }
    //===================================================================
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesDbContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    //===================================================================
}
