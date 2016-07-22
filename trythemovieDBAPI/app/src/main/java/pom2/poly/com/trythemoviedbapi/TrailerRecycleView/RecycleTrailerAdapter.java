package pom2.poly.com.trythemoviedbapi.TrailerRecycleView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.MovieAPI.TrailerResult.Result;
import pom2.poly.com.trythemoviedbapi.R;

/**
 * Created by User on 22/7/2016.
 */
public class RecycleTrailerAdapter extends RecyclerView.Adapter<RecycleTrailerAdapter.ViewHolder> {

    private List<Result> result;

    public RecycleTrailerAdapter() {
    }

    public RecycleTrailerAdapter(List<Result> result) {
        this.result = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_revycle_cell, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Result trailer = result.get(position);
        String imguri = String.format("http://img.youtube.com/vi/%s/0.jpg", trailer.getKey() + "");
        Log.d(this.getClass().getSimpleName(), imguri);
        holder.traileImage.setImageURI(imguri);

    }

    public void swapData(List<Result> result) {
        this.result = result;
        notifyDataSetChanged();
        Log.d(this.getClass().getSimpleName(), "swapData");
    }

    @Override
    public int getItemCount() {
        if (result != null)
            return result.size();
        else
            return 0;
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.traileImage)
        SimpleDraweeView traileImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
