package edu.lewisu.cs.burzlaff.movierating;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieRating.class}, version = 1, exportSchema = false)
abstract public class MovieRatingDatabase extends RoomDatabase {

    public abstract MovieRatingDao courseRatingDao();

    private static MovieRatingDatabase sMovieRatingDatabase;

    static MovieRatingDatabase getInstance(final Context context) {
        if(sMovieRatingDatabase == null) {
            synchronized (MovieRatingDatabase.class) {
                sMovieRatingDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        MovieRatingDatabase.class,
                        "rating_db")
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sMovieRatingDatabase;
    }
}