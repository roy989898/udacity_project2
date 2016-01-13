package pom2.poly.com.trythemoviedbapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.MovieAPI.APIService;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Config;
import pom2.poly.com.trythemoviedbapi.MovieAPI.Results;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    final String IMAGE = "images";
    final String BASE_URL = "base_url";
    final String POSTER_Z = "poster_sizes";
    final String MOVIE_KEY = "getTHeMovie";
    @Bind(R.id.gridView)
    GridView gridView;

    private String perf_sort_op;
    private ArrayList<Movie> movieArrayList;
    private MyArrayAdapter myArrayAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        perf_sort_op = sharedPref.getString(getResources().getString(R.string.pref_sort__key), "pop");
        //Toast.makeText(this, perf_sort_op, Toast.LENGTH_SHORT).show();
        updateMovie();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            movieArrayList = (ArrayList<Movie>) savedInstanceState.get(MOVIE_KEY);
        } else {
            movieArrayList = new ArrayList<>();
        }
        myArrayAdapter = new MyArrayAdapter(this, movieArrayList);
        gridView.setAdapter(myArrayAdapter);
        gridView.setOnItemClickListener(this);

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

    //Key:
    //poster_path
    //overview
    //release_date
    //original_title
    //vote_average
    //backdrop_path
    //this method use geMovieDataFromJson
    private ArrayList<Map<String, String>> getMovieData(String popORtop) {
        final String APIKEY = getResources().getString(R.string.api_key);
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String forecastJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            String myUrl = null;
            //to choose get the top movie information or pop movie information

            switch (popORtop) {

                case "pop": {
                    //http://api.themoviedb.org/3/movie/popular?api_key=db9db09d5d4c08b057a2aefbeea458b0&page=100
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority("api.themoviedb.org")
                            .appendPath("3")
                            .appendPath("movie")
                            .appendPath("popular")
                            .appendQueryParameter("api_key", APIKEY)
                            .appendQueryParameter("page", "100");
                    myUrl = builder.build().toString();
                }
                break;


                case "top": {
                    //http://api.themoviedb.org/3/movie/top_rated?api_key=db9db09d5d4c08b057a2aefbeea458b0
                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https")
                            .authority("api.themoviedb.org")
                            .appendPath("3")
                            .appendPath("movie")
                            .appendPath("top_rated")
                            .appendQueryParameter("api_key", APIKEY)
                            .appendQueryParameter("page", "100");
                    myUrl = builder.build().toString();
                }
                break;
            }


            URL url = new URL(myUrl);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            forecastJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("getMovieJsonString", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        ArrayList<Map<String, String>> resultMovie = null;
        if (forecastJsonStr != null) {
            try {
                resultMovie = geMovieDataFromJson(forecastJsonStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return resultMovie;
    }

    //Key:
    //BASE_URL
    //POSTER_Z
    //this method use getConfigDataFromJson
    private HashMap<String, String> getConfig() {
        final String APIKEY = getResources().getString(R.string.api_key);
        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String configJsonStr = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast

            String myUrl = null;
            //to choose get the top movie information or pop movie information

            //get the config
            //http://api.themoviedb.org/3/configuration?api_key=db9db09d5d4c08b057a2aefbeea458b0
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("configuration")
                    .appendQueryParameter("api_key", APIKEY);
            myUrl = builder.build().toString();


            URL url = new URL(myUrl);

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            configJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e("getMovieJsonString", "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("PlaceholderFragment", "Error closing stream", e);
                }
            }
        }
        HashMap<String, String> confHM = null;
        try {
            confHM = getConfigDataFromJson(configJsonStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return confHM;
    }

    private HashMap<String, String> getConfigDataFromJson(String configJsonStr)
            throws JSONException {


        HashMap<String, String> confHM = new HashMap<>();
        JSONObject confJB = new JSONObject(configJsonStr);
        JSONObject hsonIMAGEOB = confJB.getJSONObject(IMAGE);
        confHM.put(BASE_URL, hsonIMAGEOB.getString(BASE_URL));
        confHM.put(POSTER_Z, hsonIMAGEOB.getJSONArray(POSTER_Z).getString(4));//w500
        confHM.put("backdrop_sizes", hsonIMAGEOB.getJSONArray("backdrop_sizes").getString(1));//w780

        return confHM;

    }

    private ArrayList<Map<String, String>> geMovieDataFromJson(String MovieJsonStr)
            throws JSONException {
        ArrayList resultArray = null;

        JSONObject confJB = new JSONObject(MovieJsonStr);
        JSONArray resultsJSON = confJB.getJSONArray("results");
        if (resultsJSON.length() > 0) {
            resultArray = new ArrayList();
            for (int i = 0; i < resultsJSON.length(); i++) {
                JSONObject jsonObject = resultsJSON.getJSONObject(i);

                HashMap<String, String> map = new HashMap<>();
                map.put("original_title", jsonObject.getString("original_title"));
                map.put("poster_path", jsonObject.getString("poster_path"));
                map.put("overview", jsonObject.getString("overview"));
                map.put("vote_average", jsonObject.getString("vote_average"));
                map.put("release_date", jsonObject.getString("release_date"));
                map.put("backdrop_path", jsonObject.getString("backdrop_path"));

                resultArray.add(map);

            }
        }


        return resultArray;

    }


    //this method use the data from getMovieData and getConfig ,to get the MOVIE object array
    private Movie[] getMOvieObject() {
        /*HashMap<String, String> config = getConfig();
        ArrayList<Map<String, String>> movieArray = getMovieData(perf_sort_op);
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        if (config == null || movieArray == null) {
            return null;
        } else {
            for (int i = 0; i < movieArray.size(); i++) {
                Movie aMovie = new Movie(config.get(POSTER_Z), config.get(BASE_URL), config.get("backdrop_sizes"));

                Map<String, String> map = movieArray.get(i);
                aMovie.setOverview(map.get("overview"));
                aMovie.setPoster_path((map.get("poster_path")));
                aMovie.setBackdrop_path((map.get("backdrop_path")));
                aMovie.setR_date(map.get("release_date"));
                aMovie.setRage(map.get("vote_average"));
                aMovie.setTitle(map.get("original_title"));
                movieArrayList.add(aMovie);
            }
            Movie[] ma = new Movie[movieArrayList.size()];
            movieArrayList.toArray(ma);
            return ma;


        }*/
//        configr = null;
//        results1 = null;
//        //the result will put into the configr
//        new getConfigTask().execute();
//        //the result will put into the results1
//        new getMovieTask().execute(perf_sort_op);
        Config configr = getConfigData();
        Results result1 = getMovieDatav2(perf_sort_op);
        return MovieFactory.startMakeMovieArray(configr,result1);


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.class.getName(), myArrayAdapter.getItem(position));
        startActivity(intent);
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

    class GdataFromMOVIEDBtask extends AsyncTask<Void, Void, Movie[]> {
        @Override
        protected Movie[] doInBackground(Void... voids) {
            Movie[] movieArray = getMOvieObject();
            return movieArray;
        }

        @Override
        protected void onPostExecute(Movie[] movieArray) {
            super.onPostExecute(movieArray);
            if (movieArray != null) {
                Log.i("GdataFromMOVIEDBtask", "in onPostExecute");
                movieArrayList.clear();
                movieArrayList.addAll(Arrays.asList(movieArray));
                myArrayAdapter.notifyDataSetChanged();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
    }

    private Config getConfigData(){
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

    class getConfigTask extends AsyncTask<Void, Void, Config> {
        @Override
        protected Config doInBackground(Void... params) {
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Config config) {
            //super.onPostExecute(config);
            //configr = config;
        }
    }

    private Results getMovieDatav2(String topOrPop){
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

    class getMovieTask extends AsyncTask<String, Void, Results> {
        @Override
        protected Results doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create()).build();

            APIService service = retrofit.create(APIService.class);
            Call<Results> callResults = null;
            switch (params[0]) {
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

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Results results) {
            //super.onPostExecute(results);
            //results1 = results;
        }
    }


}

