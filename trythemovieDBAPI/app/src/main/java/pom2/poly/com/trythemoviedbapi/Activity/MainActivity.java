package pom2.poly.com.trythemoviedbapi.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.Fragment.DetailFragment;
import pom2.poly.com.trythemoviedbapi.Fragment.MainFragment;
import pom2.poly.com.trythemoviedbapi.R;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;
import pom2.poly.com.trythemoviedbapi.Utility;


public class MainActivity extends AppCompatActivity implements MainFragment.Callback, AdapterView.OnItemSelectedListener {

    private static Boolean isTwoPlanMode = false;

    FrameLayout frameLayoutDetailMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.spSortMode)
    Spinner spSortMode;
    private ArrayAdapter<String> sortArray;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;
    private MainFragment mainFragment;

    public static Boolean getIsTwoPlanMode() {
        return isTwoPlanMode;
    }


//    private ArrayList<Movie> movieArrayList;


    public static void showCursorContent(Cursor data) {
        Log.i("showCursorContent", "in");
        data.move(-1);
        while (data.moveToNext()) {
            Log.i("showCursorContent", data.getString(data.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_TITLE)));
        }

    }

    /*@Override
    protected void onStart() {
        super.onStart();
        //get the new setting
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        perf_sort_pop_top_fav = sharedPref.getString(getResources().getString(R.string.pref_sort__key), "pop");
        //Toast.makeText(this, perf_sort_op, Toast.LENGTH_SHORT).show();

        //get the old setting

        SharedPreferences old_sharedPref = getSharedPreferences(Utility.SHAREDPREFERENCE_KEY, Context.MODE_PRIVATE);
        old_perf_sort_op = old_sharedPref.getString(getResources().getString(R.string.old_pref_sort__key), "pop");

        if (perf_sort_pop_top_fav.equals(Utility.FAV_MOVIE) || !perf_sort_pop_top_fav.equals(old_perf_sort_op)) {
            updateMovie();
        }

    }*/

   /* @Override
    protected void onResume() {
        super.onResume();
        switch (perf_sort_pop_top_fav) {
            case Utility.POP_MOVIE:
                Log.i("loader", "POP_MOVIE");
                getSupportLoaderManager().initLoader(MOVIE_POP_LOADER, null, this);
                break;
            case Utility.TOP_MOVIE:
                Log.i("loader", "TOP_MOVIE");
                getSupportLoaderManager().initLoader(MOVIE_TOP_LOADER, null, this);
                break;
            case Utility.FAV_MOVIE:
                Log.i("loader", "TOP_MOVIE");
                getSupportLoaderManager().initLoader(MOVIE_TOP_LOADER, null, this);
                break;

        }

    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        sortArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.pref_sort_entries));
        spSortMode.setAdapter(sortArray);
        spSortMode.setOnItemSelectedListener(this);

        FragmentManager fragementManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragementManager.beginTransaction();

        //use can find the frame_layout_detail_in_main layout yo define is the phone is loarge and landscape
        frameLayoutDetailMain = (FrameLayout) findViewById(R.id.frame_layout_detail_in_main);
        isTwoPlanMode = frameLayoutDetailMain != null;

        if (savedInstanceState == null) {

            mainFragment = new MainFragment();
            Logger.d("create a mainFragment");
            fragmentTransaction.add(R.id.frame_layout_main, mainFragment,"f1");
            fragmentTransaction.commit();

        }else{
            mainFragment= (MainFragment) getSupportFragmentManager().findFragmentByTag("f1");
        }



        preference = getSharedPreferences(getString(R.string.sharedPreferenceName), Context.MODE_PRIVATE);
        editor = preference.edit();


        //S


        /*if (savedInstanceState != null) {
            movieArrayList = (ArrayList<Movie>) savedInstanceState.get(MOVIE_KEY);
        } else {
            movieArrayList = new ArrayList<>();
        }*/
        //myArrayAdapter = new MyArrayAdapter(this, movieArrayList);
//        myCursorAdapter = new MyCursorAdapter(this, null);

        //gridView.setAdapter(myArrayAdapter);
//        gridView.setAdapter(myCursorAdapter);
        //if the size of the recycle view is fixed,set this, will get optimizations
//        myRecyclerView.setHasFixedSize(true);

        //se the layout manager,defune the way to show the View iteam
