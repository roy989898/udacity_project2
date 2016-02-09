package pom2.poly.com.trythemoviedbapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.MovieAPI.TrailerResult.Result;

/**
 * Created by User on 9/2/2016.
 */
public class TrailerAdapter extends ArrayAdapter<Result> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.showtrailerlayout, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder vh= (ViewHolder) convertView.getTag();
        vh.tvTrailer.setText(getItem(position).getName());
        return convertView;
    }

    public TrailerAdapter(Context context, List<Result> objects) {
        super(context, 0, objects);
    }

    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'showtrailerlayout.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tvTrailer)
        TextView tvTrailer;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
