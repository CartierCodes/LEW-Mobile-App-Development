package edu.lewisu.cs.burzlaff.movierating;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {
    private MovieRating mMovieRating;
    private static final String ARG_MOVIE_RATING_ID = "movieRatingId";
    private MovieRatingRepository repository;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(int movieRatingId) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE_RATING_ID, movieRatingId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        repository = new MovieRatingRepository(getActivity().getApplication());
        int movieRatingId=1;
        if(getArguments() != null){
            movieRatingId = getArguments().getInt(ARG_MOVIE_RATING_ID);
        }
        MovieRatingDatabase movieRatingDatabase = MovieRatingDatabase.getInstance(getContext());
        mMovieRating = repository.getRating(movieRatingId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        EditText movieTitle = view.findViewById(R.id.title_field);
        EditText directorName = view.findViewById(R.id.director_field);
        CheckBox ratingBox = view.findViewById(R.id.theater_checkbox);
        Spinner ratingSpinner = view.findViewById(R.id.rating_spinner);

        movieTitle.setText(mMovieRating.getMovieTitle());
        directorName.setText(mMovieRating.getDirectorName());
        ratingBox.setChecked(mMovieRating.getViewedAtTheater());
        ratingSpinner.setSelection(mMovieRating.getRating()-1);

        Button updateButton = view.findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMovieRating.setMovieTitle(movieTitle.getText().toString());
                mMovieRating.setDirectorName(directorName.getText().toString());
                mMovieRating.setViewedAtTheater(ratingBox.isChecked());
                mMovieRating.setRating(Integer.parseInt(ratingSpinner.getSelectedItem().toString()));
                repository.updateRating(mMovieRating);
                getActivity().finish();
            }
        });
        Button deleteButton = view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repository.deleteRating(mMovieRating.getId());
                getActivity().finish();
            }
        });

        return view;
    }
}