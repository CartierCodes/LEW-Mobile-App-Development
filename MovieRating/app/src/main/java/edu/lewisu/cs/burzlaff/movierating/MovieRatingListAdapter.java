package edu.lewisu.cs.burzlaff.movierating;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MovieRatingListAdapter extends RecyclerView.Adapter<MovieRatingListAdapter.MovieRatingHolder>{
    private List<MovieRating> mMovieRatings;

    public interface MovieRatingListAdapterOnClickHandler{
        void onClick(MovieRating movieRating);
    }

    private final MovieRatingListAdapterOnClickHandler mClickHandler;

    public MovieRatingListAdapter(MovieRatingListAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public MovieRatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MovieRatingHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieRatingHolder holder, int position) {
        if(mMovieRatings !=null){
            MovieRating current = mMovieRatings.get(position);
            holder.mTitleTestView.setText(current.getMovieTitle());
        }
    }

    @Override
    public int getItemCount() {
        if (mMovieRatings !=null)
            return mMovieRatings.size();
        return 0;
    }

    void setMovieRatings(List<MovieRating> ratings) {
        mMovieRatings = ratings;
        notifyDataSetChanged();
    }

    class MovieRatingHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mTitleTestView;

        public MovieRatingHolder(@NonNull View itemView) {
            super(itemView);
            mTitleTestView = itemView.findViewById(R.id.movieTitleTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieRating movieRating = mMovieRatings.get(adapterPosition);
            mClickHandler.onClick(movieRating);
        }
    }
}
