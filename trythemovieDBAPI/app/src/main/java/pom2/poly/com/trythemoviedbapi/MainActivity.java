package pom2.poly.com.trythemoviedbapi;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.MovieAPI.APIService;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Config;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Results;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbHelper;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static int MOVIE_POP_LOADER = 0;
    private static int MOVIE_TOP_LOADER = 1;
    final String IMAGE = "images";
    final String BASE_URL = "base_url";
    final String POSTER_Z = "poster_sizes";
    final String MOVIE_KEY = "getTHeMovie";
    @Bind(R.id.gridView)
    GridView gridView;
    private String perf_sort_op;
    private ArrayList<Movie> movieArrayList;
    //private MyArrayAdapter myArrayAdapter;
    private MyCursorAdapter myCursorAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        perf_sort_op = sharedPref.getString(getResources().getString(R.string.pref_sort__key), "pop");
        //Toast.makeText(this, perf_sort_op, Toast.LENGTH_SHORT).show();
        updateMovie();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (perf_sort_op) {
            case Utility.POP_MOVIE:
                Log.i("loader", "POP_MOVIE");
                getSupportLoaderManager().initLoader(MOVIE_POP_LOADER, null, this);
                break;
            case Utility.TOP_MOVIE:
                Log.i("loader", "TOP_MOVIE");
                getSupportLoaderManager().initLoader(MOVIE_TOP_LOADER, null, this);
                break;

        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //try SQL
        MovieDbHelper movieDbHelper = new MovieDbHelper(this);
        movieDbHelper.getWritableDatabase();
        //S


        if (savedInstanceState != null) {
            movieArrayList = (ArrayList<Movie>) savedInstanceState.get(MOVIE_KEY);
        } else {
            movieArrayList = new ArrayList<>();
        }
        //myArrayAdapter = new MyArrayAdapter(this, movieArrayList);
        myCursorAdapter = new MyCursorAdapter(this, null);
        //gridView.setAdapter(myArrayAdapter);
        gridView.setAdapter(myCursorAdapter);
        //TODO click iteam
        //gridView.setOnItemClickListener(this);


        //new getConfigTask().execute();
        //new getMovieTask().execute(Utility.POP_MOVIE);


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(MOVIE_KEY, movieArrayList);
    }


    private void updateMovie() {
        GdataFromMOVIEDBtask task = new GdataFromMOVIEDBtask();
        task.execute();
    }


    //this method use the data from getConfigData and getMovieDatav2 ,to get the MOVIE object array
    private Movie[] getMOvieObject() {

        Config configr = getConfigData();
        Results result1 = getMovieDatav2(perf_sort_op);
        return MovieFactory.startMakeMovieArray(configr, result1);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.class.getName(), myArrayAdapter.getItem(position));
        startActivity(intent);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
        }
        return true;
    }

    private Config getConfigData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create()).build();

        APIService service = retrofit.create(APIService.class);
        Call<Config> callConfig = service.loadconfig();
        Response<Config> config = null;
        try {
            config = callConfig.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (config != null) {
            return config.body();
        } else {
            return null;
        }

    }

    private Results getMovieDatav2(String topOrPop) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create()).build();

        APIService service = retrofit.create(APIService.class);
        Call<Results> callResults = null;
        switch (topOrPop) {
            case Utility.POP_MOVIE:
                callResults = service.loadPopMovie();
                break;

            case Utility.TOP_MOVIE:
                callResults = service.loadTopMovie();
                break;

        }

        Response<Results> results = null;
        try {
            results = callResults.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (results != null) {
            return results.body();
        } else {
            return null;
        }
    }

    private Uri insertIntoContntProvider(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(MovieDbContract.MovieEntry.COLUMN_TITLE, movie.getTitle());
        cv.put(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPoster_path());
        cv.put(MovieDbContract.MovieEntry.COLUMN_BACKDROP_PATH, movie.getBackdrop_path());
        cv.put(MovieDbContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverview());
        cv.put(MovieDbContract.MovieEntry.COLUMN_RAGE, movie.getRage());
        cv.put(MovieDbContract.MovieEntry.COLUMN_POP, movie.getPopularity());
        cv.put(MovieDbContract.MovieEntry.COLUMN_R_DATE, movie.getR_date());
        cv.put(MovieDbContract.MovieEntry.COLUMN_M_ID, movie.getM_id());
        cv.put(MovieDbContract.MovieEntry.COLUMN_POSTER_SIZE, 0);
        cv.put(MovieDbContract.MovieEntry.COLUMN_BACK_DROP_SZIE, 0);
        cv.put(MovieDbContract.MovieEntry.COLUMN_BASE_URL, "");
        Uri uri = getContentResolver().insert(MovieDbContract.MovieEntry.CONTENT_URI, cv);
        return uri;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Loader<Cursor> cursorLoader = null;
        if (perf_sort_op == null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
            perf_sort_op = sharedPref.getString(getResources().getString(R.string.pref_sort__key), "pop");
        }
        switch (perf_sort_op) {
            case Utility.TOP_MOVIE:
                Log.i("loader", "onCreateLoader-TOP_MOVIE");
                cursorLoader = new CursorLoader(this, MovieDbContract.MovieEntry.CONTENT_URI_TOP, null, null, null, null);

                break;
            case Utility.POP_MOVIE:
                Log.i("loader", "onCreateLoader-POP_MOVIE");
                cursorLoader = new CursorLoader(this, MovieDbContract.MovieEntry.CONTENT_URI_POP, null, null, null, null);

                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i("loader", "onLoadFinished");
        myCursorAdapter.swapCursor(data);
        //myCursorAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i("loader", "onLoaderReset");
        myCursorAdapter.swapCursor(null);
    }

    class GdataFromMOVIEDBtask extends AsyncTask<Void, Void, Movie[]> {
        @Override
        protected Movie[] doInBackground(Void... voids) {
            Movie[] movieArray = getMOvieObject();
            for (Movie m : movieArray) {
                insertIntoContntProvider(m);

            }

            return movieArray;
        }


        @Override
        protected void onPostExecute(Movie[] movieArray) {
            super.onPostExecute(movieArray);
            //Todo delete the Array adapter
            if (movieArray != null) {
                Log.i("GdataFromMOVIEDBtask", "in onPostExecute");
                movieArrayList.clear();
                movieArrayList.addAll(Arrays.asList(movieArray));
                //myArrayAdapter.notifyDataSetChanged();
            }
            //for test the ContentProvider only
            //testThequery(MovieDbContract.MovieEntry.buildMovieID(278));


        }

        private void testThequery(Uri uri) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            while (cursor.moveToNext()) {
                Log.i("show_cursor", cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry._ID)) + " " + cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_TITLE)) + " rage: "
                        + cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_RAGE)) + " popularity: " + cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POP)));
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }


}

