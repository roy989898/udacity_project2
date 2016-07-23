package pom2.poly.com.trythemoviedbapi.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.MovieAPI.APIService;
import pom2.poly.com.trythemoviedbapi.MovieAPI.ReviewResult.ReviewResult;
import pom2.poly.com.trythemoviedbapi.MovieAPI.TrailerResult.Result;
import pom2.poly.com.trythemoviedbapi.MovieAPI.TrailerResult.TrailerResult;
import pom2.poly.com.trythemoviedbapi.R;
import pom2.poly.com.trythemoviedbapi.ReviewAdapter;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;
import pom2.poly.com.trythemoviedbapi.TrailerRecycleView.RecycleTrailerAdapter;
import pom2.poly.com.trythemoviedbapi.Utility;
import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by User on 1/2/2016.
 */
public class DetailFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static int CURSORLOADER_ID;
    @Bind(R.id.appBar_img)
    SimpleDraweeView appBarImg;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.imb1)
    ImageButton imb1;
    @Bind(R.id.ratingBar)
    RatingBar ratingBar;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvOverview)
    TextView tvOverview;


    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.lvShowReview)
    ListView lvShowReview;
    @Bind(R.id.lineayout1)
    LinearLayout lineayout1;
    @Bind(R.id.nested_scrollView)
    NestedScrollView nestedScrollView;
    @Bind(R.id.rvTrailer)
    RecyclerView rvTrailer;


    private String m_id = null;
    private Boolean isTwoPlanMode = false;
    private Bundle infBundle = null;
    private Context mContext;
    private RecycleTrailerAdapter recycleTrailerAdapter;


    public Boolean getIsTwoPlanMode() {
        return isTwoPlanMode;
    }

    public DetailFragment setIsTwoPlanMode(Boolean isTwoPlanMode) {
        this.isTwoPlanMode = isTwoPlanMode;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if do not save before ,infBundle will equal null
        mContext = getContext();
        if (savedInstanceState != null) {
            infBundle = (Bundle) savedInstanceState.get(Utility.BUNDLE_KEY_RESTORE_DETAIL_BUNDLE);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Utility.BUNDLE_KEY_RESTORE_DETAIL_BUNDLE, infBundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_v2, container, false);
        Log.i("DetailFragment", "onCreateView");
        ButterKnife.bind(this, view);


        imb1 = (ImageButton) view.findViewById(R.id.imb1);

        //        Movie aMovie = getIntent().getParcelableExtra(DetailActivity.class.getName());


        //if can't get infBundle from savedInstanceState
        if (infBundle == null) {
            if (isTwoPlanMode) {
                infBundle = getArguments();
            } else {
                infBundle = getActivity().getIntent().getExtras();
            }

        }

        bindDataToView(infBundle);

        getActivity().getSupportLoaderManager().initLoader(CURSORLOADER_ID, null, this);

        //set the ActionBar
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        //add the back button
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(infBundle.getString(Utility.BUNDLE_KEY_TITLE));
      /*  ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);*/


        return view;
    }

    private void bindDataToView(Bundle infBundle) {
        tvTitle.setText(infBundle.getString(Utility.BUNDLE_KEY_TITLE));
        //        Picasso.with(getContext()).load(infBundle.getString(Utility.BUNDLE_KEY_BACKGROUNDPATH)).into(iv1);
        appBarImg.setImageURI(infBundle.getString(Utility.BUNDLE_KEY_BACKGROUNDPATH));

        double rate = Double.parseDouble(infBundle.getString(Utility.BUNDLE_KEY_RATE));
        Double numberOfstra = rate / 10 * 5;
        Logger.d(rate + "");

        ratingBar.setNumStars(numberOfstra.intValue());
        tvDate.setText(infBundle.getString(Utility.BUNDLE_KEY_DATE));
        tvOverview.setText(infBundle.getString(Utility.BUNDLE_KEY_OVERVIEW));

        m_id = infBundle.getString(Utility.BUNDLE_KEY_M_ID);
        //Load trailer with the m_id
        new getTrailerTask().execute(m_id);

        //Load review with the m_id
        new getReviewTask().execute(m_id);
        assert m_id != null;
        CURSORLOADER_ID = Integer.parseInt(m_id);
        imb1.setOnClickListener(this);

        //set The recycle iew of trailer
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rvTrailer.setLayoutManager(layoutManager);

        recycleTrailerAdapter = new RecycleTrailerAdapter(getContext());
        rvTrailer.setAdapter(recycleTrailerAdapter);


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MovieDbContract.FavouriteEntry.buildFavouriteWithID(Long.parseLong(m_id));
        return new CursorLoader(getContext(), uri, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int i = data.getCount();
        if (i > 0) {
            //in the favourite table
            imb1.setImageResource(R.drawable.ic_favorite_black_36dp);
            imb1.setTag("R.drawable.ic_favorite_black_36dp");
        } else {
            // not in the favourite table
            imb1.setImageResource(R.drawable.ic_favorite_border_black_36dp);
            imb1.setTag("R.drawable.ic_favorite_border_black_36dp");
        }

//        data.close();

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb1:
//                Toast.makeText(this,m_id,Toast.LENGTH_SHORT).show();


                if (imb1.getTag().equals("R.drawable.ic_favorite_black_36dp")) {
                    //the star button is white now

                    //delete the  m_id from the favourite table

                    getContext().getContentResolver().delete(MovieDbContract.FavouriteEntry.buildFavouriteWithID(Long.parseLong(m_id)), null, null);

                    imb1.setImageResource(R.drawable.ic_favorite_border_black_36dp);
                } else {
                    //the star button is black now
                    //insert the m_id to the favourite table
                    ContentValues cv = new ContentValues();
                    cv.put(MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY, Long.parseLong(m_id));
                    getContext().getContentResolver().insert(MovieDbContract.FavouriteEntry.CONTENT_URI, cv);

                    imb1.setImageResource(R.drawable.ic_favorite_black_36dp);
                }

                break;
        }

    }

    private void watchYoutubeVideo(String key) {
        /*try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));

            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            startActivity(intent);
        }*/

        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + key));
        startActivity(intent);
    }

    private class getTrailerTask extends AsyncTask<String, Void, List<Result>> implements AdapterView.OnItemClickListener {

        @Override
        protected List<Result> doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService service = retrofit.create(APIService.class);
            Call<TrailerResult> callTrailerResult = service.LoadTrailer(params[0]);
            Response<TrailerResult> responseTrailerResult;
            try {
                responseTrailerResult = callTrailerResult.execute();
            } catch (IOException e) {
                e.printStackTrace();
                //if the trailerResult is null.return null
                return null;
            }
            TrailerResult trailerResult;
            if (responseTrailerResult != null) {
                trailerResult = responseTrailerResult.body();
            } else {
                //if the trailerResult is null.return null
                return null;
            }
            return trailerResult.getResults();
        }

        @Override
        protected void onPostExecute(List<Result> result) {
            super.onPostExecute(result);
            try {
                /*TrailerAdapter ta = new TrailerAdapter(mContext, result);
                lvTrailer.setAdapter(ta);
//                lvTrailer.setMinimumHeight(20);
                lvTrailer.setOnItemClickListener(this);*/

                //TODO recycle view of trailer
                recycleTrailerAdapter.swapData(result);
            } catch (Exception e) {
                Log.e("onPostExecute error", e.toString());
            }

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Result trailer = (Result) parent.getItemAtPosition(position);
            watchYoutubeVideo(trailer.getKey());

        }
    }

    private class getReviewTask extends AsyncTask<String, Void, List<pom2.poly.com.trythemoviedbapi.MovieAPI.ReviewResult.Result>> {
        @Override
        protected void onPostExecute(List<pom2.poly.com.trythemoviedbapi.MovieAPI.ReviewResult.Result> reviewResult) {
            super.onPostExecute(reviewResult);


            try {
                ReviewAdapter ra = new ReviewAdapter(mContext, reviewResult);
                lvShowReview.setAdapter(ra);


            } catch (Exception e) {
                Log.e("onPostExecute error", e.toString());
            }

        }

        @Override
        protected List<pom2.poly.com.trythemoviedbapi.MovieAPI.ReviewResult.Result> doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            APIService service = retrofit.create(APIService.class);
            Call<ReviewResult> callTrailerResult = service.LoadReview(params[0]);
            Response<ReviewResult> ResponseTrailerResult;
            try {
                ResponseTrailerResult = callTrailerResult.execute();
            } catch (IOException e) {
                e.printStackTrace();
                //if have error,return null
                return null;
            }
            ReviewResult trailerResult = ResponseTrailerResult.body();
            return trailerResult.getResults();
        }
    }
}




