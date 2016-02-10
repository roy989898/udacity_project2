package pom2.poly.com.trythemoviedbapi.Sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

public class MovieContentProvider extends ContentProvider {
    static final int MOVIE = 100;
    static final int MOVIE_POP = 101;
    static final int MOVIE_TOP = 102;
    static final int MOVIE_FAV = 103;
    static final int MOVIE_ID = 104;
    static final int FAV = 300;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final SQLiteQueryBuilder sFAV_AND_MOVquerybuilder;

    static {
        sFAV_AND_MOVquerybuilder = new SQLiteQueryBuilder();
        sFAV_AND_MOVquerybuilder.setTables(MovieDbContract.FavouriteEntry.TABLE_NAME + " INNER JOIN " + MovieDbContract.MovieEntry.TABLE_NAME + " ON " + MovieDbContract.MovieEntry.TABLE_NAME + "." + MovieDbContract.MovieEntry.COLUMN_M_ID + " = " +
                MovieDbContract.FavouriteEntry.TABLE_NAME + " . " + MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY);
    }

    private SQLiteDatabase db;
    private MovieDbHelper moviedbhelper;

    public MovieContentProvider() {
    }

    static UriMatcher buildUriMatcher() {
        //if the URI not match,will return UriMatcher.NO_MATCH
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        String authority = MovieDbContract.CONTENT_AUTHORITY;

        //now,add uri to the URi matcher

        matcher.addURI(authority, MovieDbContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, MovieDbContract.PATH_MOVIE_POP, MOVIE_POP);
        matcher.addURI(authority, MovieDbContract.PATH_MOVIE_TOP, MOVIE_TOP);
        matcher.addURI(authority, MovieDbContract.PATH_MOVIE_FAV, MOVIE_FAV);
        matcher.addURI(authority, MovieDbContract.PATH_MOVIE + "/#", MOVIE_ID);

        matcher.addURI(authority, MovieDbContract.PATH_FAV, FAV);

        return matcher;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIE:
                return MovieDbContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_POP:
                return MovieDbContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_TOP:
                return MovieDbContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_FAV:
                return MovieDbContract.MovieEntry.CONTENT_TYPE;
            case MOVIE_ID:
                return MovieDbContract.MovieEntry.CONTENT__ITEM_TYPE;
            case FAV:
                return MovieDbContract.FavouriteEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = moviedbhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri reuri = null;
        Uri setUri = null;
        switch (match) {
            case MOVIE:
                //first need to check if the m_id appear first
                Cursor cursor = db.query(MovieDbContract.MovieEntry.TABLE_NAME, new String[]{MovieDbContract.MovieEntry._ID, MovieDbContract.MovieEntry.COLUMN_M_ID}, null, null, null, null, null);
                cursor.moveToPosition(-1);

                //here check if the movie id in the data base already,if yes return the _ID(no the movie id,just the row id)
                while (cursor.moveToNext()) {
                    if (cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_M_ID)).equals(values.getAsString(MovieDbContract.MovieEntry.COLUMN_M_ID))) {
                        String _id = cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_M_ID));
                        return reuri = MovieDbContract.MovieEntry.buildMovieID(Long.parseLong(_id));
                    }
                }

                //if the movie id in the data base already,if NO, do the below
                //the row ID of the newly inserted row, or -1 if an error occurred
                long _id = db.insert(MovieDbContract.MovieEntry.TABLE_NAME, null, values);
                if (_id > -1)
                    reuri = MovieDbContract.MovieEntry.buildMovieID(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);

                setUri = MovieDbContract.MovieEntry.CONTENT_URI;
                break;

            case FAV:
                //first check if the m_id in the database
                String selection = MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY + " =? ";
                String m_id = values.getAsInteger(MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY) + "";
                Cursor useforcheckCursor = db.query(MovieDbContract.FavouriteEntry.TABLE_NAME, null, selection, new String[]{m_id}, null, null, null);
                //if >0,that mean already have the movie in the favouritetable
                long _fid = 0;

                if (useforcheckCursor.getCount() == 0)
                    _fid = db.insert(MovieDbContract.FavouriteEntry.TABLE_NAME, null, values);
                else
                    _fid = -1;

                setUri = MovieDbContract.FavouriteEntry.CONTENT_URI;
                reuri = MovieDbContract.FavouriteEntry.buildFavouriteWithID(_fid);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        getContext().getContentResolver().notifyChange(setUri, null);

        return reuri;
    }

    @Override
    public boolean onCreate() {
        moviedbhelper = new MovieDbHelper(getContext());
        db = moviedbhelper.getReadableDatabase();
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor recursor = null;
        Uri setUri = null;
        switch (sUriMatcher.match(uri)) {
            case FAV:
                Log.i("show_cursor", "in FAV");
                long m_id = MovieDbContract.FavouriteEntry.getTheM_IDfromTheURI(uri);
                if (m_id == -1) {
                    //not search with m_id
                    recursor = getFav(projection, selection, selectionArgs);
                    setUri = MovieDbContract.FavouriteEntry.CONTENT_URI;
                } else {
                    //search with m_id
                    recursor = getFav(projection, MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY + " =? ", new String[]{m_id + ""});
                    setUri = MovieDbContract.FavouriteEntry.CONTENT_URI;
                }

                break;
            case MOVIE:
                Log.i("show_cursor", "in MOVIE");
                recursor = getMovie(projection, selection, selectionArgs);
                setUri = MovieDbContract.MovieEntry.CONTENT_URI;
                break;
            case MOVIE_FAV:
                //TODO:Movie with FAVquery,not yet check ok
                Log.i("show_cursor", "in MOVIE_FAV");
                recursor = sFAV_AND_MOVquerybuilder.query(db, null, null, null, null, null, null);
                setUri = MovieDbContract.FavouriteEntry.CONTENT_URI;
                break;
            case MOVIE_POP:
                Log.i("show_cursor", "in MOVIE_POP");
                recursor = getMoviewithPOP(projection, selection, selectionArgs);
                setUri = MovieDbContract.MovieEntry.CONTENT_URI;
                break;

            case MOVIE_TOP:
                Log.i("show_cursor", "in MOVIE_TOP");
                recursor = getMoviewithTOP(projection, selection, selectionArgs);
                setUri = MovieDbContract.MovieEntry.CONTENT_URI;
                break;
            case MOVIE_ID:
                Log.i("show_cursor", "in MOVIE_ID");
                recursor = getMoviewithID(projection, MovieDbContract.MovieEntry.getMovieIDfromURI(uri));
                setUri = MovieDbContract.MovieEntry.CONTENT_URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }
        recursor.setNotificationUri(getContext().getContentResolver(), setUri);

        return recursor;
    }

    private Cursor getFav(String[] projection, String selection,
                          String[] selectionArgs) {

        return db.query(MovieDbContract.FavouriteEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);
    }

    private Cursor getMovie(String[] projection, String selection,
                            String[] selectionArgs) {

        Cursor cursor = db.query(MovieDbContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        return cursor;
    }

    private Cursor getMoviewithPOP(String[] projection, String selection,
                                   String[] selectionArgs) {

        Cursor cursor = db.query(MovieDbContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, MovieDbContract.MovieEntry.COLUMN_POP + " DESC");

        return cursor;
    }

    private Cursor getMoviewithTOP(String[] projection, String selection,
                                   String[] selectionArgs) {

        Cursor cursor = db.query(MovieDbContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, null);

        return cursor;
    }

    private Cursor getMoviewithID(String[] projection, long m_id) {

        Cursor cursor = db.query(MovieDbContract.MovieEntry.TABLE_NAME, projection, MovieDbContract.MovieEntry.COLUMN_M_ID + " =?", new String[]{m_id + ""}, null, null, MovieDbContract.MovieEntry.COLUMN_RAGE + " DESC");

        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = moviedbhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        int state = 0;
        Uri setUri = null;
        switch (match) {
            case MOVIE:
                //first need to check if the m_id appear first
                state = db.delete(MovieDbContract.MovieEntry.TABLE_NAME, null, null);

                setUri = MovieDbContract.MovieEntry.CONTENT_URI;
                break;

            case FAV:
                long m_id = MovieDbContract.FavouriteEntry.getTheM_IDfromTheURI(uri);
                if (m_id == -1) {
                    //not delete with m_id(delete whole table)
                    state = db.delete(MovieDbContract.FavouriteEntry.TABLE_NAME, null, null);

                    setUri = MovieDbContract.FavouriteEntry.CONTENT_URI;
                } else {
                    //delete with m_id(delete one row)
                    state = db.delete(MovieDbContract.FavouriteEntry.TABLE_NAME, MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY + " =? ", new String[]{m_id + ""});

                    setUri = MovieDbContract.FavouriteEntry.CONTENT_URI;
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        getContext().getContentResolver().notifyChange(setUri, null);

        return state;
    }
}
