package pom2.poly.com.trythemoviedbapi.Sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 20/1/2016.
 */
public class MovieDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 7;

    static final String DATABASE_NAME = "movie.db";
    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a table to hold favourite.  A favourite consists of the m_id(not the ID in the SQL database)
        final String SQL_CREATE_FAVOURITE_TABLE = "CREATE TABLE " + MovieDbContract.FavouriteEntry.TABLE_NAME + " (" +
                MovieDbContract.FavouriteEntry._ID + " INTEGER PRIMARY KEY," +
                MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MovieDbContract.MovieEntry.TABLE_NAME + " (" +

                MovieDbContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +

                // the ID of the location entry associated with this weather data
                MovieDbContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MovieDbContract.MovieEntry.COLUMN_POSTER_PATH+ " TEXT, " +
                MovieDbContract.MovieEntry.COLUMN_BACKDROP_PATH+ " TEXT, " +
                MovieDbContract.MovieEntry.COLUMN_OVERVIEW+ " TEXT, " +
                MovieDbContract.MovieEntry.COLUMN_RAGE+ " REAL , " +
                MovieDbContract.MovieEntry.COLUMN_POP+ " REAL , " +
                MovieDbContract.MovieEntry.COLUMN_R_DATE+ " TEXT, " +
                MovieDbContract.MovieEntry.COLUMN_POSTER_SIZE+ " TEXT , " +
                MovieDbContract.MovieEntry.COLUMN_BACK_DROP_SZIE+ " TEXT, " +
                MovieDbContract.MovieEntry.COLUMN_BASE_URL+ " TEXT, " +
                MovieDbContract.MovieEntry.COLUMN_M_ID+ " TEXT UNIQUE  NOT NULL" +
                " );";

        db.execSQL(SQL_CREATE_FAVOURITE_TABLE);
        db.execSQL(SQL_CREATE_MOVIE_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        db.execSQL("DROP TABLE IF EXISTS " + MovieDbContract.FavouriteEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + MovieDbContract.MovieEntry.TABLE_NAME);
        onCreate(db);

    }
}
