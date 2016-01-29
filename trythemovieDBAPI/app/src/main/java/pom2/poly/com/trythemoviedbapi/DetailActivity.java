package pom2.poly.com.trythemoviedbapi;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static int CURSORLOADER_ID;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.iv1)
    ImageView iv1;
    @Bind(R.id.tvRate)
    TextView tvRate;
    @Bind(R.id.tvDate)
    TextView tvDate;
    @Bind(R.id.tvOverview)
    TextView tvOverview;
    @Bind(R.id.lineayout1)
    LinearLayout lineayout1;
    @Bind(R.id.imb1)
    ImageButton imb1;
    private String m_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

//        Movie aMovie = getIntent().getParcelableExtra(DetailActivity.class.getName());
        Bundle infBundle = getIntent().getExtras();
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


        Picasso.with(this).load(infBundle.getString(Utility.BUNDLE_KEY_POSTERPATH)).into(target);
        Picasso.with(this).load(infBundle.getString(Utility.BUNDLE_KEY_BACKGROUNDPATH)).into(iv1);

        tvRate.setText(infBundle.getString(Utility.BUNDLE_KEY_RATE));
        tvDate.setText(infBundle.getString(Utility.BUNDLE_KEY_DATE));
        tvOverview.setText(infBundle.getString(Utility.BUNDLE_KEY_OVERVIEW));

        m_id = infBundle.getString(Utility.BUNDLE_KEY_M_ID);
        CURSORLOADER_ID = Integer.parseInt(m_id);
        imb1.setOnClickListener(this);

        getSupportLoaderManager().initLoader(CURSORLOADER_ID, null, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imb1:
//                Toast.makeText(this,m_id,Toast.LENGTH_SHORT).show();


                if(imb1.getTag().equals("R.drawable.ic_star_white_36dp")){
                    //the star button is white now

                    //delete the  m_id from the favourite table

                    getContentResolver().delete(MovieDbContract.FavouriteEntry.buildFavouriteWithID(Long.parseLong(m_id)),null,null);

                    imb1.setImageResource(R.drawable.ic_star_black_36dp);
                }else{
                    //the star button is black now
                    //insert the m_id to the favourite table
                    ContentValues cv = new ContentValues();
                    cv.put(MovieDbContract.FavouriteEntry.COLUMN_MOVIE_KEY, Long.parseLong(m_id));
                    getContentResolver().insert(MovieDbContract.FavouriteEntry.CONTENT_URI, cv);

                    imb1.setImageResource(R.drawable.ic_star_white_36dp);
                }

                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = MovieDbContract.FavouriteEntry.buildFavouriteWithID(Long.parseLong(m_id));
        return new CursorLoader(this, uri, null, null, null, null);
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

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
