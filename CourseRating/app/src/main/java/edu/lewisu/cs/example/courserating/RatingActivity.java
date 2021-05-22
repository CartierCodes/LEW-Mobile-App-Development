package edu.lewisu.cs.example.courserating;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class RatingActivity extends AppCompatActivity {

    private CourseRating courseRating;
    private EditText courseNameEditText;
    private EditText instructorNameEditText;
    private Spinner courseTypeSpinner;
    private RatingBar ratingBar;
    private Button submitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        courseRating = new CourseRating();

        courseNameEditText = findViewById(R.id.courseNameEditText);
        instructorNameEditText = findViewById(R.id.instructorNameEditText);
        courseNameEditText.addTextChangedListener(new NameTextListener(courseNameEditText));
        instructorNameEditText.addTextChangedListener(new NameTextListener(instructorNameEditText));

        courseTypeSpinner = findViewById(R.id.courseTypeSpinner);
        courseTypeSpinner.setOnItemSelectedListener(new CourseTypeSelectedListener());

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingChangedListener());

        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new SubmitClickListener());

    }


    private class NameTextListener implements TextWatcher {
        private View editText;

        public NameTextListener(View v){
            editText = v;
        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(editText == courseNameEditText) {
                courseRating.setCourseName(charSequence.toString());
            }else if(editText == instructorNameEditText) {
                courseRating.setInstructorName(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class CourseTypeSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String courseType = (String)adapterView.getItemAtPosition(i);
            if (i!=0) {
                courseRating.setCourseType(courseType);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    private class RatingChangedListener implements RatingBar.OnRatingBarChangeListener{
        @Override
        public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
            courseRating.setRating((int)v);
        }
    }

    private class SubmitClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {

            String courseName = courseRating.getCourseName();
            int rating = courseRating.getRating();

            Intent returnIntent = new Intent();
            returnIntent.putExtra("courseName", courseName);
            returnIntent.putExtra("rating", rating);
            setResult(RESULT_OK, returnIntent);
            finish();

        }
    }
}
