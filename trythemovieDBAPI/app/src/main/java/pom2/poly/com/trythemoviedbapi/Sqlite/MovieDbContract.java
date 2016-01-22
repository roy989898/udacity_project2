package pom2.poly.com.trythemoviedbapi.Sqlite;

/**
 * Created by User on 13/1/2016.
 */


import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the Movie database.
 */
public class MovieDbContract {

    public static final String CONTENT_AUTHORITY = "pom2.poly.com.trythemoviedbapi";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "MOVIE";
    public static final String PATH_FAV = "FAVOURITE";
    public static final String PATH_MOVIE_POP = "MOVIE_POP";
    public static final String PATH_MOVIE_TOP = "MOVIE_TOP";
    public static final String PATH_MOVIE_FAV = "MOVIE_FAV";


    /* Inner class that defines the table contents of the location table */
    public static final class FavouriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

        //define the return type of content provider
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;//for multiple item
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAV;

        //SQLite part

        // Table name
        public static final String TABLE_NAME = "favourite";
        //Foreign key
        public static final String COLUMN_MOVIE_KEY = "movie_id";

        //SQLite part


    }

    /* Inner class that defines the table contents of the weather table */
    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final Uri CONTENT_URI_POP = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_POP).build();
        public static final Uri CONTENT_URI_TOP = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_TOP).build();
        public static final Uri CONTENT_URI_FAV = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE_FAV).build();

        //define the return type of content provider
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT__ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        //SQLite part
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_BACKDROP_PATH = "packdroppath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RAGE = "rage";
        public static final String COLUMN_R_DATE = "r_date";
        public static final String COLUMN_POSTER_SIZE = "postersize";
        public static final String COLUMN_BACK_DROP_SZIE = "backdropsize";
        public static final String COLUMN_BASE_URL = "baseurl";
        public static final String COLUMN_M_ID = "moviewid";

        public static Uri buildMovieID(long ID) {
            return ContentUris.withAppendedId(CONTENT_URI, ID);
        }

        public static String getMovieIDfromURI(Uri uri){
            return uri.getLastPathSegment();
        }


    }
}
