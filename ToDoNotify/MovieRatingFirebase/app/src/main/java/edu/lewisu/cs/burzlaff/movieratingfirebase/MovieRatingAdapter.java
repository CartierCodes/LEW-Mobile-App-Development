package edu.lewisu.cs.burzlaff.movieratingfirebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class MovieRatingAdapter extends FirebaseRecyclerAdapter<MovieRating, MovieRatingAdapter.MovieRatingHolder> {
    private final MovieRatingAdapterOnClick clickHandler;

    public MovieRatingAdapter(@NonNull FirebaseRecyclerOptions<MovieRating> options, MovieRatingAdapterOnClick handler) {
        super(options);
        clickHandler = handler;
    }

    public interface MovieRatingAdapterOnClick { void onClick(int p); }

    @NonNull
    @Override
    public MovieRatingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MovieRatingHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull MovieRatingHolder holder, int position, @NonNull MovieRating model) {
        holder.movieTitleTextView.setText(model.getMovieTitle());
    }


    class MovieRatingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView movieTitleTextView;

        MovieRatingHolder(View itemView) {
            super(itemView);
            movieTitleTextView = itemView.findViewById(R.id.movie_title_text_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int aPosition = getAdapterPosition();
            clickHandler.onClick(aPosition);
        }
    }

}
