package edu.lewisu.cs.burzlaff.movierating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DetailActivity extends AppCompatActivity {
    public static final String EXTRA_MOVIE_RATING_ID = "movieRatingId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        int movieRatingId = getIntent().getIntExtra(EXTRA_MOVIE_RATING_ID, 1);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.detail_container);
        if(fragment == null){
            fragment = DetailFragment.newInstance(movieRatingId);
            fragmentManager.beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}