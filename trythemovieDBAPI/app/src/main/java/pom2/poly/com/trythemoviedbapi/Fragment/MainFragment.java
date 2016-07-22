package pom2.poly.com.trythemoviedbapi.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import pom2.poly.com.trythemoviedbapi.Activity.MainActivity;
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
    private Callback mActivity;
    private MainActivity mainActivity;
    private int screenWidth;
    private SharedPreferences preference;


    @Override
    public void onPause() {
        super.onPause();
    }

    public void updateMovie() {
        perf_sort_pop_top_fav = preference.getString(getResources().getString(R.string.pref_sort__key), Utility.POP_MOVIE);
        GdataFromMOVIEDBtask task = new GdataFromMOVIEDBtask(getContext(), perf_sort_pop_top_fav);
        task.execute();
        initCursorLoader();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (Callback) activity;
        mainActivity= (MainActivity) activity;


    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d("MainFragment,inResume");
//        updateMovie();//TODO

    }

    private void initCursorLoader(){
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



        Logger.d("onStart");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preference = getActivity().getSharedPreferences(getString(R.string.sharedPreferenceName), Context.MODE_PRIVATE);
        perf_sort_pop_top_fav = preference.getString(getResources().getString(R.string.pref_sort__key), Utility.POP_MOVIE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        screenWidth = view.getWidth();
                        Logger.d("width: " + screenWidth + "");


                    }
                });


        if (mainActivity.getIsTwoPlanMode()&&!isPortrait()) {
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
        Log.i("onLoadFinished2", "onLoadFinished :"  );
//        myrecycleViewadapter.swapCursor(data);
        //myCursorAdapter.notifyDataSetChanged();
//        showCursorContent(data);

        if (myrecycleViewadapter == null) {
            myrecycleViewadapter = new MyRecyclerViewAdapter(data, getContext());

            if (mainActivity.getIsTwoPlanMode()&&!isPortrait())
                myrecycleViewadapter.setCellWidth(screenWidth / 3);
            else
                myrecycleViewadapter.setCellWidth(screenWidth / 2);

            myRecyclerView.setAdapter(myrecycleViewadapter);
            myrecycleViewadapter.setOnItemClickListener(this);
        } else {
            if (mainActivity.getIsTwoPlanMode()&&!isPortrait())
                myrecycleViewadapter.setCellWidth(screenWidth / 3);
            else
                myrecycleViewadapter.setCellWidth(screenWidth / 2);
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

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public interface Callback {
        void onItemClick(int position, View v, Cursor c);
    }
}
