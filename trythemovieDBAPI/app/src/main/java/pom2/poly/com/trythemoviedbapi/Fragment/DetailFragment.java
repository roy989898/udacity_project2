package pom2.poly.com.trythemoviedbapi.Fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.R;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;
import pom2.poly.com.trythemoviedbapi.Utility;

/**
 * Created by User on 1/2/2016.
 */
public class DetailFragment extends Fragment implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static int CURSORLOADER_ID;
    @Bind(R.id.imb1)
    ImageButton imb1;
    @Bind(R.id.iv1)
    ImageView iv1;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvRate)
    TextView tvRate;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvOverview)
    TextView tvOverview;
    @Bind(R.id.scrollView)
    ScrollView scrollView;
    @Bind(R.id.lineayout1)
    LinearLayout lineayout1;
    private String m_id = null;
    private Boolean isTwoPlanMode = false;
    private Bundle infBundle = null;

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
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, view);

        //        Movie aMovie = getIntent().getParcelableExtra(DetailActivity.class.getName());
        //TODO: use the right  method get the bundle

        //if can't get infBundle from savedInstanceState
        if (infBundle == null) {
            if (isTwoPlanMode) {
                infBundle = getArguments();
            } else {
                infBundle = getActivity().getIntent().getExtras();
            }

        }

        tvTitle.setText(infBundle.getString(Utility.BUNDLE_KEY_TITLE));

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                BitmapDrawable backdround = new BitmapDrawable(getResources(), bitmap);
                backdround.setAlpha(150);
                lineayout1.setBackgroundDrawable(backdround);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {    //當圖片加載失敗時調用
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {     //當任務被提交時調用
            }
        };


        Picasso.with(getContext()).load(infBundle.getString(Utility.BUNDLE_KEY_POSTERPATH)).into(target);
        Picasso.with(getContext()).load(infBundle.getString(Utility.BUNDLE_KEY_BACKGROUNDPATH)).into(iv1);

        tvRate.setText(infBundle.getString(Utility.BUNDLE_KEY_RATE));
        tvDate.setText(infBundle.getString(Utility.BUNDLE_KEY_DATE));
        tvOverview.setText(infBundle.getString(Utility.BUNDLE_KEY_OVERVIEW));

        m_id = infBundle.getString(Utility.BUNDLE_KEY_M_ID);
        CURSORLOADER_ID = Integer.parseInt(m_id);
        imb1.setOnClickListener(this);

        getActivity().getSupportLoaderManager().initLoader(CURSORLOADER_ID, null, this);


        return view;
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
            imb1.setImageResource(R.drawable.ic_star_white_36dp);
            imb1.setTag("R.drawable.ic_star_white_36dp");
        } else {
            // not in the favourite table
            imb1.setImageResource(R.drawable.ic_star_black_36dp);
            imb1.setTag("(R.drawable.ic_star_black_36dp");
        }
        //TODO delete the cursor
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


                if (imb1.getTag().equals("R.drawable.ic_star_white_36dp")) {
                    //the star button is white now

                    //delete the  m_id from the favourite table

                    getContext().getContentResolver().delete(MovieDbContract.FavouriteEntry.buildFavouriteWithID(Long.parseLong(m_id)), null, null);

                    imb1.setImageResource(R.drawable.ic_star_black_36dp);
                } else {
                    //the star button is black now
                    //insert the m_id to the favourite table
                    ContentValues cv = new ContentValues();
                    cv.put(MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY, Long.parseLong(m_id));
                    getContext().getContentResolver().insert(MovieDbContract.FavouriteEntry.CONTENT_URI, cv);

                    imb1.setImageResource(R.drawable.ic_star_white_36dp);
                }

                break;
        }

    }
}
