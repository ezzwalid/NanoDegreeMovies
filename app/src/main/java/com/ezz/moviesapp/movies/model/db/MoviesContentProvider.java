package com.ezz.moviesapp.movies.model.db;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by EzzWalid on 9/22/2017.
 */

public class MoviesContentProvider extends ContentProvider {
    //===================================================================
    private final UriMatcher uriMatcher = initUriMatcher();
    //===================================================================
    MoviesDbHelper moviesDbHelper;
    //===================================================================
    public static final int MOVIES = 1;
    public static final int MOVIES_ID = 2;
    //===================================================================
    static UriMatcher initUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MoviesDbContract.CONTENT_AUTHORITY, MoviesDbContract.MovieEntry.TABLE_NAME, MOVIES);
        uriMatcher.addURI(MoviesDbContract.CONTENT_AUTHORITY, MoviesDbContract.MovieEntry.TABLE_NAME + "/#",
                MOVIES_ID);

        return uriMatcher;
    }
    //===================================================================
    @Override
    public boolean onCreate() {
        moviesDbHelper = new MoviesDbHelper(getContext());
        return true;
    }
    //===================================================================
    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getReadableDatabase();

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MoviesDbContract.MovieEntry.TABLE_NAME);

        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case MOVIES_ID:
                queryBuilder.appendWhere(MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID + "="
                        + uri.getLastPathSegment());
                break;
            case MOVIES:
                break;
            default:
                throw new IllegalArgumentException("Unknown URI");
        }

        Cursor cursor = queryBuilder.query(sqLiteDatabase,
                strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    //===================================================================
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
    //===================================================================
    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqLiteDatabase = moviesDbHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case MOVIES:
                id = sqLiteDatabase.insert(MoviesDbContract.MovieEntry.TABLE_NAME,
                        null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(MoviesDbContract.MovieEntry.TABLE_NAME + "/" + id);
    }
    //===================================================================
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase sqlDB = moviesDbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case MOVIES:
                rowsDeleted = sqlDB.delete(MoviesDbContract.MovieEntry.TABLE_NAME,
                        s,
                        strings);
                break;

            case MOVIES_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(s)) {
                    rowsDeleted = sqlDB.delete(MoviesDbContract.MovieEntry.TABLE_NAME,
                            MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID  + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(MoviesDbContract.MovieEntry.TABLE_NAME,
                            MoviesDbContract.MovieEntry.COLUMN_MOVIE_ID + "=" + id
                                    + " and " + s,
                            strings);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }
    //===================================================================
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
    //===================================================================
    @Override
    @TargetApi(11)
    public void shutdown() {
        moviesDbHelper.close();
        super.shutdown();
    }
    //===================================================================

}
