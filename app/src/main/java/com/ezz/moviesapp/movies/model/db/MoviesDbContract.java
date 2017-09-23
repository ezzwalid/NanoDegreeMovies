package com.ezz.moviesapp.movies.model.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by EzzWalid on 9/22/2017.
 */

public class MoviesDbContract {

    public static final String CONTENT_AUTHORITY = "com.ezz.moviesapp.Movies.model.db.MoviesContentProvider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY  + "/" + MovieEntry.TABLE_NAME);

    public static final class MovieEntry implements BaseColumns{

        public static final String TABLE_NAME = "movies";

        public static final String COLUMN_MOVIE_ID= "movie_id";

        public static final String COLUMN_MOVIE_NAME = "movie_name";

        public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";

        public static final String COLUMN_MOVIE_RATE = "movie_rate";

        public static final String COLUMN_MOVIE_RELEASE_DATE = "movie_release_date";

        public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";

        public static final String COLUMN_MOVIE_COVER_PATH = "movie_cover_path";

    }
}
