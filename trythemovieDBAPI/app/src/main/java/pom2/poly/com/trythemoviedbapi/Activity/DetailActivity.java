package pom2.poly.com.trythemoviedbapi.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import pom2.poly.com.trythemoviedbapi.Fragment.DetailFragment;
import pom2.poly.com.trythemoviedbapi.R;

public class DetailActivity extends AppCompatActivity {

    private static int CURSORLOADER_ID;

    ImageButton imb1;
    private String m_id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (savedInstanceState == null) {
            fragmentTransaction.add(R.id.frame_layout_detail, new DetailFragment());
            fragmentTransaction.commit();
        }


    }


}