//        mLayoutManager = new LinearLayoutManager(this);
//        mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        myRecyclerView.setLayoutManager(mLayoutManager);

        //when load finish set the Cursor


        //gridView.setOnItemClickListener(this);


        //new getConfigTask().execute();
        //new getMovieTask().execute(Utility.POP_MOVIE);


    }



    //this method use the data from getConfigData and getMovieDatav2 ,to get the MOVIE object array
    /*private Movie[] getMOvieObject() {

        Config configr = getConfigData();
        if(!perf_sort_pop_top_fav.equals(Utility.FAV_MOVIE)){
            //select top or pop int the setting
            Results result1 = getMovieDatav2(perf_sort_pop_top_fav);
            return MovieFactory.startMakeMovieArray(configr, result1);
        }else{
            //select Favourite in the setting

            MovieIdResult[] mra=getMviedResult();
            return MovieFactory.startMakeMovieArrayfromMovideResult(configr, mra);
        }



    }*/
   /* private Results getMovieDatav2(String topOrPop) {
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
    }*/

    /*private MovieIdResult[] getMviedResult() {
        //step1:get the favourited m_id
        ArrayList<MovieIdResult> mrArraylist = new ArrayList<>();
        Cursor m_idCursor = getContentResolver().query(MovieDbContract.FavouriteEntry.CONTENT_URI, null, null, null, null);
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

    }*/


    /* @Override
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
 */
    @Override
    public void onItemClick(int position, View v, Cursor c) {
        /*FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DetailFragment aNewDetailFragment=new DetailFragment();
        aNewDetailFragment.setArguments(putCursordatainToBundle(c));
        fragmentTransaction.replace(R.id.frame_layout_main,aNewDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
        if (isTwoPlanMode) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransavtion = fragmentManager.beginTransaction();

            DetailFragment df = new DetailFragment();
            df.setArguments(putCursordatainToBundle(c));
            df.setIsTwoPlanMode(true);
            fragmentTransavtion.replace(R.id.frame_layout_detail_in_main, df);
            fragmentTransavtion.commit();


        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtras(putCursordatainToBundle(c));

            startActivity(intent);
        }


    }

    private Bundle putCursordatainToBundle(Cursor c) {
        String title = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_TITLE));
        String poster_path = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH));
        String rate = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_RAGE));
        String date = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_R_DATE));
        String overview = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_OVERVIEW));
        String background_path = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_BACKDROP_PATH));
        String m_id = c.getString(c.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_M_ID));

        Bundle bundle = new Bundle();

        bundle.putString(Utility.BUNDLE_KEY_TITLE, title);
        bundle.putString(Utility.BUNDLE_KEY_POSTERPATH, poster_path);
        bundle.putString(Utility.BUNDLE_KEY_RATE, rate);
        bundle.putString(Utility.BUNDLE_KEY_DATE, date);
        bundle.putString(Utility.BUNDLE_KEY_OVERVIEW, overview);
        bundle.putString(Utility.BUNDLE_KEY_BACKGROUNDPATH, background_path);
        bundle.putString(Utility.BUNDLE_KEY_M_ID, m_id);
        return bundle;
    }

//    for the spSortMode

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {
            case 0:
//                pop
                editor.putString(getString(R.string.pref_sort__key), Utility.POP_MOVIE);
                editor.commit();
                break;

            case 1:
//                High rated
                editor.putString(getString(R.string.pref_sort__key), Utility.TOP_MOVIE);
                editor.commit();
                break;

            case 2:
//                fav
                editor.putString(getString(R.string.pref_sort__key), Utility.FAV_MOVIE);
                editor.commit();
                break;
        }

        if (mainFragment != null)
            mainFragment.updateMovie();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /*private Config getConfigData() {
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

    }*/



    /*private Uri insertIntoContntProvider(Movie movie) {
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
    }*/











    /*class GdataFromMOVIEDBtask extends AsyncTask<Void, Void, Movie[]> {
        @Override
        protected Movie[] doInBackground(Void... voids) {
            //delete the old record first,in the moviw table
            getContentResolver().delete(MovieDbContract.MovieEntry.CONTENT_URI, null, null);

            //get the new data and insert in to the SQL table
            Movie[] movieArray = getMOvieObject();
            for (Movie m : movieArray) {
                insertIntoContntProvider(m);

            }

            return movieArray;
        }


        @Override
        protected void onPostExecute(Movie[] movieArray) {
            super.onPostExecute(movieArray);
            *//*if (movieArray != null) {
                Log.i("GdataFromMOVIEDBtask", "in onPostExecute");
                movieArrayList.clear();
                movieArrayList.addAll(Arrays.asList(movieArray));
                //myArrayAdapter.notifyDataSetChanged();
            }*//*
            //for test the ContentProvider only
            //testThequery(MovieDbContract.MovieEntry.buildMovieID(278));

            //after update make the old_perf_sort_op and  perf_sort_op be the same value
            if (!old_perf_sort_op.equals(perf_sort_pop_top_fav)) {
                SharedPreferences sharePreference = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharePreference.edit();
                editor.putString(getResources().getString(R.string.old_pref_sort__key), perf_sort_pop_top_fav);
                editor.commit();
            }


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
    }*/


}

