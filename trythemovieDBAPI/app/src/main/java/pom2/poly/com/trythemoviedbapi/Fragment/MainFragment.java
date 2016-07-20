package pom2.poly.com.trythemoviedbapi.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.orhanobut.logger.Logger;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.GdataFromMOVIEDBtask;
import pom2.poly.com.trythemoviedbapi.MyRecyclerViewAdapter;
import pom2.poly.com.trythemoviedbapi.R;
import pom2.poly.com.trythemoviedbapi.SpacesItemDecoration;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbHelper;
import pom2.poly.com.trythemoviedbapi.Utility;

/**
 * Created by User on 30/1/2016.
 */
public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, MyRecyclerViewAdapter.MyClickListener {
    private static int MOVIE_POP_LOADER = 0;
    private static int MOVIE_TOP_LOADER = 1;
    private static int MOVIE_FAV_LOADER = 2;
    @Bind(R.id.my_recycler_view)
    RecyclerView myRecyclerView;
    private MyRecyclerViewAdapter myrecycleViewadapter;
    private StaggeredGridLayoutManager mLayoutManager;
    private String perf_sort_pop_top_fav;
    private String old_perf_sort_op;
    private Boolean isTwoPlanMode = false;
    private Callback mActivity;
    private int screenWidth;

    public Boolean getIsTwoPlanMode() {
        return isTwoPlanMode;
    }

    public MainFragment setIsTwoPlanMode(Boolean isTwoPlanMode) {
        this.isTwoPlanMode = isTwoPlanMode;
        return this;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateMovie() {
        GdataFromMOVIEDBtask task = new GdataFromMOVIEDBtask(getContext(), old_perf_sort_op, perf_sort_pop_top_fav);
        task.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (Callback) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (perf_sort_pop_top_fav) {
            case Utility.POP_MOVIE:
                Log.i("loader", "POP_MOVIE");
                getActivity().getSupportLoaderManager().initLoader(MOVIE_POP_LOADER, null, this);
                break;
            case Utility.TOP_MOVIE:
                Log.i("loader", "TOP_MOVIE");
                getActivity().getSupportLoaderManager().initLoader(MOVIE_TOP_LOADER, null, this);
                break;
            case Utility.FAV_MOVIE:
                Log.i("loader", "TOP_MOVIE");
                getActivity().getSupportLoaderManager().initLoader(MOVIE_TOP_LOADER, null, this);
                break;

        }
    }

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        perf_sort_pop_top_fav = sharedPref.getString(getResources().getString(R.string.pref_sort__key), "pop");
        //Toast.makeText(this, perf_sort_op, Toast.LENGTH_SHORT).show();

        //get the old setting

        //TODO
        /*SharedPreferences old_sharedPref = getContext().getSharedPreferences(Utility.SHAREDPREFERENCE_KEY, Context.MODE_PRIVATE);
        old_perf_sort_op = old_sharedPref.getString(getResources().getString(R.string.old_pref_sort__key), "pop");

        if (perf_sort_pop_top_fav.equals(Utility.FAV_MOVIE) || !perf_sort_pop_top_fav.equals(old_perf_sort_op)) {
            updateMovie();
        }*/
        updateMovie();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieDbHelper movieDbHelper = new MovieDbHelper(getContext());
        movieDbHelper.getWritableDatabase();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        //TODO test the width of the fragment
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        screenWidth = view.getWidth();
                        Logger.d("width: " + screenWidth + "");


                    }
                });


        if (isTwoPlanMode) {
            mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        } else {
            mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        }

        ButterKnife.bind(this, view);

        myRecyclerView.setLayoutManager(mLayoutManager);

//        SpacesItemDecoration decoration=new SpacesItemDecoration(16);

        myRecyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 0, 0));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Loader<Cursor> cursorLoader = null;
        if (perf_sort_pop_top_fav == null) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
            perf_sort_pop_top_fav = sharedPref.getString(getResources().getString(R.string.pref_sort__key), "pop");
        }
        switch (perf_sort_pop_top_fav) {
            case Utility.TOP_MOVIE:
                Log.i("loader", "onCreateLoader-TOP_MOVIE");
                cursorLoader = new CursorLoader(getContext(), MovieDbContract.MovieEntry.CONTENT_URI_TOP, null, null, null, null);

                break;
            case Utility.POP_MOVIE:
                Log.i("loader", "onCreateLoader-POP_MOVIE");
                cursorLoader = new CursorLoader(getContext(), MovieDbContract.MovieEntry.CONTENT_URI_POP, null, null, null, null);

                break;
            case Utility.FAV_MOVIE:
                Log.i("loader", "onCreateLoader-FAV_MOVIE");
                cursorLoader = new CursorLoader(getContext(), MovieDbContract.MovieEntry.CONTENT_URI, null, null, null, null);

                break;
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i("loader", "onLoadFinished");
//        myrecycleViewadapter.swapCursor(data);
        //myCursorAdapter.notifyDataSetChanged();
//        showCursorContent(data);

        if (myrecycleViewadapter == null) {
            myrecycleViewadapter = new MyRecyclerViewAdapter(data, getContext());
            myrecycleViewadapter.setCellWidth(screenWidth / 2);
            myRecyclerView.setAdapter(myrecycleViewadapter);
            myrecycleViewadapter.setOnItemClickListener(this);
        } else {
            myrecycleViewadapter.swapCursor(data);
        }


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.i("loader", "onLoaderReset");
//        myrecycleViewadapter.swapCursor(null);
    }

    @Override
    public void onItemClick(int position, View v) {
        //        Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
//
        Cursor cursor = myrecycleViewadapter.getCursor();
        cursor.moveToPosition(position);


        mActivity.onItemClick(position, v, cursor);
    }

    public interface Callback {
        void onItemClick(int position, View v, Cursor c);
    }

}
