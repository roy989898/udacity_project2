package pom2.poly.com.trythemoviedbapi.Sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class MovieContentProvider extends ContentProvider {
    static final int MOVIE = 100;
    static final int MOVIE_POP = 101;
    static final int MOVIE_TOP = 102;
    static final int MOVIE_FAV = 103;
    static final int MOVIE_ID = 104;
    static final int FAV = 300;

    public MovieContentProvider() {
    }

    static UriMatcher buildUriMatcher() {
        //if the URI not match,will return UriMatcher.NO_MATCH
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        String authority = MovieDbContract.CONTENT_AUTHORITY;

        //now,add uri to the URi matcher

        matcher.addURI(authority,MovieDbContract.PATH_MOVIE,MOVIE);
        matcher.addURI(authority,MovieDbContract.PATH_MOVIE_POP,MOVIE_POP);
        matcher.addURI(authority,MovieDbContract.PATH_MOVIE_POP,MOVIE_TOP);
        matcher.addURI(authority,MovieDbContract.PATH_MOVIE_FAV,MOVIE_FAV);
        matcher.addURI(authority,MovieDbContract.PATH_MOVIE+"#",MOVIE_ID);

        matcher.addURI(authority,MovieDbContract.PATH_FAV,FAV);

        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        // TODO: Implement this to initialize your content provider on startup.
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        // TODO: Implement this to handle query requests from clients.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
