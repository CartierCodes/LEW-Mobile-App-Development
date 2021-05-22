package edu.lewisu.cs.burzlaff.movierating;

import android.app.Application;

import java.util.List;

public class MovieRatingRepository {
    private MovieRatingDao mMovieRatingDao;

    public MovieRatingRepository(Application application){
        MovieRatingDatabase database = MovieRatingDatabase.getInstance(application);
        mMovieRatingDao = database.courseRatingDao();
    }

    List<MovieRating> getAllRatings(){
        return mMovieRatingDao.getAllRatings();
    }

    MovieRating getRating(int id){
        return mMovieRatingDao.getRating(id);
    }

    void insertRating(MovieRating rating){
        mMovieRatingDao.insertRating(rating);
    }

    void updateRating(MovieRating rating){
        mMovieRatingDao.updateRating(rating);
    }

    void deleteRating(int id) {
        mMovieRatingDao.deleteRating(id);
    }
}