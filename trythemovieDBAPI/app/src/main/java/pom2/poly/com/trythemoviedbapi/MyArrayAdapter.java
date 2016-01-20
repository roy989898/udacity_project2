package pom2.poly.com.trythemoviedbapi;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by User on 14/12/2015.
 */
public class MyArrayAdapter extends ArrayAdapter<Movie> {
    ImageView iv1;
    private List<Movie> objects;


    public MyArrayAdapter(Context context, List<Movie> objects) {
        super(context, 0, objects);
        this.objects = objects;
        Log.d("MyArrayAdapter", "MyArrayAdapter");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("MyArrayAdapter", position + "");
        Movie amovie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movieshow, parent, false);
        }
        iv1 = (ImageView) convertView.findViewById(R.id.iv1);
        Picasso picasso = Picasso.with(getContext());
        //picasso.setLoggingEnabled(true);
        picasso.load(amovie.getPoster_path()).into(iv1);     //可以是本地圖片或網絡圖片

        Log.d("MyArrayAdapter", "after p load");
        return convertView;

    }


}
