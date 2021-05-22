package edu.lewisu.cs.burzlaff.movierating;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MovieRatingDao {
    @Insert
    void insertRating(MovieRating... ratings);

    @Update
    void updateRating(MovieRating... ratings);

    @Query("SELECT * FROM MovieRating WHERE id = :id")
    MovieRating getRating(int id);

    @Query("SELECT * FROM MovieRating ORDER BY movieTitle, directorName")
    List<MovieRating> getAllRatings();

    @Query("DELETE FROM MovieRating WHERE id = :id")
    void deleteRating(int id);
}
