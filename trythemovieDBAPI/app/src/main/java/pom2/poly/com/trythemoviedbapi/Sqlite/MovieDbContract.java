package pom2.poly.com.trythemoviedbapi.Sqlite;

/**
 * Created by User on 13/1/2016.
 */


import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Defines table and column names for the Movie database.
 */
public class MovieDbContract {

    public static final String CONTENT_AUTHORITY = "CONTENT:// pom2.poly.com.trythemoviedbapi";
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_AUTHORITY);
    public static final String PATH_MOVIE = "MOVIE";
    public static final String PATH_FAV = "FAVOURITE";
    public static final String PATH_MOVIE_POP = "MOVIE_POP";
    public static final String PATH_MOVIE_TOP = "MOVIE_FAV";


    /* Inner class that defines the table contents of the location table */
    public static final class FavouriteEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAV).build();

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

        public static Uri buildMovieID(String ID){
            return CONTENT_URI.buildUpon().appendPath(ID).build();
        }

        public static final String TABLE_NAME = "movie";
        //SQLite part


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


    }
}
