package edu.lewisu.cs.burzlaff.movierating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.tv.TvView;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private MovieRatingRepository repository;

    private MovieRating mMovieRating;
    private EditText mMovieTitleEditText;
    private EditText mDirectorNameEditText;
    private Switch mViewedInTheaterSwitch;
    private RatingBar mMovieRatingBar;
    private Button mButton;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new MovieRatingRepository(getApplication());

        mMovieRating = new MovieRating();

        mMovieTitleEditText = findViewById(R.id.movieTitleEditText);
        mMovieTitleEditText.addTextChangedListener(new NameTextListener(mMovieTitleEditText));

        mDirectorNameEditText = findViewById(R.id.directorNameEditText);
        mDirectorNameEditText.addTextChangedListener(new NameTextListener(mDirectorNameEditText));

        mViewedInTheaterSwitch = findViewById(R.id.viewedInTheaterSwitch);
        mViewedInTheaterSwitch.setOnCheckedChangeListener(new SwitchToggleListener());

        mMovieRatingBar = findViewById(R.id.movieRatingBar);
        mMovieRatingBar.setOnRatingBarChangeListener(new RatingChangedListener());

        mButton = findViewById(R.id.submitButton);
        mButton.setOnClickListener(new SubmitClickListener());

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
    }

    public void saveButtonClicked(View v) {
        //Toast.makeText(getApplicationContext(), mMovieRating.toString(), Toast.LENGTH_SHORT).show();
    }

    private class NameTextListener implements TextWatcher {
        private View mEditText;

        public NameTextListener(View mEditText) {
            this.mEditText = mEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(mEditText == mMovieTitleEditText) {
                mMovieRating.setMovieTitle(s.toString());
            }
            else if (mEditText == mDirectorNameEditText) {
                mMovieRating.setDirectorName(s.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    private class SubmitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String movieTitle = mMovieRating.getMovieTitle();
            String directorName = mMovieRating.getDirectorName();
            int rating = mMovieRating.getRating();
            boolean viewedInTheater = mViewedInTheaterSwitch.isChecked();

            repository.insertRating(mMovieRating);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("movieTitle", movieTitle);
            returnIntent.putExtra("directorName", directorName);
            returnIntent.putExtra("rating", rating);
            setResult(RESULT_OK, returnIntent);
            finish();
        }
    }

    private class SwitchToggleListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mMovieRating.setViewedAtTheater(true);
            }
            else {
                mMovieRating.setViewedAtTheater(false);
            }
        }
    }

    private class RatingChangedListener implements RatingBar.OnRatingBarChangeListener {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            int newRating = (int)rating;
            //Toast.makeText(getApplicationContext(), String.valueOf(newRating), Toast.LENGTH_SHORT).show();
            mMovieRating.setRating(newRating);
        }
    }
}