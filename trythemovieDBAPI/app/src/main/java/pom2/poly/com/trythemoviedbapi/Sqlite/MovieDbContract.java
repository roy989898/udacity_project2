package pom2.poly.com.trythemoviedbapi.Sqlite;

/**
 * Created by User on 13/1/2016.
 */

import android.provider.BaseColumns;

/**
 * Defines table and column names for the Movie database.
 */
public class MovieDbContract {


    /* Inner class that defines the table contents of the location table */
    public static final class FavouriteEntry implements BaseColumns {


        //SQLite part

        // Table name
        public static final String TABLE_NAME = "favourite";
        //Foreign key
        public static final String COLUMN_MOVIE_KEY = "movie_id";

        //SQLite part


    }

    /* Inner class that defines the table contents of the weather table */
    public static final class MovieEntry implements BaseColumns {


        public static final String TABLE_NAME = "movie";
        //SQLite part


        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POSTER_PATH = "posterpath";
        public static final String COLUMN_BACKDROP_PATH = "packdroppath";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RAGE = "rage";
        public static final String COLUMN_R_DATE= "r_date";
        public static final String COLUMN_POSTER_SIZE= "postersize";
        public static final String COLUMN_BACK_DROP_SZIE= "backdropsize";
        public static final String COLUMN_BASE_URL= "baseurl";
        public static final String COLUMN_M_ID= "moviewid";


    }
}
