package pom2.poly.com.trythemoviedbapi;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pom2.poly.com.trythemoviedbapi.Sqlite.MovieDbContract;

/**
 * Created by User on 26/1/2016.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyHolder> {
    private static MyClickListener myClickListener;
    Cursor mcursor;
    Context mcontext;
    private double cellWidth;

    public MyRecyclerViewAdapter(Cursor cursor, Context context) {
        this.mcursor = cursor;
        this.mcontext = context;
    }

    public double getCellWidth() {
        return cellWidth;
    }

    public void setCellWidth(double cellWidth) {
        this.cellWidth = cellWidth;
    }

    public Cursor getCursor() {
        return mcursor;
    }

    public void swapCursor(Cursor d) {

        this.mcursor = d;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View root = inflater.inflate(R.layout.recyclerview_item, parent, false);

        return new MyHolder(root);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        if(!mcursor.isClosed()){
            mcursor.moveToPosition(position);
            ImageView imv = holder.iv1;

            imv.getLayoutParams().width= (int) cellWidth;
            imv.getLayoutParams().height= (int) (cellWidth*3/2);

            Picasso picasso = Picasso.with(mcontext);
            //picasso.setLoggingEnabled(true);
            picasso.load(mcursor.getString(mcursor.getColumnIndex(MovieDbContract.MovieEntry.COLUMN_POSTER_PATH))).into(imv);
        }


    }

    @Override
    public int getItemCount() {
        return mcursor.getCount();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv1;

        public MyHolder(View itemView) {
            super(itemView);
            iv1 = (ImageView) itemView.findViewById(R.id.iv1_rey);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getPosition(), v);
        }
    }
}
