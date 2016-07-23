package pom2.poly.com.trythemoviedbapi.TrailerRecycleView;

import android.content.Context;
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

    private Context mContext;
    private List<Result> result;
    private OnItemClickListener listener;

    public RecycleTrailerAdapter(Context context) {
        mContext = context;
    }

    public RecycleTrailerAdapter(List<Result> result, Context context) {
        this.result = result;
    }

    public void setIteamClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Result trailer = result.get(position);
        String imguri = String.format(mContext.getResources().getString(R.string.youtubeTrailer_link), trailer.getKey() + "");
        final String key = trailer.getKey();

        Log.d(this.getClass().getSimpleName(), imguri);

        holder.traileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position, key);
            }
        });
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

    public interface OnItemClickListener {
        void onItemClick(int position, String trailerKey);
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
