package pom2.poly.com.trythemoviedbapi;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

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

        imb1.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

    }
}
