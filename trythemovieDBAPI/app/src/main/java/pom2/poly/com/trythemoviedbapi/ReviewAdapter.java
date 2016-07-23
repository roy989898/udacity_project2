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
import pom2.poly.com.trythemoviedbapi.MovieAPI.ReviewResult.Result;

/**
 * Created by User on 9/2/2016.
 */
public class ReviewAdapter extends ArrayAdapter<Result> {
    public ReviewAdapter(Context context, List<Result> result) {
        super(context, 0, result);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Result result = getItem(position);
        if (convertView == null) {
            //cant reuse
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.showreviewlayout, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.tvReview.setText(result.getContent());
        vh.tvAuthor.setText(result.getAuthor());


        return convertView;

    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'showreviewlayout.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */
    static class ViewHolder {
        @Bind(R.id.tvAuthor)
        TextView tvAuthor;
        @Bind(R.id.tvReview)
        TextView tvReview;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
//            tvReview= (TextView) view.findViewById(R.id.tvReview);
        }
    }


}
