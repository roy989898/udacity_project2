package pom2.poly.com.trythemoviedbapi;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import pom2.poly.com.trythemoviedbapi.MovieAPI.APIService;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Config;
import pom2.poly.com.trythemoviedbapi.MovieAPI.MovieIDResult.MovieIdResult;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Results;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by User on 30/1/2016.
 */
public class GdataFromMOVIEDBtask extends AsyncTask<Void, Void, Movie[]> {
    private String perf_sort_pop_top_fav;
    private String old_perf_sort_op;
    private Context mContext = null;

    public GdataFromMOVIEDBtask(Context mContext, String old_perf_sort_op, String perf_sort_pop_top_fav) {
        super();
        this.mContext = mContext;
        this.old_perf_sort_op = old_perf_sort_op;
        this.perf_sort_pop_top_fav = perf_sort_pop_top_fav;
    }

    public String getOld_perf_sort_op() {
        return old_perf_sort_op;
    }

    public GdataFromMOVIEDBtask setOld_perf_sort_op(String old_perf_sort_op) {
        this.old_perf_sort_op = old_perf_sort_op;
        return this;
    }

    public String getPerf_sort_pop_top_fav() {
        return perf_sort_pop_top_fav;
    }

    public GdataFromMOVIEDBtask setPerf_sort_pop_top_fav(String perf_sort_pop_top_fav) {
        this.perf_sort_pop_top_fav = perf_sort_pop_top_fav;
        return this;
    }

    public Context getmContext() {
        return mContext;
    }

    public GdataFromMOVIEDBtask setmContext(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    @Override
    protected Movie[] doInBackground(Void... voids) {
        //delete the old record first,in the moviw table
        mContext.getContentResolver().delete(MovieDbContract.MovieEntry.CONTENT_URI, null, null);

        //get the new data and insert in to the SQL table
        Movie[] movieArray = getMOvieObject();
        for (Movie m : movieArray) {
            insertIntoContntProvider(m);

        }

        return movieArray;
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
        Uri uri = mContext.getContentResolver().insert(MovieDbContract.MovieEntry.CONTENT_URI, cv);
        return uri;
    }

    @Override
    protected void onPostExecute(Movie[] movieArray) {
        super.onPostExecute(movieArray);
        //Todo delete the Array adapter
            /*if (movieArray != null) {
                Log.i("GdataFromMOVIEDBtask", "in onPostExecute");
                movieArrayList.clear();
                movieArrayList.addAll(Arrays.asList(movieArray));
                //myArrayAdapter.notifyDataSetChanged();
            }*/
        //for test the ContentProvider only
        //testThequery(MovieDbContract.MovieEntry.buildMovieID(278));

        //after update make the old_perf_sort_op and  perf_sort_op be the same value
        if (!old_perf_sort_op.equals(perf_sort_pop_top_fav)) {
            SharedPreferences sharePreference = mContext.getSharedPreferences(Utility.SHAREDPREFERENCE_KEY, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePreference.edit();
            editor.putString(mContext.getResources().getString(R.string.old_pref_sort__key), perf_sort_pop_top_fav);
            editor.commit();
        }


    }

    private void testThequery(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.i("show_cursor", cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry._ID)) + " " + cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_TITLE)) + " rage: "
                    + cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_RAGE)) + " popularity: " + cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POP)));
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

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

    private MovieIdResult[] getMviedResult() {
        //step1:get the favourited m_id
        ArrayList<MovieIdResult> mrArraylist = new ArrayList<>();
        Cursor m_idCursor = mContext.getContentResolver().query(MovieDbContract.FavouriteEntry.CONTENT_URI, null, null, null, null);
        //step2:use the favouriteed m_id to get the MovieIdResult
        if (m_idCursor == null) {
            return null;
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create()).build();

            APIService service = retrofit.create(APIService.class);
            m_idCursor.moveToPosition(-1);
            while (m_idCursor.moveToNext()) {
                int c_index = m_idCursor.getColumnIndex(MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY);
                String m_id = m_idCursor.getString(c_index);
                Call<MovieIdResult> callMovieResult = service.LoadSingleFavouriteMovie(m_id);
                try {
                    Response<MovieIdResult> responseMovieResult = callMovieResult.execute();
                    MovieIdResult movieResult = responseMovieResult.body();
                    mrArraylist.add(movieResult);
                } catch (IOException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            MovieIdResult[] mrArray = new MovieIdResult[mrArraylist.size()];
            return mrArraylist.toArray(mrArray);
        }

    }

    private Movie[] getMOvieObject() {

        Config configr = getConfigData();
        if (!perf_sort_pop_top_fav.equals(Utility.FAV_MOVIE)) {
            //select top or pop int the setting
            Results result1 = getMovieDatav2(perf_sort_pop_top_fav);
            return MovieFactory.startMakeMovieArray(configr, result1);
        } else {
            //select Favourite in the setting
            //TODO
            MovieIdResult[] mra = getMviedResult();
            return MovieFactory.startMakeMovieArrayfromMovideResult(configr, mra);
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
}