package edu.lewisu.cs.burzlaff.movieratingfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingActivity extends AppCompatActivity {
    private static final String TAG = "RatingActivity";

    private MovieRating mMovieRating;
    private EditText mMovieTitleEditText;
    private EditText mDirectorNameEditText;
    private Switch mViewedInTheaterSwitch;
    private RatingBar mMovieRatingBar;
    private Button mButton;
    private String uid;
    private String reference;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        System.out.println("\n\n\n\n\n\n creating");
        Log.d("rating", "\n\n\n\n\n done");
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

        uid = getIntent().getStringExtra("uid");
        mMovieRating = new MovieRating(uid);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        reference = getIntent().getStringExtra("reference");

        if (reference != null) {
            mDatabaseReference = mFirebaseDatabase.getReference().child("movie_rating").child(reference);
            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mMovieRating = snapshot.getValue(MovieRating.class);
                    setData();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        } else {
            mButton.setOnClickListener(new addButtonClick());
            mFirebaseDatabase = FirebaseDatabase.getInstance();
            mDatabaseReference = mFirebaseDatabase.getReference("movie_rating");
        }
    }

    private void setData() {
        if(mMovieRating != null) {
            mMovieTitleEditText.setText(mMovieRating.getMovieTitle());
            mDirectorNameEditText.setText(mMovieRating.getDirectorName());
            mMovieRatingBar.setRating(mMovieRating.getRating());
            mViewedInTheaterSwitch.setChecked(mMovieRating.getViewedAtTheater());

            mButton.setText(R.string.update);
            mButton.setOnClickListener(new updateButtonClick());
        }
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

            mDatabaseReference.push().setValue(mMovieRating);

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

    private class addButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mDatabaseReference.push().setValue(mMovieRating);
            finish();
        }
    }

    private class updateButtonClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            mDatabaseReference.setValue(mMovieRating);
            finish();
        }
    }
}