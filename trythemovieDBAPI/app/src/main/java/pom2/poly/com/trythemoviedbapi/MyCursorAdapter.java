package pom2.poly.com.trythemoviedbapi;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;

/**
 * Created by User on 22/1/2016.
 */
public class MyCursorAdapter extends CursorAdapter {

    private Context mcontext;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        mcontext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
        view.setTag(new ViewHolder(view));
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder vh = (ViewHolder) view.getTag();

        ImageView imv = vh.moviePoster;
        Picasso picasso = Picasso.with(mcontext);
        //picasso.setLoggingEnabled(true);
        picasso.load(cursor.getString(cursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH))).into(imv);

    }

    ///view holder
    private class ViewHolder {

        public final ImageView moviePoster;


        public ViewHolder(View view) {
            moviePoster = (ImageView) view.findViewById(R.id.iv1);

        }

    }
}


